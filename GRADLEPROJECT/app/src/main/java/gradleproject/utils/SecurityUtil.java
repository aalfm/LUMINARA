package gradleproject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {

    // Karakter pemisah antara Salt dan Hash saat disimpan ke database
    private static final String DELIMITER = ":";

    // Constructor dibuat private karena ini adalah kelas Utility (hanya berisi method statis)
    private SecurityUtil() {}

    /**
     * Mengamankan password baru dengan kombinasi Salt acak dan Hashing (SHA-256).
     * Format output yang dihasilkan: Base64(Salt) + ":" + Base64(Hash)
     */
    public static String hashPassword(String password) {
        try {
            // 1. Buat Salt acak yang aman secara kriptografi
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // 2. Lakukan hashing password + salt
            byte[] hash = generateHash(password, salt);

            // 3. Konversi byte[] menjadi String Base64 agar mudah disimpan ke database teks (SQLite)
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String encodedHash = Base64.getEncoder().encodeToString(hash);

            // 4. Gabungkan keduanya
            return encodedSalt + DELIMITER + encodedHash;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritma Hashing tidak ditemukan di sistem.", e);
        }
    }

    /**
     * Memverifikasi input password pengguna dengan data hash yang ada di database.
     */
    public static boolean verifyPassword(String inputPassword, String storedData) {
        try {
            // 1. Pecah data dari database menjadi Salt dan Hash
            String[] parts = storedData.split(DELIMITER);
            if (parts.length != 2) {
                return false; // Format tidak valid
            }
            
            String encodedSalt = parts[0];
            String encodedHash = parts[1];

            // 2. Kembalikan String Base64 menjadi bentuk byte[]
            byte[] salt = Base64.getDecoder().decode(encodedSalt);
            byte[] storedHash = Base64.getDecoder().decode(encodedHash);

            // 3. Lakukan proses hashing ulang pada input password dengan salt yang sama
            byte[] computedHash = generateHash(inputPassword, salt);

            // 4. Bandingkan hasil hash baru dengan hash yang ada di database
            return MessageDigest.isEqual(storedHash, computedHash);

        } catch (Exception e) {
            System.err.println("Gagal memverifikasi password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fungsi inti (Core) untuk memproses algoritma SHA-256
     */
    private static byte[] generateHash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // Tambahkan salt ke dalam proses digest
        digest.update(salt);
        // Lakukan hashing pada password string
        return digest.digest(password.getBytes());
    }
}