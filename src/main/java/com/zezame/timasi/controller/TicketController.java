package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.ticket.CreateTicketRequestDTO;
import com.zezame.timasi.dto.ticket.TicketMessageResponseDTO;
import com.zezame.timasi.dto.ticket.TicketResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tickets")
public class TicketController {
    @GetMapping
    public ResponseEntity<TicketResponseDTO> getTickets() {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponseDTO> getTicket(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createTicket(@Valid @RequestBody CreateTicketRequestDTO request) {
        return null;
    }

    @PatchMapping("{id}/{codeStatus}")
    public ResponseEntity<UpdateResponseDTO> updateTicket(@PathVariable String id,
                                                          @PathVariable String codeStatus) {
        return null;
    }

    @GetMapping("{ticketId}/messages")
    public ResponseEntity<TicketMessageResponseDTO> getTicketMessages(@PathVariable String ticketId) {
        return null;
    }

    @GetMapping("{ticketId}/messages/{id}")
    public ResponseEntity<TicketMessageResponseDTO> getTicketMessage(@PathVariable String ticketId,
                                                                     @PathVariable String id) {
        return null;
    }

    @PostMapping("{ticketId}/messages")
    public ResponseEntity<CreateResponseDTO> createTicketMessage(@PathVariable String ticketId) {
        return null;
    }

    @PatchMapping("{ticketId}/messages/{id}")
    public ResponseEntity<UpdateResponseDTO> updateTicketResponse(@PathVariable String ticketId,
                                                                  @PathVariable String id) {
        return null;
    }
}
