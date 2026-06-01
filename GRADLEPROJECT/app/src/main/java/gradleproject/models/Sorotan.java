package gradleproject.models;

public class Sorotan {
    private int id;
    private String judul;
    private String deskripsiSingkat;
    private String deskripsiDetail;
    private String imagePath;

    public Sorotan(int id, String judul, String deskripsiSingkat, String deskripsiDetail, String imagePath) {
        this.id = id;
        this.judul = judul;
        this.deskripsiSingkat = deskripsiSingkat;
        this.deskripsiDetail = deskripsiDetail;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getDeskripsiSingkat() { return deskripsiSingkat; }
    public String getDeskripsiDetail() { return deskripsiDetail; }
    public String getImagePath() { return imagePath; }
}