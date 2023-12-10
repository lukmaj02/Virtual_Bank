package com.Projekt.Bankomat.IService;

import com.Projekt.Bankomat.Enums.CreditType;
import com.Projekt.Bankomat.Models.Credit;

import java.math.BigDecimal;
import java.util.List;

public interface ICreditService {
    void requestCredit(String accountNr,
                       BigDecimal creditAmount,
                       Integer installmentCount,
                       CreditType creditType);

    void activeCredit(String creditId);
    void refuseCredit(String creditId);
    void reactiveCredit(String creditId);
    void cancelCredit(String creditId);
    BigDecimal checkInstallmentRate(BigDecimal creditAmount,
                                    Integer installmentCount,
                                    CreditType creditType);
    List<Credit> getUserCredits(String email);
}
