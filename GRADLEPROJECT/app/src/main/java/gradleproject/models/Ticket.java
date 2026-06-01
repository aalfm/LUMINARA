package gradleproject.models;

public class Ticket extends BaseEntity {
    private int eventId;
    private int userId;
    private int ticketTierId; // Ditambahkan agar selaras dengan skema database
    private String ticketType; // Ditambahkan untuk menampung data "TBA" / hasil JOIN
    private String paymentStatus; // Pending, Paid, Cancelled

    private int isAttended; 

    private String userName;
    private String userEmail;
    private String userPhone;

    public Ticket() {}

    public Ticket(int id, int eventId, int userId, int ticketTierId, String ticketType, String paymentStatus) {
        super(id); // ID dikelola oleh BaseEntity
        this.eventId = eventId;
        this.userId = userId;
        this.ticketTierId = ticketTierId;
        this.ticketType = ticketType;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTicketTierId() { return ticketTierId; }
    public void setTicketTierId(int ticketTierId) { this.ticketTierId = ticketTierId; }

    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    // Di dalam model Ticket.java tambahkan:
    private String namaKegiatan;
    private String tanggal;

    // Lalu buatkan Getter dan Setter-nya:
    public String getNamaKegiatan() { return namaKegiatan; }
    public void setNamaKegiatan(String namaKegiatan) { this.namaKegiatan = namaKegiatan; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    // Tambahkan ini di bagian deklarasi variabel (di bawah paymentStatus)

    // Tambahkan Getter dan Setter-nya
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
        
        // Catatan: getTicketId() & setTicketId() dihapus. 
        // Gunakan getId() & setId() bawaan dari class BaseEntity.

        // Pastikan variabel ini ada di bagian atas

    // Tambahkan metode ini di bagian bawah
    public int getIsAttended() {
        return isAttended;
    }

    public void setIsAttended(int isAttended) {
        this.isAttended = isAttended;
    }
    }