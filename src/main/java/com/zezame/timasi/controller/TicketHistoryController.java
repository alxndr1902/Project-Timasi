package com.zezame.timasi.controller;

import com.zezame.timasi.dto.ticket.TicketHistoryResponseDTO;
import com.zezame.timasi.service.TicketHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ticket-histories")
public class TicketHistoryController {
    private final TicketHistoryService ticketHistoryService;

    public TicketHistoryController(TicketHistoryService ticketHistoryService) {
        this.ticketHistoryService = ticketHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<TicketHistoryResponseDTO>> getTicketHistories(
            @RequestParam(required = false) String customerId) {
        List<TicketHistoryResponseDTO> responses = ticketHistoryService.getTicketHistoriesByCustomerId(customerId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketHistoryResponseDTO> getTicketHistory(@PathVariable String id) {
        TicketHistoryResponseDTO response = ticketHistoryService.getTicketHistoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
