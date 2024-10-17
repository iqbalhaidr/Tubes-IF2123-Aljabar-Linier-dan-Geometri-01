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

    //menerima parameter dalam bentukan matriks augmented dan print hasilnya
    public void RegresiLinear(Matrix matriksAug) {
        double[] answer = solveRegresiLinear(matriksAug);
        double taksiran;
        displayRegresiLinear(answer);
        taksiran = calculateYLinear(answer);
        writeRegresiLinear(answer, taksiran);
    }

    public void RegresiKuadratik(Matrix matriksAug) {
        double[] answer = solveRegresiKuadratik(matriksAug);
        double taksiran;
        displayRegresiKuadratik(answer, matriksAug.get_COL_EFF()-1);
        taksiran = calculateYKuadratik(answer, matriksAug.get_COL_EFF()-1);
        writeRegresiKuadratik(answer, matriksAug.get_COL_EFF()-1, taksiran);
    }
    
    /*prekondisi: (X^T).X memiliki inverse
      parameter : matriksAug adalah matriks augmented dengan format X1 X2 X3 ... Y
      return    : matriks 1 dimensi [b0, b1, b2, ..., bn] */
    public double[] solveRegresiLinear(Matrix matriksAug) {
        Matrix X = new Matrix(); ODM.createMatrix(X, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());
        Matrix y = new Matrix(); ODM.createMatrix(y, matriksAug.get_ROW_EFF(), 1);
        
        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            X.set_ELMT(i, 0, 1);
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                X.set_ELMT(i, j+1, matriksAug.get_ELMT(i, j));           //copy elmt untuk matriksAug A          //copy elmt untuk matriksAug b
            }
            y.set_ELMT(i, 0, matriksAug.get_ELMT(i, matriksAug.get_COL_EFF()-1));
        }
        Matrix matriksMerge = ODM.mergeMatrix(ODM.multiplyMatrix(MB.transpose(X), X), ODM.multiplyMatrix(MB.transpose(X), y));
        EG.GausMethod(matriksMerge);
        return EG.backsubsV2(matriksMerge);
        //return (ODM.multiplyMatrix(MB.inverseWithAdj(ODM.multiplyMatrix(MB.transpose(X), X)), ODM.multiplyMatrix(MB.transpose(X), y)));
    }

    /*parameter : koefRegresi adalah matriks augmented dengan format X1 X2 X3 ... Y */
    public void displayRegresiLinear(double[] koefRegresi) {
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

        System.out.println(result.toString());
    }

    // Method untuk menghitung Y berdasarkan input user untuk variabel x1, x2, ..., xn
    public double calculateYLinear(double[] koefRegresi) {
        Scanner scanner = new Scanner(System.in);

        // Array untuk menampung nilai variabel X yang dimasukkan user
        double[] X = new double[koefRegresi.length - 1];

        // Menerima input dari user untuk setiap variabel X
        for (int i = 0; i < X.length; i++) {
            System.out.print("Masukkan nilai X" + (i + 1) + ": ");
            X[i] = scanner.nextDouble();
        }

        // Menghitung Y berdasarkan persamaan regresi linear
        double Y = koefRegresi[0]; // Inisialisasi dengan nilai intersep (a)

        // Menambahkan kontribusi dari variabel x1, x2, ..., xn
        for (int i = 0; i < X.length; i++) {
            Y += koefRegresi[i + 1] * X[i]; // Koefisien linier dikali dengan nilai X
        }

        System.out.println("Hasil y berdasarkan input user: " + Y); // Mengembalikan hasil perhitungan Y; // Mengembalikan hasil perhitungan Y
        return Y;
    }
    
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
    
                // Menambahkan intersep
                result.append(koefRegresi[0]);
    
                // Menambahkan koefisien variabel
                for (int i = 1; i < koefRegresi.length; i++) {
                    if (koefRegresi[i] >= 0) {
                        result.append(" + ");
                    } else {
                        result.append(" - ");
                    }
                    result.append(Math.abs(koefRegresi[i])).append(".x").append(i);
                }
    
                result.append("\nNilai taksiran y: ").append(taksiran);
    
                // Menulis hasil ke file
                writer.write(result.toString());
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

    //menggunakan eliminasi gauss
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
        EG.GausMethod(matriksMerge);
        return EG.backsubsV2(matriksMerge);
        //return 0;
        //return X;
        //return y;
        //return (ODM.multiplyMatrix(MB.inverseWithAdj(ODM.multiplyMatrix(MB.transpose(X), X)), ODM.multiplyMatrix(MB.transpose(X), y)));
        //return ODM.multiplyMatrix(MB.transpose(X), X);
        //return ODM.multiplyMatrix(MB.transpose(X), y);
        //return MB.inverseWithAdj(ODM.multiplyMatrix(MB.transpose(X), X));
    }

    public void displayRegresiKuadratik(double[] coefficients, int n) {
        StringBuilder result = new StringBuilder("y = ");
    
        // Cetak intersep
        result.append(coefficients[0]);
    
        // Cetak koefisien linier untuk X1 sampai Xn
        for (int i = 1; i <= n; i++) {
            if (coefficients[i] != 0) {
                if (coefficients[i] > 0) result.append(" + ");
                else result.append(" - ");
                result.append(Math.abs(coefficients[i])).append(".x").append(i);
            }
        }
    
        // Cetak koefisien kuadratik untuk X1^2 sampai Xn^2
        for (int i = 1; i <= n; i++) {
            if (coefficients[n + i] != 0 || Math.abs(coefficients[n + i]) < 1e-10) {  // Cek koefisien mendekati 0
                if (coefficients[n + i] > 0) result.append(" + ");
                else result.append(" - ");
                result.append(Math.abs(coefficients[n + i])).append(".x").append(i).append("^2");
            }
        }
    
        // Cetak koefisien interaksi antara variabel
        int interactionStartIdx = 2 * n + 1;
        int interactionIdx = interactionStartIdx;
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
    
        // Cetak persamaan regresi
        System.out.println(result.toString());
    }
    
    // Method untuk menghitung Y berdasarkan input user untuk X1, X2, ..., Xn
    public double calculateYKuadratik(double[] coefficients, int n) {
        Scanner scanner = new Scanner(System.in);

        // Array untuk menampung nilai-nilai X yang dimasukkan user
        double[] X = new double[n];

        // Menerima input dari user untuk setiap variabel X
        for (int i = 0; i < n; i++) {
            System.out.print("Masukkan nilai x" + (i + 1) + ": ");
            X[i] = scanner.nextDouble();
        }

        // Menghitung Y berdasarkan persamaan regresi
        double Y = coefficients[0]; // Inisialisasi dengan nilai intersep (a)

        // Menambahkan kontribusi dari term linier
        for (int i = 0; i < n; i++) {
            Y += coefficients[i + 1] * X[i]; // Koefisien linier untuk X1, X2, ..., Xn
        }

        // Menambahkan kontribusi dari term kuadratik
        for (int i = 0; i < n; i++) {
            Y += coefficients[n + i + 1] * X[i] * X[i]; // Koefisien untuk X1^2, X2^2, ..., Xn^2
        }

        // Menambahkan kontribusi dari interaksi antar variabel
        int interactionIdx = 2 * n + 1;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Y += coefficients[interactionIdx] * X[i] * X[j]; // Koefisien untuk interaksi X_i * X_j
                interactionIdx++;
            }
        }

        System.out.println("Hasil y berdasarkan input user: " + Y); // Mengembalikan hasil perhitungan Y
        return Y;
    }

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

                // Menambahkan intersep
                result.append(koefRegresi[0]);

                // Menambahkan koefisien linier untuk X1, X2, ..., Xn
                for (int i = 1; i <= n; i++) {
                    if (koefRegresi[i] >= 0) {
                        result.append(" + ");
                    } else {
                        result.append(" - ");
                    }
                    result.append(Math.abs(koefRegresi[i])).append(".x").append(i);
                }

                // Menambahkan koefisien kuadratik untuk X1^2, X2^2, ..., Xn^2
                for (int i = 1; i <= n; i++) {
                    if (koefRegresi[n + i] >= 0) {
                        result.append(" + ");
                    } else {
                        result.append(" - ");
                    }
                    result.append(Math.abs(koefRegresi[n + i])).append(".x").append(i).append("^2");
                }

                // Menambahkan koefisien interaksi antara variabel
                int interactionIdx = 2 * n + 1;
                for (int i = 1; i <= n; i++) {
                    for (int j = i + 1; j <= n; j++) {  // Interaksi X_i * X_j
                        if (koefRegresi[interactionIdx] >= 0) {
                            result.append(" + ");
                        } else {
                            result.append(" - ");
                        }
                        result.append(Math.abs(koefRegresi[interactionIdx])).append(".x").append(i).append(".x").append(j);
                        interactionIdx++;
                    }
                }

                // Menambahkan nilai taksiran
                result.append("\nNilai taksiran y: ").append(taksiran);

                // Menulis hasil ke file
                writer.write(result.toString());
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