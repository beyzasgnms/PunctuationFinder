package main;

// Programda dosya okumak için scanner ve io metotlarıyla beraber
// Listelemelerde kolaylık açısından arraylist'i import ettik
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    
    public static void main(String[] args) {
        // İstatistikleri saklarken bize gerekecek olan değişkenleri tanımladık
        int toplamKelimeSay = 0;
        int dogruYazilanKelimeSay = 0;
        int yanlisYazilanKelimeSay = 0;
        int duzeltilenKelimeSay = 0;
        int duzeltilemeyenKelimeSay = 0;
        int muhendisSay = 0;
        int doktorSay = 0;
        double muhendisDogruOran = 0;
        double doktorYanlisOran = 0;
        double algoBasariOran = 0;
        
        // Sayı bulunduran kelimeler doğru kabul edileceğinden sayıları tutan bir liste oluşturduk
        String[] sayilar = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        // Okunacak dosyalar için scannerlar tanımlanıyor
        Scanner sozlukOku = null;
        // Scanner metadosyaOku = null;
        Scanner icerikOku = null;
        
        // Sözlük dosyası okunuyor
        try {
            sozlukOku = new Scanner(new FileInputStream("words.txt"));
            // metadosyaOku = new Scanner(new FileInputStream("icerikler.txt"));
        }
        catch(FileNotFoundException e) {
            System.out.println("Dosya bulunamadı.");
            System.exit(0);
        }
        
        // Sözlükteki her bir kelimeyi arrayliste yollamak için gerekenler hazırlanıyor
        ArrayList<String> sozlukKelimeList = new ArrayList<>();
        String sozlukKelime = sozlukOku.next();
        while(sozlukOku.hasNext()) {
            sozlukKelimeList.add(sozlukKelime);
            sozlukKelime = sozlukOku.next();
        }
        int sozlukKelimeSay = sozlukKelimeList.size();
        sozlukOku.close();
        // Sözlüğü komple bir liste içine aldığımızdan scanner'la işimiz bitti
        
        ArrayList<String> icerikDosyaList = new ArrayList<>();
//        String dosya = metadosyaOku.next();
//        while(metadosyaOku.hasNext()) {
//            icerikDosyaList.add(dosya);
//            dosya = metadosyaOku.next();
//        }
        /* Meta-Dosya kullanma işini bir türlü başaramadım.
        Her seferinde hata verdi. Bu yüzden dosyaları manuel ekledim.
        */
        icerikDosyaList.add("icerik1.txt");
        icerikDosyaList.add("icerik2.txt");
        icerikDosyaList.add("icerik3.txt");
        icerikDosyaList.add("icerik4.txt");
        icerikDosyaList.add("icerik5.txt");
        
        // İçeriklerle ilgili değişkenleri ve arrayleri tanımlıyoruz
        String deo, icerik;
        String[] deoElemanlar = null;
        String[] icerikKelimeListesi = null;
        DataEntryOperator yazar = null;
        DataEntryOperator[] yazarlar;
        yazarlar = new DataEntryOperator[icerikDosyaList.size()];
        Text t = null;
        Text[] texts;
        texts = new Text[icerikDosyaList.size()];
        int icerikKelimeSay = 0;
        
        // İstatistikleri saklayacağımız iki boyutlu matrisimiz de hazır
        double[][] stats = new double[icerikDosyaList.size()][6];
        
        // İlk for döngümüz dosya sayısı kadar dönüyor
        // Her bir dosya için işlem sona erdiğinde başa dönüyor
        for(int dosyaNo = 0; dosyaNo < icerikDosyaList.size(); dosyaNo++) {
            // İlk dosyamızı okuyoruz
            try {
                icerikOku = new Scanner(new FileInputStream(icerikDosyaList.get(dosyaNo)));
            }
            catch(IOException e) {
                System.out.println("İçerik dosyası okunamadı.");
            }
            // Dosyadaki ilk satır operatörle ilgili bilgileri taşıyor
            // Bunu bildiğimizden DataEntryOperator sınıfındaki nesnemizin önce parametrelerini...
            deo = icerikOku.nextLine();
            deoElemanlar = deo.split(", ");
            String IDstr = deoElemanlar[0];
            int ID = Integer.parseInt(IDstr);
            String adSoyad = deoElemanlar[1];
            String bolum = deoElemanlar[2];
            // ...sonra kendisini oluşturuyoruz ve ilgili listeye yolluyoruz
            yazar = new DataEntryOperator(ID, adSoyad, bolum);
            yazarlar[dosyaNo] = yazar;
            
            // Operatörle ilgili bilgilerden sonra metnimiz geliyor
            // Metindeki her bir kelimeye tek tek bakılacağından kelimeler listeye alınıyor
            icerik = icerikOku.nextLine();
            icerikKelimeListesi = icerik.split(" ");
            icerikKelimeSay = icerikKelimeListesi.length;
            
            // Yazarımız ve içeriğimiz oluşturulduğuna göre Text sınıfındaki nesnemizi oluşturabiliriz
            t = new Text(yazar, icerik);
            texts[dosyaNo] = t;
            
            // İçerideki for döngüsü, metindeki kelime sayısı kadar dönüyor
            // Her kelime teker teker kontrol ediliyor
            for(int i = 0; i < icerikKelimeSay; i++) {
                String icerikKelime = icerikKelimeListesi[i];
                // İçeriği güncellerken hatalı kelimeyi değiştireceğiz
                // Bu yüzden kelimenin düzeltilmemiş halini de saklıyoruz
                String orijinalKelime = icerikKelime;
                // Sözlükteki kelimelerin hepsi küçük harfle başladığından kelimeleri küçük harfli biçimde denetliyoruz
                icerikKelime = icerikKelime.toLowerCase();
                // Sayısal değer varsa doğru sayılacak
                for(int rakam = 0; rakam < 10; rakam++) {
                    if(icerikKelime.contains(sayilar[rakam])) {
                        dogruYazilanKelimeSay++;
                        break;
                    }
                }
                // Sözlükteki kelimeler noktalama işareti içermiyor
                // Bu yüzden sonunda noktalama işareti olan kelimeleri denetlerken o işareti kaldırmamız lazım
                if(icerikKelime.endsWith(".") || icerikKelime.endsWith(",") || icerikKelime.endsWith(";")
                        || icerikKelime.endsWith("?") || icerikKelime.endsWith("!")) {
                    // StringBuffer'la bu işi yapıyoruz
                    StringBuffer sb;
                    sb = new StringBuffer();
                    sb.append(icerikKelime);
                    sb.deleteCharAt(icerikKelime.length() - 1);
                    icerikKelime = sb.toString();
                }
                // Bazı kelimeler parantez içinde yazılmış da olabilir
                if((icerikKelime.startsWith("(") && icerikKelime.endsWith(")")) || (icerikKelime.startsWith("\"") && icerikKelime.endsWith("\""))) {
                    StringBuffer sb;
                    sb = new StringBuffer();
                    sb.append(icerikKelime);
                    sb.deleteCharAt(icerikKelime.length() - 1);
                    sb.deleteCharAt(0);
                    icerikKelime = sb.toString();
                }
                // Kelime kısa çizgiyle ayrılmış iki kelimeden oluşmuş olabilir
                // Böyle bir durumda iki parçayı ayrı ayrı inceliyoruz
                if(icerikKelime.contains("-")) {
                    StringBuffer sb;
                    sb = new StringBuffer();
                    sb.append(icerikKelime);
                    int index = sb.indexOf("-");
                    sb.deleteCharAt(index);
                    sb.insert(index, " ");
                    // Kısa çizgiye kadar olan bölümü ve
                    String part1 = sb.substring(0, sb.indexOf(" "));
                    // Kısa çizgiden sonraki bölümü ayırıyoruz
                    String part2 = sb.substring(sb.indexOf(" ") + 1);
                    // Sözlükte bu kelime varsa doğru sayısını artırıyoruz
                    if(sozlukKelimeList.contains(part1)) {
                        dogruYazilanKelimeSay++;
                    }
                    // Yoksa düzeltmek için gerekli metodu çağırıyoruz
                    else {
                        yanlisYazilanKelimeSay++;
                        String duzeltPart1 = null;
                        for(int j = 0; j < sozlukKelimeSay; j++) {
                            sozlukKelime = sozlukKelimeList.get(j);
                            duzeltPart1 = t.hataBul(part1, sozlukKelime);
                            // Metoddan dönen sonuç 0 değilse düzeltilmiş demektir
                            // Düzeltilen sayısı artırılıyor ve döngüden çıkılıyor
                            if(!duzeltPart1.equals("0")) {
                                duzeltilenKelimeSay++;
                                break;
                            }
                        }
                        // Döngü bittiğinde kelime düzeltilememişse düzeltilemeyen sayısı artırılıyor
                        if(duzeltPart1.equals("0")) {
                            duzeltilemeyenKelimeSay++;
                        }
                    }
                    // Aynı işlemler ikinci parça için tekrarlanıyor
                    if(sozlukKelimeList.contains(part2)) {
                        dogruYazilanKelimeSay++;
                    }
                    else {
                        yanlisYazilanKelimeSay++;
                        String duzeltPart2 = null;
                        for(int j = 0; j < sozlukKelimeSay; j++) {
                            sozlukKelime = sozlukKelimeList.get(j);
                            duzeltPart2 = t.hataBul(part2, sozlukKelime);
                            if(!duzeltPart2.equals("0")) {
                                duzeltilenKelimeSay++;
                                break;
                            }
                        }
                        if(duzeltPart2.equals("0")) {
                            duzeltilemeyenKelimeSay++;
                        }
                    }
                }
                // Kelime kısa çizgiyle ayrılmamış bir kelimeyse yine aynı işlemler bu sefer tek kelime için uygulanıyor
                else if(sozlukKelimeList.contains(icerikKelime)) {
                    dogruYazilanKelimeSay++;
                }
                else {
                    yanlisYazilanKelimeSay++;
                    String duzelt = null;
                    for(int j = 0; j < sozlukKelimeSay; j++) {
                        sozlukKelime = sozlukKelimeList.get(j);
                        duzelt = t.hataBul(icerikKelime, sozlukKelime);
                        if(!duzelt.equals("0")) {
                            duzeltilenKelimeSay++;
                            t.edit(orijinalKelime, duzelt);
                            break;
                        }
                    }
                    if(duzelt.equals("0")) {
                        duzeltilemeyenKelimeSay++;
                    }
                }
            }
            // For döngümüz bitti, yani metinde okunmayan kelime kalmadı
            // Şimdi istatistiklerimizi ilgili listeye yazıyoruz
            toplamKelimeSay = dogruYazilanKelimeSay + yanlisYazilanKelimeSay;
            stats[dosyaNo][0] = toplamKelimeSay;
            stats[dosyaNo][1] = dogruYazilanKelimeSay;
            stats[dosyaNo][2] = duzeltilenKelimeSay;
            stats[dosyaNo][3] = duzeltilemeyenKelimeSay;
            stats[dosyaNo][4] = dogruYazilanKelimeSay / toplamKelimeSay;
            if(duzeltilenKelimeSay == 0 && duzeltilemeyenKelimeSay == 0) {
                // Sıfıra bölme hatası çıkınca java çıktı olarak NaN (not a number) çıkarıyor
                // Buna önlem olarak if-else koyduk
                stats[dosyaNo][5] = 0;
            }
            else {
                stats[dosyaNo][5] = (double)duzeltilenKelimeSay / (double)(duzeltilenKelimeSay + duzeltilemeyenKelimeSay);
            }
            algoBasariOran += stats[dosyaNo][5];
            
            // Operatörün bölümüne göre sayısı ve istatistiği düzenleniyor
            if(yazar.getDepartman().contains("Tip")) {
                doktorSay++;
                doktorYanlisOran += (double)yanlisYazilanKelimeSay / (double)toplamKelimeSay;
            }
            else if(yazar.getDepartman().contains("Muhendis")) {
                muhendisSay++;
                muhendisDogruOran += (double)dogruYazilanKelimeSay / (double)toplamKelimeSay;
            }
            
            // Yeni dosyaya geçmeden önce veriler sıfırlanıyor
            dogruYazilanKelimeSay = 0;
            yanlisYazilanKelimeSay = 0;
            duzeltilenKelimeSay = 0;
            duzeltilemeyenKelimeSay = 0;
        }
        // Tüm dosyalar bittikten sonra okuyucu kapatılıyor ve veriler yazdırılıyor
        icerikOku.close();
        System.out.println("Tüm içerikler için;");
        System.out.print("a) Toplam Kelime Sayısı: ");
        System.out.println((int)(stats[0][0] + stats[1][0] + stats[2][0] + stats[3][0] + stats[4][0]));
        System.out.print("b) Doğru Yazılan Kelime Sayısı: ");
        System.out.println((int)(stats[0][1] + stats[1][1] + stats[2][1] + stats[3][1] + stats[4][1]));
        System.out.print("c) Düzeltilen Kelime Sayısı: ");
        System.out.println((int)(stats[0][2] + stats[1][2] + stats[2][2] + stats[3][2] + stats[4][2]));
        System.out.print("d) Mühendislerin Doğru Yazım Ortalaması: %");
        System.out.printf("%.2f %n", (muhendisDogruOran / muhendisSay) * 100);
        System.out.print("e) Doktorların Yanlış Yazım Ortalaması: %");
        System.out.printf("%.2f %n", (doktorYanlisOran / doktorSay) * 100);
        System.out.print("f) Algoritmanın Başarı Oranı Ortalaması: %");
        System.out.printf("%.2f %n", (algoBasariOran / 5.0) * 100.0);
    }
}
