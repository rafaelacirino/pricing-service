package com.cirino.rafaela.inditex.pricingservice.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
