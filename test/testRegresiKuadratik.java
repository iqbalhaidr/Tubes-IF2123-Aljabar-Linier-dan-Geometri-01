package test;

import AplikasiSPL.*;
import Library.*;
//import java.util.Arrays;

public class testRegresiKuadratik {
    public static void main(String[] args) {
        RegresiBerganda RB = new RegresiBerganda();
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        //EliminasiGaus EG = new EliminasiGaus();
        //gaussjordan GJ = new gaussjordan();
        
        Matrix matriksAug = new Matrix();
        ODM.createMatrix(matriksAug, 5, 4); //asumsi 10 data, 5 peubah
        ODM.readMatrix(matriksAug, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());

        //System.out.println(Arrays.toString(GJ.solveSPL(RB.solveRegresiKuadratik(matriksAug))));
        //System.out.println(Arrays.toString(RB.solveRegresiKuadratik(matriksAug)));
        //RB.RegresiLinear(matriksAug);
        RB.RegresiKuadratik(matriksAug);
    }
}
