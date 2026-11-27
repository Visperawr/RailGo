package com.mycompany.ahmaddamar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

/** Menu khusus ADMIN untuk pemantauan data dan pengelolaan keluhan. */
public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("RailGo - Dashboard Admin");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0x37474F));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel judul = new JLabel("RailGo | Dashboard Admin");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("SansSerif", Font.BOLD, 20));
        JButton logout = new JButton("Logout");
        logout.setBackground(new Color(0xD32F2F));
        logout.setForeground(Color.WHITE);
        logout.setOpaque(true);
        logout.setBorderPainted(false);
        logout.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });
        header.add(judul, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Cek Data & Cetak Laporan", new CekDataPanel());
        tabs.addTab("Kelola Status Keluhan", new PerbaruiKeluhanPanel());
        add(tabs, BorderLayout.CENTER);
    }
}
