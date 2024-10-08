package Library;

import java.util.Scanner;

public class OperasiDasarMatrix {

    static final int ROW_CAP = 1000;
    static final int COL_CAP = 1000;

    // constructor
    public void createMatrix(Matrix m, int nRows, int nCols) {

        m.set_ROW_EFF(nRows);
        m.set_COL_EFF(nCols);}


// selector

//     Read & Write
    public void readMatrix(Matrix m, int nRow, int nCol) {
        int i,j;
        Scanner sc = new Scanner(System.in);
        for (i = 0 ; i < nRow; i++) {
            for (j = 0 ; j < nCol; j++) {
                m.set_ELMT(i,j,sc.nextDouble());
            }
        }
    }

//    public void readMatrixFile(String filename, Matrix m) {
//
//    }
//
//    public void readMatrixFileInterpolate(String filename, Matrix m, double x) {
//
//    }
//
//    public Matrix readSPL() {
//
//        return null;
//    }
//
//    public Matrix readSPLCramer() {
//
//        return null;
//    }
//
    public void displayMatrix(Matrix m) {
        for (int i = 0; i <= getLastIdxRow(m); i++) {
            for (int j = 0; j <= getLastIdxCol(m); j++) {
                System.out.print(m.get_ELMT(i,j));
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
//
//    public void displayMatrixtoFile(Matrix m, String filename) {
//
//    }

    // boolean stuff
    public boolean isMatrixIdxValid(int i, int j) {
        return (i < ROW_CAP && j < COL_CAP);
    }

    public int getLastIdxRow(Matrix m) {
        return (m.get_ROW_EFF()-1);
    }

    public int getLastIdxCol(Matrix m) {
        return (m.get_COL_EFF()-1);
    }

    public boolean isIdxEff(Matrix m, int i, int j) {
        return ((i <= getLastIdxRow(m) && i >= 0) && (j <= getLastIdxCol(m) && j >= 0));
    }

    public double getElmtDiagonal(Matrix m, int i) {
        return m.get_ELMT(i,i);
    }

    public Matrix copyMatrix(Matrix mIn) {
        Matrix mOut = new Matrix();
        createMatrix(mOut, mIn.get_ROW_EFF(), mIn.get_COL_EFF());

        for (int i = 0; i <= getLastIdxRow(mIn); i++) {
            for (int j = 0; j <= getLastIdxCol(mIn); j++) {
                mOut.set_ELMT(i,j,mIn.get_ELMT(i,j));
            }
        }
        return mOut;
    }

    public Matrix addMatrix(Matrix m1, Matrix m2) {
        Matrix m3 = new Matrix();
        createMatrix(m3, m1.get_ROW_EFF(), m2.get_COL_EFF());
        for (int i = 0; i <= getLastIdxRow(m1); i++) {
            for (int j = 0; j <= getLastIdxCol(m1); j++) {
                m3.set_ELMT(i,j,(m1.get_ELMT(i,j))+(m2.get_ELMT(i,j)));
            }
        }
        return m3;

    }

    // section-10
    public Matrix subtractMatrix(Matrix m1, Matrix m2) {
        Matrix m3 = new Matrix();
        createMatrix(m3, m1.get_ROW_EFF(), m2.get_COL_EFF());
        for (int i = 0; i <= getLastIdxRow(m1);i++) {
            for (int j = 0; j <= getLastIdxCol(m1);j++) {
                m3.set_ELMT(i,j,(m1.get_ELMT(i,j))-(m2.get_ELMT(i,j)));
            }
        }
        return m3;
    }

    // section 11
    public Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix m3 = new Matrix();
        createMatrix(m3, m1.get_ROW_EFF(), m2.get_COL_EFF());
        for (int i = 0; i < m1.get_ROW_EFF(); i++) {
            for (int j = 0; j < m2.get_COL_EFF(); j++) {
                double sum = 0;
                for (int k = 0; k < m1.get_COL_EFF(); k++) {
                    sum += m1.get_ELMT(i, k) * m2.get_ELMT(k, j);
                }
                m3.set_ELMT(i, j, sum);
            }
        }
        return m3;
    }

    // section-12
    public Matrix multiplyMatrixWithMod(Matrix m1, Matrix m2, int mod) {
        Matrix m3 = new Matrix();
        createMatrix(m3, m1.get_ROW_EFF(), m2.get_COL_EFF());
        m3 = multiplyMatrix(m1,m2);
        for (int i = 0; i < m1.get_ROW_EFF(); i++) {
            for (int j = 0; j < m2.get_COL_EFF(); j++) {
                m3.set_ELMT(i,j,m3.get_ELMT(i,j)%mod);
            }
        }
        }

    public Matrix multiplyByConst(Matrix m, double x) {
        Matrix m3 = new Matrix();
        createMatrix(m3, m.get_ROW_EFF(), m.get_COL_EFF());
        for (int i = 0; i <= getLastIdxRow(m);i++) {
            for (int j = 0; j <= getLastIdxCol(m);j++) {
                m3.set_ELMT(i,j,(m.get_ELMT(i,j) * x));
            }
        }
        return m3;
        }



    public boolean isMatrixSizeEqual(Matrix m1, Matrix m2) {
        return (m1.get_ROW_EFF() == m2.get_ROW_EFF() && m1.get_COL_EFF() == m2.get_COL_EFF());
    }

    public boolean isMatrixEqual(Matrix m1, Matrix m2) {
        if (isMatrixSizeEqual(m1, m2)) {
            for (int i = 0; i < m1.get_ROW_EFF(); i++) {
                for (int j = 0; j < m1.get_COL_EFF(); j++) {
                    if (m1.get_ELMT(i, j) != m2.get_ELMT(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }


    public int countElmt(Matrix m) {
        return (m.get_ROW_EFF() * m.get_COL_EFF());
    }

    public boolean isSquare(Matrix m) {
        return (m.get_ROW_EFF() == m.get_COL_EFF() );
    }

    public boolean isSymmetric(Matrix m) {
        if (!isSquare(m)) {
            return false;
        }

        for (int i = 0; i < m.get_ROW_EFF(); i++) {
            for (int j = 0; j < m.get_COL_EFF(); j++) {
                if (m.get_ELMT(i, j) != m.get_ELMT(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIdentity(Matrix m) {
        if (!isSquare(m)) {
            return false;
        }
        for (int i = 0; i < m.get_ROW_EFF(); i++) {
            for (int j = 0; j < m.get_COL_EFF(); j++) {
                if (m.get_ELMT(i, j) != 0 && i != j) {
                    return false;
                }
                if (m.get_ELMT(i, j) != 1 && i == j) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSparse(Matrix m) {
        int countzero = 0;
        int countnonzero = 0;
        for (int i = 0; i < m.get_ROW_EFF(); i++) {
            for (int j = 0; j < m.get_COL_EFF(); j++) {
                if (m.get_ELMT(i, j) == 0) {
                    countzero += 1;
                }
                else if (m.get_ELMT(i, j) != 0) {
                    countnonzero += 1;
                }
            }
        }
        if (countzero > countnonzero) {
            return true;
        }
        else {
            return false;
        }
    }

    public Matrix negation(Matrix m) {
        return (multiplyByConst(m, -1));
    }

//    public double determinant(Matrix m);

    public Matrix mergeMatrix(Matrix m1, Matrix m2) {
        Matrix m3 = new Matrix();
        createMatrix(m3, m1.get_ROW_EFF(), m1.get_COL_EFF() + m2.get_COL_EFF());
        for (int i = 0; i <= getLastIdxRow(m1); i++) {
            for (int j = 0; j <= getLastIdxCol(m1);j++) {
                m3.set_ELMT(i,j,m1.get_ELMT(i,j));
            }
        }
        for (int i = 0; i <= getLastIdxRow(m2);i++) {
            for (int j = getLastIdxCol(m1)+1; j <= getLastIdxCol(m1) + getLastIdxCol(m2)+1;j++) {
                m3.set_ELMT(i,j,m2.get_ELMT(i,j));
            }
        }
        return m3;
    }

}