package com.Projekt.Bankomat.Exceptions;

public class TransactionIdNotFoundException extends RuntimeException{
    public TransactionIdNotFoundException(){
        super("Transaction did not found");
    }
}
