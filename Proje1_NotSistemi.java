/**
* Ad Soyad: Ümitcan ÇİNAR
* Numara: 250541009
* Proje: NotSistemi
* Tarih: [20/11/2025]
*/


import java.util.Scanner;
import java.text.DecimalFormat; // Ortalama çıktısını iki ondalık basamakla sınırlamak için

public class OgrenciNotProgrami {

    // Ortalama hesaplama metodu: Vize %30 + Final %40 + Odev %30
    public static double calculateAverage(int vize, int finalNotu, int odev) {
        // Notların double'a çevrilmesi, böylece hassas bir hesaplama yapılır.
        return (vize * 0.30) + (finalNotu * 0.40) + (odev * 0.30);
    }

    // Geçme durumu metodu: >= 50 GEÇTİ, < 50 KALDI
    public static String isPassingGrade(double ortalama) {
        if (ortalama >= 50.0) {
            return "GECTI";
        } else {
            return "KALDI";
        }
    }

    // Harf notu metodu
    public static String getLetterGrade(double ortalama) {
        if (ortalama >= 90) {
            return "A"; // 90-100
        } else if (ortalama >= 80) {
            return "B"; // 80-89
        } else if (ortalama >= 70) {
            return "C"; // 70-79
        } else if (ortalama >= 60) {
            return "D"; // 60-69
        } else {
            return "F"; // < 60
        }
    }

    // Onur listesi metodu: Ortalama >= 85 VE hiçbir not < 70 olmamalı
    public static String isHonorList(double ortalama, int vize, int finalNotu, int odev) {
        if (ortalama >= 85.0 && vize >= 70 && finalNotu >= 70 && odev >= 70) {
            return "EVET";
        } else {
            return "HAYIR";
        }
    }

    // Bütünleme hakkı metodu: 40 <= ort < 50
    public static String hasRetakeRight(double ortalama) {
        if (ortalama >= 40.0 && ortalama < 50.0) {
            return "VAR";
        } else {
            return "YOK";
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // DecimalFormat ile ortalama çıktısını iki ondalık basamağa ayarlıyoruz
        DecimalFormat df = new DecimalFormat("0.0");

        System.out.print("Vize notunuzu giriniz (0-100): ");
        int vize = input.nextInt();
        System.out.print("Final notunuzu giriniz (0-100): ");
        // İstenen örnek çıktıdaki "Final" notu olarak adlandırılan sınav notunu alıyoruz.
        // Girdi örneğinde final1 olarak geçiyor, bunu finalNotu olarak alıyoruz.
        int finalNotu = input.nextInt(); 
        System.out.print("Odev notunuzu giriniz (0-100): ");
        int odev = input.nextInt();
        input.close();

        // 1. Ortalama Hesaplama
        double ortalama = calculateAverage(vize, finalNotu, odev);
        
        // 2. Diğer Değerlendirmeler
        String durum = isPassingGrade(ortalama);
        String harfNotu = getLetterGrade(ortalama);
        String onurListesi = isHonorList(ortalama, vize, finalNotu, odev);
        String butunleme = hasRetakeRight(ortalama);

        System.out.println("\n=== OGRENCI NOT RAPORU ===");
        System.out.println("Vize Notu : " + df.format(vize));
        System.out.println("Final Notu : " + df.format(finalNotu));
        System.out.println("Odev Notu : " + df.format(odev));
        System.out.println("------------------------------");
        System.out.println("Ortalama : " + df.format(ortalama));
        System.out.println("Harf Notu : " + harfNotu);
        System.out.println("Durum : " + durum);
        System.out.println("Onur Listesi : " + onurListesi);
        System.out.println("Butunleme : " + butunleme);
    }
}
