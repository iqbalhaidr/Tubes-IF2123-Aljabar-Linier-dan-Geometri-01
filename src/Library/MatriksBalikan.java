package Library;

import java.lang.Math;

public class MatriksBalikan {

    OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    Determinan DET = new Determinan();


    public Matrix matriksKofaktor (Matrix matriks) {                                                                                   //MENGHASILKAN MATRIKS KOFAKTOR
        Matrix matriksKof = new Matrix(); ODM.createMatrix(matriksKof, matriks.get_ROW_EFF(), matriks.get_COL_EFF());                  //inisialisasi matriksKof & matriksMinor
        Matrix matriksMinor = new Matrix(); ODM.createMatrix(matriksMinor, matriks.get_ROW_EFF()-1, matriks.get_COL_EFF()-1);
        int minorRow, minorCol;

        for (int matriksRow = 0; matriksRow < matriks.get_ROW_EFF(); matriksRow++) {                                                  //tinjau setiap elmt matriks
            for (int matriksCol = 0; matriksCol < matriks.get_COL_EFF(); matriksCol++) {
                minorRow = -1;                                                                                                         //idx matriksMinor
                for (int i = 0; i < matriks.get_ROW_EFF(); i++) {                                                                     //cari elmt matriksMinor
                    if (i == matriksRow) {
                        continue;
                    }
                    minorRow++; minorCol = 0;                                                                                          // idx matriksMinor
                    for (int j = 0; j < matriks.get_ROW_EFF(); j++) {
                        if (j == matriksCol) {
                            continue;
                        }
                        matriksMinor.set_ELMT(minorRow, minorCol, matriks.get_ELMT(i, j));
                        minorCol++;
                    }
                }
                matriksKof.set_ELMT(matriksRow, matriksCol, (DET.kofaktor(matriksMinor) * Math.pow((-1), (matriksRow + matriksCol)))); //set elmt matriksKof
            }
        }
        return matriksKof;
    }
}