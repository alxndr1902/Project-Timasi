package com.zezame.timasi.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTicketRequestDTO {
    @NotBlank(message = "Ticket Title Is Required")
    @Size(max = 100, message = "Ticket Title Maximum Length Is 100 Characters")
    private String title;

    @NotBlank(message = "Ticket Description Is Required")
    @Size(max = 300, message = "Ticket Description Maximum Length Is 300 Characters")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
