package Library;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SPL { 

    OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    Determinan DET = new Determinan();
    MatriksBalikan MB = new MatriksBalikan();

    /*prekondisi: det(A) != 0 (solusi unik)
      parameter : matriksAug adalah matriks augmented dengan n (persamaan & peubah) 
      return    : array 1 dimensi [X1, X2, X3, ..., Xn] */
    public double[] SPLCramer (Matrix matriksAug) {
        double[] answer = new double[matriksAug.get_COL_EFF()-1];
        double[] b = new double[matriksAug.get_ROW_EFF()];
        Matrix A = new Matrix(); ODM.createMatrix(A, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF()-1);
        Matrix ATemp = new Matrix(); ODM.createMatrix(ATemp, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF()-1);

        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                A.set_ELMT(i, j, matriksAug.get_ELMT(i, j)); //copy elmt untuk matriksAug A

            b[i] = matriksAug.get_ELMT(i, j+1);              //copy elmt untuk matriksAug b
            }
        }

        double determinan = DET.kofaktor(A);                
        for (int x = 0; x < answer.length; x++) {
            for (int i = 0; i < A.get_ROW_EFF(); i++) {
                for (int j = 0; j < A.get_COL_EFF(); j++) {
                    ATemp.set_ELMT(i, j, A.get_ELMT(i, j)); //salin A ke ATemp
                }
            }

            for (int k = 0; k < b.length; k++) {
                ATemp.set_ELMT(k, x, b[k]);                 //set Aj pada ATemp
            }

            answer[x] = DET.kofaktor(ATemp)/determinan;     //simpan solusi tiap peubah
        }
        
        return answer;
    }

    /*prekondisi: A memiliki inverse (solusi unik)
      parameter : matriksAug adalah matriks augmented 
      return    : matriks Nx1 [X1, X2, X3, ..., Xn] */
    public double[] SPLInverse (Matrix matriksAug) {
        //Matrix answer = new Matrix();
        Matrix A = new Matrix();
        Matrix b = new Matrix();
        //ODM.createMatrix(answer, matriksAug.get_ROW_EFF(), 1);
        ODM.createMatrix(A, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF()-1);
        ODM.createMatrix(b, matriksAug.get_ROW_EFF(), 1);

        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                A.set_ELMT(i, j, matriksAug.get_ELMT(i, j)); 
            b.set_ELMT(i, 0, matriksAug.get_ELMT(i, j+1));
            }
        }

        Matrix resultMatrix = ODM.multiplyMatrix(MB.inverseWithAdj(A), b);
        double[] resultArray = new double[resultMatrix.get_ROW_EFF()];
        for (int i = 0; i < resultMatrix.get_ROW_EFF(); i++) {
            resultArray[i] = resultMatrix.get_ELMT(i, 0); // Menyimpan setiap elemen hasil ke array
        }

        return resultArray;
    }

    public void displaySPLCramer(double[] solusi) {
        StringBuilder result = new StringBuilder();
    
        // Menampilkan setiap nilai x1, x2, ..., xn dari array solusi
        for (int i = 0; i < solusi.length; i++) {
            result.append("x").append(i + 1).append(" = ").append(solusi[i]).append("\n");
        }
    
        // Mencetak hasil
        System.out.println(result.toString());
    }

    public void writeSPL(double[] solusi) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");   
        String filePath = sc.nextLine();
        filePath = filePath + ".txt";
        try {
            FileWriter writer = new FileWriter("test/" + filePath);
            StringBuilder result = new StringBuilder();
    
            // Menambahkan setiap nilai x1, x2, ..., xn dari array solusi
            for (int i = 0; i < solusi.length; i++) {
                result.append("x").append(i + 1).append(" = ").append(solusi[i]).append("\n");
            }
    
            // Menulis hasil ke file
            writer.write(result.toString());
            writer.close();
    
            System.out.println("Hasil SPL Cramer telah ditulis ke file: " + filePath);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis ke file.");
            e.printStackTrace();
        }
    }
}
