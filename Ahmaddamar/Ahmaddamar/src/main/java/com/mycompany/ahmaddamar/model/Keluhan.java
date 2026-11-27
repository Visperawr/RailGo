package com.mycompany.ahmaddamar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "keluhan")
public class Keluhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nomor_kursi", nullable = false)
    private String nomorKursi;

    @Column(name = "isi_keluhan", nullable = false)
    private String isiKeluhan;

    @Column(name = "status", nullable = false)
    private String status;

    public Keluhan() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomorKursi() { return nomorKursi; }
    public void setNomorKursi(String nomorKursi) { this.nomorKursi = nomorKursi; }

    public String getIsiKeluhan() { return isiKeluhan; }
    public void setIsiKeluhan(String isiKeluhan) { this.isiKeluhan = isiKeluhan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
