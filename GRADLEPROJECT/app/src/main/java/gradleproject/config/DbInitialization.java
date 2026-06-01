package gradleproject.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitialization {

        public static void initDatabase() {
            System.out.println("DEBUG: Memulai initDatabase..."); // 1. Cek apakah metode ini dipanggil

            try (Connection conn = DbConnect.getConnection();
                Statement stmt = conn.createStatement()) {
                
                // 2. Cek lokasi database mana yang sebenarnya dibuka oleh aplikasi
                String dbPath = conn.getMetaData().getURL();
                System.out.println("DEBUG: Database terkoneksi ke: " + dbPath);
            
                stmt.execute("PRAGMA foreign_keys = ON;");

                String sqlUsers = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT NOT NULL,
                        email TEXT NOT NULL,
                        password TEXT NOT NULL,
                        phone_number TEXT,
                        role TEXT,
                        account_status TEXT DEFAULT 'Active',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                    """;
                stmt.execute(sqlUsers);

                String sqlOrganizers = """
                    CREATE TABLE IF NOT EXISTS organizers (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER NOT NULL,
                        name TEXT NOT NULL,
                        description TEXT,
                        logo_url TEXT,
                        approval_status TEXT DEFAULT 'Pending' CHECK(approval_status IN ('Pending', 'Approved', 'Rejected', 'Banned')),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlOrganizers);

                String sqlEvents = """
                    CREATE TABLE IF NOT EXISTS events (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        organizer_id INTEGER NOT NULL,
                        category TEXT NOT NULL CHECK(category IN ('Festival', 'Lokakarya', 'Musik', 'Budaya')),
                        title TEXT NOT NULL,
                        image_url TEXT DEFAULT 'default.png', -- 👉 PERBAIKAN 1: Dibuat boleh kosong (tanpa NOT NULL)
                        preview_text TEXT,
                        detail_description TEXT NOT NULL,
                        location TEXT NOT NULL,
                        kuota INTEGER NOT NULL DEFAULT 0, -- 👉 PERBAIKAN 2: Menambahkan kolom kuota
                        ticket_type TEXT NOT NULL CHECK(ticket_type IN ('Paid', 'Free')),
                        price REAL DEFAULT 0,
                        is_recommended INTEGER DEFAULT 0,
                        is_spotlight INTEGER DEFAULT 0,
                        status TEXT DEFAULT 'Draft' CHECK(status IN ('Draft', 'Active', 'Past', 'Rejected')), -- 👉 PERBAIKAN 3: Menambahkan 'Rejected'
                        approval_status TEXT DEFAULT 'Pending' CHECK(approval_status IN ('Pending', 'Approved', 'Rejected')),
                        event_date DATETIME,
                        FOREIGN KEY (organizer_id) REFERENCES organizers(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlEvents);

                String sqlTicketTiers = """
                    CREATE TABLE IF NOT EXISTS ticket_tiers (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        event_id INTEGER NOT NULL,
                        ticket_type TEXT DEFAULT 'Free' CHECK(ticket_type IN ('Paid', 'Free')),
                        price REAL NOT NULL DEFAULT 0,
                        quota INTEGER NOT NULL,
                        FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlTicketTiers);

                String sqlTickets = """
                    CREATE TABLE IF NOT EXISTS tickets (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER NOT NULL,
                        event_id INTEGER NOT NULL,
                        ticket_tier_id INTEGER NOT NULL,
                        payment_status TEXT DEFAULT 'Pending' CHECK(payment_status IN ('Pending', 'Paid', 'Cancelled')),
                        is_attended INTEGER DEFAULT 0,
                        booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        payment_date TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
                        FOREIGN KEY (ticket_tier_id) REFERENCES ticket_tiers(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlTickets);

                String sqlReviews = """
                    CREATE TABLE IF NOT EXISTS reviews (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        event_id INTEGER NOT NULL,
                        user_id INTEGER NOT NULL,
                        rating INTEGER NOT NULL,
                        review_text TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlReviews);

                String sqlRefunds = """
                    CREATE TABLE IF NOT EXISTS refund_requests (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        ticket_id INTEGER NOT NULL,
                        reason TEXT NOT NULL,
                        status TEXT DEFAULT 'Pending' CHECK(status IN ('Pending', 'Approved', 'Rejected')),
                        requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        resolved_at TIMESTAMP,
                        FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlRefunds);

                String sqlTransactions = """
                    CREATE TABLE IF NOT EXISTS transactions (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        ticket_id INTEGER NOT NULL, -- 🎯 TAMBAHKAN INI SEBAGAI PENGHUBUNG
                        amount REAL NOT NULL,
                        transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status TEXT CHECK(status IN ('Success', 'Failed', 'Refunded')),
                        FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlTransactions);

                String sqlAuditLogs = """
                    CREATE TABLE IF NOT EXISTS audit_logs (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        admin_id INTEGER NOT NULL,
                        action_type TEXT NOT NULL,
                        description TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (admin_id) REFERENCES users(id) ON DELETE CASCADE
                    );
                    """;
                stmt.execute(sqlAuditLogs);

                String sqlSorotan = """
                    CREATE TABLE IF NOT EXISTS sorotan_budaya (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        judul TEXT NOT NULL,
                        deskripsi_singkat TEXT,
                        deskripsi_detail TEXT,
                        image_path TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                    """;
                stmt.execute(sqlSorotan);

                // ... (Kode Pembuatan seluruh tabel kamu yang kemarin)

                try (Statement checkStmt = conn.createStatement()) { 
    
                // 2. Gunakan 'checkStmt' di sini, BUKAN 'stmt'
                ResultSet rs = checkStmt.executeQuery("SELECT COUNT(*) FROM users"); 

                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("DEBUG: Jumlah baris di tabel users saat ini: " + count);

                    if (count == 0) {
                        String insertAdminSql = "INSERT INTO users (username, email, password, phone_number, role) " +
                                                "VALUES ('Super Admin', 'admin@luminara.com', 'admin123', '08123456789', 'ADMIN')";
                        
                        // 3. Gunakan 'checkStmt' juga di sini
                        checkStmt.executeUpdate(insertAdminSql); 
                        System.out.println("⭐ Admin berhasil disuntikkan!");
                    } else {
                        System.out.println("ℹ️ Admin sudah ada.");
                    }
                } 
                } catch (SQLException e) { e.printStackTrace();}

                ResultSet rs = stmt.executeQuery("SELECT * FROM ticket_tiers");
                while (rs.next()) {
                    System.out.println(
                        "Tier ID = " + rs.getInt("id") +
                        ", Event ID = " + rs.getInt("event_id") +
                        ", Type = " + rs.getString("ticket_type")
                    );
                } 

        } catch (Exception e) {
            System.err.println("❌ Terjadi kesalahan saat membuat tabel: " + e.getMessage());
            e.getStackTrace();
        }
    }
}
