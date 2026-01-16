package com.zezame.timasi.controller;

import com.zezame.timasi.dto.ticket.TicketHistoryResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket-histories")
public class TicketHistoryController {
    @GetMapping
    public ResponseEntity<TicketHistoryResponseDTO> getTicketHistories(
            @RequestParam(required = false) String customerId) {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketHistoryResponseDTO> getTicketHistory(@PathVariable String id) {
        return null;
    }
}
