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