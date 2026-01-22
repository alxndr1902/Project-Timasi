package com.zezame.timasi.service.impl;

import com.zezame.timasi.config.RabbitMQConfig;
import com.zezame.timasi.constant.Message;
import com.zezame.timasi.constant.RoleCode;
import com.zezame.timasi.constant.TicketStatusCode;
import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.ticket.*;
import com.zezame.timasi.exceptiohandler.exception.ForbiddenException;
import com.zezame.timasi.exceptiohandler.exception.NotAllowedException;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.model.ticket.Ticket;
import com.zezame.timasi.model.ticket.TicketHistory;
import com.zezame.timasi.model.ticket.TicketMessage;
import com.zezame.timasi.model.ticket.TicketStatus;
import com.zezame.timasi.pojo.CreateTicketMailPojo;
import com.zezame.timasi.pojo.ReplyTicketMailPojo;
import com.zezame.timasi.repository.*;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.TicketService;
import com.zezame.timasi.util.EmailUtil;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl extends BaseService implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final AssigneeCustomerRepository assigneeCustomerRepository;
    private final TicketStatusRepository ticketStatusRepository;
    private final TicketMessageRepository ticketMessageRepository;
    private final RabbitTemplate rabbitTemplate;
    private final EmailUtil emailUtil;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository, TicketHistoryRepository ticketHistoryRepository, AssigneeCustomerRepository assigneeCustomerRepository, TicketStatusRepository ticketStatusRepository, TicketMessageRepository ticketMessageRepository, RabbitTemplate rabbitTemplate, EmailUtil emailUtil) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.assigneeCustomerRepository = assigneeCustomerRepository;
        this.ticketStatusRepository = ticketStatusRepository;
        this.ticketMessageRepository = ticketMessageRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.emailUtil = emailUtil;
    }

    @Override
    @Cacheable(value = "ticket", key = "'all'")
    public List<TicketResponseDTO> getTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketResponseDTO> response = tickets.stream()
                .map(this::mapToDto)
                .toList();
        return response;
    }

    @Override
    public TicketResponseDTO getTicket(String id) {
        var ticket = findTicketById(id);
        var response = mapToDto(ticket);
        return response;
    }

    private TicketResponseDTO mapToDto(Ticket ticket) {
        var dto = new TicketResponseDTO(
                ticket.getId(), ticket.getCode(), ticket.getTitle(),
                ticket.getDescription(), ticket.getCustomer().getFullName(),
                ticket.getAssignee().getFullName());

        return dto;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    @CacheEvict(value = "company", allEntries = true)
    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO request) {
        var customerId = convertToUUID(principalService.getPrincipal().getId());
        var customer = userRepository.findByIdAndRoleCode(customerId, RoleCode.CUST.name())
                .orElseThrow(() -> new ForbiddenException("Request Forbidden"));

        var ticketStatus = ticketStatusRepository.findByCode(TicketStatusCode.OPEN.name())
                .orElseThrow(() -> new NotFoundException("Ticket Status Not Found"));

        var assignee = getAssignee(customer);

        var ticket = new Ticket();
        ticket.setCode(generateRandomAlphaNumeric());
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCustomer(customer);
        ticket.setAssignee(assignee);
        ticket.setTicketStatus(ticketStatus);

        var savedTicket = ticketRepository.save(prepareCreate(ticket));
        createTicketHistory(savedTicket, ticketStatus);
        sendEmail(savedTicket.getCode());
        return new CreateTicketResponseDTO(savedTicket.getId(), savedTicket.getCode(), Message.CREATED.getName());
    }

    private User getAssignee(User customer) {
        var assigneeCustomer = assigneeCustomerRepository.findByCustomer(customer)
                .orElseThrow(() -> new NotFoundException("Customer Has No Assignee"));

        return assigneeCustomer.getAssignee();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    @CacheEvict(value = "ticket", allEntries = true)
    public UpdateResponseDTO updateTicket(String id, String statusCode) {
        var ticket = findTicketById(id);
        var ticketStatus = ticketStatusRepository.findByCode(statusCode)
                .orElseThrow(() -> new NotFoundException("Ticket Status Not Found"));

        if (ticketStatus.getCode().equals(TicketStatusCode.OPEN.name())) {
            throw new NotAllowedException("Request Is Not Allowed");
        }

        if (ticketStatus.getCode().equals(TicketStatusCode.CLOSED.name()) &&
                ticket.getTicketStatus().getCode().equals(TicketStatusCode.CLOSED.name())) {
            throw new NotAllowedException("Ticker Is Already Closed");
        }

        if (ticketStatus.getCode().equals(TicketStatusCode.REOPEN.name()) &&
                (ticket.getTicketStatus().getCode().equals(TicketStatusCode.OPEN.name())) &&
                ticket.getTicketStatus().getCode().equals(TicketStatusCode.REOPEN.name())) {
            throw new NotAllowedException("Ticker Is Already Open");
        }

        ticket.setTicketStatus(ticketStatus);
        var updatedTicket = ticketRepository.saveAndFlush(prepareUpdate(ticket));
        createTicketHistory(updatedTicket, ticketStatus);
        return new UpdateResponseDTO(updatedTicket.getId(), Message.UPDATED.getName(), updatedTicket.getVersion());
    }

    private void sendEmail(String code) {
        var superAdmin = userRepository.findByRoleCode(RoleCode.SA.name())
                .orElseThrow(() -> new NotFoundException("User Is Not Found"));
        var mailPojo = new CreateTicketMailPojo(superAdmin.getEmail(), code);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_QUEUE_CR,
                RabbitMQConfig.EMAIL_ROUTING_KEY_CR,
                mailPojo);
    }

    private void createTicketHistory(Ticket ticket, TicketStatus ticketStatus) {
        var ticketHistory = new TicketHistory();
        ticketHistory.setTicket(ticket);
        ticketHistory.setTicketStatus(ticketStatus);
        ticketHistoryRepository.save(prepareCreate(ticketHistory));
    }

    @Override
    public CreateResponseDTO createTicketMesage(String ticketId, CreateTicketMessageRequestDTO request) {
        var userId = convertToUUID(principalService.getPrincipal().getId());
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        var ticket = findTicketById(ticketId);

        if (!user.getId().equals(ticket.getCustomer().getId()) && !user.getId().equals(ticket.getAssignee().getId())) {
            throw new NotAllowedException("Request Not Allowed");
        }
        var ticketMessage = new TicketMessage();
        ticketMessage.setTicket(ticket);
        ticketMessage.setMessage(request.getTicketMessage());
        ticketMessage.setUser(user);
        var savedTicketMessage = ticketMessageRepository.save(prepareCreate(ticketMessage));
        sendReplyEmail(user.getEmail(),
                user.getRole().getCode().equals(RoleCode.PIC.name()) ? ticket.getCustomer().getEmail() : ticket.getAssignee().getEmail(),
                ticket.getCode());
        return new CreateResponseDTO(savedTicketMessage.getId(), Message.CREATED.getName());
    }

    private void sendReplyEmail(String from, String to, String code) {
        var mailPojo = new ReplyTicketMailPojo(from, to, code);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_QUEUE_UD,
                RabbitMQConfig.EMAIL_ROUTING_KEY_UD,
                mailPojo);
    }

    @Override
    public List<TicketMessageResponseDTO> getTicketMessages(String id) {
        var ticket = findTicketById(id);
        List<TicketMessage> messages = ticketMessageRepository.findAllByTicket(ticket);
        List<TicketMessageResponseDTO> responses = messages.stream()
                .map(this::mapToDto)
                .toList();
        return responses;
    }

    @Override
    public TicketMessageResponseDTO getTicketMessage(String id) {
        var ticketMessageId = convertToUUID(id);
        var ticketMessage = ticketMessageRepository.findById(ticketMessageId)
                .orElseThrow(() -> new NotFoundException("Message Not Found"));
        var response = mapToDto(ticketMessage);
        return response;
    }

    private TicketMessageResponseDTO mapToDto(TicketMessage message) {
        var dto = new TicketMessageResponseDTO(
                message.getId(), message.getMessage(), message.getUser().getFullName(),
                message.getUser().getRole().getName(), message.getCreatedAt(), message.getVersion());

        return dto;
    }

    @Override
    public UpdateResponseDTO updateTicketMesage(String id, UpdateTicketMessageRequestDTO request) {
        var messageId = convertToUUID(id);
        var ticketMessage = ticketMessageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("Message Not Found"));

        var userId = convertToUUID(principalService.getPrincipal().getId());
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        if (!ticketMessage.getUser().getId().equals(user.getId())) {
            throw new NotAllowedException("Request Not Allowed");
        }

        ticketMessage.setMessage(request.getMessage());
        var updatedMessage = ticketMessageRepository.saveAndFlush(prepareUpdate(ticketMessage));

        return new UpdateResponseDTO(updatedMessage.getId(), Message.UPDATED.getName(), updatedMessage.getVersion());
    }

    private Ticket findTicketById(String id) {
        var ticketId = convertToUUID(id);
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket Not Found"));
        return ticket;
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE_CR)
    public void receiveEmailNotificationCO(CreateTicketMailPojo data) {
        emailUtil.sendEmail(data.getEmail(),
                "New Ticket",
                "New Ticket Has Been Created " + data.getLoanCode());
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE_UD)
    public void receiveEmailNotificationCI(CreateTicketMailPojo data) {
        emailUtil.sendEmail(data.getEmail(),
                "New Reply From Ticket " + data.getLoanCode(),
                "You Have New Reply");
    }
}
