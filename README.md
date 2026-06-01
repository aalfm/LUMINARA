## 🌿 Team Branches
Setiap anggota tim memiliki branch masing-masing untuk development fitur agar pengerjaan lebih terstruktur dan mengurangi konflik code
Branch List:

    - main
    - Alifah
    - Faiqh
    - Syarief
    - Zahwa


## 👥 Team Members
| Nama                                   | Branch  | Role         |
|---------                               |-------- |------------- |
| Andi Alifah Mahrani                    | Alifah  | Backend      |
| A. Muh. Fa'iqh Musharraf Reginald      | Faiqh   | Frontend     |
| Syarief Rahmatullah                    | Syarief | Frontend     |
| Zahwa Dwi Putri                        | Zahwa   | UI/UX        |


## 🗃️ Management Repository
Example:

    ✨ Feature (Fitur Baru)
    feat: menambahkan halaman login
    feat: membuat sistem register user
    feat: integrasi database SQLite

    🐛 Bug Fix (Perbaikan Error/Bug)
    fix: memperbaiki error validasi input
    fix: memperbaiki koneksi database
    fix: bug login tidak bisa membaca password 

    ♻️ Refactor (Merapikan/Mengubah Struktur Kode Tanpa Mengubah Fitur)
    refactor: menyusun ulang struktur kelas
    refactor: memisahkan DAO layer
    refactor: membersihkan kode duplikat



## 🔀 Collaboration Workflow
📍 Dilakukan di:
Terminal (Git Bash / VS Code Terminal) di dalam folder project repository

Clone repository (HANYA pertama kali):

    git clone <url-repository>
    cd <nama-folder-project>
📍 Dilakukan saat pertama kali mengambil project dari GitHub


Ambil update terbaru dari main:

    git checkout main
    git pull origin main
📍 Dilakukan di terminal, pastikan sudah berada di folder project Git

Pindah ke branch masing-masing:

    git checkout Alifah
📍 Masih di terminal dalam repository project yang sama

Push perubahan:

    git add .
    git commit -m "feat: add login feature"
    git push origin Alifah
📍 Dilakukan di terminal setelah selesai coding di VS Code

## ⚠️ Team Rules
    - Jangan langsung coding di main
    - Gunakan branch masing-masing
    - Lakukan pull sebelum mulai coding
    - Merge ke main hanya jika fitur sudah stabil

 🌟 LUMINARA (Luminar Lontara)

LUMINARA (Luminar Lontara) adalah platform digital terintegrasi yang dirancang untuk mengelola dan menjelajahi berbagai event budaya, festival, workshop kreatif, pertunjukan musik, hingga wisata adat di Kota Makassar secara modern dan interaktif. 

Aplikasi ini dibangun menggunakan bahasa *Java* dengan framework *JavaFX* secara pure programmatic (tanpa FXML), menggunakan *CSS* untuk styling, *Gradle* sebagai build tool, dan *SQLite* sebagai sistem penyimpanan database lokal.

---

## 📝 Latar Belakang

Perkembangan teknologi saat ini sangat memudahkan masyarakat dalam memperoleh informasi. Namun, khusus untuk informasi mengenai event budaya, hiburan, dan komunitas di Makassar, datanya masih tersebar di berbagai platform media sosial sehingga sulit ditemukan secara terpusat. Di sisi lain, para penyelenggara event (organizer) juga mengalami kesulitan dalam mempromosikan acara mereka secara efektif dan mengelola manajemen kuota peserta secara praktis.

LUMINARA hadir sebagai solusi satu pintu (one-stop solution) yang menggabungkan informasi event budaya, rekomendasi personal berbasis kesehatan mental, dan sistem pemesanan tiket dalam satu aplikasi yang praktis serta mudah digunakan.

---

## 🎯 Tujuan Proyek

1. *Memudahkan Masyarakat:* Membantu pengunjung maupun warga lokal mencari informasi event budaya dan komunitas di Makassar secara cepat dan akurat.
2. *Platform Terpusat:* Menyediakan wadah tunggal bagi publikasi berbagai kegiatan sosial, hiburan, edukasi, dan pelestarian budaya.
3. *Efisiensi Penyelenggara:* Membantu pihak organizer dalam mempromosikan, memublikasikan, serta mengelola pendaftaran peserta secara lebih praktis dan efisien.
4. *Pendekatan Budaya untuk Healing:* Meningkatkan partisipasi masyarakat sekaligus memperkenalkan budaya Makassar sebagai media pemulihan kesehatan mental (cultural healing).

---

## ✨ Fitur Utama

Aplikasi LUMINARA mendukung multi-role ekosistem (*Quest Mode, Pengguna/User, Penyelenggara/Organizer, dan Admin*) dengan fitur-fitur inovatif berikut:

* *🧩 Mood Filter (Rekomendasi Terarah)* 
* *🌿 Aktivitas Healing Kebudayaan:* Rekomendasi kegiatan meditatif lokal seperti kelas privat menulis Aksara Lontara, belajar memanen padi tradisional, mengikuti ritual adat yang menenangkan, hingga pengalaman live-in di rumah adat (Balla/Tongkonan).
* *📅 Sistem Reservasi/Booking Slots:* Pengguna dapat memilih tanggal, melihat sisa kuota tempat per hari secara real-time demi menjaga lokasi agar tidak terlalu ramai (crowded) sehingga esensi ketenangan dan kenyamanan tetap terjaga.
* *💳 Diferensiasi Tiket (Gratis & Berbayar):* Sistem pembagian otomatis alur pemesanan tiket berdasarkan nominal harga (price == 0 untuk pendaftaran langsung gratis, dan price > 0 untuk jalur transaksi berbayar).
* *💬 TuturRasa (Cerita Healing):* Ruang komunitas bagi pengguna untuk membagikan cerita perjalanan mereka baik secara anonim maupun publik tentang bagaimana kedekatan budaya lokal membantu kesehatan mental mereka.