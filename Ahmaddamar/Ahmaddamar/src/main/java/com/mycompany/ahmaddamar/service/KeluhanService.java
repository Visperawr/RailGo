package com.mycompany.ahmaddamar.service;

import com.mycompany.ahmaddamar.dao.KeluhanDAO;
import com.mycompany.ahmaddamar.model.Keluhan;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class KeluhanService {

    private KeluhanDAO keluhanDAO;

    // IMPLEMENTASI DI: Setter Injection
    public void setKeluhanDAO(KeluhanDAO keluhanDAO) {
        this.keluhanDAO = keluhanDAO;
    }

    // Declarative Transaction: tidak ada beginTransaction/commit/rollback manual
    @Transactional
    public void laporkanKeluhan(String nomorKursi, String isiKeluhan) {
        if (nomorKursi == null || nomorKursi.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor kursi tidak boleh kosong.");
        }
        if (isiKeluhan == null || isiKeluhan.trim().isEmpty()) {
            throw new IllegalArgumentException("Keluhan tidak boleh kosong.");
        }

        Keluhan keluhan = new Keluhan();
        keluhan.setNomorKursi(nomorKursi.trim());
        keluhan.setIsiKeluhan(isiKeluhan.trim());
        keluhan.setStatus("Menunggu");   // status awal, sama seperti kode asli
        keluhanDAO.simpan(keluhan);
    }

    @Transactional(readOnly = true)
    public List<Keluhan> ambilSemuaKeluhan() {
        return keluhanDAO.semuaKeluhan();
    }

    // Memperbarui status keluhan (Menunggu / Diproses / Selesai)
    @Transactional
    public void perbaruiStatus(int id, String statusBaru) {
        if (!"Menunggu".equals(statusBaru) && !"Diproses".equals(statusBaru)
                && !"Selesai".equals(statusBaru)) {
            throw new IllegalArgumentException("Status tidak valid.");
        }

        Keluhan keluhan = keluhanDAO.cariById(id);
        if (keluhan == null) {
            throw new IllegalArgumentException("Keluhan tidak ditemukan.");
        }
        if (statusBaru.equals(keluhan.getStatus())) {
            throw new IllegalArgumentException("Status keluhan sudah " + statusBaru + ".");
        }

        keluhan.setStatus(statusBaru);
        keluhanDAO.ubah(keluhan);
    }
}
