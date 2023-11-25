package com.Projekt.Bankomat.Models;

import com.Projekt.Bankomat.Enums.CurrencyType;
import com.Projekt.Bankomat.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "_TRANSACTION")
public class Transaction {
    @Id
    @UuidGenerator
    @Column(name = "transactionId", nullable = false)
    private String transactionId;

    @Column(name = "transactionType", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "isValid")
    private boolean isValid;

    @Column(name = "transactionDate", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currencyType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(name = "title")
    private String title;

    @Column(name = "fromAccountNr",nullable = false)
    private String fromAccountNr;

    @Column(name = "toAccountNr", nullable = false)
    private String toAccountNr;

    @Override
    public String toString() {
        return transactionId + "," +
                transactionType + "," +
                isValid + "," +
                transactionDate + "," +
                amount + "," +
                currencyType + "," +
                title + "," +
                fromAccountNr + "," +
                toAccountNr +",";
    }
}
