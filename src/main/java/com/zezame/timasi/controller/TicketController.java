package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.ticket.*;
import com.zezame.timasi.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getTickets() {
        List<TicketResponseDTO> responses = ticketService.getTickets();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponseDTO> getTicket(@PathVariable String id) {
        TicketResponseDTO ticketResponseDTO = ticketService.getTicket(id);
        return new ResponseEntity<>(ticketResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateTicketResponseDTO> createTicket(@Valid @RequestBody CreateTicketRequestDTO request) {
        CreateTicketResponseDTO createTicketResponseDTO = ticketService.createTicket(request);
        return new ResponseEntity<>(createTicketResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("{id}/{statusCode}")
    public ResponseEntity<UpdateResponseDTO> updateTicket(@PathVariable String id,
                                                          @PathVariable String statusCode) {
        UpdateResponseDTO response = ticketService.updateTicket(id, statusCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{ticketId}/messages")
    public ResponseEntity<List<TicketMessageResponseDTO>> getTicketMessages(@PathVariable String ticketId) {
        List<TicketMessageResponseDTO> responses = ticketService.getTicketMessages(ticketId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{ticketId}/messages/{id}")
    public ResponseEntity<TicketMessageResponseDTO> getTicketMessage(@PathVariable String id) {
        TicketMessageResponseDTO response = ticketService.getTicketMessage(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("{ticketId}/messages")
    public ResponseEntity<CreateResponseDTO> createTicketMessage(@PathVariable String ticketId,
                                                                 @Valid @RequestBody CreateTicketMessageRequestDTO request) {
        CreateResponseDTO responseDTO = ticketService.createTicketMesage(ticketId, request);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("{ticketId}/messages/{id}")
    public ResponseEntity<UpdateResponseDTO> updateTicketMessage(@PathVariable String id,
                                                                 @Valid @RequestBody UpdateTicketMessageRequestDTO request) {
        UpdateResponseDTO response = ticketService.updateTicketMesage(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
