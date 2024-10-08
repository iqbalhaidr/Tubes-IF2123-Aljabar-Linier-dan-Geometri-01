package Library;

public class Matrix {

    static final int ROW_CAP = 1000;
    static final int COL_CAP = 1000;

    // main data
    public int rowEff = 0;
    public int colEff = 0;

    // selector
    public double[][] m = new double[ROW_CAP][COL_CAP];


    // selector
    public int get_ROW_EFF() {
        return this.rowEff;
    }
    public int get_COL_EFF() {
        return this.colEff;
    }
    public double get_ELMT(int i, int j) {
        return this.m[i][j];
    }

    public void set_ROW_EFF(int nRow) {
        this.rowEff = nRow;
    }
    public void set_COL_EFF(int nCol) {
        this.colEff = nCol;
    }
    public void set_ELMT(int i, int j, double x) {
        this.m[i][j] = x;
    }

}

