import AplikasiSPL.*;
import Library.*;

import java.io.FileWriter;
import java.io.IOException;
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


    public static int saveOutput() {
        Scanner sc = new Scanner(System.in);
        int choose;
        System.out.println("------------------------------");
        System.out.println("Simpan jawaban dalam file?");
        System.out.println("1. Simpan");
        System.out.println("2. Tidak");
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
        OperasiDasarMatrix operasi = new OperasiDasarMatrix();
        SPL spl = new SPL();
        MatriksBalikan balikan = new MatriksBalikan();
        EliminasiGaus gauss = new EliminasiGaus();
        gaussjordan gj = new gaussjordan();
        Determinan det = new Determinan();
        InterpolasiPolinomial interpolasi = new InterpolasiPolinomial();
        RegresiBerganda RB = new RegresiBerganda();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            int choice = menu();
            int inputMethod = 0;
            if (choice != 7) {
            inputMethod = InputType();}

            switch (choice) {
                case 1:

                    // Sistem Persamaan Linier (SPL)
                    int splMethod = SPLType();

                    if (splMethod == 1) { //eliminasi gauss
                        //implementasi
                    }
                    else if (splMethod == 2) { //gauss jordan
                        Matrix m = new Matrix();
                        if (inputMethod == 1) {
                            m = operasi.readSPL();
                        }
                        else {
                            System.out.print("Masukkan nama file input: ");
                            String nama = sc.nextLine();
                            operasi.readMatrixFile(nama,m);
                        }

                        if (m.get_COL_EFF() != 0 && m.get_ROW_EFF() != 0) {
                       String solusi[] =  gj.solveSPL(m);
                       if (solusi != null) {
                           for (int i = 0; i < solusi.length; i++) {
                               System.out.println(solusi[i]);
                           }
                       }
                       int save = saveOutput();
                       if (save == 1) {
                           System.out.print("Masukkan nama file: ");
                           String filename = sc.nextLine();
                           try {
                               FileWriter myWriter = new FileWriter("solusi" + filename + ".txt");
                               if (solusi == null) {
                                   myWriter.write("Tidak ada solusi");
                               }
                               else {
                                   for (int i = 0; i < solusi.length; i++) {
                                       myWriter.write (solusi[i]);
                                       myWriter.write ("\n");
                                   }
                               }
                               myWriter.close();
                               System.out.println("Jawaban berhasil disimpan di solusi" + filename + ".txt");

                           } catch (IOException e) {
                               System.out.println("Terjadi kesalahan saat menyimpan file.");
                           }


                       }
                       else {
                           System.out.println("Jawaban tidak disimpan");
                       }
                       } }


                    else if (splMethod == 3) { //matriks balikan
                    //implementasi
                    }
                    else { //cramer splMethod == 4
                        //implementasi
                    }
                    break;

                case 2:
                    // Determinan
                    int DeterminanMethod = DeterminanType();

                    if (DeterminanMethod == 1) { //reduksi baris
                        Matrix m = new Matrix();
                        if (inputMethod == 1) {
                            System.out.print("Masukkan banyak baris matriks: ");
                            int row = sc.nextInt();
                            System.out.print("Masukkan banyak kolom matriks: ");
                            int col = sc.nextInt();
                            operasi.createMatrix(m,row,col);
                            System.out.println("Masukkan elemen-elemen matriks:");
                            operasi.readMatrix(m,row,col);
                        }
                        else {
                            System.out.print("Masukkan nama file: ");
                            String nama = sc.nextLine();
                            operasi.readMatrixFile(nama,m);
                        }

                        if (m.get_COL_EFF() != 0 && m.get_ROW_EFF() != 0) {
                        if (!(operasi.isSquare(m))) {
                            System.out.println("Matriks tidak memiliki determinan");
                        }
                        else {
                        double determinan = gj.DeterminantOBE(m);
                        System.out.println("Determinan matriks adalah " + determinan);
                        int save = saveOutput();
                        if (save == 1) {
                            System.out.print("Masukkan nama file: ");
                            String filename = sc.nextLine();

                            try {
                                FileWriter writer = new FileWriter("solusi" + filename + ".txt");
                                writer.write("determinan = " + determinan);
                                writer.close();
                                System.out.println("Jawaban berhasil disimpan di solusi" + filename + ".txt");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan saat menyimpan file.");
                            }

                        }
                        else {
                            System.out.println("Jawaban tidak disimpan");
                        }
                    } } }

                    else if (DeterminanMethod == 2) { //kofaktor
                        //implementasi
                    }

                    break;

                case 3:
                    // Matriks Balikan
                    int balikanMethod = BalikanType();
                    if (balikanMethod == 1) { //gauss jordan
                        Matrix m = new Matrix();
                        if (inputMethod == 1) {
                            System.out.print("Masukkan banyak baris matriks: ");
                            int row = sc.nextInt();
                            System.out.print("Masukkan banyak kolom matriks: ");
                            int col = sc.nextInt();
                            operasi.createMatrix(m,row,col);
                            System.out.println("Masukkan elemen-elemen matriks:");
                            operasi.readMatrix(m,row,col);
                        }
                        else {
                            System.out.print("Masukkan nama file: ");
                            String nama = sc.nextLine();
                            operasi.readMatrixFile(nama,m);
                        }

                        if (m.get_COL_EFF() != 0 && m.get_ROW_EFF() != 0) {
                        if (!(operasi.isSquare(m)) || det.kofaktor(m) == 0) {
                            System.out.println("Matriks tidak memiliki matriks balikan");
                        }

                        else {
                            Matrix m2 = gj.MatriksBalikan(m);
                            operasi.displayMatrix(m2);
                            int save = saveOutput();
                            if (save == 1) {
                                System.out.print("Masukkan nama file: ");
                                String filename = sc.nextLine();
                                filename = "solusi" + filename + ".txt";
                                operasi.displayMatrixtoFile(m2,filename);
                                System.out.println("Jawaban berhasil disimpan di " + filename + ".txt");

                            }
                            else {
                                System.out.println("Jawaban tidak disimpan");
                            }
                        }
                    } } else { //Inverse Adjoin
                        Matrix matriks = new Matrix();
                        if (inputMethod == 1) {
                            System.out.print("Masukkan banyak baris matriks: ");
                            int row = sc.nextInt();
                            System.out.print("Masukkan banyak kolom matriks: ");
                            int col = sc.nextInt();
                            operasi.createMatrix(matriks, row, col);
                            System.out.println("Masukkan elemen-elemen matriks:\n");
                            operasi.readMatrix(matriks, row, col);
                        } else {
                            System.out.print("Masukkan nama file: ");
                            String nama = sc.nextLine();
                            operasi.readMatrixFile(nama, matriks);
                        }

                        if (!(operasi.isSquare(matriks)) || det.kofaktor(matriks) == 0) {
                            System.out.println("Matriks tidak memiliki matriks balikan");
                        } else {
                            operasi.displayMatrix(balikan.inverseWithAdj(matriks));
                            int save = saveOutput();
                            if (save == 1) {
                                System.out.print("Masukkan nama file: ");
                                String filename = sc.nextLine();
                                filename = filename + ".txt";
                                operasi.displayMatrixtoFile(balikan.inverseWithAdj(matriks), filename);
                                System.out.println("Jawaban berhasil disimpan di " + filename);
                            } else {
                                System.out.println("Jawaban tidak disimpan");
                            }
                        }
                    }

                    break;

                case 4:
                    // Interpolasi Polinom
                    Matrix m = new Matrix();
                    double y = 0;
                    if (inputMethod == 1) {
                        y = interpolasi.SolveInterpolasi(m,false);
                    }
                    else {
                        y = interpolasi.SolveInterpolasi(m,true);
                    }

                    if (y != -999999) {
                    int save = saveOutput();
                    if (save == 1) {
                        System.out.print("Masukkan nama file: ");
                        String filename = sc.nextLine();

                        try {
                            FileWriter writer = new FileWriter("solusi" + filename + ".txt");
                            writer.write("Nilai f(x) " + "adalah " + y);
                            writer.close();
                            System.out.println("Jawaban berhasil disimpan di solusi" + filename + ".txt");
                        } catch (IOException e) {
                            System.out.println("Terjadi kesalahan saat menyimpan file.");
                        }

                    }
                    else {
                        System.out.println("Jawaban tidak disimpan");
                    } }

                    break;

                case 5:

                    // Interpolasi Bicubic Spline
                    //implementasi

                    break;

                case 6: // Regresi Linier dan Kuadratik Berganda

                    int regresiMethod = RegresiType();
                    Matrix matriksAug = new Matrix();
                    if (inputMethod == 1) {
                        matriksAug = operasi.readRegresi();
                    } else {
                        System.out.print("Masukkan nama file input: ");
                        String nama = sc.nextLine();
                        operasi.readMatrixFile(nama, matriksAug);
                    }

                    if (regresiMethod == 1) {             // regresi linier
                        RB.RegresiLinear(matriksAug);
                    } else {                              //regresi kuadratik
                        RB.RegresiKuadratik(matriksAug);
                    }

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
