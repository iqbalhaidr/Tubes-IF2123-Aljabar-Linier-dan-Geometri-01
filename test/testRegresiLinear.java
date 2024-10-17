package test;

import AplikasiSPL.*;
import Library.*;

public class testRegresiLinear {
    public static void main(String[] args) {
        RegresiBerganda RB = new RegresiBerganda();
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        
        Matrix matriksAug = new Matrix();
        matriksAug = ODM.readRegresi();
        RB.RegresiLinear(matriksAug);
    }
}
