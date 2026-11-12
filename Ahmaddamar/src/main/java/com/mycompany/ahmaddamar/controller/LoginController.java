package com.mycompany.ahmaddamar.controller;

import com.mycompany.ahmaddamar.model.User;
import com.mycompany.ahmaddamar.util.HibernateUtil;
import org.hibernate.Session;

public class LoginController {

    public boolean login(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.createQuery(
                    "from User where username = :u and password = :p", User.class)
                    .setParameter("u", username)
                    .setParameter("p", password)
                    .uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}