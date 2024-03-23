package com.moneygang.finfarm.domain.banking.dto.request;

import lombok.Getter;

@Getter
public class BankingAccountRemitRequest {

    private Long otherUserPk;
    private Long amount;
    private Integer accountPassword;
}
