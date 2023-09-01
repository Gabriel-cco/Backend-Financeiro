package com.picpaysimplificado.dtos;

import java.math.BigDecimal;

public record TransactionDTO(Long sender,
                             Long receiver,
                             BigDecimal amount) {
}
