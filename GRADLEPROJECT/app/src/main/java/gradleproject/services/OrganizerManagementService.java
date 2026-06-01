package gradleproject.services;

import gradleproject.dao.EventDAO;
import gradleproject.models.Event;

import java.sql.SQLException;
import java.util.List;

public class OrganizerManagementService {
    private EventDAO eventDAO;

    public OrganizerManagementService() {
        this.eventDAO = new EventDAO();
    
    }

    // 🎯 PERUBAHAN: Ubah return type menjadi void dan lempar SQLException
    public void createEventDraft(Event event) throws SQLException {
        event.setStatus("Draft");
        eventDAO.insert(event); // Akan otomatis melempar error ke View jika gagal
    }

    // Mengajukan event ke Admin untuk di-review
    public boolean submitForApproval(int eventId) {
        // Ubah status approval menjadi Pending
        return eventDAO.updateStatus(eventId, "Pending"); 
    }

    // Melihat semua event milik organizer tertentu
    public List<Event> getMyEvents(int organizerId) {
        System.out.println("Menampilkan daftar acara untuk Organizer ID: " + organizerId);
        return eventDAO.findByOrganizerId(organizerId); 
    }
    // Mengambil laporan finansial
    public void printFinancialReport(int organizerId) {
        System.out.println("Menampilkan laporan finansial untuk Organizer ID: " + organizerId);
    }
}