package gradleproject.utils;

import gradleproject.models.User;

public class SessionManager {
    
    // Variabel statis untuk menyimpan user yang sedang login
    private static User currentUser = null;

    // Constructor dibuat private agar kelas ini tidak bisa diinstansiasi (Utility Class pattern)
    private SessionManager() {
    }

    /**
     * Menyimpan data pengguna ke dalam sesi. 
     * Dipanggil oleh AuthService saat login berhasil.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Mengambil data pengguna yang sedang aktif.
     * Dipanggil oleh Controller atau Service lain (seperti TicketingService).
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Menghapus sesi pengguna saat ini.
     * Dipanggil saat pengguna melakukan Logout.
     */
    public static void clearSession() {
        currentUser = null;
    }

    /**
     * Helper: Mengecek apakah ada user yang sedang login.
     * Berguna untuk menentukan apakah UI harus menampilkan menu Guest atau menu User.
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Helper: Mengecek role dari user yang sedang login.
     * Berguna untuk otorisasi (menyembunyikan/menampilkan tombol khusus Admin/Organizer).
     */
    public static boolean hasRole(String role) {
        if (currentUser != null && currentUser.getRole() != null) {
            return currentUser.getRole().equalsIgnoreCase(role);
        }
        return false;
    }
}