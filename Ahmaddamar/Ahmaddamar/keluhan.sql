-- jalankan di database db_ahmaddamar (phpMyAdmin > tab SQL > Go)
CREATE TABLE IF NOT EXISTS keluhan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomor_kursi VARCHAR(20) NOT NULL,
    isi_keluhan VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL
);
