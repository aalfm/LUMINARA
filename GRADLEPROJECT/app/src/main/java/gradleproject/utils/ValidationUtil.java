package gradleproject.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Regex untuk format email standar (contoh: user@email.com)
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Regex untuk nomor telepon (opsional diawali '+', hanya angka, panjang 10-15 digit)
    private static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Constructor private agar tidak bisa diinisialisasi (Utility Class)
    private ValidationUtil() {}

    /**
     * Memeriksa apakah sebuah string kosong atau null.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Memvalidasi format email menggunakan Regular Expression (Regex).
     */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Memvalidasi nomor telepon agar hanya berisi angka yang logis.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) return false;
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    /**
     * Memvalidasi harga tiket (tidak boleh bernilai negatif).
     */
    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    /**
     * Memvalidasi kekuatan password dasar (minimal 8 karakter).
     */
    public static boolean isValidPassword(String password) {
        return !isNullOrEmpty(password) && password.length() >= 8;
    }
}