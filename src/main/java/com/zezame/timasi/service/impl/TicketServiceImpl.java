package com.zezame.timasi.service.impl;

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
import com.zezame.timasi.repository.*;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl extends BaseService implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final AssigneeCustomerRepository assigneeCustomerRepository;
    private final TicketStatusRepository ticketStatusRepository;
    private final TicketMessageRepository ticketMessageRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository, TicketHistoryRepository ticketHistoryRepository, AssigneeCustomerRepository assigneeCustomerRepository, TicketStatusRepository ticketStatusRepository, TicketMessageRepository ticketMessageRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.assigneeCustomerRepository = assigneeCustomerRepository;
        this.ticketStatusRepository = ticketStatusRepository;
        this.ticketMessageRepository = ticketMessageRepository;
    }

    @Override
    public List<TicketResponseDTO> getTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketResponseDTO> response = tickets.stream()
                .map(this::mapToDto)
                .toList();
        return response;
    }

    @Override
    public TicketResponseDTO getTicket(String id) {
        Ticket ticket = findTicketById(id);
        TicketResponseDTO response = mapToDto(ticket);
        return response;
    }

    private TicketResponseDTO mapToDto(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO(
                ticket.getId(), ticket.getCode(), ticket.getTitle(),
                ticket.getDescription(), ticket.getCustomer().getFullName(),
                ticket.getAssignee().getFullName());

        return dto;
    }

    @Override
    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO request) {
        var customerId = convertToUUID(principalService.getPrincipal().getId());
        var customer = userRepository.findByIdAndRoleCode(customerId, RoleCode.CUST.name())
                .orElseThrow(() -> new ForbiddenException("Request Forbidden"));

        var ticketStatus = ticketStatusRepository.findByCode(TicketStatusCode.OPEN.name())
                .orElseThrow(() -> new NotFoundException("Ticket Status Not Found"));

        var assignee = getCustomerAssignee(customer);

        var ticket = new Ticket();
        ticket.setCode(generateRandomAlphaNumeric());
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCustomer(customer);
        ticket.setAssignee(assignee);
        ticket.setTicketStatus(ticketStatus);

        var savedTicket = ticketRepository.save(prepareCreate(ticket));
        createTicketHistory(savedTicket, ticketStatus);
        return new CreateTicketResponseDTO(savedTicket.getId(), savedTicket.getCode(), Message.CREATED.getName());
    }

    private User getCustomerAssignee(User customer) {
        var assigneeCustomer = assigneeCustomerRepository.findByCustomer(customer)
                .orElseThrow(() -> new NotFoundException("This Customer Has No Assignee"));

        return assigneeCustomer.getAssignee();
    }

    @Override
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

    private void createTicketHistory(Ticket ticket, TicketStatus ticketStatus) {
        var ticketHistory = new TicketHistory();
        ticketHistory.setTicket(ticket);
        ticketHistory.setTicketStatus(ticketStatus);
        ticketHistoryRepository.save(prepareCreate(ticketHistory));
    }

    @Override
    public List<TicketMessageResponseDTO> getTicketMessages(String id) {
        var ticket = findTicketById(id);
        List<TicketMessage> messages = ticketMessageRepository.findAllByTicket(ticket);
        return List.of();
    }

    @Override
    public TicketMessageResponseDTO getTicketMessage(String ticketId, String id) {
        return null;
    }

    @Override
    public CreateResponseDTO createTicketMesage(String ticketId, CreateTicketMessageRequestDTO request) {
        return null;
    }

    @Override
    public UpdateResponseDTO updateTicketMesage(String ticketId, String id, UpdateTicketMessageRequestDTO request) {
        return null;
    }

    private Ticket findTicketById(String id) {
        UUID ticketId = convertToUUID(id);
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket Not Found"));
        return ticket;
    }
}
