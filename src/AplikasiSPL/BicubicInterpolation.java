package AplikasiSPL;

import java.util.ArrayList;
import java.util.Scanner;

import Library.Matrix;
import Library.OperasiDasarMatrix;
import Library.gaussjordan;


public class BicubicInterpolation {

    private OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    private Matrix matriks = new Matrix();
    private Matrix matriksCoeff = new Matrix();
    private Titik[] arrayOfTitik= new Titik[4];
    private gaussjordan inversematriks = new gaussjordan();
    public Matrix Xmatriks = new Matrix();
    public static boolean XmatrixDoneProces= false;


    public class Titik {
        private double x;  // Koordinat X
        private double y;  // Koordinat Y
    
        // Konstruktor Titik 2D
        public Titik(double x, double y) {
            this.x = x;
            this.y = y;
        }
        // Getter untuk X
        public double getX() {
            return x;
        }
        // Getter untuk Y
        public double getY() {
            return y;
        }

    }
    

    public BicubicInterpolation() {  //inisialisasi
        ODM.createMatrix(matriks, 4, 4);  
        ODM.createMatrix(matriksCoeff, 4, 4); 
        ODM.createMatrix(Xmatriks, 16, 16);
        arrayOfTitik[0] = new Titik(0.0, 0.0);
        arrayOfTitik[1] = new Titik(1.0, 0.0);
        arrayOfTitik[2] = new Titik(0.0, 1.0);
        arrayOfTitik[3] = new Titik(1.0, 1.0);
    }

    public Titik[] getArrayOfTitik() {
        return arrayOfTitik;
    }

    //step 0
    public void inputManualF() {
        System.out.print("Masukkan Elemen Matriks anda (4x4):\n");
        ODM.readMatrix(matriks, 4, 4);
    }

    //jenis 0 f(x,y)
    //jenis 1 fx
    //jenis 2 fy
    //jenis 3 fxy

    //step 1
    public void makeCoeff (){ //Menghitung a00 - a33 lalu disalin ke Xmatriks untuk [0..1]
        if (!XmatrixDoneProces) {
            int row=0;
            for(int jenis=0; jenis<arrayOfTitik.length; jenis++){
                for(int elem=0; elem<arrayOfTitik.length; elem++){
                    double x=arrayOfTitik[elem].getX();
                    double y=arrayOfTitik[elem].getY();
                    if (jenis==0){
                        for (int i=0; i<4; i++){
                            for (int j=0; j<4; j++){
                                matriksCoeff.m[i][j]= pangkat(x, i)*pangkat(y, j);
                            }
                        }
                    }
                    else if(jenis==1){
                        for (int i=0; i<4; i++){
                            for (int j=0; j<4; j++){
                                matriksCoeff.m[i][j]= (i==0) ? 0 : (i*pangkat(x, i-1)*pangkat(y, j));
                            }
                        }
                    }
                    
                    else if(jenis==2){
                        for (int i=0; i<4; i++){
                            for (int j=0; j<4; j++){
                                matriksCoeff.m[i][j]= (j==0) ? 0 : (j* pangkat(x, i)*pangkat(y, j-1));
                            }
                        }
                    }
                    else if(jenis==3){
                        for (int i=0; i<4; i++){
                            for (int j=0; j<4; j++){
                                matriksCoeff.m[i][j]= (i==0 || j==0) ? 0 : i*j* pangkat(x, i-1)*pangkat(y, j-1);
                            }
                        }
                    }
                    copyToXMatriks(matriksCoeff, Xmatriks, row);
                    row+=1;
                }
            }

            Xmatriks= inversematriks.MatriksBalikan(Xmatriks);
            XmatrixDoneProces = true; 
            // memastikan coeff hanya dihitung sekali saja
            // jika  sudah pernah dibuat
        }
    }

    public double pangkat(double base, int eksponen) {
        double hasil = 1.0;
        boolean negatif = eksponen < 0;
        eksponen = (eksponen < 0) ? (-eksponen): eksponen;

        for (int i = 0; i < eksponen; i++) {
            hasil *= base;
        }
        return negatif ? 1.0 / hasil : hasil;
    }

    public void copyToXMatriks (Matrix matriksCoeff, Matrix Xmatriks, int row){
        int col=0;
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                Xmatriks.m[row][col]=matriksCoeff.m[j][i];
                col+=1;
            }
        }

    }

    public double[][] convert2DTo1Column(double[][] matriks) {

        double[][] array2DWith1Column = new double[16][1];
        int index = 0;
    
        // Loop untuk menyalin elemen berdasarkan baris
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array2DWith1Column[index][0] = matriks[i][j];
                index++;
            }
        }
    
        return array2DWith1Column;
    }

    public Matrix convert1Column_To_2d_4x4(Matrix result){
        Matrix matrix4x4 = new Matrix();
        ODM.createMatrix(matrix4x4, 4, 4);
        int index=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Hitung indeks pada matriks 16x1
                matrix4x4.m[j][i] = result.m[index][0];
                index+=1;
            }
        }
    
        return matrix4x4;
    }

    public Matrix getAll_a_value (Matrix matriksfile){ //step 2
        Matrix valueFinOneColumn = new Matrix();
        Matrix result = new Matrix();
        Matrix final_a = new Matrix();
        ODM.createMatrix(valueFinOneColumn, 16, 1);
        ODM.createMatrix(final_a, 4, 4);
        valueFinOneColumn.m = (matriksfile!=null) ? convert2DTo1Column(matriksfile.m):convert2DTo1Column(matriks.m);
        result= ODM.multiplyMatrix(Xmatriks, valueFinOneColumn);
        final_a=convert1Column_To_2d_4x4(result);
        return final_a;
    }

    public boolean isInRange(double x, double y) {
        return (x >= 0 && x <= 1) && (y >= 0 && y <= 1);
    }


    public String SolutionBicubic (ArrayList<Double> xy, Matrix matriks){
        Scanner scanner = new Scanner(System.in);
        makeCoeff();
        Matrix Result_a;
        double x,y;
        double result = 0.0;
        Result_a = getAll_a_value(matriks) ;

        if (xy.size()==0){
            // Minta input dua angka dalam satu baris
            do {
                System.out.print("Masukkan nilai (x,y) dalam rentang [0, 1]: ");
                x = scanner.nextDouble();
                y = scanner.nextDouble();
    
                if (isInRange(x, y)) {
                    System.out.println("Nilai yang dimasukkan valid.");
                } else {
                    System.out.println("Nilai tidak valid. Harap masukkan x dan y antara 0 dan 1.");
                }
            } while (!isInRange(x, y));
        }
        else{
            x=xy.get(0);
            y=xy.get(1);
        }
        
        // Loop untuk menghitung ∑∑ a_ij * x^i * y^j
        for (int i=0; i<4; i++){
            for (int j=0; j<4;j++){
                result+= Result_a.m[i][j]*pangkat(x, i)*pangkat(y, j);
            }
        }
        String kalimat = String.format("Hasil f(%.2f, %.2f) adalah %.2f\n\n", x, y, result);
        System.out.println(kalimat);
        return kalimat;

    }




}