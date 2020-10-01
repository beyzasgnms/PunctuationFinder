package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Text {
    
    private DataEntryOperator yazar;
    private String content;

    // Constructor
    public Text(DataEntryOperator yazar, String content) {
        this.yazar = yazar;
        this.content = content;
    }

    // Parametresiz constructor
    public Text() {
        this.yazar = new DataEntryOperator();
        this.content = "";
    }

    public DataEntryOperator getYazar() {
        return yazar;
    }

    public void setYazar(DataEntryOperator yazar) {
        this.yazar = yazar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    // Hata bulan metotların tamamı içerikteki ve sözlükteki kelimeyi parametre olarak alıyor
    // İlk metodumuz iki harfin yerini ters basmayla yapılan hatayı buluyor
    private String yerDegistirmeBul(String wordContent, String wordDict) {
        int uzunluk = wordContent.length();
        // Eşleşen harf sayısı ve
        int eslesen = 0;
        // İlk eşleşmeyen harfin indeksi tutuluyor
        int eslesmedi = -1;
        // Kelimedeki harf sayısı kadar dönüyor
        for(int i = 0; i < uzunluk; i++) {
            // Aynı indeksteki harfler aynıysa
            if(wordDict.toLowerCase().charAt(i) == wordContent.toLowerCase().charAt(i)) {
                // Eşleşen harf sayısı artırılıyor
                eslesen++;
            }
            // Değilse ve
            else {
                // Daha önce eşleşmeyen harf yoksa
                if(eslesmedi == -1) {
                    // O harfin indeksi tutuluyor
                    eslesmedi = i;
                }
            }
        }
        // Eşleşme sayısı uzunluktan 2 eksikse
        if(eslesen == uzunluk - 2) {
            // Ve çapraz indekslerdeki harfler eşleşiyorsa
            if(wordDict.toLowerCase().charAt(eslesmedi) == wordContent.toLowerCase().charAt(eslesmedi + 1)) {
                if(wordDict.toLowerCase().charAt(eslesmedi + 1) == wordContent.toLowerCase().charAt(eslesmedi)) {
                    // Doğru kelime döndürülüyor
                    return wordDict;
                }
            }
        }
        // Kelime düzeltilemezse 0 döndürülüyor
        return "0";
    }
    
    // Bu metodumuzda bir harfe 2 kere basma hatası buluyoruz
    private String ikiKereBasmaBul(String wC, String wD) {
        int uzunluk = wC.length();
        // Fazla harfi silebilmek için StringBuffer kullandık
        StringBuffer sb;
        sb = new StringBuffer();
        sb.append(wC);
        // İndeks sıkıntısı yaşamamak için for döngüsünü harf sayısından 1 eksik döndürüyoruz
        for(int i = 0; i < uzunluk - 1; i++) {
            // Arka arkaya iki harf aynıysa
            if(wC.toLowerCase().charAt(i) == wC.toLowerCase().charAt(i + 1)){
                // O harf siliniyor ve kelimenin o hali sözlüktekiyle karşılaştırılıyor
                sb.deleteCharAt(i);
                if(sb.toString().equals(wD)) {
                    // Eşleşme bulunursa sözlükteki kelime döndürülüyor
                    return wD;
                }
                else {
                    // Bulunamazsa harf yerine ekleniyor ve döngü dönmeye devam ediyor
                    sb.insert(i, wC.charAt(i));
                }
            }
        }
        // Hiçbir sonuç çıkmazsa 0 döndürülüyor
        return "0";
    }
    
    // 3. ve son metodumuz klavyede doğru harfin yanındaki harfe basınca gerçekleşen hata tespit ediliyor
    private String yanaBasmaBul(String wC, String wD) {
        // Tüm satırlardaki tüm harfler birer birer arraylist'e alınıyor
        String satir1 = " qwertyuiop ";
        String[] s1 = satir1.split("");
        ArrayList<String> array1 = new ArrayList<>(Arrays.asList(s1));
        String satir2 = " asdfghjkl ";
        String[] s2 = satir2.split("");
        ArrayList<String> array2 = new ArrayList<>(Arrays.asList(s2));
        String satir3 = " zxcvbnm ";
        String[] s3 = satir3.split("");
        ArrayList<String> array3 = new ArrayList<>(Arrays.asList(s3));
        StringBuffer sb;
        sb = new StringBuffer();
        sb.append(wC);
        for(int i = 0; i < wC.length(); i++) {
            // .contains metodu String parametresi istediği için geçerli harf önce char sonra String yapılıyor
            char harf = wC.toLowerCase().charAt(i);
            String h = harf + "";
            // Harf ilk satırdaysa
            if(satir1.contains(h)) {
                int index = array1.indexOf(h);
                // İlk satırdaki harfleri tutan arraylist'te bir önceki indekse bakılıyor
                sb.setCharAt(i, array1.get(index - 1).charAt(0));
                if(sb.toString().equalsIgnoreCase(wD)) {
                    // Eşleşme çıkarsa kelime döndürülüyor
                    return wD;
                }
                // Çıkmazsa bir sonraki indekse bakılıyor
                sb.setCharAt(i, array1.get(index + 1).charAt(0));
                if(sb.toString().equalsIgnoreCase(wD)) {
                    // Eşleşme çıkarsa kelime döndürülüyor
                    return wD;
                }
                // İkisinde de çıkmazsa kelime eski haline döndürülüyor
                sb.setCharAt(i, harf);
            }
            // Aynı işlemler 2. ve 3. satır için de yapılıyor
            else if(array2.contains(h)) {
                int index = array2.indexOf(h);
                sb.setCharAt(i, array2.get(index - 1).charAt(0));
                if(sb.toString().equalsIgnoreCase(wD)) {
                    return wD;
                }
                sb.setCharAt(i, array2.get(index + 1).charAt(0));
                if(sb.toString().equalsIgnoreCase(wD)) {
                    return wD;
                }
                sb.setCharAt(i, harf);
            }
            else if(array3.contains(h)) {
                int index = array3.indexOf(h);
                sb.setCharAt(i, array3.get(index - 1).charAt(0));
                if(sb.toString().equalsIgnoreCase(wD)) {
                    return wD;
                }
                sb.setCharAt(i, array3.get(index + 1).charAt(0));
                if(sb.toString().equalsIgnoreCase(wD)) {
                    return wD;
                }
                sb.setCharAt(i, harf);
            }
        }
        // Hiçbir sonuç çıkmaması halinde 0 döndürülüyor
        return "0";
    }
    
    // Yukarıdaki metotlar private olduğundan dışa erişime açık bir metot daha yazdık
    // Main class'ımızda bu metodu kullanıyoruz
    // Bu metodun içinde kelimelerin harf sayısına göre hangi karşılaştırma metodunun kullanılacağı ayarlanıyor
    // Buna göre diğer metotlar çağırılıyor
    public String hataBul(String wC, String wD) {
        String x = "0";
        if(wD.length() == wC.length()) {
            x = this.yerDegistirmeBul(wC, wD);
            if(x.equals("0")) {
                x = this.yanaBasmaBul(wC, wD);
            }
        }
        if(wD.length() + 1 == wC.length()) {
            x = this.ikiKereBasmaBul(wC, wD);
        }
        return x;
    }

    @Override
    public String toString() {
        return content;
    }
    
    // İçeriği düzenlenmiş haliyle güncellememiz isteniyor
    // edit metoduyla bu iş yapılıyor
    public void edit(String wC, String wD) {
        String icerik = this.getContent();
        String yeniIcerik = icerik.replace(wC, wD);
        this.setContent(yeniIcerik);
    }
}
