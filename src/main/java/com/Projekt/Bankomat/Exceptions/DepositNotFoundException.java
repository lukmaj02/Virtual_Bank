package com.Projekt.Bankomat.Exceptions;

public class DepositNotFoundException extends RuntimeException{
    public DepositNotFoundException(){
        super("Deposit not found!");
    }
}
