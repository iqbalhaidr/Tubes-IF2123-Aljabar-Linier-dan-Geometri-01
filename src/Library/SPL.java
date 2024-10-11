package Library;

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
    public Matrix SPLInverse (Matrix matriksAug) {
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

        return ODM.multiplyMatrix(MB.inverseWithAdj(A), b);
    }
}