package com.QuietHead.Head.dto;

import lombok.Data;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
public class CreateEventDTO {
    @NotBlank
    private String name;
    
    @NotBlank
    private String local;
    
    @FutureOrPresent
    private LocalDate data;
    
    @NotNull
    private Long administradorId;

    public String getDescription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }

    public String getImageUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImageUrl'");
    }
}