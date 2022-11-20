package com.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"cards"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atm extends BaseEntity {

    private List<Card> cards = new ArrayList<>();

}
