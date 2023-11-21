package com.Projekt.Bankomat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Generators {
    public static String generateRandomNumber(int numberOfDigits) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        result.append(random.nextInt(9) + 1);
        for (int i = 1; i < numberOfDigits; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
