/**
* Ad Soyad: Ümitcan ÇİNAR
* Numara: 250541009
* Proje: SinemaBileti
* Tarih: [20/11/2025]
*/



import java.util.Scanner;
import java.text.DecimalFormat;

public class SinemaBiletiFiyatlandirma {

    /**
     * Gündüz numarasına göre hafta sonu (6=Cts, 7=Paz) olup olmadığını kontrol eder.
     * @param gun 1=Pzt, 2=Sal, ..., 7=Paz
     * @return Hafta sonu ise true, aksi halde false.
     */
    public static boolean isWeekend(int gun) {
        return gun == 6 || gun == 7;
    }

    /**
     * Saat değerine göre matine (12:00 öncesi) olup olmadığını kontrol eder.
     * @param saat 8-23 arası bir tam sayı.
     * @return Matine ise true, aksi halde false.
     */
    public static boolean isMatinee(int saat) {
        return saat < 12;
    }

    /**
     * Gün ve saate göre temel bilet fiyatını hesaplar.
     * @param gun 1=Pzt, ..., 7=Paz
     * @param saat Gösterim saati.
     * @return Temel bilet fiyatı (TL).
     */
    public static double calculateBasePrice(int gun, int saat) {
        boolean isHaftaSonu = isWeekend(gun);
        boolean isMatine = isMatinee(saat);
        
        if (!isHaftaSonu && isMatine) {
            // Hafta içi matine (12:00 öncesi)
            return 45.0;
        } else if (!isHaftaSonu && !isMatine) {
            // Hafta içi normal
            return 65.0;
        } else if (isHaftaSonu && isMatine) {
            // Hafta sonu matine
            return 55.0;
        } else { // if (isHaftaSonu && !isMatine)
            // Hafta sonu normal
            return 85.0;
        }
    }

    /**
     * Yaş, meslek ve güne göre uygulanacak indirim oranını hesaplar.
     * @param yas Kullanıcının yaşı.
     * @param meslek 1=Öğrenci, 2=Öğretmen, 3=Diğer.
     * @param gun 1=Pzt, ..., 7=Paz.
     * @return Uygulanacak toplam indirim oranı (örnek: 0.20 = %20).
     */
    public static double calculateDiscount(int yas, int meslek, int gun) {
        double maxDiscount = 0.0;
        
        // 1. Yaşa Bağlı İndirimler
        if (yas < 12) {
            // 12 yaş altı: %25 (her gün)
            maxDiscount = Math.max(maxDiscount, 0.25);
        }
        if (yas >= 65) {
            // 65+ yaş: %30 (her gün)
            maxDiscount = Math.max(maxDiscount, 0.30);
        }

        // 2. Mesleğe Bağlı İndirimler
        switch (meslek) {
            case 1: // Öğrenci
                if (gun >= 1 && gun <= 4) { // Pazartesi-Perşembe (Hafta içi)
                    // %20 indirim
                    maxDiscount = Math.max(maxDiscount, 0.20);
                } else if (gun >= 5 && gun <= 7) { // Cuma-Pazar (Hafta sonu)
                    // %15 indirim
                    maxDiscount = Math.max(maxDiscount, 0.15);
                }
                break;
            case 2: // Öğretmen
                if (gun == 3) { // Sadece Çarşamba
                    // %35 indirim
                    maxDiscount = Math.max(maxDiscount, 0.35);
                }
                break;
            case 3: // Diğer (İndirim yok, zaten 65+ veya 12 yaş altı kontrol edildi)
                break;
        }

        // Birden fazla indirim varsa en yüksek olanı uygulanır (maxDiscount)
        return maxDiscount;
    }

    /**
     * Seçilen film formatına göre ekstra ücreti hesaplar.
     * @param filmTuru 1=2D, 2=3D, 3=IMAX, 4=4DX.
     * @return Film formatı ekstra ücreti (TL).
     */
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 1: // 2D
                return 0.0;
            case 2: // 3D
                return 25.0;
            case 3: // IMAX
                return 35.0;
            case 4: // 4DX
                return 50.0;
            default:
                return 0.0; // Geçersiz tür için ekstra ücret yok varsayılır
        }
    }
    
    /**
     * Temel fiyat, indirim oranı ve ekstra ücretleri kullanarak nihai bilet fiyatını hesaplar.
     */
    public static double calculateFinalPrice(double temelFiyat, double indirimOrani, double ekstraUcret) {
        double indirimliFiyat = temelFiyat * (1.0 - indirimOrani);
        return indirimliFiyat + ekstraUcret;
    }
    
    /**
     * Tüm hesaplamaları yapar ve bilet bilgilerini rapor olarak oluşturur.
     */
    public static void generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru) {
        DecimalFormat df = new DecimalFormat("0.00");
        
        // 1. Temel Fiyatı Hesapla
        double temelFiyat = calculateBasePrice(gun, saat);
        
        // 2. İndirim Oranını Hesapla
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double indirimTutari = temelFiyat * indirimOrani;
        
        // 3. İndirimli Fiyatı Hesapla
        double indirimliFiyat = temelFiyat - indirimTutari;

        // 4. Format Ekstra Ücretini Hesapla
        double ekstraUcret = getFormatExtra(filmTuru);
        
        // 5. Nihai Fiyatı Hesapla
        double finalFiyat = calculateFinalPrice(temelFiyat, indirimOrani, ekstraUcret);
        
        // Rapor Oluşturma
        System.out.println("\n=== SINEMA BİLET RAPORU ===");
        System.out.println("Temel Fiyat : " + df.format(temelFiyat) + " TL" + 
                           " (" + (isWeekend(gun) ? "Hafta Sonu" : "Hafta İçi") + 
                           ", " + (isMatinee(saat) ? "Matine" : "Normal") + ")");
        
        System.out.println("------------------------------");
        System.out.println("Uygulanan İndirim Oranı : %" + df.format(indirimOrani * 100));
        System.out.println("İndirim Tutarı : " + df.format(indirimTutari) + " TL");
        System.out.println("İndirimli Fiyat : " + df.format(indirimliFiyat) + " TL");
        System.out.println("Format Ekstra Ücreti : " + df.format(ekstraUcret) + " TL");
        System.out.println("------------------------------");
        System.out.println("Toplam Bilet Fiyatı : " + df.format(finalFiyat) + " TL");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("1=Pzt, 2=Sal, 3=Çar, 4=Per, 5=Cum, 6=Cts, 7=Paz");
        System.out.print("Gün seçimi yapınız (1-7): ");
        int gun = input.nextInt();
        
        System.out.print("Saat giriniz (8-23): ");
        int saat = input.nextInt();
        
        System.out.print("Yaşınızı giriniz: ");
        int yas = input.nextInt();
        
        System.out.println("1=Öğrenci, 2=Öğretmen, 3=Diğer");
        System.out.print("Meslek seçimi yapınız (1-3): ");
        int meslek = input.nextInt();
        
        System.out.println("1=2D, 2=3D, 3=IMAX, 4=4DX");
        System.out.print("Film Türü seçimi yapınız (1-4): ");
        int filmTuru = input.nextInt();
        
        input.close();
        
        // Nihai raporu oluşturma metodu
        generateTicketInfo(gun, saat, yas, meslek, filmTuru);
    }
}
