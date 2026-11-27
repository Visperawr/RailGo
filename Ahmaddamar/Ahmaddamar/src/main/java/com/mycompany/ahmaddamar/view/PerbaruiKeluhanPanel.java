package com.mycompany.ahmaddamar.view;

import com.mycompany.ahmaddamar.model.Keluhan;
import com.mycompany.ahmaddamar.service.KeluhanService;
import com.mycompany.ahmaddamar.util.AppContext;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Tab "Perbarui Status": memperbarui status keluhan penumpang
public class PerbaruiKeluhanPanel extends JPanel {
    JLabel lblPetunjuk = new JLabel("Pilih keluhan dari daftar, lalu pilih status baru:");
    JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Menunggu", "Diproses", "Selesai"});
    JButton btnPerbarui = new JButton("Perbarui Status");
    JLabel lblPesan = new JLabel(" ");

    DefaultTableModel model = new DefaultTableModel(
            new String[]{"No", "Nomor Kursi", "Keluhan", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    JTable tabel = new JTable(model);

    private List<Keluhan> data = new ArrayList<>();
    private KeluhanService service;

    public PerbaruiKeluhanPanel() {
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblPesan.setFont(new Font("SansSerif", Font.BOLD, 13));

        // Bagian atas: pilihan status baru + tombol, lalu pesan hasil
        JPanel baris1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        baris1.add(lblPetunjuk);
        baris1.add(cmbStatus);
        baris1.add(btnPerbarui);

        JPanel baris2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        baris2.add(lblPesan);

        JPanel atas = new JPanel(new GridLayout(2, 1, 0, 4));
        atas.add(baris1);
        atas.add(baris2);

        tabel.setRowHeight(24);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Ambil service dari Spring (dependency injection)
        try {
            service = AppContext.getBean("serviceKeluhan", KeluhanService.class);
        } catch (Exception e) {
            e.printStackTrace();
            lblPesan.setText("Gagal memuat Spring (lihat panel Output)");
        }

        // Klik baris -> pilihan status otomatis mengikuti status keluhan itu
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int baris = tabel.getSelectedRow();
            if (baris != -1 && baris < data.size()) {
                cmbStatus.setSelectedItem(data.get(baris).getStatus());
            }
        });

        btnPerbarui.addActionListener(e -> perbaruiStatus());

        // Setiap tab ini dibuka, daftar diperbarui otomatis
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                muatData();
            }
        });

        muatData();
    }

    void muatData() {
        model.setRowCount(0);
        data = new ArrayList<>();
        if (service == null) {
            return;
        }
        try {
            data = service.ambilSemuaKeluhan();
            int no = 1;
            for (Keluhan k : data) {
                model.addRow(new Object[]{
                    no++, k.getNomorKursi(), k.getIsiKeluhan(), k.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblPesan.setText("Gagal membaca data (lihat panel Output)");
        }
    }

    void perbaruiStatus() {
        if (service == null) {
            return;
        }
        int baris = tabel.getSelectedRow();
        if (baris == -1 || baris >= data.size()) {
            lblPesan.setText("Pilih keluhan dari daftar dulu!");
            return;
        }

        Keluhan dipilih = data.get(baris);
        String statusBaru = (String) cmbStatus.getSelectedItem();
        try {
            service.perbaruiStatus(dipilih.getId(), statusBaru);
            lblPesan.setText("Status keluhan no. " + (baris + 1)
                    + " diperbarui menjadi " + statusBaru + "!");
            muatData();
            if (baris < data.size()) {
                tabel.setRowSelectionInterval(baris, baris);
            }
        } catch (IllegalArgumentException e) {
            lblPesan.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            lblPesan.setText("Gagal memperbarui data (lihat panel Output)");
        }
    }
}
