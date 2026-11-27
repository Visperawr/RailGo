package com.mycompany.ahmaddamar.dao;

import com.mycompany.ahmaddamar.model.Keluhan;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class KeluhanDAO {

    private SessionFactory sessionFactory;

    // IMPLEMENTASI DI: Setter Injection (SessionFactory disuntikkan Spring)
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Session dikelola Spring (transparent session management)
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // Menyimpan keluhan baru
    public void simpan(Keluhan keluhan) {
        getSession().save(keluhan);
    }

    // Mengambil semua keluhan, diurutkan dari yang paling awal
    public List<Keluhan> semuaKeluhan() {
        return getSession()
                .createQuery("FROM Keluhan K ORDER BY K.id", Keluhan.class)
                .list();
    }

    // Mencari keluhan berdasarkan id, null jika tidak ada
    public Keluhan cariById(int id) {
        return getSession().get(Keluhan.class, id);
    }

    // Memperbarui data keluhan yang sudah ada (misalnya status)
    public void ubah(Keluhan keluhan) {
        getSession().update(keluhan);
    }
}
