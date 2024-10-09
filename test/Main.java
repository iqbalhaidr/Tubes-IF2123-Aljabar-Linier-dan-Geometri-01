package test;


import Library.OperasiDasarMatrix;
import Library.Determinan;
import Library.EliminasiGaus;
import Library.Matrix;


public class Main {
    public static void main(String[] args){
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        EliminasiGaus gauss = new EliminasiGaus();
        Determinan cofactor = new Determinan();
        double hasilDet;

        Matrix matriks = new Matrix();
        System.out.println("Silahkan masukkan matriks anda:");
        ODM.createMatrix(matriks,4,4);
        ODM.readMatrix(matriks, 4, 4);

        // mencoba eliminasi gauss
        System.out.println("Matriks Sebelum:");
        ODM.displayMatrix(matriks);

        gauss.GausMethod(matriks);

        System.out.println("Matriks Sesudah:");
        ODM.displayMatrix(matriks);


        // mencoba determinan kofaktor
        hasilDet=cofactor.kofaktor(matriks);
        System.out.printf("Hasil Kofaktor: %f",hasilDet);


    }
}
