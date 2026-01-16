package com.zezame.timasi.controller;

import com.zezame.timasi.dto.ticket.TicketStatusResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ticket-status")
public class TicketStatusController {
    @GetMapping
    public ResponseEntity<TicketStatusResponseDTO> getTickets() {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketStatusResponseDTO> getTicket(@PathVariable String id) {
        return null;
    }
}
