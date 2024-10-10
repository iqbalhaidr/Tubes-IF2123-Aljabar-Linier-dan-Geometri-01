package Library;



public class Determinan {
    OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    public double kofaktor(Matrix matriks){
        double deter=0;
        //buat basis
        if (matriks.rowEff==2){
            return matriks.m[0][0]*matriks.m[1][1]-matriks.m[0][1]*matriks.m[1][0];
        } else if (matriks.rowEff==1){ //start change
            return matriks.m[0][0];
        } //end change
        else{
            //buat rekuren
            for (int i=0; i<matriks.colEff; i++){
                Matrix Submatriks=createSubMatrix(matriks, i);
                double elem = matriks.m[0][i]*((i % 2 == 0) ? 1 : -1)*(kofaktor(Submatriks));
                deter=deter+elem;
            }
            return deter;
        }
    }

    public Matrix createSubMatrix (Matrix matriks, int colExcluded){
        int subSize = matriks.rowEff - 1;
        Matrix Submatriks = new Matrix();
        ODM.createMatrix(Submatriks, subSize, subSize);
        for (int i=1; i<matriks.rowEff; i++){
            int idxOfCol=0;
            for (int j=0; j<matriks.colEff; j++){
                if (j!=colExcluded){
                    Submatriks.m[i-1][idxOfCol]=matriks.m[i][j];
                    idxOfCol=idxOfCol+1;
                }
            }
        }
        return Submatriks;
    }
}
