package com.moneygang.finfarm.domain.banking.entity;

import com.moneygang.finfarm.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity(name = "account_TB")
@Getter
//@Table(name = "account_TB")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_pk")
    private Long accountPk;

    @Column(name = "account_amount")
    private String accountAmount;

    @Column(name = "account_date")
    private LocalDateTime accountDate;

    @Column(name = "account_type")
    private String accountType; // 입금, 출금, 송금

    @Column(name = "account_nickname")
    private String accountNickname;

    @Column(name = "account_balance")
    private Long accountBalance;

    @ManyToOne
    @JoinColumn(name = "member_pk")
    private Member member;


    public void setMember(Member member) {
        this.member = member;
        member.getAccountList().add(this);
    }
}
