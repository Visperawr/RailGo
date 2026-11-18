package com.mycompany.ahmaddamar.controller;

import com.mycompany.ahmaddamar.model.Tiket;
import com.mycompany.ahmaddamar.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TiketController {

    public static final int JUMLAH_KURSI = 30;

    // semua tiket yang sudah dipesan, diurutkan berdasarkan nomor kursi
    public List<Tiket> getSemuaTiket() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("from Tiket order by nomorKursi", Tiket.class)
                    .list();
        }
    }

    // kursi yang belum dipesan (1..30 dikurangi yang sudah ada di database)
    public List<Integer> getKursiKosong() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Integer> terpakai = session
                    .createQuery("select t.nomorKursi from Tiket t", Integer.class)
                    .list();

            List<Integer> kosong = new ArrayList<>();
            for (int i = 1; i <= JUMLAH_KURSI; i++) {
                if (!terpakai.contains(i)) {
                    kosong.add(i);
                }
            }
            return kosong;
        }
    }

    // pesan tiket kursi acak, return nomor kursi (atau -1 kalau tiket habis)
    public int pesanTiket(String nama) {
        List<Integer> kosong = getKursiKosong();
        if (kosong.isEmpty()) {
            return -1;
        }

        Collections.shuffle(kosong);
        int kursi = kosong.get(0);

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(new Tiket(kursi, nama));
            tx.commit();
            return kursi;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}