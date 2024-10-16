package AplikasiSPL;
import Library.*;

public class RegresiBerganda {

    OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    Determinan DET = new Determinan();
    MatriksBalikan MB = new MatriksBalikan();
    
    /*prekondisi: (X^T).X memiliki inverse
      parameter : matriksAug adalah matriks augmented dengan format X1 X2 X3 ... Y
      return    : matriks 1 dimensi [b0, b1, b2, ..., bn] */
    public Matrix solveRegresiLinear(Matrix matriksAug) {
        Matrix X = new Matrix(); ODM.createMatrix(X, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());
        Matrix y = new Matrix(); ODM.createMatrix(y, matriksAug.get_ROW_EFF(), 1);
        
        for (int i = 0; i < matriksAug.get_ROW_EFF(); i++) {
            X.set_ELMT(i, 0, 1);
            for (int j = 0; j < matriksAug.get_COL_EFF()-1; j++) {
                X.set_ELMT(i, j+1, matriksAug.get_ELMT(i, j));           //copy elmt untuk matriksAug A          //copy elmt untuk matriksAug b
            }
            y.set_ELMT(i, 0, matriksAug.get_ELMT(i, matriksAug.get_COL_EFF()-1));
        }
        return (ODM.multiplyMatrix(MB.inverseWithAdj(ODM.multiplyMatrix(MB.transpose(X), X)), ODM.multiplyMatrix(MB.transpose(X), y)));
    }

    /*parameter : koefRegresi adalah matriks augmented dengan format X1 X2 X3 ... Y */
    public void displayRegresiLinear(Matrix koefRegresi) {
        StringBuilder result = new StringBuilder("y = ");

        result.append(koefRegresi.get_ELMT(0, 0));

        for (int i = 1; i < koefRegresi.get_ROW_EFF(); i++) {
            if (koefRegresi.get_ELMT(i, 0) >= 0) {
                result.append(" + ");
            } else {
                result.append(" - ");
            }
            result.append(Math.abs(koefRegresi.get_ELMT(i, 0))).append(".X").append(i);
        }

        System.out.println(result.toString());
    }

    //menerima parameter dalam bentukan matriks augmented dan print hasilnya
    public void RegresiLinear(Matrix matriksAug) {
        displayRegresiLinear(solveRegresiLinear(matriksAug));
    }
}
