package com.example.linkedInApp.user_service.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hashes the password
    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword,BCrypt.gensalt());
    }

    //chek that plain text password  with previously hashed one
    public static boolean checkPassword(String plainTextPassword,String hashedPassword){
        return BCrypt.checkpw(plainTextPassword,hashedPassword);
    }
}
