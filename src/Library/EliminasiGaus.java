package Library;

public class EliminasiGaus {
    public void swap(Matrix matriks, int i, int j){
        if ((i<matriks.rowEff) && (j<matriks.rowEff)){
            double[] row1 = matriks.m[i];
            double[] row2 = matriks.m[j];
            double[] temp=row1;
            matriks.m[i]=row2;
            matriks.m[j]=temp;
        }
    }

    //mengurangi row i dengan n kali row j
    public void plus_or_subtract_row (Matrix matriks, int i, int j){
        double[] row1 = matriks.m[i];
        double[] row2 = matriks.m[j];
        double constant=searchPivot(matriks, j)/searchPivot(matriks, i);
        for (int col=0; col<matriks.colEff; col++){
            row2[col]=row2[col]-(constant*row1[col]);
        }
        
    }

    //cari pivot di baris
    public double searchPivot (Matrix matriks, int rowinmtrx){
        double[] row=matriks.m[rowinmtrx];
        for (int col=0; col<matriks.rowEff; col++){
            if (row[col]!=0){
                return row[col]; //return elem pertama yang tidak 0
            }
        }
        return 0;
    }

    // Cari baris non-nol pertama dalam kolom
    public int searchindexnonzero (Matrix matriks, int index){
        for (int col=index; col<matriks.colEff; col++){
            for (int row=index; row<matriks.rowEff; row++){
                if (matriks.m[row][col]!=0){
                    return row;
                }
            }
        }
        return matriks.rowEff-1;
    }

    // Fungsi untuk mengubah pivot menjadi positif jika negatif
    public void makePivotPositive(Matrix matriks, int row_now) {
        double pivot = searchPivot(matriks, row_now);
        if (pivot < 0) {
            for (int j = 0; j < matriks.colEff; j++) {
                matriks.set_ELMT(row_now, j, matriks.m[row_now][j] * (-1));
            }
        }
    }

    //buat kolom dibawah pivot 0
    public void makeValueBelowPivotZero (Matrix matriks, int row_now, int col){
        for (int row=row_now+1; row<matriks.rowEff; row++){
            if (matriks.m[row][col]!=0){
                makePivotPositive(matriks, row_now);
                plus_or_subtract_row(matriks, row_now, row);
            }
        }
    }

    public void simplify (Matrix matriks){
        for (int row=0; row<matriks.rowEff; row++){
            double start=matriks.m[row][row];
            for (int col=row; col<matriks.colEff; col++){
                matriks.set_ELMT(row,col,matriks.m[row][col]/start);
            }
        }
    }
    
    //metode gauss
    public void GausMethod (Matrix matriks){
        for (int col=0; col<matriks.colEff; col++){
            if (matriks.m[col][col]==0){
                int notzero =searchindexnonzero(matriks, col);
                if (notzero==0){
                    continue;
                }
                else{
                    swap(matriks, col, notzero);
                }
            }
            for (int row=col; row<matriks.rowEff; row++){
                makeValueBelowPivotZero(matriks, row , col);   
            }
        }
        simplify(matriks);
    }    
}