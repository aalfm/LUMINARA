package gradleproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.InputStream;

public class TambahAcaraView extends VBox {

    private int currentStep = 1;

    // Komponen Header Indikator Step
    private HBox stepHeaderIndicator;
    private Label lblStep1, lblStep2, lblStep3;

    // Area Konten Utama Putih
    private StackPane formContentArea;
    private VBox formStep1Content;
    private VBox formStep2Content;
    private VBox formStep3Content;

    // Tombol Navigasi Bawah
    private Button btnSimpanDraft;
    private Button btnNextAction;

    // --- DATA FIELD STEP 1 (Informasi Dasar) ---
    private TextField txtNamaAcara;
    private TextField txtDeskripsiSingkat;
    private TextArea txtDeskripsiDetail;
    private TextField txtTanggal;
    private TextField txtWaktu;
    private TextField txtTempatLokasi;

    // --- DATA FIELD STEP 2 (Pengaturan Event) ---
    private boolean isTiketGratis = true; // Default gratis sesuai mockup
    private VBox cardGratis, cardBerbayar;
    private TextField txtKuota;
    private TextField txtKategori;
    private TextField txtHarga;

    private final int organizerId;

    // --- PREVIEW FIELD STEP 3 (Submit / Ringkasan) ---
    private TextField viewNama, viewTanggal, viewWaktu, viewLokasi, viewKuota, viewKategori, viewHarga;
    private ImageView viewPamfletPreview; // ImageView khusus untuk menampung aset icon/gambar unggahan
    private String imagePath = "";

    public TambahAcaraView(int organizerId) {
        this.organizerId = organizerId;
        this.setSpacing(20);
        this.setPadding(new Insets(20, 40, 20, 40));
        this.getStyleClass().add("tambah-acara-root");

        // 1. Header Greetings
        VBox greetingBox = new VBox(5);
        Label greeting = new Label("Hai, tim.");
        greeting.getStyleClass().add("heading");
        Label subGreeting = new Label("Ingat untuk atur kinerja acara kamu . . .");
        subGreeting.getStyleClass().add("subheading");
        greetingBox.getChildren().addAll(greeting, subGreeting);

        // 2. Build Header Menu Wizard
        buildStepHeaderIndicator();

        // 3. Container Putih
        formContentArea = new StackPane();
        formContentArea.getStyleClass().add("form-white-container");
        VBox.setVgrow(formContentArea, Priority.ALWAYS);

        // Inisialisasi Konten Tiap Step
        initStep1Content();
        initStep2Content();
        initStep3Content();

        // 4. Navigasi Bawah
        HBox navigationBottomBox = new HBox(15);
        navigationBottomBox.setAlignment(Pos.CENTER_RIGHT);
        navigationBottomBox.setPadding(new Insets(10, 0, 10, 0));

        btnSimpanDraft = new Button("Simpan Draft");
        btnSimpanDraft.getStyleClass().add("btn-simpan-draft");

        btnNextAction = new Button("Selanjutnya");
        btnNextAction.getStyleClass().add("btn-next-action");

        navigationBottomBox.getChildren().addAll(btnSimpanDraft, btnNextAction);

        // --- EVENT HANDLING NAVIGATION ---
        lblStep1.setOnMouseClicked(e -> goToStep(1));
        lblStep2.setOnMouseClicked(e -> goToStep(2));
        lblStep3.setOnMouseClicked(e -> {
            updateStep3Preview(); // Sinkronisasikan data sebelum membuka step 3
            goToStep(3);
        });

        btnNextAction.setOnAction(e -> {
            if (currentStep == 1) {
                goToStep(2);
            } else if (currentStep == 2) {
                updateStep3Preview(); // Salin data ke preview step 3
                goToStep(3);
            } else if (currentStep == 3) {
                prosesSelesaiSimpan();
            }
        });

        btnSimpanDraft.setOnAction(e -> {
            System.out.println("Draft acara '" + txtNamaAcara.getText() + "' disimpan.");
        });

        // Tampilan Awal
        goToStep(1);

        // Opsi B: Bungkus area form putih saja ke dalam ScrollPane
        ScrollPane scrollForm = new ScrollPane(formContentArea);
        scrollForm.setFitToWidth(true);
        scrollForm.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollForm.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollForm.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollForm, Priority.ALWAYS);

        this.getChildren().addAll(greetingBox, stepHeaderIndicator, scrollForm, navigationBottomBox);
    }

    private void buildStepHeaderIndicator() {
        stepHeaderIndicator = new HBox(30);
        stepHeaderIndicator.getStyleClass().add("step-header-container");
        stepHeaderIndicator.setPadding(new Insets(12, 20, 12, 20));
        stepHeaderIndicator.setAlignment(Pos.CENTER_LEFT);

        lblStep1 = new Label("1  Informasi");
        lblStep2 = new Label("2  Pengaturan Event");
        lblStep3 = new Label("3  Submit");

        lblStep1.getStyleClass().add("step-indicator-item");
        lblStep2.getStyleClass().add("step-indicator-item");
        lblStep3.getStyleClass().add("step-indicator-item");

        stepHeaderIndicator.getChildren().addAll(lblStep1, lblStep2, lblStep3);
    }

    private void goToStep(int stepNumber) {
        this.currentStep = stepNumber;
        formContentArea.getChildren().clear();

        lblStep1.getStyleClass().remove("step-active");
        lblStep2.getStyleClass().remove("step-active");
        lblStep3.getStyleClass().remove("step-active");

        switch (stepNumber) {
            case 1:
                lblStep1.getStyleClass().add("step-active");
                formContentArea.getChildren().add(formStep1Content);
                btnNextAction.setText("Selanjutnya");
                break;
            case 2:
                lblStep2.getStyleClass().add("step-active");
                formContentArea.getChildren().add(formStep2Content);
                btnNextAction.setText("Selanjutnya");
                break;
            case 3:
                lblStep3.getStyleClass().add("step-active");
                formContentArea.getChildren().add(formStep3Content);
                btnNextAction.setText("Selesai");
                break;
        }
    }

    // =========================================================================
    // STEP 1: INFORMASI DASAR
    // =========================================================================
    private void initStep1Content() {
        formStep1Content = new VBox(15);
        formStep1Content.setPadding(new Insets(25));

        Label sectionTitle1 = new Label("Informasi Dasar");
        sectionTitle1.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #003366;");

        Label lblNama = new Label("Nama acara");
        lblNama.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        txtNamaAcara = new TextField();
        txtNamaAcara.setPromptText("cth. Luminara Fest 2026 ()");
        txtNamaAcara.getStyleClass().add("form-input-field");

        Label lblDescSingkat = new Label("Deskripsi Singkat");
        lblDescSingkat.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        txtDeskripsiSingkat = new TextField();
        txtDeskripsiSingkat.setPromptText("Deskripsikan secara singkat tentang acaramu (maks. 50 kata)");
        txtDeskripsiSingkat.getStyleClass().add("form-input-field");

        Label lblDescDetail = new Label("Deskripsi Detail");
        lblDescDetail.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        txtDeskripsiDetail = new TextArea();
        txtDeskripsiDetail.setPromptText("Deskripsikan secara detail tentang acaramu (maks. 150 kata)");
        txtDeskripsiDetail.getStyleClass().add("form-text-area-field");
        txtDeskripsiDetail.setPrefHeight(80);

        Label sectionTitle2 = new Label("Waktu & Tempat");
        sectionTitle2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #003366; -fx-padding: 10 0 0 0;");

        GridPane gridDateTime = new GridPane();
        gridDateTime.setHgap(20);
        gridDateTime.setVgap(5);
        
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(25);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(50);
        gridDateTime.getColumnConstraints().addAll(c1, c2, c3);

        Label lblTanggal = new Label("Tanggal"); lblTanggal.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        Label lblWaktu = new Label("Waktu"); lblWaktu.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        Label lblTempat = new Label("Tempat/Lokasi"); lblTempat.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");

        txtTanggal = new TextField(); txtTanggal.setPromptText("YYYY-MM-DD"); txtTanggal.getStyleClass().add("form-input-field");
        txtWaktu = new TextField(); txtWaktu.setPromptText("HH:MM"); txtWaktu.getStyleClass().add("form-input-field");
        txtTempatLokasi = new TextField(); txtTempatLokasi.setPromptText("e.g Baruga AP Pettarani Universitas Hasanuddin"); 
        txtTempatLokasi.getStyleClass().add("form-input-field");

        gridDateTime.add(lblTanggal, 0, 0);   gridDateTime.add(lblWaktu, 1, 0);   gridDateTime.add(lblTempat, 2, 0);
        gridDateTime.add(txtTanggal, 0, 1);   gridDateTime.add(txtWaktu, 1, 1);   gridDateTime.add(txtTempatLokasi, 2, 1);

        formStep1Content.getChildren().addAll(sectionTitle1, lblNama, txtNamaAcara, lblDescSingkat, txtDeskripsiSingkat, lblDescDetail, txtDeskripsiDetail, sectionTitle2, gridDateTime);
    }

    // =========================================================================
    // STEP 2: PENGATURAN EVENT
    // =========================================================================
    private void initStep2Content() {
        formStep2Content = new VBox(20);
        formStep2Content.setPadding(new Insets(25));

        // 1. Baris Pilihan Kartu Tiket (Gratis vs Berbayar)
        HBox RowPilihanTiket = new HBox(30);
        RowPilihanTiket.setAlignment(Pos.CENTER);

        cardGratis = buatCardTipeTiket("Tiket Gratis", "Pilih jika acara yang kamu\ndaftarkan tidak memungut biaya.", "/aset/iconLuminara/icon-tiket.png");
        cardBerbayar = buatCardTipeTiket("Tiket Berbayar", "Pilih jika acara yang kamu\ndaftarkan memungut biaya,\nmeski hanya pembayaran pajak.", "/aset/iconLuminara/pendapatan-biru.png");
        
        RowPilihanTiket.getChildren().addAll(cardGratis, cardBerbayar);

        // Logika Klik Toggle Kartu Tiket
        cardGratis.setOnMouseClicked(e -> setTipeTiketAktif(true));
        cardBerbayar.setOnMouseClicked(e -> setTipeTiketAktif(false));

        // 2. Form Rincian Event
        Label sectionTitle = new Label("Rincian Event");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #003366;");

        GridPane gridRincian = new GridPane();
        gridRincian.setHgap(30);
        gridRincian.setVgap(12);
        
        ColumnConstraints colLeft = new ColumnConstraints(); colLeft.setPercentWidth(50);
        ColumnConstraints colRight = new ColumnConstraints(); colRight.setPercentWidth(50);
        gridRincian.getColumnConstraints().addAll(colLeft, colRight);

        // Kuota & Kategori
        Label lblKuota = new Label("Kuota"); lblKuota.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        Label lblKategori = new Label("Kategori"); lblKategori.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        txtKuota = new TextField(); txtKuota.setPromptText("cth. 100"); txtKuota.getStyleClass().add("form-input-field");
        txtKategori = new TextField(); txtKategori.setPromptText("Budaya/Festival/Lokakarya/Musik"); txtKategori.getStyleClass().add("form-input-field");

        // Harga & Pamflet
        Label lblHarga = new Label("Harga"); lblHarga.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        Label lblPamflet = new Label("Pamflet Acara"); lblPamflet.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        
        txtHarga = new TextField("0"); 
        txtHarga.setDisable(true); // Default gratis, kolom terkunci
        txtHarga.getStyleClass().add("form-input-field");

        // Box Upload Pamflet Mockup
        HBox uploadBox = new HBox(10);
        uploadBox.setAlignment(Pos.CENTER);
        uploadBox.getStyleClass().add("form-input-field");
        uploadBox.setStyle("-fx-border-style: dashed; -fx-background-color: #F8FAFC; -fx-cursor: hand;");
        Label lblUpload = new Label("Klik dan Unggah");
        lblUpload.setStyle("-fx-text-fill: #718096;");
        uploadBox.getChildren().add(lblUpload);

        // Aksi klik untuk upload box di Step 2
        uploadBox.setOnMouseClicked(e -> pemicuPilihGambar());

        gridRincian.add(lblKuota, 0, 0); gridRincian.add(lblKategori, 1, 0);
        gridRincian.add(txtKuota, 0, 1); gridRincian.add(txtKategori, 1, 1);
        gridRincian.add(lblHarga, 0, 2); gridRincian.add(lblPamflet, 1, 2);
        gridRincian.add(txtHarga, 0, 3); gridRincian.add(uploadBox, 1, 3);

        formStep2Content.getChildren().addAll(RowPilihanTiket, sectionTitle, gridRincian);
        setTipeTiketAktif(true); // Standar awal
    }

    private VBox buatCardTipeTiket(String title, String desc, String iconPath) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(280, 180);
        box.getStyleClass().add("ticket-type-card"); // Mengambil basis style utama dari CSS
        
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("label-title"); // Identitas penanda judul CSS
        
        Label lblDesc = new Label(desc);
        lblDesc.getStyleClass().add("label-desc");   // Identitas penanda deskripsi CSS
        lblDesc.setWrapText(true);

        try {
            InputStream in = getClass().getResourceAsStream(iconPath);
            if (in != null) {
                ImageView iv = new ImageView(new Image(in));
                iv.setFitWidth(30); iv.setFitHeight(30);
                box.getChildren().addAll(lblTitle, iv, lblDesc);
            } else {
                box.getChildren().addAll(lblTitle, lblDesc);
            }
        } catch(Exception e) {
            box.getChildren().addAll(lblTitle, lblDesc);
        }
        return box;
    }

    private void setTipeTiketAktif(boolean gratis) {
        this.isTiketGratis = gratis;
        
        // Bersihkan seluruh status kelas aktif di CSS terlebih dahulu
        cardGratis.getStyleClass().remove("ticket-type-card-active");
        cardBerbayar.getStyleClass().remove("ticket-type-card-active");

        if (gratis) {
            cardGratis.getStyleClass().add("ticket-type-card-active"); // Aktifkan style biru
            txtHarga.setDisable(true);
            txtHarga.setText("0");
        } else {
            cardBerbayar.getStyleClass().add("ticket-type-card-active"); // Aktifkan style biru
            txtHarga.setDisable(false);
            txtHarga.setText("");
            txtHarga.requestFocus();
        }
    }

    // =========================================================================
    // STEP 3: SUBMIT / SUMMARY PREVIEW (DIPERBAIKI)
    // =========================================================================
    private void initStep3Content() {
        formStep3Content = new VBox(15);
        formStep3Content.setPadding(new Insets(25));

        Label sectionTitle = new Label("Detail Acara");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #003366;");

        HBox splitLayout = new HBox(30);
        VBox leftFields = new VBox(10);
        HBox.setHgrow(leftFields, Priority.ALWAYS);

        // Inisialisasi kolom bertenaga read-only untuk review ringkasan
        viewNama = buatLabelPreviewField("Nama Acara", leftFields);
        
        HBox row1 = new HBox(15);
        VBox c1 = new VBox(5); VBox c2 = new VBox(5); HBox.setHgrow(c1, Priority.ALWAYS); HBox.setHgrow(c2, Priority.ALWAYS);
        viewTanggal = buatLabelPreviewField("Tanggal", c1);
        viewWaktu = buatLabelPreviewField("Waktu", c2);
        row1.getChildren().addAll(c1, c2);
        leftFields.getChildren().add(row1);

        viewLokasi = buatLabelPreviewField("Lokasi", leftFields);

        HBox row2 = new HBox(15);
        VBox c3 = new VBox(5); VBox c4 = new VBox(5); HBox.setHgrow(c3, Priority.ALWAYS); HBox.setHgrow(c4, Priority.ALWAYS);
        viewKuota = buatLabelPreviewField("Kuota", c3);
        viewKategori = buatLabelPreviewField("Kategori", c4);
        row2.getChildren().addAll(c3, c4);
        leftFields.getChildren().add(row2);

        viewHarga = buatLabelPreviewField("Harga", leftFields);

        // Sisi Kanan: Menggunakan StackPane agar ImageView bisa melekat rapi di dalam bingkai kontainer
        StackPane rightImageFrame = new StackPane();
        rightImageFrame.setPrefSize(280, 320);
        rightImageFrame.setStyle("-fx-border-color: #003A6C; -fx-border-radius: 15; -fx-background-color: #F8FAFC; -fx-background-radius: 15; -fx-cursor: hand;");
        
        // Memakai ImageView bawaan untuk memuat aset icon gallery kamu dari folder resource
        viewPamfletPreview = new ImageView();
        viewPamfletPreview.setFitWidth(60); 
        viewPamfletPreview.setFitHeight(60);
        viewPamfletPreview.setPreserveRatio(true);

        // Memuat icon bawaan sebagai placeholder sebelum ada gambar yang diunggah
        try {
            InputStream in = getClass().getResourceAsStream("/aset/iconLuminara/icon-gambar.png"); // <--- Sesuaikan dengan nama icon gambarmu
            if (in != null) {
                viewPamfletPreview.setImage(new Image(in));
            }
        } catch (Exception e) {
            System.out.println("Aset icon galeri preview tidak ditemukan.");
        }

        rightImageFrame.getChildren().add(viewPamfletPreview);
        
        // AKSI KLIK: Kotak preview di Step 3 sekarang bisa merespon klik untuk mengganti gambar
        rightImageFrame.setOnMouseClicked(e -> pemicuPilihGambar());

        splitLayout.getChildren().addAll(leftFields, rightImageFrame);
        formStep3Content.getChildren().addAll(sectionTitle, splitLayout);
    }

    private TextField buatLabelPreviewField(String title, VBox parentContainer) {
        Label lbl = new Label(title);
        lbl.setStyle("-fx-text-fill: #003A6C; -fx-font-weight: bold;");
        TextField tf = new TextField();
        tf.setEditable(false); // Mengunci agar tidak bisa diedit di halaman konfirmasi
        tf.getStyleClass().add("form-input-field");
        parentContainer.getChildren().addAll(lbl, tf);
        return tf;
    }

    // Fungsi pembantu terpusat untuk memanggil FileChooser sistem operasi
    private void pemicuPilihGambar() {
    javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
    fileChooser.setTitle("Pilih Pamflet Acara");
    fileChooser.getExtensionFilters().addAll(
        new javafx.stage.FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
    );
    
    java.io.File selectedFile = (this.getScene() != null && this.getScene().getWindow() != null) ? 
        fileChooser.showOpenDialog(this.getScene().getWindow()) : null;
    
    if (selectedFile != null) {
        // 🎯 FIX: Simpan path lengkapnya ke variabel imagePath
        this.imagePath = selectedFile.getAbsolutePath(); 
        
        Image imageBaru = new Image(selectedFile.toURI().toString());
        viewPamfletPreview.setFitWidth(260);
        viewPamfletPreview.setFitHeight(300);
        viewPamfletPreview.setImage(imageBaru);
        
        System.out.println("DEBUG: Gambar terpilih: " + this.imagePath);
        }
    }

    // Menggabungkan isi input dari step 1 dan 2 untuk direview di step 3
    private void updateStep3Preview() {
        viewNama.setText(txtNamaAcara.getText());
        viewTanggal.setText(txtTanggal.getText());
        viewWaktu.setText(txtWaktu.getText());
        viewLokasi.setText(txtTempatLokasi.getText());
        viewKuota.setText(txtKuota.getText());
        viewKategori.setText(txtKategori.getText());
        viewHarga.setText(isTiketGratis ? "Gratis (Rp 0)" : txtHarga.getText());
    }

    private void prosesSelesaiSimpan() {
        try {
            // 1. Bersihkan Data Harga
            String rawHarga = txtHarga.getText().replaceAll("[^0-9]", ""); 
            double harga = rawHarga.isEmpty() ? 0 : Double.parseDouble(rawHarga);

            // 2. Format tanggal/waktu (Menambahkan :00.0 agar Timestamp Java tidak error)
            String dateTimeStr = txtTanggal.getText() + " " + txtWaktu.getText() + ":00.0";
            java.sql.Timestamp eventTimestamp = java.sql.Timestamp.valueOf(dateTimeStr);

            // 3. Buat objek Event
            gradleproject.models.Event newEvent = new gradleproject.models.Event(
                0,
                this.organizerId,
                txtNamaAcara.getText(),
                txtDeskripsiDetail.getText(),
                txtKategori.getText(),
                isTiketGratis ? "Free" : "Paid",
                "Draft", // 🎯 UBAH INI DARI "Active" MENJADI "Draft"
                Integer.parseInt(txtKuota.getText()), 
                harga,
                eventTimestamp,
                txtTempatLokasi.getText(), 
                this.imagePath // Pastikan imagePath sudah menyimpan path lengkap dari file yang dipilih di step 2
            );

            // 4. Simpan ke Database
            gradleproject.services.OrganizerManagementService service = new gradleproject.services.OrganizerManagementService();
            service.createEventDraft(newEvent);

            // Jika sampai ke baris ini berarti BERHASIL (tidak ada error dari DB)
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukses");
            alert.setContentText("Acara berhasil dipublikasikan.");
            alert.showAndWait();
            
        } catch (java.sql.SQLException sqlError) {
            // 🎯 INI KUNCI UTAMANYA: Menampilkan error langsung dari SQLite ke layar
            showError("Ditolak oleh Database:\n" + sqlError.getMessage());
        } catch (NumberFormatException e) {
            showError("Data angka tidak valid (Kuota/Harga). Pastikan hanya berisi angka.");
        } catch (IllegalArgumentException e) {
            showError("Format Tanggal salah. Gunakan: YYYY-MM-DD dan Waktu: HH:MM\nContoh: 2026-09-10 dan 08:00");
        } catch (Exception e) {
            showError("Terjadi kesalahan sistem:\n" + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gagal");
        alert.setContentText(message);
        alert.showAndWait();
    }
}