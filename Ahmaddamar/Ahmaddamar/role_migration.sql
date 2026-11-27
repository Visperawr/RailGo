-- Jalankan SATU KALI pada database db_ahmaddamar melalui phpMyAdmin.
-- Semua akun lama otomatis menjadi USER.
ALTER TABLE users
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';

-- Jadikan akun yang username-nya 'admin' sebagai ADMIN.
-- Jika username admin belum ada, ganti 'admin' dengan username akun admin Anda.
UPDATE users
SET role = 'ADMIN'
WHERE username = 'admin';

-- Untuk mengubah role secara manual:
-- UPDATE users SET role = 'ADMIN' WHERE username = 'username_admin';
-- UPDATE users SET role = 'USER' WHERE username = 'username_user';
