package com.cytech.marketplace;

import com.cytech.marketplace.dao.DAOBase;
import com.cytech.marketplace.entity.Users;

public class Main {
    public static void main(String[] args) {
        Users user = new Users();
        user.setEmail("phoenix.wright@gmail.com");
        user.setPassword("1234");
        user.setName("Phoenix Wright");
        user.setAdmin(false);

        DAOBase.addUser(user);
    }
}
