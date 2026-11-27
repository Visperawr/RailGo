package com.mycompany.ahmaddamar.service;

import com.mycompany.ahmaddamar.dao.KeluhanDAO;
import com.mycompany.ahmaddamar.dao.TiketDAO;
import com.mycompany.ahmaddamar.model.Keluhan;
import com.mycompany.ahmaddamar.model.Tiket;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

// Service untuk menu "Cek Semua Data"
public class DataService {

    public static final int JUMLAH_KURSI = 30;

    private TiketDAO tiketDAO;
    private KeluhanDAO keluhanDAO;

    // IMPLEMENTASI DI: Setter Injection
    public void setTiketDAO(TiketDAO tiketDAO) {
        this.tiketDAO = tiketDAO;
    }

    public void setKeluhanDAO(KeluhanDAO keluhanDAO) {
        this.keluhanDAO = keluhanDAO;
    }

    @Transactional(readOnly = true)
    public List<Tiket> ambilDataPenumpang() {
        return tiketDAO.semuaTiket();
    }

    @Transactional(readOnly = true)
    public List<Keluhan> ambilDataKeluhan() {
        return keluhanDAO.semuaKeluhan();
    }

    // Sisa kursi = jumlah seluruh kursi dikurangi kursi yang sudah dipesan
    @Transactional(readOnly = true)
    public int hitungSisaKursi() {
        return JUMLAH_KURSI - (int) tiketDAO.hitungTiket();
    }
}
