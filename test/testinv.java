package test;

import Library.OperasiDasarMatrix;
//import Library.Determinan;
import Library.MatriksBalikan;
import Library.Matrix;

public class testinv {
    public static void main(String[] args) {
        MatriksBalikan MB = new MatriksBalikan();
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        //Determinan DET = new Determinan();
        Matrix matriks = new Matrix();

        ODM.createMatrix(matriks, 3, 3);
        ODM.readMatrix(matriks, matriks.get_ROW_EFF(), matriks.get_COL_EFF());
        ODM.displayMatrix(MB.inverseWithAdj(matriks));
    }
}


/*
input
1 2 3
3 2 1
2 1 3

output
-0.41666666666666663 0.25 0.3333333333333333 
0.5833333333333333 0.25 -0.6666666666666666 
0.08333333333333333 -0.25 0.3333333333333333
*/