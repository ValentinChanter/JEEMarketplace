package com.cytech.marketplace;

import com.cytech.marketplace.dao.UsersDAO;

public class Main {
    public static void main(String[] args) {
        System.out.println(UsersDAO.login("phoenix.wright@gmail.com", "1234"));
    }
}
