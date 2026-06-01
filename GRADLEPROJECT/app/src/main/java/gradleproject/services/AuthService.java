package gradleproject.services;

import gradleproject.dao.UserDAO;
import gradleproject.models.User;
import gradleproject.utils.SessionManager;
import gradleproject.utils.ValidationUtil;

public class AuthService {
    private UserDAO userDAO;
    private static User currentUser = null; 

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public String register(User user) {
        if (!ValidationUtil.isValidEmail(user.getEmail())) return "Format email tidak valid.";
        if (!ValidationUtil.isValidPhoneNumber(user.getPhoneNumber())) return "Nomor telepon tidak valid.";
        if (!ValidationUtil.isValidPassword(user.getPassword())) return "Password minimal 8 karakter.";
        if (userDAO.findByEmail(user.getEmail()) != null) return "Email sudah terdaftar.";

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("Pengunjung"); 
        }

        // TIDAK ADA HASHING: Password disimpan apa adanya ke database
        return userDAO.insert(user) ? "SUKSES" : "Database gagal menyimpan!";
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        
        if (user == null) {
            System.out.println("Login Gagal: Email tidak ditemukan.");
            return null;
        }

        // ✅ PERUBAHAN UTAMA: Membandingkan password secara langsung (Plain Text)
        if (!password.equals(user.getPassword())) {
            System.out.println("Login Gagal: Password salah.");
            return null;
        }

        if ("Banned".equalsIgnoreCase(user.getAccountStatus())) {
            System.out.println("Login Gagal: Akun diblokir.");
            return null; 
        }

        // Sinkronisasi Sesi
        SessionManager.setCurrentUser(user);
        currentUser = user; 
        
        System.out.println("Login Berhasil! Selamat datang, " + user.getUsername());
        return user;
    }
    
    public void logout() {
        SessionManager.clearSession();
        currentUser = null;
        System.out.println("Berhasil logout.");
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}