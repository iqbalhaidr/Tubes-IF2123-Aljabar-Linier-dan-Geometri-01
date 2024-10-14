import AplikasiSPL.*;
import Library.*;
import java.util.*;

public class MyApp {

    public static int menu() {
        Scanner sc = new Scanner(System.in);
        int choose;

        // Display the menu
        System.out.println("------------------------------");
        System.out.println("MENU");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic Spline");
        System.out.println("6. Regresi Linier dan Kuadratik Berganda");
        System.out.println("7. Keluar");
        System.out.print("\nMasukkan pilihan (dalam angka): ");

        do {
            choose = sc.nextInt();
            if (choose < 1 || choose > 7) {
                System.out.print("Pilihan tidak valid. Masukkan angka antara 1 dan 7: ");
            }
        } while (choose < 1 || choose > 7);

        return choose;
    }

    public static int InputType() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("------------------------------");
        System.out.println("PILIHAN METODE MASUKAN/INPUT");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        System.out.print("\nMasukkan pilihan (dalam angka): ");

        do {
            choose = sc.nextInt();
            if (choose < 1 || choose > 2) {
                System.out.print("Pilihan tidak valid. Masukkan angka antara 1 atau 2: ");
            }
        } while (choose < 1 || choose > 2);

        return choose;
    }

    public static int SPLType() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("------------------------------");
        System.out.println("PILIHAN METODE PENYELESAIAN SPL");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.print("\nMasukkan pilihan (dalam angka): ");

        do {
            choose = sc.nextInt();
            if (choose < 1 || choose > 4) {
                System.out.print("Pilihan tidak valid. Masukkan angka antara 1 sampai 4: ");
            }
        } while (choose < 1 || choose > 4);

        return choose;

    }

    public static int DeterminanType() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("------------------------------");
        System.out.println("PILIHAN METODE PENENTUAN DETERMINAN");
        System.out.println("1. Metode reduksi baris");
        System.out.println("2. Metode ekspansi kofaktor");
        System.out.print("\nMasukkan pilihan (dalam angka): ");

        do {
            choose = sc.nextInt();
            if (choose < 1 || choose > 2) {
                System.out.print("Pilihan tidak valid. Masukkan angka antara 1 sampai 2: ");
            }
        } while (choose < 1 || choose > 2);

        return choose;

    }

    public static int BalikanType() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("------------------------------");
        System.out.println("PILIHAN METODE PENENTUAN BALIKAN/INVERSE");
        System.out.println("1. Metode eliminasi Gauss-Jordan");
        System.out.println("2. Metode adjoin");
        System.out.print("\nMasukkan pilihan (dalam angka): ");

        do {
            choose = sc.nextInt();
            if (choose < 1 || choose > 2) {
                System.out.print("Pilihan tidak valid. Masukkan angka antara 1 sampai 2: ");
            }
        } while (choose < 1 || choose > 2);

        return choose;

    }

    public static int RegresiType() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("------------------------------");
        System.out.println("PILIHAN JENIS REGRESI");
        System.out.println("1. Regresi linier berganda");
        System.out.println("2. Regresi kuadratik berganda");
        System.out.print("\nMasukkan pilihan (dalam angka): ");

        do {
            choose = sc.nextInt();
            if (choose < 1 || choose > 2) {
                System.out.print("Pilihan tidak valid. Masukkan angka antara 1 sampai 2: ");
            }
        } while (choose < 1 || choose > 2);

        return choose;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            int choice = menu();

            switch (choice) {
                case 1:
                    // Sistem Persamaan Linier (SPL)
                    int splMethod = SPLType();
                    int inputMethod = InputType();
                    System.out.println("Metode SPL dipilih: " + splMethod);
                    System.out.println("Input dari: " + (inputMethod == 1 ? "keyboard" : "file"));
                    // Add further functionality for SPL here
                    break;
                case 2:
                    // Determinan
                    int detMethod = DeterminanType();
                    inputMethod = InputType();
                    System.out.println("Metode Determinan dipilih: " + detMethod);
                    System.out.println("Input dari: " + (inputMethod == 1 ? "keyboard" : "file"));
                    // Add further functionality for Determinan here
                    break;
                case 3:
                    // Matriks Balikan
                    int balikanMethod = BalikanType();
                    inputMethod = InputType();
                    System.out.println("Metode Matriks Balikan dipilih: " + balikanMethod);
                    System.out.println("Input dari: " + (inputMethod == 1 ? "keyboard" : "file"));
                    // Add further functionality for Matriks Balikan here
                    break;
                case 4:
                    // Interpolasi Polinom
                    inputMethod = InputType();
                    System.out.println("Interpolasi Polinom dipilih.");
                    System.out.println("Input dari: " + (inputMethod == 1 ? "keyboard" : "file"));
                    // Add further functionality for Interpolasi Polinom here
                    break;
                case 5:
                    // Interpolasi Bicubic Spline
                    inputMethod = InputType();
                    System.out.println("Interpolasi Bicubic Spline dipilih.");
                    System.out.println("Input dari: " + (inputMethod == 1 ? "keyboard" : "file"));
                    // Add further functionality for Bicubic Spline here
                    break;
                case 6:
                    // Regresi Linier dan Kuadratik Berganda
                    int regresiType = RegresiType();
                    inputMethod = InputType();
                    System.out.println("Jenis Regresi dipilih: " + regresiType);
                    System.out.println("Input dari: " + (inputMethod == 1 ? "keyboard" : "file"));
                    // Add further functionality for Regresi Linier and Kuadratik here
                    break;
                case 7:
                    // Keluar
                    running = false;
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        sc.close();
    }

}
