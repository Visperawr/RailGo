package com.mycompany.ahmaddamar.view;

import com.mycompany.ahmaddamar.model.Keluhan;
import com.mycompany.ahmaddamar.model.Tiket;
import com.mycompany.ahmaddamar.service.DataService;
import com.mycompany.ahmaddamar.util.AppContext;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.HierarchyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Tab "Cek Semua Data": data penumpang, data keluhan, dan sisa kursi
public class CekDataPanel extends JPanel {
    JLabel lblSisa = new JLabel(" ");
    JButton btnPerbarui = new JButton("Perbarui Data");
    JButton btnCetak = new JButton("Cetak Laporan");
    JLabel lblPenumpang = new JLabel("DATA PENUMPANG");
    JLabel lblKeluhan = new JLabel("DATA KELUHAN");

    DefaultTableModel modelPenumpang = buatModel("No", "Nomor Kursi", "Nama Penumpang");
    DefaultTableModel modelKeluhan = buatModel("No", "Nomor Kursi", "Keluhan", "Status");

    private DataService service;

    public CekDataPanel() {
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Bagian atas: sisa kursi + tombol perbarui
        lblSisa.setFont(new Font("SansSerif", Font.BOLD, 14));
        JPanel atas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        atas.add(lblSisa);
        atas.add(btnPerbarui);
        atas.add(btnCetak);

        // Bagian tengah: dua tabel
        JPanel tengah = new JPanel(new GridLayout(2, 1, 0, 10));
        tengah.add(panelTabel(lblPenumpang, modelPenumpang));
        tengah.add(panelTabel(lblKeluhan, modelKeluhan));

        add(atas, BorderLayout.NORTH);
        add(tengah, BorderLayout.CENTER);

        // Ambil service dari Spring (dependency injection)
        try {
            service = AppContext.getBean("serviceData", DataService.class);
        } catch (Exception e) {
            e.printStackTrace();
            lblSisa.setText("Gagal memuat Spring (lihat panel Output)");
        }

        btnPerbarui.addActionListener(e -> muatData());
        btnCetak.addActionListener(e -> cetakLaporan());

        // Setiap tab ini dibuka, data diperbarui otomatis
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                muatData();
            }
        });

        muatData();
    }

    void muatData() {
        modelPenumpang.setRowCount(0);
        modelKeluhan.setRowCount(0);
        if (service == null) {
            return;
        }

        try {
            List<Tiket> penumpang = service.ambilDataPenumpang();
            int no = 1;
            for (Tiket t : penumpang) {
                modelPenumpang.addRow(new Object[]{
                    no++, t.getNomorKursi(), t.getNamaPenumpang()
                });
            }
            lblPenumpang.setText(penumpang.isEmpty()
                    ? "DATA PENUMPANG (Kosong)" : "DATA PENUMPANG");

            List<Keluhan> keluhan = service.ambilDataKeluhan();
            no = 1;
            for (Keluhan k : keluhan) {
                modelKeluhan.addRow(new Object[]{
                    no++, k.getNomorKursi(), k.getIsiKeluhan(), k.getStatus()
                });
            }
            lblKeluhan.setText(keluhan.isEmpty()
                    ? "DATA KELUHAN (Kosong)" : "DATA KELUHAN");

            lblSisa.setText("Sisa kursi: " + service.hitungSisaKursi()
                    + " dari " + DataService.JUMLAH_KURSI);
        } catch (Exception e) {
            e.printStackTrace();
            lblSisa.setText("Gagal membaca data (lihat panel Output)");
        }
    }

    // Membuat dan menampilkan laporan data tiket menggunakan JasperReports.
    private void cetakLaporan() {
        try (InputStream reportStream = getClass().getResourceAsStream("/reports/Simple_Blue.jrxml")) {
            if (reportStream == null) {
                throw new IllegalStateException("File reports/Simple_Blue.jrxml tidak ditemukan.");
            }

            // 1. Kompilasi template JRXML menjadi JasperReport.
            JasperReport jr = JasperCompileManager.compileReport(reportStream);

            // 2. Ambil koneksi database dari DataSource yang dikelola Spring.
            DataSource ds = AppContext.getBean("dataSource", DataSource.class);
            try (Connection conn = ds.getConnection()) {
                // 3. Isi laporan dengan data dari query SQL di file JRXML.
                Map<String, Object> param = new HashMap<>();
                JasperPrint jp = JasperFillManager.fillReport(jr, param, conn);

                // 4. Tampilkan hasil laporan.
                JasperViewer.viewReport(jp, false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mencetak laporan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private JPanel panelTabel(JLabel judul, DefaultTableModel model) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        judul.setFont(new Font("SansSerif", Font.BOLD, 13));
        JTable tabel = new JTable(model);
        tabel.setRowHeight(22);
        p.add(judul, BorderLayout.NORTH);
        p.add(new JScrollPane(tabel), BorderLayout.CENTER);
        return p;
    }

    private static DefaultTableModel buatModel(String... kolom) {
        return new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
    }
}
