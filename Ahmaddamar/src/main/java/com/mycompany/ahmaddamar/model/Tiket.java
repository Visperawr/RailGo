package com.mycompany.ahmaddamar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tiket")
public class Tiket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nomor_kursi", nullable = false, unique = true)
    private int nomorKursi;

    @Column(name = "nama_penumpang", nullable = false, length = 100)
    private String namaPenumpang;

    public Tiket() {
    }

    public Tiket(int nomorKursi, String namaPenumpang) {
        this.nomorKursi = nomorKursi;
        this.namaPenumpang = namaPenumpang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNomorKursi() {
        return nomorKursi;
    }

    public void setNomorKursi(int nomorKursi) {
        this.nomorKursi = nomorKursi;
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }

    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }
}