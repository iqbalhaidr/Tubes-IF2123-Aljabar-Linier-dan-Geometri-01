package test;

import Library.OperasiDasarMatrix;
import Library.Matrix;
import Library.SPL;
import java.util.Arrays;


public class testCramer {
    public static void main(String[] args) {
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        SPL objSPL = new SPL();
        Matrix matriksAug = new Matrix();

        ODM.createMatrix(matriksAug, 3, 4); //asumsi 3 (peubah dan persamaan)
        ODM.readMatrix(matriksAug, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());

        System.out.println(Arrays.toString(objSPL.SPLCramer(matriksAug)));
    }
}


/*
Input (asumsi n = 3)
-1 2 -3 1
2 0 1 0
3 -4 4 2

Output
[0.8, -1.5, -1.6]
 */