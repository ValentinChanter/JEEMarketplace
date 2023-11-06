package com.cytech.marketplace;

import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Users;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        System.out.println(UsersDAO.login("phoenix.wright@gmail.com", "1234"));
    }
}
