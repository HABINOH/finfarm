package com.moneygang.finfarm.domain.banking.service;

import com.moneygang.finfarm.domain.banking.dto.response.BankingAccountDepositResponse;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    // 계좌 입금
    public ResponseEntity<BankingAccountDepositResponse> deposit(long userId, long amount);

    // 계좌 송금
    public void withdraw(long userId, int accountPassword, long amount);

    // 계좌 이체 (다른 사람에게)
    public void remit(long userId, int accountPassword, String otherNickname, long amount);
}
