package Library;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SPL { 

    OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    Determinan DET = new Determinan();
    MatriksBalikan MB = new MatriksBalikan();

    /*parameter : matriksAug adalah matriks augmented SPL dengan n (persamaan & peubah)
      mengambalikan solusi SPL dalam array 1 dimensi [X1, X2, X3, ..., Xn] 
      mengembalikan null jika det(A) == 0*/
    public double[] SPLCramer(Matrix matriksAug) {
        double[] solusi = new double[matriksAug.get_COL_EFF()-1];
        double[] b = new double[matriksAug.get_ROW_EFF()];
        Matrix A = new Matrix(); ODM.createMatrix(A, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF()-1);
        Matrix ATemp = new Matrix(); ODM.createMatrix(ATemp, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF()-1);

        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                A.set_ELMT(i, j, matriksAug.get_ELMT(i, j)); // copy elmt untuk matriks A

            b[i] = matriksAug.get_ELMT(i, j+1);              // copy elmt untuk matriks b
            }
        }

        double detA = DET.kofaktor(A); 
        if (detA != 0) {
            for (int x = 0; x < solusi.length; x++) {
                for (int i = 0; i < A.get_ROW_EFF(); i++) {
                    for (int j = 0; j < A.get_COL_EFF(); j++) {
                        ATemp.set_ELMT(i, j, A.get_ELMT(i, j)); // salin A ke ATemp
                    }
                }

                for (int k = 0; k < b.length; k++) {
                    ATemp.set_ELMT(k, x, b[k]);                 // set b pada ATemp
                }

                solusi[x] = DET.kofaktor(ATemp)/detA;           // simpan solusi tiap peubah
            }
            
            return solusi;
        } else {
            return null;
        }
    }

    /*parameter : matriksAug adalah matriks augmented SPL
      mengembalikan solusi SPL dalam array 1 dimensi [X1, X2, X3, ..., Xn]
      mengembalikan null jika A tidak memiliki inverse */
    public double[] SPLInverse(Matrix matriksAug) {
        Matrix A = new Matrix();
        Matrix b = new Matrix();

        ODM.createMatrix(A, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF()-1);
        ODM.createMatrix(b, matriksAug.get_ROW_EFF(), 1);

        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                A.set_ELMT(i, j, matriksAug.get_ELMT(i, j));                // copy elmt matriks A
            b.set_ELMT(i, 0, matriksAug.get_ELMT(i, j+1));                // copy elmt matriks b
            }
        }

        Matrix inverseA = MB.inverseWithAdj(A);
        if (inverseA != null) {
            Matrix answerMatrix = ODM.multiplyMatrix(inverseA, b);          // solusi SPL dalam Matrix
            double[] solusi = new double[answerMatrix.get_ROW_EFF()];
            for (int i = 0; i < answerMatrix.get_ROW_EFF(); i++) {          // konversi solusi SPL menjadi dalam Array 1 dimensi
                solusi[i] = answerMatrix.get_ELMT(i, 0);
            }

            return solusi;
        } else {
            return null;
        }
    }

    /*I.S. "solusi" adalah array 1 dimensi berisi solusi SPL
      F.S. solusi SPL ditampilkan ke layar dengan format (xn = ...)*/
    public void displaySPL(double[] solusi) {
        StringBuilder result = new StringBuilder();
    
        for (int i = 0; i < solusi.length; i++) { // Membuat format solusi setiap peubah
            result.append("x").append(i + 1).append(" = ").append(solusi[i]).append("\n");
        }
    
        System.out.println(result.toString());    // Menampilkan ke layar
    }

    /*I.S. "solusi" adalah array 1 dimensi berisi solusi SPL
      F.S. solusi SPL disimpan ke file dengan format (xn = ...)*/
    public void writeSPL(double[] solusi) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");   // input nama file untuk simpan solusi SPL
        String filePath = sc.nextLine();
        filePath = filePath + ".txt";

        try {
            FileWriter writer = new FileWriter("test/" + filePath);
            StringBuilder result = new StringBuilder();
    
            for (int i = 0; i < solusi.length; i++) { // Membuat format solusi setiap peubah
                result.append("x").append(i + 1).append(" = ").append(solusi[i]).append("\n");
            }
    
            writer.write(result.toString());          // Menulis ke file
            writer.close();
            System.out.println("Solusi SPL telah ditulis ke file: test/" + filePath);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis ke file.");
            e.printStackTrace();
        }
    }
}
