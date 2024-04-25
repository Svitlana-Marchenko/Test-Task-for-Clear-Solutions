package com.marchenko.clearsolutiontest;

import com.marchenko.clearsolutiontest.model.User;

import java.time.LocalDate;
import java.util.Random;

public class Utils {
    public static User getRandomUser() {
        Random rnd = new Random();
        return new User(rnd.nextLong(), getRandomEmail(), getRandomString(10), getRandomString(10), LocalDate.of(2002, 11, 3), getRandomString(10), getRandomString(10));
    }

    private static String getRandomString(int length) {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder res = new StringBuilder();
        Random rnd = new Random();
        while (res.length() < length) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            res.append(CHARS.charAt(index));
        }
        return res.toString();
    }

    private static String getRandomEmail(){
        return getRandomString(5)+"@email.com";
    }
}
