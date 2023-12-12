package com.Projekt.Bankomat.Exceptions;

public class PersonalBankAccountNotFoundException extends RuntimeException{
    public PersonalBankAccountNotFoundException(String phoneNumber){
        super("Personal Account for number " + phoneNumber +" not found!");
    }
}
