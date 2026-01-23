package com.zezame.timasi.service.unit;

import com.zezame.timasi.constant.RoleCode;
import com.zezame.timasi.constant.TicketStatusCode;
import com.zezame.timasi.dto.ticket.CreateTicketRequestDTO;
import com.zezame.timasi.model.company.AssigneeCustomer;
import com.zezame.timasi.model.company.Role;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.model.ticket.Ticket;
import com.zezame.timasi.model.ticket.TicketHistory;
import com.zezame.timasi.model.ticket.TicketStatus;
import com.zezame.timasi.pojo.AuthorizationPojo;
import com.zezame.timasi.repository.*;
import com.zezame.timasi.service.PrincipalService;
import com.zezame.timasi.service.impl.TicketServiceImpl;
import com.zezame.timasi.util.EmailUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TicketService {
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketHistoryRepository ticketHistoryRepository;

    @Mock
    private AssigneeCustomerRepository assigneeCustomerRepository;

    @Mock
    private TicketStatusRepository ticketStatusRepository;

    @Mock
    private TicketMessageRepository ticketMessageRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private PrincipalService principalService;

    @Test
    public void shouldCreateTicket_whenDataValid() {
        UUID customerId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();

        var request = new CreateTicketRequestDTO();
        request.setTitle("Test Ticket");
        request.setDescription("Test Description");

        ReflectionTestUtils.setField(
                ticketService,
                "principalService",
                principalService
        );

        var principal = Mockito.mock(AuthorizationPojo.class);
        Mockito.when(principalService.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.getId()).thenReturn(customerId.toString());

        var customer = new User();
        customer.setId(customerId);

        Mockito.when(userRepository.findByIdAndRoleCode(
                        customerId, RoleCode.CUST.name()))
                .thenReturn(Optional.of(customer));

        var ticketStatus = new TicketStatus();
        ticketStatus.setCode(TicketStatusCode.OPEN.name());

        Mockito.when(ticketStatusRepository.findByCode(
                        TicketStatusCode.OPEN.name()))
                .thenReturn(Optional.of(ticketStatus));

        var assignee = new User();
        var assigneeCustomer = new AssigneeCustomer();
        assigneeCustomer.setAssignee(assignee);

        Mockito.when(assigneeCustomerRepository.findByCustomer(customer))
                .thenReturn(Optional.of(assigneeCustomer));

        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class)))
                .thenAnswer(invocation -> {
                    Ticket ticket = invocation.getArgument(0);
                    ticket.setId(ticketId);
                    return ticket;
                });

        var superAdmin = new User();

        Mockito.when(userRepository.findByRoleCode(RoleCode.SA.name()))
                .thenReturn(Optional.of(superAdmin));

        var result = ticketService.createTicket(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ticketId, result.getId());

        Mockito.verify(ticketRepository, Mockito.times(1))
                .save(Mockito.any(Ticket.class));
        Mockito.verify(ticketHistoryRepository, Mockito.times(1))
                .save(Mockito.any(TicketHistory.class));
    }

    @Test
    public void shoudldReturnAll() {
        UUID ticketId = UUID.randomUUID();

        var customer = new User();
        customer.setFullName("Customer Test");

        var assignee = new User();
        assignee.setFullName("Assignee Test");

        var ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setTitle("Test Ticket");
        ticket.setDescription("Test Desc");
        ticket.setCustomer(customer);
        ticket.setAssignee(assignee);

        List<Ticket> tickets = List.of(ticket);

        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        var result = ticketService.getTickets();

        Assertions.assertEquals(tickets.size(), result.size());
        Assertions.assertEquals(tickets.get(0).getId(), result.get(0).getId());

        Mockito.verify(ticketRepository, Mockito.times(1)).findAll();
    }
}
