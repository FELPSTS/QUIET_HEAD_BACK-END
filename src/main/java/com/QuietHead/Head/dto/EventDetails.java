package com.QuietHead.Head.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EventDetails {
    @NotBlank(message = "Nome do evento é obrigatório")
    private String title;

    @NotBlank(message = "Local do evento é obrigatório")
    private String local;

    @NotNull(message = "Data do evento é obrigatória")
    @FutureOrPresent(message = "Data deve ser presente ou futura")
    private LocalDate data;

    private String description;
    private String imageUrl;
}
