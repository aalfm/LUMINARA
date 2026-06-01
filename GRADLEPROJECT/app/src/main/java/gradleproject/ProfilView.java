package gradleproject;

import gradleproject.dao.OrganizerDAO;
import gradleproject.models.OrganizerProfile;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ProfilView extends StackPane {

    public ManajemenAcaraView mainDashboard;

    public ProfilView(ManajemenAcaraView dashboard) {
        this.mainDashboard = dashboard;
        tampilkanLihatProfil();
    }

    public void tampilkanLihatProfil() {
        this.getChildren().clear();

        OrganizerProfile org = mainDashboard.getCurrentOrganizer();
        
        // 🎯 DEKLARASI AMAN: Menangkap data dari database atau memberi nilai default jika kosong
        String namaAsli = (org != null && org.getName() != null && !org.getName().isEmpty()) ? org.getName() : "Nama Belum Diatur";
        String emailAsli = (org != null && org.getEmail() != null && !org.getEmail().isEmpty()) ? org.getEmail() : "-";
        String telpAsli = (org != null && org.getPhoneNumber() != null && !org.getPhoneNumber().isEmpty()) ? org.getPhoneNumber() : "-";
        String roleAsli = (org != null && org.getRole() != null && !org.getRole().isEmpty()) ? org.getRole() : "Penyelenggara";

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        VBox rootContent = new VBox(25);
        rootContent.setPadding(new Insets(30, 40, 30, 40));

        String inisial = namaAsli.substring(0, 1).toUpperCase();
        AnchorPane bannerAnchor = createProfileBanner(inisial);

        VBox metaBox = new VBox(10);
        Label lblTagline = new Label("Kami hadir, menghubungkan komunitas dan budaya melalui event yang inspiratif.");
        lblTagline.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 14px;");
        
        Label lblLokasi = new Label("Makassar, Sulawesi Selatan");
        lblLokasi.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #556B83; -fx-font-size: 13px;");
        
        Label lblSejak = new Label("Sejak November 2025");
        lblSejak.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #556B83; -fx-font-size: 13px;");
        
        metaBox.getChildren().addAll(lblTagline, lblLokasi, lblSejak);

        HBox bottomLayout = new HBox(40);
        HBox.setHgrow(bottomLayout, Priority.ALWAYS);

        VBox infoOrganisasiBox = new VBox(15);
        HBox.setHgrow(infoOrganisasiBox, Priority.ALWAYS);
        
        Label lblSectionInfo = new Label("Informasi Organisasi");
        lblSectionInfo.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #002B5B; -fx-border-color: transparent transparent transparent #FF9914; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10;");

        // 🎯 TAMPILKAN DI LAYAR: Menyatukan teks ke dalam kotak visual UI
        VBox fieldNama = createDisabledField("Nama", namaAsli);
        VBox fieldTelepon = createDisabledField("No Telepon", telpAsli);
        VBox fieldEmail = createDisabledField("Email", emailAsli);
        VBox fieldRole = createDisabledField("Peran Akun", roleAsli); 

        infoOrganisasiBox.getChildren().addAll(lblSectionInfo, fieldNama, fieldTelepon, fieldEmail, fieldRole);

        VBox ulasanBox = new VBox(15);
        ulasanBox.setPrefWidth(380);
        
        Label lblSectionUlasan = new Label("Ulasan");
        lblSectionUlasan.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #002B5B; -fx-border-color: transparent transparent transparent #FF9914; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10;");

        VBox ulasanCardContainer = new VBox(12);
        ulasanCardContainer.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px; -fx-padding: 15px;");
        
        gradleproject.dao.ReviewDAO reviewDAO = new gradleproject.dao.ReviewDAO();
        
        // Ambil ID Organizer yang sedang login
        int currentUserId = UserSession.getInstance().getUserId();
        gradleproject.dao.OrganizerDAO orgDao = new gradleproject.dao.OrganizerDAO();
        gradleproject.models.OrganizerProfile orgProfile = orgDao.findByUserId(currentUserId);
        int orgId = (orgProfile != null) ? orgProfile.getId() : 0;

        // Tarik data menggunakan method baru
        List<String> listUlasanDB = reviewDAO.getReviewsByOrganizer(orgId);

        if (listUlasanDB.isEmpty()) {
            Label lblKosong = new Label("Belum ada ulasan dari peserta saat ini.");
            lblKosong.setStyle("-fx-text-fill: #A0A9B5; -fx-font-family: 'Poppins'; -fx-font-size: 13px;");
            ulasanBox.getChildren().add(lblKosong);
        } else {
            for (String teksUlasan : listUlasanDB) {
                Label itemUlasan = new Label(teksUlasan);
                itemUlasan.setWrapText(true);
                itemUlasan.setStyle("-fx-text-fill: #0A3B5C; -fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-padding: 0 0 10 0; -fx-border-color: transparent transparent #D3D9DE transparent; -fx-border-width: 0 0 1 0;");
                ulasanBox.getChildren().add(itemUlasan);
            }
        }

        ulasanBox.getChildren().addAll(lblSectionUlasan, ulasanCardContainer);
        bottomLayout.getChildren().addAll(infoOrganisasiBox, ulasanBox);

        rootContent.getChildren().addAll(bannerAnchor, metaBox, bottomLayout);
        scrollPane.setContent(rootContent);
        this.getChildren().add(scrollPane);
    }

    public void tampilkanEditProfil() {
        this.getChildren().clear();

        OrganizerProfile org = mainDashboard.getCurrentOrganizer();
        String namaAsli = (org != null && org.getName() != null) ? org.getName() : "";
        String emailAsli = (org != null && org.getEmail() != null) ? org.getEmail() : "";
        String telpAsli = (org != null && org.getPhoneNumber() != null) ? org.getPhoneNumber() : "";
        String roleAsli = (org != null && org.getRole() != null && !org.getRole().isEmpty()) ? org.getRole() : "Penyelenggara";
        String inisial = namaAsli.isEmpty() ? "U" : namaAsli.substring(0, 1).toUpperCase();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        VBox rootContent = new VBox(25);
        rootContent.setPadding(new Insets(30, 40, 30, 40));

        AnchorPane bannerAnchor = createProfileBanner(inisial);
        if(bannerAnchor.getChildren().size() > 2) {
            bannerAnchor.getChildren().get(2).setVisible(false);
        }

        VBox metaBox = new VBox(10);
        Label lblTagline = new Label("Kami hadir, menghubungkan komunitas dan budaya melalui event yang inspiratif.");
        lblTagline.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-text-fill: #002B5B; -fx-font-size: 14px;");
        metaBox.getChildren().addAll(lblTagline);

        VBox formSectionBox = new VBox(15);
        Label lblSectionForm = new Label("Informasi Organisasi");
        lblSectionForm.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #002B5B; -fx-border-color: transparent transparent transparent #FF9914; -fx-border-width: 0 0 0 4; -fx-padding: 0 0 0 10;");
        
        GridPane formGrid = new GridPane();
        formGrid.setHgap(40);
        formGrid.setVgap(20);
        
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        formGrid.getColumnConstraints().addAll(col, col);

        TextField tfNama = new TextField(namaAsli);
        TextField tfEmail = new TextField(emailAsli);
        TextField tfTelepon = new TextField(telpAsli);
        TextField tfPassword = new TextField("********");

        VBox inputNama = createInputField("Nama", tfNama, true);
        VBox inputEmail = createInputField("Email", tfEmail, false);
        VBox inputTelepon = createInputField("No Telepon", tfTelepon, false);
        VBox inputPassword = createInputField("Password", tfPassword, false); 
        
        // 🎯 FIX: Tampilkan role di halaman edit profil, tapi dalam mode non-edit
        VBox fieldRole = createDisabledField("Peran Akun", roleAsli); 

        formGrid.add(inputNama, 0, 0);
        formGrid.add(inputEmail, 1, 0);
        formGrid.add(inputTelepon, 0, 1);
        formGrid.add(inputPassword, 1, 1);
        formGrid.add(fieldRole, 0, 2);

        Button btnSimpan = new Button("Simpan Perubahan");
        btnSimpan.setStyle("-fx-background-color: #FF9914; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-padding: 8px 25px; -fx-cursor: hand;");
        btnSimpan.setOnAction(e -> {
            if (org != null) {
                org.setName(tfNama.getText());
                org.setEmail(tfEmail.getText());
                org.setPhoneNumber(tfTelepon.getText());
                
                OrganizerDAO dao = new OrganizerDAO();
                boolean isSuccess = dao.updateProfile(org); 
                
                if(isSuccess) {
                    System.out.println("Profil berhasil diperbarui!");
                } else {
                    System.out.println("Gagal memperbarui profil ke DB.");
                }

                mainDashboard.refreshSidebarName(org.getName());
                tampilkanLihatProfil();
            }
        });
        
        HBox actionRow = new HBox(btnSimpan);
        actionRow.setAlignment(Pos.CENTER_RIGHT);
        actionRow.setPadding(new Insets(15, 0, 0, 0));

        formSectionBox.getChildren().addAll(lblSectionForm, formGrid, actionRow);

        rootContent.getChildren().addAll(bannerAnchor, metaBox, formSectionBox);
        scrollPane.setContent(rootContent);
        this.getChildren().add(scrollPane);
    }

    public void tampilkanHalamanSemuaUlasan() {
        this.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");

        VBox rootContent = new VBox(25);
        rootContent.setPadding(new Insets(30, 40, 30, 40));

        Label lblJudulUlasan = new Label("Semua Ulasan");
        lblJudulUlasan.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #003A6C;");

        VBox boxDaftarUlasan = new VBox(15);
        boxDaftarUlasan.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-radius: 16px; -fx-padding: 25px;");
        HBox.setHgrow(boxDaftarUlasan, Priority.ALWAYS);

        List<UlasanData> listUlasan = getUlasanDariDB();
        
        if (listUlasan.isEmpty()) {
            Label lblKosong = new Label("Belum ada ulasan.");
            lblKosong.setStyle("-fx-font-family: 'Poppins'; -fx-text-fill: #9CB1C6; -fx-font-size: 14px;");
            boxDaftarUlasan.getChildren().add(lblKosong);
        } else {
            for (int i = 0; i < listUlasan.size(); i++) {
                UlasanData ulasan = listUlasan.get(i);
                boxDaftarUlasan.getChildren().add(createUlasanItemFullWidth(ulasan.username, ulasan.komentar, ulasan.rating));
                
                if (i < listUlasan.size() - 1) {
                    boxDaftarUlasan.getChildren().add(new Separator());
                }
            }
        }

        Button btnKembali = new Button("< Kembali");
        btnKembali.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF9914; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 0;");
        btnKembali.setOnAction(e -> tampilkanLihatProfil());

        rootContent.getChildren().addAll(btnKembali, lblJudulUlasan, boxDaftarUlasan);
        scrollPane.setContent(rootContent);
        this.getChildren().add(scrollPane);
    }
    
    // 🎯 METODE DATABASE SEKARANG BERADA DI LUAR TAMPILKAN HALAMAN
    private List<UlasanData> getUlasanDariDB() {
        List<UlasanData> list = new ArrayList<>();
        
        // 1. Ambil ID Organizer dari UserSession
        if (UserSession.getInstance() == null) return list;
        
        int currentUserId = UserSession.getInstance().getUserId();
        gradleproject.dao.OrganizerDAO orgDao = new gradleproject.dao.OrganizerDAO();
        gradleproject.models.OrganizerProfile orgProfile = orgDao.findByUserId(currentUserId);
        
        // Jika profil organizer tidak ditemukan, kembalikan list kosong
        if (orgProfile == null) return list; 
        
        int orgId = orgProfile.getId();
        
        // 2. Query JOIN untuk menarik data ulasan, pengguna, dan acara
        String sql = "SELECT r.review_text, r.rating, u.username as reviewer_name, e.title as event_title " +
                     "FROM reviews r " +
                     "JOIN events e ON r.event_id = e.id " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE e.organizer_id = ? " +
                     "ORDER BY r.id DESC";
                     
        try (java.sql.Connection conn = gradleproject.config.DbConnect.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, orgId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Ambil data dari hasil query
                String namaPengulas = rs.getString("reviewer_name"); 
                String judulEvent = rs.getString("event_title");
                String komentar = rs.getString("review_text");
                int rating = rs.getInt("rating");
                
                // Format nama agar memunculkan info acara (Contoh: "Zahwa (AdaFest)")
                String namaDitampilkan = namaPengulas + " (" + judulEvent + ")";
                
                // Masukkan ke dalam list
                list.add(new UlasanData(namaDitampilkan, komentar, rating));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error tarik ulasan dari DB: " + e.getMessage());
            e.printStackTrace();
        }
        
        return list;
    }

    private AnchorPane createProfileBanner(String avatarInisial) {
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefHeight(180);

        Pane bannerBg = new Pane();
        bannerBg.setPrefHeight(140);
        bannerBg.setStyle("-fx-background-color: #002B5B; -fx-background-radius: 25px; -fx-background-image: url('/aset/banner-phinisi.jpg'); -fx-background-size: cover; -fx-background-position: center;");
        AnchorPane.setTopAnchor(bannerBg, 0.0);
        AnchorPane.setLeftAnchor(bannerBg, 0.0);
        AnchorPane.setRightAnchor(bannerBg, 0.0);

        Label lblAvatar = new Label(avatarInisial);
        lblAvatar.setAlignment(Pos.CENTER);
        lblAvatar.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFCC80, #FF9914); -fx-text-fill: white; -fx-font-size: 42px; -fx-font-weight: bold; -fx-background-radius: 60px; -fx-border-color: white; -fx-border-width: 5px; -fx-border-radius: 60px;");
        lblAvatar.setPrefSize(110, 110);
        lblAvatar.setMinSize(110, 110);
        
        AnchorPane.setTopAnchor(lblAvatar, 65.0);
        AnchorPane.setLeftAnchor(lblAvatar, 35.0);

        Button btnEditProfil = new Button("Edit profil");
        btnEditProfil.setStyle("-fx-background-color: #FF9914; -fx-text-fill: white; -fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-padding: 6px 22px; -fx-cursor: hand;");
        btnEditProfil.setOnAction(e -> tampilkanEditProfil());
        
        AnchorPane.setTopAnchor(btnEditProfil, 150.0);
        AnchorPane.setRightAnchor(btnEditProfil, 10.0);

        anchor.getChildren().addAll(bannerBg, lblAvatar, btnEditProfil);
        return anchor;
    }

    private VBox createDisabledField(String labelName, String valueText) {
        VBox box = new VBox(6);
        Label lbl = new Label(labelName);
        lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #002B5B; -fx-font-weight: bold;");
        
        TextField tf = new TextField(valueText);
        tf.setEditable(false);
        tf.setStyle("-fx-background-color: #F1F5F9; -fx-background-radius: 10px; -fx-border-color: #CBD5E1; -fx-border-radius: 10px; -fx-padding: 12px 15px; -fx-font-family: 'Poppins'; -fx-text-fill: #334155; -fx-font-size: 13px;");
        
        box.getChildren().addAll(lbl, tf);
        return box;
    }

    private VBox createInputField(String labelName, TextField tf, boolean hasEditIcon) {
        VBox box = new VBox(6);
        Label lbl = new Label(labelName);
        lbl.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 13px; -fx-text-fill: #002B5B; -fx-font-weight: bold;");
        
        StackPane inputPane = new StackPane();
        tf.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: #CBD5E1; -fx-border-radius: 10px; -fx-padding: 12px 40px 12px 15px; -fx-font-family: 'Poppins'; -fx-text-fill: #334155; -fx-font-size: 13px;");
        
        inputPane.getChildren().add(tf);

        if (hasEditIcon) {
            Label iconEdit = new Label("✏️"); 
            iconEdit.setStyle("-fx-font-size: 12px; -fx-cursor: hand;");
            StackPane.setAlignment(iconEdit, Pos.CENTER_RIGHT);
            StackPane.setMargin(iconEdit, new Insets(0, 15, 0, 0));
            inputPane.getChildren().add(iconEdit);
        }
        
        box.getChildren().addAll(lbl, inputPane);
        return box;
    }

    private HBox createUlasanItem(String username, String comment, int rating) {
        HBox itemRow = new HBox(12);
        itemRow.setAlignment(Pos.TOP_LEFT);
        itemRow.setPadding(new Insets(5, 0, 5, 0));

        Label circleAvatar = new Label(username.substring(0, 1).toUpperCase());
        circleAvatar.setAlignment(Pos.CENTER);
        circleAvatar.setStyle("-fx-background-color: #99E9F2; -fx-text-fill: #0B7285; -fx-font-weight: bold; -fx-background-radius: 18px; -fx-min-width: 32px; -fx-min-height: 32px; -fx-max-width: 32px; -fx-max-height: 32px; -fx-font-size: 12px;");

        VBox textContent = new VBox(2);
        HBox userRow = new HBox(8);
        userRow.setAlignment(Pos.CENTER_LEFT);
        
        Label lblUser = new Label(username);
        lblUser.setStyle("-fx-font-family: 'Poppins'; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #002B5B;");
        
        String stars = "⭐".repeat(Math.max(1, Math.min(5, rating)));
        Label lblStars = new Label(stars);
        lblStars.setStyle("-fx-font-size: 9px;");
        
        userRow.getChildren().addAll(lblUser, lblStars);

        Label lblComment = new Label(comment);
        lblComment.setWrapText(true);
        lblComment.setStyle("-fx-font-family: 'Poppins'; -fx-font-size: 11px; -fx-text-fill: #556B83;");

        textContent.getChildren().addAll(userRow, lblComment);
        itemRow.getChildren().addAll(circleAvatar, textContent);
        return itemRow;
    }

    private HBox createUlasanItemFullWidth(String username, String comment, int rating) {
        HBox itemRow = createUlasanItem(username, comment, rating);
        itemRow.setPadding(new Insets(8, 0, 8, 0));
        HBox.setHgrow(itemRow, Priority.ALWAYS);
        return itemRow;
    }

    public static class UlasanData {
        public String username;
        public String komentar;
        public int rating;

        public UlasanData(String username, String komentar, int rating) {
            this.username = username;
            this.komentar = komentar;
            this.rating = rating;
        }
    }
}