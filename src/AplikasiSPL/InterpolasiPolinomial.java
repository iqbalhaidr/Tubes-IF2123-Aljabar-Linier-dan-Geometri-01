package AplikasiSPL;
import Library.*;

import java.util.Scanner;


public class InterpolasiPolinomial {

    Matrix m = new Matrix();
    OperasiDasarMatrix operasi = new OperasiDasarMatrix();
    gaussjordan gj = new gaussjordan();

    public Matrix readPoint(Matrix m) {
        Matrix mOut = new Matrix();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan banyak titik: ");
        int row = scanner.nextInt();
        operasi.createMatrix(mOut, row, 2);
        System.out.println("Masukkan titik: ");
        operasi.readMatrix(mOut,row,2);

        return mOut;
    }

    public Matrix ToMatrixInterpolasi(Matrix mIn) {
        Matrix mOut = new Matrix();
        operasi.createMatrix(mOut,mIn.get_ROW_EFF(), mIn.get_ROW_EFF()+1);
        for (int i = 0; i <= operasi.getLastIdxRow(mOut);i++) {
            int power = 0;
           for (int j = 0; j <= operasi.getLastIdxCol(mOut);j++) {
               if (j < operasi.getLastIdxCol(mOut)) {
               mOut.set_ELMT(i,j, 1*Math.pow(mIn.get_ELMT(i,0),power));
               power++; }
               else { //value of b (Ax = b)
                   mOut.set_ELMT(i,j, mIn.get_ELMT(i,1));
               }
           }
        }

        return mOut;
        }

    public static void printPolynomial(double[] coefficients) {
        StringBuilder polynomial = new StringBuilder();
        int degree = coefficients.length - 1; // suku tertinggi

        for (int i = 0; i <= degree; i++) {
            if (coefficients[i] == 0) {
                continue;
            }


            if (polynomial.length() > 0) {
                polynomial.append(coefficients[i] > 0 ? " + " : " - ");
            } else if (coefficients[i] < 0) {
                polynomial.append("- ");
            }


            double absCoefficient = Math.abs(coefficients[i]);

            // Append coeff
            if (i == 0 || absCoefficient != 1) {
                polynomial.append(absCoefficient);
            }


            if (i > 0) {
                if (i > 1) {
                    polynomial.append("(");
                }
                polynomial.append("x");
                if (i > 1) {
                    polynomial.append("^").append(i).append(")");
                }
            }
        }

        // print hasil
        System.out.println(polynomial.toString().trim());
    }



    public double SolveInterpolasi(Matrix m, boolean inputfile) {
        double x = 0;
        double[] numbers = null;
        double y = -999999; //mark

        if (!inputfile) {
            y = 0;
            Matrix mPoint = readPoint(m);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan nilai x yang akan ditaksir: ");
            x = scanner.nextDouble();

            Matrix mInterpolasi = ToMatrixInterpolasi(mPoint);

            numbers = new double[mInterpolasi.get_COL_EFF() - 1];
            numbers = gj.solveSPL2(mInterpolasi);


            System.out.print("\nPersamaan polinomial: ");
            printPolynomial(numbers);

            for (int col = 0; col < numbers.length; col++) {
                double hasil = numbers[col] * Math.pow(x, col);
                y += hasil;
            }

            y = Math.round(y * 10000.0) / 10000.0;

            System.out.println("Nilai f(x) untuk x = " + x + " adalah " + y);

        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Masukkan nama file: ");
            String filename = sc.nextLine();
            operasi.readMatrixFileInterpolate(filename, m);

            if (m.get_ROW_EFF() != 0) {
            y = 0;
            x = m.get_ELMT(m.get_ROW_EFF() - 1, 0);
            m.set_ROW_EFF(m.get_ROW_EFF() - 1);
            m.set_COL_EFF(m.get_COL_EFF() - 1);

            Matrix mInterpolasi = ToMatrixInterpolasi(m);

            numbers = new double[mInterpolasi.get_COL_EFF() - 1];
            numbers = gj.solveSPL2(mInterpolasi);


                System.out.print("\nPersamaan polinomial: ");
                printPolynomial(numbers);

            for (int col = 0; col < numbers.length; col++) {
                double hasil = numbers[col] * Math.pow(x, col);
                y += hasil;
            }

            y = Math.round(y * 10000.0) / 10000.0;

            System.out.println("Nilai f(x) untuk x = " + x + " adalah " + y);
        } }


        return y;
    }


}
