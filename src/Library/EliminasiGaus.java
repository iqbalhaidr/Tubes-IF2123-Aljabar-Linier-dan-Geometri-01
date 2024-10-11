package Library;

public class EliminasiGaus {
    static char[] parameter={'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    OperasiDasarMatrix ODM=new OperasiDasarMatrix();
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

    public int searchElementNonZeroInRow(double[] arr, int length){
        for (int i=0; i<length; i++){
            if (arr[i]!=0){
                return i;
            }
        }
        return -1;
    }

    public void simplify (Matrix matriks){
        for (int row=0; row<matriks.rowEff; row++){
            double id=searchElementNonZeroInRow(matriks.m[row], matriks.colEff-1);
            if (id!=-1){
                double start=matriks.m[row][(int)id];
                for (int col=row; col<matriks.colEff; col++){
                    matriks.set_ELMT(row,col,matriks.m[row][col]/start);
                }
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

    //prosedur untuk mencari nilai solusi yang ada dan memprintnya
    public void backsubs (Matrix matriks){
        if (matriks.m[matriks.rowEff - 1][matriks.colEff - 2] == 0 && matriks.m[matriks.rowEff - 1][matriks.colEff - 1] != 0) {
            System.out.println("There is no solution for this SPL.");
        } 
        else {
            // Inisialisasi array solusi dengan 0
            double[] solution = new double[matriks.colEff - 1];
            boolean[] parametric = new boolean[matriks.colEff - 1]; 

            for (int i=0; i<solution.length; i++){
                solution[i]=0;
            }

            for (int row=matriks.rowEff-1; row>=0; row-- ){
                for (int col=0; col<matriks.colEff-1; col++){

                    if (matriks.m[row][col]==1){
                        solution[col]=matriks.m[row][matriks.colEff-1];
                        for (int j=col+1; j<matriks.colEff-1; j++){
                            if (solution[j]==0){
                                parametric[col]=true;
                            }
                            else{
                                if (!parametric[j]){
                                    solution[col]-=solution[j]*matriks.m[row][j];
                                }
                            }
                        }
                        break;
                    }
                }   
            }

            writesolution(matriks, solution, parametric);
        }
    }

    //fungsi mencari index dari leading one
    public int searchLeadingone(double[] arr, int length){
        for (int i=0; i<length; i++){
            if (arr[i]==1){
                return i;
            }
        }
        return -1;
    }

    public void writesolution (Matrix matriks, double[] arr, boolean[] parameter){
        System.out.println("");
        System.out.println("Solution for your SPL is:");
        for (int row=0; row<matriks.rowEff;row++){  
            String result="";
            boolean check=false;
            for (int col=0; col<matriks.colEff-1; col++){
                result="X"+(col+1)+" = ";
                if (matriks.m[row][col]==1){
                    result=result+arr[col];
                    check=true;
                    for (int j=col+1; j<matriks.colEff-1; j++){
                        if (parameter[j] || arr[j]==0){
                            if(matriks.m[row][j]!=0 &&  matriks.m[row][j]>0){
                                result=result+" - "+matriks.m[row][j]+"X"+(j+1);
                            }
                            else if(matriks.m[row][j]!=0 &&  matriks.m[row][j]<0){
                                result=result+" + "+((-1)*matriks.m[row][j])+"X"+(j+1);
                            }
                            
                        }
                    }
                    break;
                }
            }
            if (check){
                System.out.println(result);
            }

        }
        for (int col=0; col<matriks.colEff-1; col++){
            if (arr[col]==0){
                System.out.printf("X%d = Bebas",(col+1));
                System.out.println("");
            }
        }
    }
}


    
    
