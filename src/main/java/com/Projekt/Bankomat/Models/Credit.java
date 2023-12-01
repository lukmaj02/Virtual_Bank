package com.Projekt.Bankomat.Models;

import com.Projekt.Bankomat.Enums.CreditStatus;
import com.Projekt.Bankomat.Enums.CreditType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CREDIT")
public class Credit {
    @Id
    @UuidGenerator
    @Column(name = "creditId", nullable = false)
    private String creditId;

    @Column(name = "installmentCount", nullable = false)
    private Integer installmentCount;

    @Column(name = "lendingRate", nullable = false)
    private BigDecimal lendingRate;

    @Column(name = "creditAmount", nullable = false)
    private BigDecimal creditAmount;

    @Column(name = "installmentAmount", nullable = false)
    private BigDecimal installmentAmount;

    @Column(name = "creditType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Column(name = "creditStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;


    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private BankAccount bankAccountCredit;

    @Override
    public String toString() {
        return creditId + "," +
                installmentCount + "," +
                lendingRate + "," +
                creditAmount + "," +
                installmentAmount + "," +
                creditType + ",";
    }
}
