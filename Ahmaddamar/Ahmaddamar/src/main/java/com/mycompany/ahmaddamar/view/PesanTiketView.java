package com.mycompany.ahmaddamar.view;

import com.mycompany.ahmaddamar.controller.TiketController;
import java.util.Scanner;

public class PesanTiketView {

    private final TiketController controller = new TiketController();

    // dipanggil langsung setelah login: pesan tiket, bisa diulang
    public void mulai(Scanner in) {
        String lagi;
        do {
            System.out.println("\n--- PESAN TIKET (KURSI ACAK) ---");
            tampil(in);
            System.out.print("Pesan tiket lagi? (y/n): ");
            lagi = in.nextLine().trim();
        } while (lagi.equalsIgnoreCase("y"));
        System.out.println("Selesai.");
    }

    public void tampil(Scanner in) {
        System.out.print("Nama Penumpang: ");
        String nm = in.nextLine().trim();

        if (nm.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }

        try {
            int kursi = controller.pesanTiket(nm);
            if (kursi == -1) {
                System.out.println("Tiket habis!");
            } else {
                System.out.println("Sukses! Dapet Kursi: " + kursi);
            }
        } catch (Exception e) {
            System.out.println("Gagal menyimpan ke database: " + e.getMessage());
        }
    }
}