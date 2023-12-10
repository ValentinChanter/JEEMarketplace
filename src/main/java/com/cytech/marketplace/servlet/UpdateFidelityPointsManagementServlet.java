package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "updateFidelityPointsManagementServlet", value = "/updateFidelityPointsManagement-servlet")
public class UpdateFidelityPointsManagementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<String> parameterNames = req.getParameterMap().keySet();

        for (String parameterName : parameterNames) {
            UUID userID = UUID.fromString(parameterName);
            String newLoyaltyPointsString = req.getParameter(parameterName);

            int newLoyaltyPointsValue;
            if(!newLoyaltyPointsString.isEmpty()) {
                newLoyaltyPointsValue = Integer.parseInt(newLoyaltyPointsString);
            }
            else {
                newLoyaltyPointsValue = 0;
            }

            Users user = UsersDAO.getUser(userID);
            user.setLoyaltyPoints(BigInteger.valueOf(newLoyaltyPointsValue));
            UsersDAO.updateUser(user);
        }

        req.getRequestDispatcher("/WEB-INF/view/fidelityPointsManagement.jsp").forward(req, resp);
    }
}
