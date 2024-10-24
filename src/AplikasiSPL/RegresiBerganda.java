package AplikasiSPL;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import Library.*;

public class RegresiBerganda {

    OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    Determinan DET = new Determinan();
    MatriksBalikan MB = new MatriksBalikan();
    gaussjordan GJ = new gaussjordan();
    EliminasiGaus EG = new EliminasiGaus();

    /*I.S. matriksAug adalah sampel data dengan format x1 x2 ... xn y
      Proses: dilaksanakan semua prosedur regresi linear berganda*/
    public void RegresiLinear(Matrix matriksAug) {
        double[] answer = solveRegresiLinear(matriksAug);
        double taksiran;

        displayRegresiLinear(answer);
        taksiran = calculateYLinear(answer);
        writeRegresiLinear(answer, taksiran);
    }

    /*I.S. matriksAug adalah sampel data dengan format x1 x2 ... xn y
      Proses: dilaksanakan semua prosedur regresi kuadratik berganda*/
    public void RegresiKuadratik(Matrix matriksAug) {
        double[] answer = solveRegresiKuadratik(matriksAug);
        double taksiran;

        displayRegresiKuadratik(answer, matriksAug.get_COL_EFF()-1);
        taksiran = calculateYKuadratik(answer, matriksAug.get_COL_EFF()-1);
        writeRegresiKuadratik(answer, matriksAug.get_COL_EFF()-1, taksiran);
    }
    
    /*parameter: matriksAug adalah sampel data dengan format x1 x2 ... xn y
      mengembalikan koefisien persamaan regresi linear dalam matriks dimensi satu [b0, b1, b2, ..., bn] */
    public double[] solveRegresiLinear(Matrix matriksAug) {
        Matrix X = new Matrix(); ODM.createMatrix(X, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());
        Matrix y = new Matrix(); ODM.createMatrix(y, matriksAug.get_ROW_EFF(), 1);
        
        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            X.set_ELMT(i, 0, 1);
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                X.set_ELMT(i, j+1, matriksAug.get_ELMT(i, j));                       //copy elmt untuk matriks X (koefisien variabel independen)
            }
            y.set_ELMT(i, 0, matriksAug.get_ELMT(i, matriksAug.get_COL_EFF()-1));  //copy elmt untuk matriks y (nilai variabel dependen)
        }
        Matrix matriksMerge = ODM.mergeMatrix(ODM.multiplyMatrix(MB.transpose(X), X), ODM.multiplyMatrix(MB.transpose(X), y));
        return GJ.solveSPL2(matriksMerge);
    }

    /*I.S. koefRegresi adalah array satu dimensi berisi koefisien persamaan regresi linear berganda
      F.S. persamaan regresi linear tampil di layar dengan format y = b0 + b1x1 + b2x2 + ... + bnxn*/
    public void displayRegresiLinear(double[] koefRegresi) {
        StringBuilder result = new StringBuilder("\ny = ");
        result.append(koefRegresi[0]);

        for (int i = 1; i < koefRegresi.length; i++) {
            if (koefRegresi[i] >= 0) {
                result.append(" + ");
            } else {
                result.append(" - ");
            }
            result.append(Math.abs(koefRegresi[i])).append(".x").append(i);
        }

        System.out.println(result.toString());
        System.out.print("\n");
    }

    /*parameter: koefRegresi adalah array satu dimensi berisi koefisien persamaan regresi linear berganda
      proses: input nilai tiap variabel independen dari user, menghitung taksiran variabel dependen, menampilkan ke layar
      mengembalikan nilai taksiran y*/
    public double calculateYLinear(double[] koefRegresi) {
        Scanner sc = new Scanner(System.in);
        double[] X = new double[koefRegresi.length - 1];

        for (int i = 0; i < X.length; i++) { // input nilai setiap variabel independen
            System.out.print("Masukkan nilai x" + (i + 1) + ": ");
            X[i] = sc.nextDouble();
        }

        double Y = koefRegresi[0]; 
        for (int i = 0; i < X.length; i++) {
            Y += koefRegresi[i + 1] * X[i]; // menghitung taksiran
        }

        System.out.println("Hasil taksiran berdasarkan input user: " + Y);
        return Y;
    }
    
    /*I.S. koefRegresi adalah array satu dimensi berisi koefisien persamaan regresi linear berganda, taksiran adalah nilai taksiran y
      F.S. persamaan regresi linear dengan format y = b0 + b1x1 + b2x2 + ... + bnxn dan hasil taksiran disimpan di file*/
    public void writeRegresiLinear(double[] koefRegresi, double taksiran) {
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
        
        sc.nextLine();

        if (choose == 1) {
            System.out.print("Masukkan nama file: ");
            String filename = sc.nextLine();
            filename = filename + ".txt";
            try {
                FileWriter writer = new FileWriter("test/" + filename);
                StringBuilder result = new StringBuilder("y = ");
    
                result.append(koefRegresi[0]);
                for (int i = 1; i < koefRegresi.length; i++) {
                    if (koefRegresi[i] >= 0) {
                        result.append(" + ");
                    } else {
                        result.append(" - ");
                    }
                    result.append(Math.abs(koefRegresi[i])).append(".x").append(i);
                }
    
                result.append("\nNilai taksiran y: ").append(taksiran);
    
                writer.write(result.toString());  // Menulis ke file
                writer.close();
                System.out.println("Persamaan regresi telah ditulis ke file: " + filename);
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menulis ke file.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Jawaban tidak disimpan");
        }
    }

    /*parameter: matriksAug adalah sampel data dengan format x1 x2 ... xn y
      mengembalikan koefisien persamaan regresi kuadratik dalam matriks dimensi satu [b0, b1, b2, ..., bn] */
    public double[] solveRegresiKuadratik(Matrix matriksAug) {
        int peubah = matriksAug.get_COL_EFF()-1;
        Matrix X = new Matrix(); ODM.createMatrix(X, matriksAug.get_ROW_EFF(), (1 + 2*peubah + (peubah*(peubah - 1)/2)));
        Matrix y = new Matrix(); ODM.createMatrix(y, matriksAug.get_ROW_EFF(), 1);
        
        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            X.set_ELMT(i, 0, 1);                                                  //set 1
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                X.set_ELMT(i, j+1, matriksAug.get_ELMT(i, j));                        //set variabel linier
                X.set_ELMT(i, j+1+peubah, Math.pow(matriksAug.get_ELMT(i, j), 2));  //set variabel kuadratik
            }
            int colIdxInteraksi = 1+(peubah*2);
            for (int k = 0; k < matriksAug.get_COL_EFF()-2; k++) {                    //set variabel interaksi
                for (int l = k+1; l < matriksAug.get_COL_EFF()-1; l++) {
                    X.set_ELMT(i, colIdxInteraksi++, matriksAug.get_ELMT(i, k)*matriksAug.get_ELMT(i, l));
                }
            }
            y.set_ELMT(i, 0, matriksAug.get_ELMT(i, matriksAug.get_COL_EFF()-1));
        }

        Matrix matriksMerge = ODM.mergeMatrix(ODM.multiplyMatrix(MB.transpose(X), X), ODM.multiplyMatrix(MB.transpose(X), y));
        return GJ.solveSPL2(matriksMerge);
    }

    /*I.S. coefficients adalah array satu dimensi berisi koefisien persamaan regresi kuadratik berganda, n adalah jumlah variabel independen
      F.S. persamaan regresi kuadratik tampil di layar*/
    public void displayRegresiKuadratik(double[] coefficients, int n) {
        StringBuilder result = new StringBuilder("\ny = ");
        result.append(coefficients[0]);
    
        for (int i = 1; i <= n; i++) { // untuk koefisien linier untuk x1 sampai xn
            if (coefficients[i] != 0) {
                if (coefficients[i] > 0) result.append(" + ");
                else result.append(" - ");
                result.append(Math.abs(coefficients[i])).append(".x").append(i);
            }
        }
    
        for (int i = 1; i <= n; i++) { // untuk koefisien kuadratik untuk x1^2 sampai xn^2
            if (coefficients[n + i] != 0 || Math.abs(coefficients[n + i]) < 1e-10) {  // Cek koefisien mendekati 0
                if (coefficients[n + i] > 0) result.append(" + ");
                else result.append(" - ");
                result.append(Math.abs(coefficients[n + i])).append(".x").append(i).append("^2");
            }
        }
    
        int interactionIdx = 2 * n + 1; // untuk koefisien interaksi antara variabel (x1.x2, x1.x3 sampai xm.xn)
        //int interactionIdx = interactionStartIdx;
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {  // Hanya interaksi X_i * X_j dengan i < j
                if (coefficients[interactionIdx] != 0 || Math.abs(coefficients[interactionIdx]) < 1e-10) {
                    if (coefficients[interactionIdx] > 0) result.append(" + ");
                    else result.append(" - ");
                    result.append(Math.abs(coefficients[interactionIdx])).append(".x").append(i).append("x").append(j);
                }
                interactionIdx++;
            }
        }
    
        System.out.println(result.toString()); // cetak persamaan regresi kuadratik
        System.out.print("\n");
    }
    
    /*parameter: coefficients adalah array satu dimensi berisi koefisien persamaan regresi kuadratik berganda, n adalah jumlah variabel independen
      proses: input nilai tiap variabel independen dari user, menghitung taksiran variabel dependen, menampilkan ke layar
      mengembalikan nilai taksiran y*/
    public double calculateYKuadratik(double[] coefficients, int n) {
        Scanner sc = new Scanner(System.in);
        double[] X = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Masukkan nilai x" + (i + 1) + ": ");
            X[i] = sc.nextDouble();
        }

        double Y = coefficients[0];
        for (int i = 0; i < n; i++) { // koef linier
            Y += coefficients[i + 1] * X[i];
        }

        for (int i = 0; i < n; i++) { // koef kuadratik
            Y += coefficients[n + i + 1] * X[i] * X[i];
        }

        int interactionIdx = 2 * n + 1; // koef interaksi
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Y += coefficients[interactionIdx] * X[i] * X[j];
                interactionIdx++;
            }
        }

        System.out.println("Hasil taksiran berdasarkan input user: " + Y);
        return Y;
    }

    /*I.S. koefRegresi adalah array satu dimensi berisi koefisien persamaan regresi kuadratik berganda, taksiran adalah nilai taksiran y, n adalah jumlah variabel independen
      F.S. persamaan regresi kuadratik dan hasil taksiran disimpan di file*/
    public void writeRegresiKuadratik(double[] koefRegresi, int n, double taksiran) {
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
        
        sc.nextLine();

        if (choose == 1) {
            System.out.print("Masukkan nama file: ");
            String filePath = sc.nextLine();
            filePath = filePath + ".txt";
            try {
                FileWriter writer = new FileWriter("test/" + filePath);
                StringBuilder result = new StringBuilder("y = ");

                result.append(koefRegresi[0]);
                for (int i = 1; i <= n; i++) { // koef linier
                    if (koefRegresi[i] >= 0) {
                        result.append(" + ");
                    } else {
                        result.append(" - ");
                    }
                    result.append(Math.abs(koefRegresi[i])).append(".x").append(i);
                }

                for (int i = 1; i <= n; i++) { // koef kuadratik
                    if (koefRegresi[n + i] >= 0) {
                        result.append(" + ");
                    } else {
                        result.append(" - ");
                    }
                    result.append(Math.abs(koefRegresi[n + i])).append(".x").append(i).append("^2");
                }

                int interactionIdx = 2 * n + 1; // koef interaksi
                for (int i = 1; i <= n; i++) {
                    for (int j = i + 1; j <= n; j++) { 
                        if (koefRegresi[interactionIdx] >= 0) {
                            result.append(" + ");
                        } else {
                            result.append(" - ");
                        }
                        result.append(Math.abs(koefRegresi[interactionIdx])).append(".x").append(i).append(".x").append(j);
                        interactionIdx++;
                    }
                }

                result.append("\nNilai taksiran y: ").append(taksiran); // taksiran

                writer.write(result.toString()); // menulis hasil ke file
                writer.close();
                System.out.println("Persamaan regresi kuadratik dan nilai taksiran telah ditulis ke file: " + filePath);
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menulis ke file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Jawaban tidak disimpan");
        }
    }
}