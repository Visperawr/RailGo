package com.mycompany.ahmaddamar;

import javax.swing.*;

public class LoginFrame extends JFrame {
    JLabel lblJudul = new JLabel("LOGIN", SwingConstants.CENTER);
    JLabel lblUser = new JLabel("Username");
    JLabel lblPass = new JLabel("Password");
    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();
    JButton btnLogin = new JButton("Login");
    JLabel lblPesan = new JLabel("", SwingConstants.CENTER);
    int percobaan = 0;

    public LoginFrame() {
        setTitle("Login");
        setSize(320, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        lblJudul.setBounds(20, 15, 260, 25);
        lblUser.setBounds(30, 55, 80, 25);
        txtUser.setBounds(110, 55, 160, 25);
        lblPass.setBounds(30, 95, 80, 25);
        txtPass.setBounds(110, 95, 160, 25);
        btnLogin.setBounds(110, 135, 80, 28);
        lblPesan.setBounds(20, 175, 260, 25);

        add(lblJudul);
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);
        add(lblPesan);

        // Enter di username -> pindah ke password
        txtUser.addActionListener(e -> txtPass.requestFocus());

        // Enter di password atau klik tombol -> login
        txtPass.addActionListener(e -> prosesLogin());
        btnLogin.addActionListener(e -> prosesLogin());
    }

    void prosesLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            lblPesan.setText("Username dan password harus diisi!");
            return;
        }

        if (user.equals("admin") && pass.equals("123")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            dispose();
            Ahmaddamar.menu();
        } else {
            percobaan++;
            if (percobaan >= 3) {
                JOptionPane.showMessageDialog(this, "Gagal login 3 kali. Program ditutup.");
                System.exit(0);
            }
            lblPesan.setText("Username atau password salah! (" + percobaan + "/3)");
            txtPass.setText("");
            txtPass.requestFocus();
        }
    }
}