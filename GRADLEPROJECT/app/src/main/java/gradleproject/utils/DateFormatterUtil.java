package gradleproject.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatterUtil {

    // Menggunakan Locale Indonesia agar format bulan otomatis berbahasa Indonesia
    private static final Locale INDO_LOCALE = new Locale.Builder().setLanguage("id").setRegion("ID").build();

    // Format standar untuk UI (Contoh: 24 Agustus 2026, 14:30 WIB)
    private static final DateTimeFormatter UI_DATETIME_FORMATTER = 
        DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", INDO_LOCALE);

    // Format standar untuk input form (Contoh: 2026-08-24 14:30)
    private static final DateTimeFormatter DB_INPUT_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Constructor private untuk Utility Class
    private DateFormatterUtil() {}

    /**
     * Konversi dari java.sql.Timestamp (Database) menjadi String yang cantik untuk UI.
     * Cocok digunakan saat menampilkan jadwal Event di ListView/TableView JavaFX.
     */
    public static String formatForUI(Timestamp timestamp) {
        if (timestamp == null) {
            return "Waktu tidak ditentukan";
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.format(UI_DATETIME_FORMATTER) + " WITA"; // Menyesuaikan zona waktu utama acara
    }

    /**
     * Konversi dari String (Inputan User/Date Picker JavaFX) menjadi java.sql.Timestamp.
     * Digunakan saat Organizer membuat Event baru.
     * @param dateTimeString Format wajib: "yyyy-MM-dd HH:mm"
     */
    public static Timestamp parseToDatabase(String dateTimeString) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DB_INPUT_FORMATTER);
            return Timestamp.valueOf(localDateTime);
        } catch (DateTimeParseException e) {
            System.err.println("Format tanggal salah! Gunakan format yyyy-MM-dd HH:mm. Detail: " + e.getMessage());
            return null;
        }
    }

    /**
     * Cek apakah waktu acara sudah berlalu dari waktu saat ini.
     * Digunakan oleh AdminModerationService atau TicketingService untuk menutup penjualan tiket.
     */
    public static boolean isEventPast(Timestamp eventDate) {
        if (eventDate == null) return false;
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventTime = eventDate.toLocalDateTime();
        
        return eventTime.isBefore(now);
    }
}