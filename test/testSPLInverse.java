package test;

import Library.OperasiDasarMatrix;
//import Library.Determinan;
//import Library.MatriksBalikan;
import Library.SPL;
import Library.Matrix;

public class testSPLInverse {
    public static void main(String[] args) {
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        SPL objSPL = new SPL();
        Matrix matriksAug = new Matrix();

        ODM.createMatrix(matriksAug, 4, 5); //asumsi 3 (peubah dan persamaan)
        ODM.readMatrix(matriksAug, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());

        ODM.displayMatrix(objSPL.SPLInverse(matriksAug));
    }
}


/*
Input (asumsi n = 3)
-1 2 -3 1
2 0 1 0
3 -4 4 2

Output
0.8 
-1.5 
-1.6 
 */