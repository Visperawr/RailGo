package com.mycompany.ahmaddamar.view;

import com.mycompany.ahmaddamar.controller.TiketController;
import com.mycompany.ahmaddamar.model.Tiket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PesanTiketFrame extends JFrame {
    // header
    JLabel lblJudul = new JLabel("RailGo");
    JButton btnLogout = new JButton("Logout");
    JTabbedPane tabs = new JTabbedPane();

    // tab Pesan Tiket
    JPanel panelPesan = new JPanel(null);
    JLabel lblNama = new JLabel("Nama Penumpang");
    JTextField txtNama = new JTextField();
    JButton btnPesan = new JButton("Pesan Tiket");
    JLabel lblHasil = new JLabel("", SwingConstants.CENTER);
    JLabel lblSisa = new JLabel("", SwingConstants.CENTER);

    // tab Data Tiket
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID Tiket", "Nomor Kursi", "Nama Penumpang"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    JTable tabel = new JTable(model);

    TiketController controller = new TiketController();

    public PesanTiketFrame() {
        setTitle("RailGo - Pemesanan Tiket");
        setSize(640, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Header =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0x616161));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 20));

        btnLogout.setBackground(new Color(0xD32F2F));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnLogout.setOpaque(true);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);

        header.add(lblJudul, BorderLayout.WEST);
        header.add(btnLogout, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ===== Tab 1: Pesan Tiket =====
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

        // ===== Tab 2: Data Tiket =====
        JPanel panelData = new JPanel(new BorderLayout(0, 8));
        panelData.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel lblData = new JLabel("Daftar Tiket yang Sudah Dipesan:");
        lblData.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabel.setRowHeight(24);
        panelData.add(lblData, BorderLayout.NORTH);
        panelData.add(new JScrollPane(tabel), BorderLayout.CENTER);

        // ===== Tab =====
        tabs.addTab("Pesan Tiket (Kursi Acak)", panelPesan);
        tabs.addTab("Data Tiket", panelData);
        add(tabs, BorderLayout.CENTER);

        // ===== Aksi =====
        // Enter di nama atau klik tombol -> pesan tiket
        txtNama.addActionListener(e -> pesanTiket());
        btnPesan.addActionListener(e -> pesanTiket());

        // pindah tab -> data tabel diperbarui
        tabs.addChangeListener(e -> isiTabel());

        // Logout -> kembali ke form login
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        updateSisa();
        isiTabel();
    }

    void pesanTiket() {
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
                lblHasil.setText("Sukses! " + nama + " dapat Kursi: " + kursi);
                txtNama.setText("");
            }
            updateSisa();
            isiTabel();
        } catch (Exception e) {
            lblHasil.setText("Gagal menyimpan ke database!");
        }
        txtNama.requestFocus();
    }

    void updateSisa() {
        try {
            lblSisa.setText("Sisa kursi: " + controller.getKursiKosong().size());
        } catch (Exception e) {
            lblSisa.setText("Sisa kursi: -");
        }
    }

    void isiTabel() {
        model.setRowCount(0);
        try {
            List<Tiket> daftar = controller.getSemuaTiket();
            for (Tiket t : daftar) {
                model.addRow(new Object[]{
                    t.getId(), t.getNomorKursi(), t.getNamaPenumpang()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membaca data tiket!");
        }
    }
}