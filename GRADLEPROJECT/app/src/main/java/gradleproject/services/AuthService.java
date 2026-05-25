package gradleproject.services;

import gradleproject.dao.UserDAO;
import gradleproject.models.User;
import gradleproject.utils.SecurityUtil;
import gradleproject.utils.SessionManager;
import gradleproject.utils.ValidationUtil;

public class AuthService {
    private UserDAO userDAO;
    
    // Menyimpan sesi pengguna yang sedang login
    private static User currentUser = null;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(User user) {
        // 1. Validasi format email
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            System.out.println("Pendaftaran Gagal: Format email tidak valid.");
            return false;
        }

        // 2. Validasi nomor telepon
        if (!ValidationUtil.isValidPhoneNumber(user.getPhoneNumber())) {
            System.out.println("Pendaftaran Gagal: Nomor telepon tidak valid.");
            return false;
        }

        // 3. Validasi kekuatan password
        if (!ValidationUtil.isValidPassword(user.getPassword())) {
            System.out.println("Pendaftaran Gagal: Password minimal 8 karakter.");
            return false;
        }

        // 4. Pastikan Email belum digunakan
        if (userDAO.findByEmail(user.getEmail()) != null) {
            System.out.println("Pendaftaran Gagal: Email sudah digunakan.");
            return false;
        }

        // 5. Otomatis set sebagai 'user' biasa
        user.setRole("user"); 

        // 6. Hash password
        String plainPassword = user.getPassword();
        String securedPassword = SecurityUtil.hashPassword(plainPassword);
        user.setPassword(securedPassword);

        // 7. Simpan ke database
        return userDAO.insert(user);
    }

        public User login(String email, String password) {
        // Ambil data user beserta password (yang sudah berbentuk Salt:Hash) dari database
        User user = userDAO.findByEmail(email);
        
        if (user == null) {
            System.out.println("Login Gagal: Email tidak ditemukan.");
            return null;
        }

        // Gunakan fungsi verifikasi dari SecurityUtil
        boolean isPasswordMatch = SecurityUtil.verifyPassword(password, user.getPassword());

        if (isPasswordMatch) {
            if ("Banned".equalsIgnoreCase(user.getAccountStatus())) {
                System.out.println("Login Gagal: Akun Anda telah dibanned.");
                return null;
            }
            SessionManager.setCurrentUser(user); 
            System.out.println("Login Berhasil! Selamat datang, " + user.getUsername());
            return user;
        } else {
            System.out.println("Login Gagal: Password salah.");
            return null;
        }
    }

    public void logout() {
        // --- CLEAR SESSION DI SINI ---
        SessionManager.clearSession();
        System.out.println("Berhasil logout. Anda kembali ke Guest Mode.");
    }

    // Mendapatkan user yang sedang login saat ini
    public static User getCurrentUser() {
        return currentUser;
    }
}