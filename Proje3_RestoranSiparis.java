/**
* Ad Soyad: Ümitcan ÇİNAR
* Numara: 250541009
* Proje: RestoranSiparis
* Tarih: [20/11/20255]
*/


import java.util.Scanner;
import java.text.DecimalFormat;

public class AkilliRestoranSistem {

    // DecimalFormat, fiyatları iki ondalık basamaklı olarak göstermek için kullanılır.
    private static final DecimalFormat df = new DecimalFormat("0.00");

    // --- Zorunlu Fiyat Metotları (Switch-Case Kullanımı) ---

    /**
     * Seçilen ana yemeğin fiyatını döndürür.
     * @param secim Ana yemek seçimi (1-4).
     * @return Fiyat (TL).
     */
    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0; // Izgara Tavuk
            case 2: return 120.0; // Adana Kebap
            case 3: return 110.0; // Levrek
            case 4: return 65.0; // Mantı
            default: return 0.0; // Geçersiz veya seçim yok
        }
    }

    /**
     * Seçilen başlangıcın fiyatını döndürür.
     * @param secim Başlangıç seçimi (1-3).
     * @return Fiyat (TL).
     */
    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0; // Çorba
            case 2: return 45.0; // Humus
            case 3: return 55.0; // Sigara Böreği
            default: return 0.0;
        }
    }

    /**
     * Seçilen içeceğin fiyatını döndürür.
     * @param secim İçecek seçimi (1-4).
     * @return Fiyat (TL).
     */
    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0; // Kola
            case 2: return 12.0; // Ayran
            case 3: return 35.0; // Taze Meyve Suyu
            case 4: return 25.0; // Limonata
            default: return 0.0;
        }
    }

    /**
     * Seçilen tatlının fiyatını döndürür.
     * @param secim Tatlı seçimi (1-3).
     * @return Fiyat (TL).
     */
    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0; // Künefe
            case 2: return 55.0; // Baklava
            case 3: return 35.0; // Sütlaç
            default: return 0.0;
        }
    }
    
    // --- Zorunlu Durum Kontrol Metotları ---

    /**
     * Combo menü (Ana yemek + İçecek + Tatlı) siparişi verilip verilmediğini kontrol eder.
     */
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return anaVar && icecekVar && tatliVar;
    }

    /**
     * Sipariş saatinin Happy Hour (14:00-17:00 arası) aralığında olup olmadığını kontrol eder.
     * @param saat Siparişin verildiği saat (8-23).
     */
    public static boolean isHappyHour(int saat) {
        return saat >= 14 && saat < 17;
    }

    // --- Zorunlu Hesaplama Metotları ---
    
    /**
     * Uygulanması gereken tüm indirimleri hesaplar ve nihai toplam tutarı döndürür.
     * Önemli: Happy Hour indirimi sadece içeceğe uygulanır, diğer indirimler genel tutara uygulanır.
     *
     * @param araToplam Başlangıç + Ana Yemek + Tatlı toplamı.
     * @param icecekFiyati Sadece içecek fiyatı.
     * @param combo Combo menü indiriminin uygulanıp uygulanmayacağı.
     * @param ogrenci Öğrenci indiriminin uygulanıp uygulanmayacağı.
     * @param gun Haftanın günü (1=Pzt, ..., 7=Paz) öğrenci indirimi için.
     * @param saat Sipariş saati (Happy Hour için).
     * @return Tüm indirimler uygulandıktan sonraki nihai tutar.
     */
    public static double calculateDiscount(double araToplam, double icecekFiyati, boolean combo, boolean ogrenci, int gun, int saat) {
        double toplamIndirimTutari = 0.0;
        double genelToplam = araToplam;
        double indirimliIçecekFiyati = icecekFiyati;
        String indirimAciklama = ""; // Rapor için indirimlerin takibi

        // 1. Happy Hour İndirimi (Sadece içeceğe %20)
        if (isHappyHour(saat) && icecekFiyati > 0) {
            double happyHourIndirim = icecekFiyati * 0.20;
            indirimliIçecekFiyati -= happyHourIndirim;
            toplamIndirimTutari += happyHourIndirim;
            indirimAciklama += "Happy Hour (İçecek %20): -" + df.format(happyHourIndirim) + " TL\n";
        }

        // Genel toplamı (içecek indirimi hariç) güncelliyoruz.
        double genelIndirimeBazFiyat = araToplam - icecekFiyati + indirimliIçecekFiyati;

        // 2. Combo İndirimi (%15 - İndirimli fiyata uygulanır)
        if (combo) {
            double comboIndirim = genelIndirimeBazFiyat * 0.15;
            genelIndirimeBazFiyat -= comboIndirim;
            toplamIndirimTutari += comboIndirim;
            indirimAciklama += "Combo Menü (%15): -" + df.format(comboIndirim) + " TL\n";
        }

        // 3. Öğrenci İndirimi (Hafta içi, %10 ekstra - İndirimli fiyata uygulanır)
        if (ogrenci && (gun >= 1 && gun <= 5)) { // Pazartesi-Cuma (Hafta içi)
            double ogrenciIndirim = genelIndirimeBazFiyat * 0.10;
            genelIndirimeBazFiyat -= ogrenciIndirim;
            toplamIndirimTutari += ogrenciIndirim;
            indirimAciklama += "Öğrenci (Hafta İçi %10): -" + df.format(ogrenciIndirim) + " TL\n";
        }
        
        // 4. 200 TL Üzeri İndirim (%10 - Öğrenci/Combo/HH indirimlerinden sonraki tutara uygulanır)
        // Not: Öğrenci ve Combo indirimleri uygulandıktan sonraki fiyata bakılır.
        if (genelIndirimeBazFiyat > 200.0) {
            double tutar200Indirim = genelIndirimeBazFiyat * 0.10;
            genelIndirimeBazFiyat -= tutar200Indirim;
            toplamIndirimTutari += tutar200Indirim;
            indirimAciklama += "200 TL Üzeri (%10): -" + df.format(tutar200Indirim) + " TL\n";
        }
        
        // Final tutarı: İndirimli fiyattır.
        return genelIndirimeBazFiyat; 
    }

    /**
     * %10 bahşiş önerisini hesaplar.
     * @param indirimliTutar Tüm indirimler uygulandıktan sonraki nihai tutar.
     * @return %10 bahşiş tutarı.
     */
    public static double calculateServiceTip(double indirimliTutar) {
        return indirimliTutar * 0.10;
    }

    // --- Ana Program ---

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Kullanıcıdan Girdileri Al
        System.out.print("Ana Yemek seçimi (1-4, Yoksa 0): ");
        int anaSecim = input.nextInt();
        System.out.print("Başlangıç seçimi (1-3, Yoksa 0): ");
        int baslangicSecim = input.nextInt();
        System.out.print("İçecek seçimi (1-4, Yoksa 0): ");
        int icecekSecim = input.nextInt();
        System.out.print("Tatlı seçimi (1-3, Yoksa 0): ");
        int tatliSecim = input.nextInt();
        System.out.print("Sipariş Saati (8-23): ");
        int saat = input.nextInt();
        System.out.print("Öğrenci misiniz? (E/H): ");
        String ogrenciStr = input.next().toUpperCase();
        System.out.print("Hangi gün? (1=Pzt, 2=Sal, ..., 7=Paz): ");
        int gun = input.nextInt();
        
        input.close();

        // 1. Fiyatları Hesapla
        double anaFiyat = getMainDishPrice(anaSecim);
        double baslangicFiyat = getAppetizerPrice(baslangicSecim);
        double icecekFiyat = getDrinkPrice(icecekSecim);
        double tatliFiyat = getDessertPrice(tatliSecim);

        // 2. Durumları Belirle (Boolean değişkenler)
        boolean anaVar = anaFiyat > 0;
        boolean icecekVar = icecekFiyat > 0;
        boolean tatliVar = tatliFiyat > 0;
        boolean ogrenci = ogrenciStr.equals("E");
        
        boolean combo = isComboOrder(anaVar, icecekVar, tatliVar);
        boolean happyHour = isHappyHour(saat);
        
        // 3. Ara Toplamı Hesapla
        double araToplam = anaFiyat + baslangicFiyat + icecekFiyat + tatliFiyat;

        // 4. İndirimli Toplamı Hesapla
        double indirimliToplam = calculateDiscount(araToplam, icecekFiyat, combo, ogrenci, gun, saat);
        
        // 5. Bahşiş Önerisini Hesapla
        double bahsisOnerisi = calculateServiceTip(indirimliToplam);
        
        // 6. Raporlama
        double uygulananIndirimTutari = araToplam - indirimliToplam;
        
        System.out.println("\n=== SİPARİŞ DETAYLI RAPORU ===");
        System.out.println("Ana Yemek Fiyatı : " + df.format(anaFiyat) + " TL");
        System.out.println("Başlangıç Fiyatı : " + df.format(baslangicFiyat) + " TL");
        System.out.println("İçecek Fiyatı : " + df.format(icecekFiyat) + " TL");
        System.out.println("Tatlı Fiyatı : " + df.format(tatliFiyat) + " TL");
        System.out.println("------------------------------");
        System.out.println("Ara Toplam : " + df.format(araToplam) + " TL");
        System.out.println("Combo Menü Durumu : " + (combo ? "Uygulandı (%15)" : "Yok"));
        System.out.println("Happy Hour Durumu : " + (happyHour && icecekVar ? "Uygulandı (İçecek %20)" : "Yok"));
        System.out.println("Öğrenci Durumu : " + (ogrenci && (gun >= 1 && gun <= 5) ? "Uygulandı (Hafta İçi %10)" : "Yok"));
        System.out.println("------------------------------");
        System.out.println("Toplam İndirim Tutarı : -" + df.format(uygulananIndirimTutari) + " TL");
        System.out.println("Nihai Toplam (İndirimli) : " + df.format(indirimliToplam) + " TL");
        System.out.println("------------------------------");
        System.out.println("Garson Servisi Bahşiş Önerisi (%10) : " + df.format(bahsisOnerisi) + " TL");
    }
}
