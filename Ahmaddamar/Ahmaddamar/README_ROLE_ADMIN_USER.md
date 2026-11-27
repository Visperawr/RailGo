# Pembagian Hak Akses RailGo

## Fitur USER
- Memesan tiket dengan pemilihan kursi otomatis.
- Melaporkan keluhan layanan.
- Tidak ditampilkan daftar seluruh penumpang, seluruh keluhan, fitur cetak laporan, atau pengelolaan status keluhan.

## Fitur ADMIN
- Melihat seluruh data penumpang/tiket dan data keluhan.
- Melihat jumlah sisa kursi.
- Mencetak laporan tiket menggunakan JasperReports.
- Mengubah status keluhan menjadi Menunggu, Diproses, atau Selesai.

## Pengaturan database (wajib sebelum menjalankan versi ini)
1. Pastikan database `db_ahmaddamar` dan tabel `users` sudah tersedia.
2. Buka phpMyAdmin, pilih database `db_ahmaddamar`, lalu jalankan isi file `role_migration.sql` satu kali.
3. Semua akun lama akan mendapat role `USER` secara otomatis.
4. Pastikan ada akun admin. Jika username akun admin bukan `admin`, ubah nilai pada perintah UPDATE di file SQL sebelum dijalankan, atau jalankan perintah berikut setelah migrasi:
   `UPDATE users SET role = 'ADMIN' WHERE username = 'username_admin';`
5. Akun lain dapat dipastikan sebagai user dengan:
   `UPDATE users SET role = 'USER' WHERE username = 'username_user';`
6. Jalankan aplikasi melalui `com.mycompany.ahmaddamar.Ahmaddamar`.

## Alur login
- Role `ADMIN` diarahkan ke Dashboard Admin.
- Role `USER` diarahkan ke Dashboard User.
- Logout mengembalikan aplikasi ke halaman login.

Catatan: program asal menyimpan password sebagai teks biasa. Perubahan ini mempertahankan mekanisme login yang sudah ada dan hanya menambahkan pembacaan role; penerapan hashing password merupakan peningkatan terpisah.
