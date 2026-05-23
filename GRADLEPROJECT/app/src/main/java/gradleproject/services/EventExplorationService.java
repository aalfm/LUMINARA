package gradleproject.services;

import gradleproject.dao.EventDAO;
import gradleproject.models.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventExplorationService {
    private EventDAO eventDAO;

    public EventExplorationService() {
        this.eventDAO = new EventDAO();
    }

    // Menampilkan acara yang statusnya 'Active' (sudah di-approve Admin)
    public List<Event> getActiveEventsByCategory(String category) {
        List<Event> categoryEvents = eventDAO.findByCategory(category);
        
        // Menggunakan Stream API untuk memfilter hanya acara yang berstatus 'Active'
        return categoryEvents.stream()
                .filter(e -> "Active".equalsIgnoreCase(e.getStatus()))
                .collect(Collectors.toList());
    }

    // Fungsi Explore & Filter (Free / Paid)
    public List<Event> exploreEvents(String category, String ticketTypeFilter) {
        List<Event> events = getActiveEventsByCategory(category);

        if (ticketTypeFilter != null && !ticketTypeFilter.isEmpty()) {
            return events.stream()
                    .filter(e -> e.getTicketType().equalsIgnoreCase(ticketTypeFilter))
                    .collect(Collectors.toList());
        }
        return events;
    }
}