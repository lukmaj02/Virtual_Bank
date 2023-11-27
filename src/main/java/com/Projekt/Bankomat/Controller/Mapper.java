package com.Projekt.Bankomat.Controller;

import com.Projekt.Bankomat.DtoModels.UserDto;
import com.Projekt.Bankomat.Models.BankAccount;
import com.Projekt.Bankomat.Models.Transaction;
import com.Projekt.Bankomat.Models.User;

import java.time.LocalDate;
import java.util.List;

public class Mapper {
    private Mapper(){}

    public static String[] splitedData(String data){
        return data.split(",");
    }
    public static String[] toLogin(String data) {
        return data.split(",",2);
    }

    public static UserDto toUserDto(String data){
        var splitedData = data.split(",", 7);
        return UserDto.builder()
                .firstName(splitedData[0])
                .lastName(splitedData[1])
                .email(splitedData[2])
                .password(splitedData[3])
                .phoneNumber(splitedData[4])
                .dateOfBirth(LocalDate.parse(splitedData[5]))
                .address(splitedData[6])
                .build();
    }

    public static String[] toTransaction(String data){
        return data.split(",",6);
    }

    public static <T> String listToString(List<T> objects){
        StringBuilder response = new StringBuilder();
        for (T object : objects){
            response.append(object.toString());
        }
        return response.toString();
    }
}
