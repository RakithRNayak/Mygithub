package com.myblog1.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.Encoder;

public class Test {
    public static void main(String[] args) {
        PasswordEncoder encoder= new BCryptPasswordEncoder();
        System.out.println(encoder.encode("Hello"));



    }





}
