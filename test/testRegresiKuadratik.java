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
        matriksAug = ODM.readRegresi();
        //System.out.println(Arrays.toString(GJ.solveSPL(RB.solveRegresiKuadratik(matriksAug))));
        //System.out.println(Arrays.toString(RB.solveRegresiKuadratik(matriksAug)));
        //RB.RegresiLinear(matriksAug);
        RB.RegresiKuadratik(matriksAug);
    }
}
