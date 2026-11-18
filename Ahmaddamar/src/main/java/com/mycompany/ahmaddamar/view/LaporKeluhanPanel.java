package com.mycompany.ahmaddamar.view;

import com.mycompany.ahmaddamar.service.KeluhanService;
import com.mycompany.ahmaddamar.util.AppContext;
import java.awt.Font;
import javax.swing.*;

// Tab "Lapor Keluhan" (Laporkan Keluhan Layanan)
public class LaporKeluhanPanel extends JPanel {
    JLabel lblJudul = new JLabel("LAPORKAN KELUHAN LAYANAN", SwingConstants.CENTER);
    JLabel lblKursi = new JLabel("Nomor Kursi");
    JTextField txtKursi = new JTextField();
    JLabel lblKeluhan = new JLabel("Keluhan");
    JTextField txtKeluhan = new JTextField();
    JButton btnKirim = new JButton("Kirim Laporan");
    JLabel lblPesan = new JLabel("", SwingConstants.CENTER);

    private KeluhanService service;

    public LaporKeluhanPanel() {
        setLayout(null);

        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPesan.setFont(new Font("SansSerif", Font.BOLD, 13));

        lblJudul.setBounds(50, 20, 520, 25);
        lblKursi.setBounds(140, 65, 110, 25);
        txtKursi.setBounds(260, 65, 230, 25);
        lblKeluhan.setBounds(140, 100, 110, 25);
        txtKeluhan.setBounds(260, 100, 230, 25);
        btnKirim.setBounds(260, 140, 130, 28);
        lblPesan.setBounds(50, 185, 520, 25);

        add(lblJudul);
        add(lblKursi);
        add(txtKursi);
        add(lblKeluhan);
        add(txtKeluhan);
        add(btnKirim);
        add(lblPesan);

        // Ambil service dari Spring (dependency injection)
        try {
            service = AppContext.getBean("serviceKeluhan", KeluhanService.class);
        } catch (Exception e) {
            e.printStackTrace();
            lblPesan.setText("Gagal memuat Spring (lihat panel Output)");
        }

        // Enter di nomor kursi -> pindah ke keluhan
        txtKursi.addActionListener(e -> txtKeluhan.requestFocus());

        // Enter di keluhan atau klik tombol -> kirim laporan
        txtKeluhan.addActionListener(e -> kirim());
        btnKirim.addActionListener(e -> kirim());
    }

    void kirim() {
        if (service == null) {
            lblPesan.setText("Service belum siap (lihat panel Output)");
            return;
        }
        try {
            service.laporkanKeluhan(txtKursi.getText(), txtKeluhan.getText());
            lblPesan.setText("Laporan sudah masuk!");
            txtKursi.setText("");
            txtKeluhan.setText("");
            txtKursi.requestFocus();
        } catch (IllegalArgumentException e) {
            lblPesan.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            lblPesan.setText("Gagal menyimpan ke database!");
        }
    }
}
