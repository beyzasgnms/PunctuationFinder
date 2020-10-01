package main;

public class DataEntryOperator {
    
    private int id;
    private String adSoyad;
    private String departman;

    // Constructor
    public DataEntryOperator(int id, String adSoyad, String departman) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.departman = departman;
    }
    
    // Parametresiz constructor
    public DataEntryOperator() {
        this(0, "", "");
    }

    @Override
    public String toString() {
        return "Bu metin " + departman + "'nde çalışan " + id + " kimlik numaralı " + adSoyad + " tarafından yazılmıştır.";
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }
}
