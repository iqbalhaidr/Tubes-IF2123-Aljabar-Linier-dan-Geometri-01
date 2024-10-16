package test;

import AplikasiSPL.*;
import Library.*;

public class testRegresiLinear {
    public static void main(String[] args) {
        RegresiBerganda RB = new RegresiBerganda();
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        
        Matrix matriksAug = new Matrix();
        ODM.createMatrix(matriksAug, 5, 4); //asumsi 10 data, 5 peubah
        ODM.readMatrix(matriksAug, matriksAug.get_ROW_EFF(), matriksAug.get_COL_EFF());

        RB.RegresiLinear(matriksAug);
    }
}
