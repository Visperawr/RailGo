package com.mycompany.ahmaddamar.view;

import com.mycompany.ahmaddamar.controller.TiketController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

/** Menu khusus USER: pemesanan tiket dan pelaporan keluhan. */
public class UserFrame extends JFrame {
    private final JButton btnLogout = new JButton("Logout");
    private final JTabbedPane tabs = new JTabbedPane();
    private final JPanel panelPesan = new JPanel(null);
    private final JLabel lblNama = new JLabel("Nama Penumpang");
    private final JTextField txtNama = new JTextField();
    private final JButton btnPesan = new JButton("Pesan Tiket");
    private final JLabel lblHasil = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblSisa = new JLabel("", SwingConstants.CENTER);
    private final TiketController controller = new TiketController();

    public UserFrame() {
        setTitle("RailGo - Dashboard User");
        setSize(640, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0x1565C0));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel judul = new JLabel("RailGo | Dashboard User");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnLogout.setBackground(new Color(0xD32F2F));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setOpaque(true);
        btnLogout.setBorderPainted(false);
        header.add(judul, BorderLayout.WEST);
        header.add(btnLogout, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        lblNama.setBounds(140, 50, 120, 25);
        txtNama.setBounds(270, 50, 180, 25);
        btnPesan.setBounds(270, 90, 120, 28);
        lblHasil.setBounds(50, 140, 520, 25);
        lblHasil.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSisa.setBounds(50, 170, 520, 25);
        panelPesan.add(lblNama);
        panelPesan.add(txtNama);
        panelPesan.add(btnPesan);
        panelPesan.add(lblHasil);
        panelPesan.add(lblSisa);

        tabs.addTab("Pesan Tiket", panelPesan);
        tabs.addTab("Lapor Keluhan", new LaporKeluhanPanel());
        add(tabs, BorderLayout.CENTER);

        txtNama.addActionListener(e -> pesanTiket());
        btnPesan.addActionListener(e -> pesanTiket());
        btnLogout.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });
        updateSisa();
    }

    private void pesanTiket() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            lblHasil.setText("Nama harus diisi!");
            return;
        }
        try {
            int kursi = controller.pesanTiket(nama);
            if (kursi == -1) {
                lblHasil.setText("Tiket habis!");
            } else {
                lblHasil.setText("Sukses! " + nama + " mendapatkan kursi: " + kursi);
                txtNama.setText("");
            }
            updateSisa();
        } catch (Exception e) {
            lblHasil.setText("Gagal menyimpan ke database!");
            e.printStackTrace();
        }
        txtNama.requestFocus();
    }

    private void updateSisa() {
        try {
            lblSisa.setText("Sisa kursi: " + controller.getKursiKosong().size());
        } catch (Exception e) {
            lblSisa.setText("Sisa kursi: -");
        }
    }
}
