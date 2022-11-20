package com.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card extends BaseEntity {

    private Integer pin;
    private String number;
    private BigDecimal balance;
    private Status status;
    private LocalDateTime dateUnblocked;

}
