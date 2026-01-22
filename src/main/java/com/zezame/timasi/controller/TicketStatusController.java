package com.zezame.timasi.controller;

import com.zezame.timasi.dto.ticket.TicketStatusResponseDTO;
import com.zezame.timasi.service.TicketStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ticket-status")
public class TicketStatusController {
    private final TicketStatusService ticketStatusService;

    public TicketStatusController(TicketStatusService ticketStatusService) {
        this.ticketStatusService = ticketStatusService;
    }

    @GetMapping
    public ResponseEntity<List<TicketStatusResponseDTO>> getTickets() {
        List<TicketStatusResponseDTO> responses = ticketStatusService.getTicketStatus();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketStatusResponseDTO> getTicket(@PathVariable String id) {
        TicketStatusResponseDTO response = ticketStatusService.getTicketStatus(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
