package com.Projekt.Bankomat.Service;

import com.Projekt.Bankomat.Enums.CardType;
import com.Projekt.Bankomat.Enums.TransactionType;
import com.Projekt.Bankomat.Exceptions.CardNotFoundException;
import com.Projekt.Bankomat.Generators;
import com.Projekt.Bankomat.Models.BankAccount;
import com.Projekt.Bankomat.Models.Card;
import com.Projekt.Bankomat.Repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CardService {
    private final CardRepo cardRepo;
    private final TransactionService transactionService;
    @Autowired
    public CardService(CardRepo cardRepo, TransactionService transactionService) {
        this.cardRepo = cardRepo;
        this.transactionService = transactionService;
    }

    public void createCard(BankAccount bankAccount, CardType cardType){
        var nowaKarta = Card.builder()
                .cvc(Generators.generateRandomNumber(3))
                .expirationDate(LocalDate.now().plusYears(4))
                .cardNr(Generators.generateRandomNumber(16))
                .cardType(cardType)
                .cardId(UUID.randomUUID().toString())
                .isDiscard(false)
                .bankAccount(bankAccount)
                .build();
        cardRepo.save(nowaKarta);
    }

    public void discardCard(String nrKarty){
        var karta = cardRepo.findByCardNr(nrKarty).orElseThrow(() -> new CardNotFoundException(nrKarty));
        karta.setDiscard(true);
        cardRepo.save(karta);
    }

    public void deleteCard(String nrKarty){
        var karta = cardRepo.findByCardNr(nrKarty).orElseThrow(() -> new CardNotFoundException(nrKarty));
        cardRepo.delete(karta);
    }

    public void extendExpirationDate(String nrKarty){
        var karta = cardRepo.findByCardNr(nrKarty)
                .orElseThrow(() -> new CardNotFoundException(nrKarty));
        karta.setExpirationDate(karta.getExpirationDate().plusYears(3));
    }

    public void paymentByCard(String doNrKonta,
                              String zNrKarty,
                              String cvc,
                              String imieNadawcy,
                              String nazwiskoNadawcy,
                              BigDecimal kwota){
        var kartaNadawcy = cardRepo
                .findUserCard(zNrKarty,cvc,imieNadawcy,nazwiskoNadawcy)
                .orElseThrow(() -> new CardNotFoundException(zNrKarty));
        var kontoKarty = kartaNadawcy.getBankAccount();

        transactionService.createTransaction(
                kontoKarty.getAccountNr(),
                doNrKonta,
                kwota,
                "Platnosc karta",
                kontoKarty.getCurrencyType(),
                TransactionType.PRZELEW_KARTA
                );
    }
}