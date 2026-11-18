package com.mycompany.ahmaddamar.dao;

import com.mycompany.ahmaddamar.model.Tiket;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TiketDAO {

    private SessionFactory sessionFactory;

    // IMPLEMENTASI DI: Setter Injection (SessionFactory disuntikkan Spring)
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Session dikelola Spring (transparent session management)
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // Mengambil semua tiket, diurutkan berdasarkan nomor kursi
    public List<Tiket> semuaTiket() {
        return getSession()
                .createQuery("FROM Tiket T ORDER BY T.nomorKursi", Tiket.class)
                .list();
    }

    // Menghitung jumlah kursi yang sudah dipesan
    public long hitungTiket() {
        return getSession()
                .createQuery("SELECT COUNT(T) FROM Tiket T", Long.class)
                .uniqueResult();
    }
}
