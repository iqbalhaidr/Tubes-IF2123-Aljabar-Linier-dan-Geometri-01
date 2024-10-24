package Library;

public class gaussjordan {

    OperasiDasarMatrix operasi = new OperasiDasarMatrix();

    public void SubtractRow(Matrix m, double factor, int pivotRow, int targetRow) {
        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            m.set_ELMT(targetRow, j, m.get_ELMT(targetRow, j) - factor * m.get_ELMT(pivotRow, j));
        }
    }

    public void MultiplyRow(Matrix m, double x, int i) {
        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            m.set_ELMT(i, j, m.get_ELMT(i, j) * x);
        }
    }

    public void DivideRow(Matrix m, double x, int i) {
        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            m.set_ELMT(i, j, m.get_ELMT(i, j) / x);
        }
    }

    public void SwapRow(Matrix m, int row1, int row2) {
        Matrix mCopy = new Matrix();
        operasi.createMatrix(mCopy, m.get_ROW_EFF(), m.get_COL_EFF());

        for (int i = 0; i <= operasi.getLastIdxRow(m); i++) {
            for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
                mCopy.set_ELMT(i, j, m.get_ELMT(i, j));
            }
        }

        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            m.set_ELMT(row1, j, mCopy.get_ELMT(row2, j));
            m.set_ELMT(row2, j, mCopy.get_ELMT(row1, j));
        }
    }

    public void ToEchelon(Matrix m) {
        int c = 0;

        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            int i = c;

            while (i <= operasi.getLastIdxRow(m) && m.get_ELMT(i, j) == 0) {
                i += 1;
            }

            if (i <= operasi.getLastIdxRow(m)) {
                if (i != c) {
                    SwapRow(m, c, i);
                }

                double pivot = m.get_ELMT(c, j);
                if (pivot != 0) {
                    DivideRow(m, pivot, c);
                }

                for (int k = c + 1; k <= operasi.getLastIdxRow(m); k++) {
                    double factor = m.get_ELMT(k, j);
                    SubtractRow(m, factor, c, k);
                }

                c += 1;
            }
        }
    }

    public void ToEchelonRed(Matrix m) {
        int c = 0;

        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            int i = c;

            while (i <= operasi.getLastIdxRow(m) && m.get_ELMT(i, j) == 0) {
                i += 1;
            }

            if (i <= operasi.getLastIdxRow(m)) {
                if (i != c) {
                    SwapRow(m, c, i);
                }

                double pivot = m.get_ELMT(c, j);
                if (pivot != 0) {
                    DivideRow(m, pivot, c);
                }

                for (int k = 0; k <= operasi.getLastIdxRow(m); k++) {
                    if (k == c) {
                        continue;
                    } else {
                        double factor = m.get_ELMT(k, j);
                        SubtractRow(m, factor, c, k);
                    }
                }

                c += 1;
            }
        }
    }

    public int findPivot(Matrix m, int row) {
        int pivotCol = -1;
        for (int j = 0; j <= m.get_COL_EFF()-2;j++) {
            if (m.get_ELMT(row,j) == 1) {
                return j;
            }
        }
        return pivotCol; }

    public String[] solveSPL(Matrix m) {
        ToEchelonRed(m);
        System.out.println("\nHasil matriks eselon baris tereduksi: ");
        operasi.displayMatrix(m);
        int flag = 1; //solusi unik
        boolean allzero = true;

        for (int i = operasi.getLastIdxRow(m); i >= 0; i--) {
            for (int j = 0; j < operasi.getLastIdxCol(m); j++) {
                if (m.get_ELMT(i, j) != 0) {
                    allzero = false;
                    break;
                }
            }
            if (allzero) {
                if (m.get_ELMT(i,operasi.getLastIdxCol(m)) != 0) {
                    flag = 3; //tidak ada solusi
                }
                else {
                    flag = 2; //banyak solusi
                }
            }
        }


        if (flag == 3) {
            System.out.println("\n SPL tidak memiliki solusi");

        }
//        else if (flag == 1) {
//            String solution[] = new String[m.get_ROW_EFF()];
//            for (int row = 0; row <= operasi.getLastIdxRow(m);row++) {
//                solution [row] = "X" + (row+1)  + " = ";
//                solution[row] += Double.toString(m.get_ELMT(row,operasi.getLastIdxCol(m)));
//            }
//            return solution;
//        }

        else { // flag = 2

            double[] nonParamCoef = new double[m.get_COL_EFF()-1];
            Matrix ParamCoef = new Matrix();
            operasi.createMatrix(ParamCoef, m.get_COL_EFF()-1, m.get_COL_EFF()-1);

            // cari variabel bebas
            boolean[] isFreeVariable = new boolean[m.get_COL_EFF()-1];
            for (int col = 0; col < m.get_COL_EFF()-1; col++) {
                isFreeVariable[col] = true;
            }
            for (int row = 0; row < m.get_ROW_EFF(); row++) {
                for (int col = 0; col < m.get_COL_EFF()-1; col++) {
                    if (m.get_ELMT(row, col) == 1) {
                        isFreeVariable[col] = false;
                        break;
                    }
                }
            }

            for (int i = 0; i < m.get_COL_EFF()-1;i++) {
                if (isFreeVariable[i]) {
                    ParamCoef.set_ELMT(i,i,1);
                }
            }

            // iterate from bottom row
            for (int row = m.get_ROW_EFF()- 1; row >= 0; row--) {
                int pivotCol = findPivot(m, row);
                if (pivotCol == -1) { // no leading one on the row
                    continue; }

                else { //ParamCoef[x][y] is x = ParamCoef * y
                        for (int col = pivotCol + 1; col < m.get_COL_EFF() - 1; col++) {
                            if (m.get_ELMT(row,col) != 0) {
                                if (isFreeVariable[col]) { //set coeff of parametric as (nonparam = -param)
                                 ParamCoef.set_ELMT(pivotCol,col,-m.get_ELMT(row,col));  }
                                else { //set coeff of non parametric and param again
                                  nonParamCoef[pivotCol] = -(m.get_ELMT(row,col)*nonParamCoef[col]);
                                    ParamCoef.set_ELMT(pivotCol,col,-m.get_ELMT(row,col));
                                  //substitute paramcoef
                                  for (int j = 0; j < m.get_COL_EFF()-1;j++) {
                                      ParamCoef.set_ELMT(pivotCol,j,ParamCoef.get_ELMT(pivotCol,col)*ParamCoef.get_ELMT(col,j));

                                  }


                                }
                            }
                        }
                        nonParamCoef[pivotCol] += m.get_ELMT(row,m.get_COL_EFF()-1); // add the b (Ax=b) to nonParam
             }


            }
            String[] result = new String[m.get_COL_EFF()-1];
            for (int i = 0; i < m.get_COL_EFF()-1;i++) {
                result[i] = "";
                result[i] += "X" + Integer.toString(i+1) + " = ";

                if (!(isFreeVariable[i])) {
                    result[i] +=  Double.toString(nonParamCoef[i]) + " ";
                }
                if (isFreeVariable[i]) {
                    result[i] += "Bebas";
                }

                else {
                    for (int j = 0; j < m.get_COL_EFF()-1;j++) {
                        if (ParamCoef.get_ELMT(i, j) != 0) {
                            if (ParamCoef.get_ELMT(i, j) > 0) {
                                result[i] += " + ";
                            } else if (ParamCoef.get_ELMT(i, j) < 0) {
                                result[i] += " - ";
                            }
                            if (Math.abs(ParamCoef.get_ELMT(i, j)) != 1) {
                                result[i] += Double.toString(Math.abs(ParamCoef.get_ELMT(i, j)));
                            }
                            result[i] += "X" + Integer.toString(j + 1);
                        }
                    }
                }
            }
            return result;
        }

        return null;
    }

    public double DeterminantOBE(Matrix m) {
        int c = 0;
        int swapcount = 0;
        double det = 1;
        double multiplydet = 1;

        for (int j = 0; j <= operasi.getLastIdxCol(m); j++) {
            int i = c;

            while (i <= operasi.getLastIdxRow(m) && m.get_ELMT(i, j) == 0) {
                i += 1;
            }

            if (i <= operasi.getLastIdxRow(m)) {
                if (i != c) {
                    SwapRow(m, c, i);
                    swapcount += 1;
                }

                double pivot = m.get_ELMT(c, j);
                if (pivot != 0) {
                    DivideRow(m, pivot, c);
                    multiplydet *= pivot;
                }

                for (int k = c + 1; k <= operasi.getLastIdxRow(m); k++) {
                    double factor = m.get_ELMT(k, j);
                    SubtractRow(m, factor, c, k);
                }

                c += 1;
            }
        }

        for (int z = 0; z <= operasi.getLastIdxRow(m); z++) {
            det *= m.get_ELMT(z, z);
        }
        det *= multiplydet;
        if (swapcount % 2 == 1) {
            det *= -1;
        }
        return det;
    }

    public Matrix MatriksBalikan(Matrix m) {
        Matrix m2 = new Matrix();
        operasi.createMatrix(m2, m.get_ROW_EFF(), m.get_COL_EFF());
        for (int i = 0; i <= operasi.getLastIdxRow(m2); i++) {
            for (int j = 0; j <= operasi.getLastIdxCol(m2); j++) {
                if (i == j) {
                    m2.set_ELMT(i, j, 1);
                } else {
                    m2.set_ELMT(i, j, 0);
                }
            }
        }
        m = operasi.mergeMatrix(m, m2);
        int c = 0;

        for (int j = 0; j <= (operasi.getLastIdxCol(m) - m2.get_COL_EFF()); j++) {
            int i = c;

            while (i <= operasi.getLastIdxRow(m) && m.get_ELMT(i, j) == 0) {
                i += 1;
            }

            if (i <= operasi.getLastIdxRow(m)) {
                if (i != c) {
                    SwapRow(m, c, i);
                }

                double pivot = m.get_ELMT(c, j);
                if (pivot != 0) {
                    DivideRow(m, pivot, c);
                }

                for (int k = 0; k <= operasi.getLastIdxRow(m); k++) {
                    if (k == c) {
                        continue;
                    } else {
                        double factor = m.get_ELMT(k, j);
                        SubtractRow(m, factor, c, k);
                    }
                }

                c += 1;
            }
        }

        for (int i = 0; i <= operasi.getLastIdxRow(m2); i++) {
            for (int j = 0; j <= operasi.getLastIdxCol(m2); j++) {
                m2.set_ELMT(i, j, m.get_ELMT(i, j + m2.get_COL_EFF()));
            }
        }
        return m2;
    }
}
