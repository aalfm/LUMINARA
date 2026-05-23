package gradleproject.services;

import gradleproject.dao.EventDAO;
import gradleproject.dao.TransactionDAO;
import gradleproject.models.Event;

import java.util.List;

public class OrganizerManagementService {
    private EventDAO eventDAO;
    private TransactionDAO transactionDAO;

    public OrganizerManagementService() {
        this.eventDAO = new EventDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Membuat event baru sebagai Draft
    public boolean createEventDraft(Event event) {
        event.setStatus("Draft");
        return eventDAO.insert(event);
    }

    // Mengajukan event ke Admin untuk di-review
    public boolean submitForApproval(int eventId) {
        // Ubah status approval menjadi Pending
        // Karena di EventDAO kita menggunakan method updateStatus untuk kolom status
        return eventDAO.updateStatus(eventId, "Pending"); 
    }

    // Melihat semua event milik organizer tertentu
    public List<Event> getMyEvents(int organizerId) {
        // Catatan: Anda perlu menambahkan query getEventsByOrganizerId di EventDAO
        // Ini adalah bentuk gambaran logikanya:
        System.out.println("Menampilkan daftar acara untuk Organizer ID: " + organizerId);
        return eventDAO.findByOrganizerId(organizerId); 
    }

    // Mengambil laporan finansial
    public void printFinancialReport(int organizerId) {
        double totalRevenue = transactionDAO.getRevenueByOrganizer(organizerId);
        System.out.println("=== Laporan Keuangan Organizer ===");
        System.out.println("Total Pendapatan: Rp " + totalRevenue);
    }
}