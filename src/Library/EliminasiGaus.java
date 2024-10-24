package Library;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EliminasiGaus {
    OperasiDasarMatrix ODM=new OperasiDasarMatrix();
    gaussjordan gaussjordan = new gaussjordan();

    // Cari baris non-nol pertama dalam kolom
    public int searchindexnonzero (Matrix matriks, int index, int rownow){
        for (int col=index; col<matriks.colEff; col++){
            for (int row=rownow+1; row<matriks.rowEff; row++){
                if (matriks.m[row][col]!=0){
                    return row;
                }
            }
            break;
        }
        return -1;
    }
    
    //metode gauss
    public void GausMethod (Matrix matriks){
        gaussjordan.ToEchelon(matriks);
    }    

    //prosedur untuk mencari nilai solusi yang ada dan memprintnya

    public double[] backsubsV2 (Matrix matriks){ //JANGAN DELETE!!
        if (matriks.m[matriks.rowEff - 1][matriks.colEff - 2] == 0 && matriks.m[matriks.rowEff - 1][matriks.colEff - 1] != 0) {
            System.out.println("There is no solution for this SPL.");
            return null;
        } 
        else {
            // Inisialisasi array solusi dengan 0
            double[] solution = new double[matriks.colEff - 1];
            boolean[] parametric = new boolean[matriks.colEff - 1]; 
            for (int i=0; i<matriks.colEff-1; i++){
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
            return solution;
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

    public ArrayList<String> backsubsperfected(Matrix m){
        boolean unique =true;
        ArrayList<String> save = new ArrayList<>();
        for (int i =0; i<m.rowEff; i++){
            int idx = searchLeadingone(m.m[i], m.colEff-1);
            if (idx==-1 && m.m[i][m.colEff - 1]!=0 ){
                save.add("Tidak ada solusi.");
                System.out.println("Tidak ada solusi.");
                return save;
                
            }
            else if(idx==-1){
                unique=false;
            }
        }
        if (m.colEff-1!=m.rowEff){
            unique=false;
        }
        if (unique){
            double[] jawabanUnik= new double[m.rowEff];
            String result ="";
            
            for (int row=m.rowEff-1; row>=0; row--){
                int idx=searchLeadingone(m.m[row], m.rowEff);
                jawabanUnik[row] = m.m[row][m.colEff-1];
                for (int i=m.colEff-2; i>idx; i--){
                    jawabanUnik[row]-=m.m[row][i]*jawabanUnik[i];
                }
            }
            System.out.println("Hasil Gaus:");
            ODM.displayMatrix(m);
            System.out.println();
            System.err.println("Solusi SPL tersebut adalah:");
            for (int i=0; i<m.rowEff; i++){
                result="X"+(i+1)+" = "+jawabanUnik[i];
                System.err.println(result);
                save.add(result);
            }
            return save;
                
        }
        else{
            Matrix asli = new Matrix();
            ODM.createMatrix(asli, m.rowEff, m.colEff);
            asli=m;
            boolean[] parametric = new boolean[m.colEff - 1]; 
            for (int i=0; i<m.colEff-1; i++){ //array penanda mana variabel bebas mana tidak
                parametric[i]=true;
            }

            for (int i=m.rowEff-1; i>=0; i--){ //dalam for ini backsubs
                int idx= searchLeadingone(m.m[i], m.colEff-1);
                if (i==m.rowEff-1){
                    if (idx !=-1){
                        parametric[idx]=false;
                      
                        for (int j= idx+1; j<m.colEff-1;j++){
                            m.m[i][j]=m.m[i][j]*-1.0;
                        }
                    }
                }    
                else{
                    if (idx !=-1){
                        parametric[idx]=false;
                        for (int j= idx+1; j<m.colEff-1;j++){
                            m.m[i][j]=m.m[i][j]*-1.0;
                        }

                        for (int col=idx+1; col<m.colEff; col++){
                            for (int lead=parametric.length-1; lead>idx;lead--){
                                if (lead==col && m.m[i][col]!=0 && !parametric[lead]){
                                    int l=searchindexnonzero(m, col, i);
                                    double pengali=m.m[i][col];
                                    for (int z=col+1; z<m.colEff; z++){
                                        m.m[i][z]=m.m[i][z]+m.m[l][z]*pengali;
                                    }
                                    m.m[i][col]=0;
                                    break;
                                }
                            }
                            
                        }
                        
                    }
                }
            }

            System.out.println("Hasil Gaus:");
            ODM.displayMatrix(asli);
            System.out.println();
            save.add("Solusi untuk SPL mu:\n");
            System.out.println("Solusi untuk SPL mu:");
            for (int row=0; row<m.rowEff;row++){  
                String result="";
                for (int col=0; col<m.colEff-1; col++){
                    if (m.m[row][col]==1){
                        int counter=0;
                        result += "X" + (col + 1) + " = " + ((m.m[row][m.colEff - 1] != 0) ? String.valueOf(m.m[row][m.colEff - 1]) : "");
                        if (m.m[row][m.colEff - 1] == 0){
                            counter-=1;
                        }
                        for (int j=col+1; j<m.colEff-1; j++){
                            if (m.m[row][j]!=0 && m.m[row][j]>0 && counter>=0){
                                result+=" + "+((m.m[row][j] >1) ? (String.valueOf(m.m[row][j])+"X"+(j+1)) : ("X"+(j+1)));
                            }
                            else if (m.m[row][j]!=0 && m.m[row][j]<0 && counter>=0){
                                result+=" - "+ ((Math.abs(m.m[row][j]) >1) ? (String.valueOf(m.m[row][j])+"X"+(j+1)) : ("X"+(j+1)));
                            }
                            else if (m.m[row][j]!=0 && m.m[row][j]>0 && counter<0){
                                result+=((m.m[row][j] >1) ? (String.valueOf(m.m[row][j])+"X"+(j+1)) : ("X"+(j+1)));
                                counter+=1;
                            }
                            else if (m.m[row][j]!=0 && m.m[row][j]<0 && counter<0){
                                result+="-"+((Math.abs(m.m[row][j]) >1) ? (String.valueOf(m.m[row][j])+"X"+(j+1)) : ("X"+(j+1)));
                                counter+=1;
                            }
                            
                        }
                        if (counter<0){
                            result+=m.m[row][m.colEff - 1];
                        }
                        System.err.println(result);
                        save.add(result);
                        break;
                    }
                }

            }
            for (int col=0; col<m.colEff-1; col++){
                if (parametric[col]){
                    System.out.printf("X%d = Bebas",(col+1));
                    System.out.println("");
                    String result="X"+(col+1)+"= Bebas\n";
                    save.add(result);
                }
            }
            return save;
        }
    }
        
}
    