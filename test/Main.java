package test;


import Library.OperasiDasarMatrix;
import Library.EliminasiGaus;
import Library.Matrix;

public class Main {
    public static void main(String[] args){
        OperasiDasarMatrix ODM = new OperasiDasarMatrix();
        EliminasiGaus gauss = new EliminasiGaus();

        Matrix matriks = new Matrix();
        System.out.println("Silahkan masukkan matriks anda:");
        ODM.createMatrix(matriks,4,5);
        ODM.readMatrix(matriks, 4, 5);

        System.out.println("Matriks Sebelum:");
        ODM.displayMatrix(matriks);

        gauss.GausMethod(matriks);

        System.out.println("Matriks Sesudah:");
        ODM.displayMatrix(matriks);

    }
}
