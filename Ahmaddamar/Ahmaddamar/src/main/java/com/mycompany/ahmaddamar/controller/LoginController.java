package com.mycompany.ahmaddamar.controller;

import com.mycompany.ahmaddamar.model.User;
import com.mycompany.ahmaddamar.util.HibernateUtil;
import org.hibernate.Session;

public class LoginController {

    /** Mengembalikan akun yang berhasil login, atau null jika kredensial salah. */
    public User authenticate(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from User where username = :u and password = :p", User.class)
                    .setParameter("u", username)
                    .setParameter("p", password)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Dipertahankan untuk kompatibilitas dengan pemanggil lama. */
    public boolean login(String username, String password) {
        return authenticate(username, password) != null;
    }
}
