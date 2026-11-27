# Project Ahmaddamar - JasperReports Praktikum 6

Project ini mempertahankan struktur aplikasi pemesanan tiket yang diberikan. Penambahan untuk praktikum JasperReports:

- Template `Simple_Blue.jrxml` di `src/main/resources/reports/`.
- Tombol **Cetak Laporan** pada tab **Cek Semua Data**.
- Proses kompilasi JRXML, pengisian laporan memakai koneksi `DataSource` Spring, dan tampilan melalui `JasperViewer`.

## Data laporan

Laporan membaca tabel `tiket` pada database `db_ahmaddamar` dengan kolom `id`, `nomor_kursi`, dan `nama_penumpang`, diurutkan berdasarkan nomor kursi. Nama kolom dan tipe field mengikuti entity `Tiket` yang ada di project.

## Menjalankan

1. Ekstrak ZIP.
2. Pastikan MySQL/Laragon aktif dan database `db_ahmaddamar` tersedia.
3. Sesuaikan username/password MySQL pada `src/main/resources/applicationContext.xml` dan `hibernate.cfg.xml` bila diperlukan.
4. Buka folder project yang berisi `pom.xml` di NetBeans sebagai Maven Project.
5. Tunggu Maven mengunduh dependency, lalu jalankan `com.mycompany.ahmaddamar.Ahmaddamar`.
6. Masuk ke tab **Cek Semua Data** lalu klik **Cetak Laporan**.

## Logika program JasperReports

File JRXML mengatur ukuran halaman, judul, header kolom, detail data, tanggal cetak, dan nomor halaman. Query SQL mengambil `id`, `nomor_kursi`, serta `nama_penumpang` dari tabel `tiket`. Ketiga kolom dipetakan ke field JasperReports dan ditampilkan berulang pada bagian `<detail>`.

Method `cetakLaporan()` memuat template dari resources, menjalankan `JasperCompileManager.compileReport()`, mengambil koneksi dari bean `dataSource` Spring, mengisi laporan dengan `JasperFillManager.fillReport()`, lalu menampilkan hasilnya menggunakan `JasperViewer.viewReport()`. Jika terjadi kesalahan, aplikasi menampilkan pesan melalui `JOptionPane`.

## Catatan

- File SQL `keluhan.sql` dan source aplikasi lainnya tetap disertakan.
- Dependency JasperReports dan OpenPDF sudah tersedia pada `pom.xml` project utama.
- File `target/` hasil build lama tidak disertakan agar NetBeans/Maven membangun ulang dari source.

## Hak akses Admin dan User
Lihat `README_ROLE_ADMIN_USER.md` untuk daftar fitur per role dan langkah migrasi kolom `role` pada tabel `users`. Migrasi database wajib dijalankan satu kali sebelum menjalankan versi dengan pemisahan role.
