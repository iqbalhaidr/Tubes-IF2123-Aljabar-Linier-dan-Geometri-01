package Library;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Imageresizer {
    private BufferedImage image; 
    private BicubicInterpolation bicubic = new BicubicInterpolation();
    private OperasiDasarMatrix ODM = new OperasiDasarMatrix();
    private BicubicInterpolation.Titik[] titikF = bicubic.getArrayOfTitik();
    private BicubicInterpolation.Titik[] titikI = new BicubicInterpolation.Titik[16];
    private Matrix Dmatriks = new Matrix();
    public static Matrix MatrixforImage = new Matrix();
    public static boolean MatrixforImageDoneProces= false;
    private double[][][] imagePixel;

    public BufferedImage getImage (){
        return image;
    }

    public int clamp(int value, int min, int max) { //jika ada indeks yang >height/width 
        return Math.max(min, Math.min(value, max));  //value akan dibuat sama dengan value terdekat
    }

    private void initializeMatrixWithZero(Matrix matrix) {
        for (int i = 0; i < matrix.m.length; i++) {
            for (int j = 0; j < matrix.m[i].length; j++) {
                matrix.m[i][j] = 0.0;
            }
        }
    }

    public void inputImage(String imagePath) {
        try{
            image = ImageIO.read(new File(imagePath));
            if (image == null) {
                System.out.println("Gambar tidak valid atau tidak ditemukan.");
            }
            else{
                System.out.println("Gambar Anda berhasil dimuat: ");
                createimagePixel();
            }
        }
        catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca gambar. Proses resize dihentikan ");
        }
    }

    // Method untuk membuat matriks piksel dari gambar
    private void createimagePixel() {
        int width = image.getWidth();
        int height = image.getHeight();
        imagePixel = new double[4][height][width]; // Inisialisasi array 23D

        // Mengisi array dengan nilai piksel dari gambar A R G B
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int value = image.getRGB(col, row);
                double alpha = (double) ((value >> 24) & 0xFF) / 255.0;
                double red = (double) ((value >> 16) & 0xFF) / 255.0;
                double green = (double) ((value >> 8) & 0xFF) / 255.0;
                double blue = (double) (value & 0xFF) / 255.0;
    
                imagePixel[0][row][col] = alpha;
                imagePixel[1][row][col] = red;
                imagePixel[2][row][col] = green;
                imagePixel[3][row][col] = blue;
            }
        }
        System.out.println("Matriks piksel berhasil dibuat.");
    }

    public double[][] get4x4(double[][] imageMatrix, int row, int col) {
        double[][] block4x4 = new double[4][4]; 
        int maxRow = imageMatrix.length - 1;
        int maxCol = imageMatrix[0].length - 1;
    
        // Ambil blok 4x4 piksel di sekitar 
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = clamp(row - 1 + i, 0, maxRow);
                int y = clamp(col - 1 + j, 0, maxCol);
    
                block4x4[i][j] = imageMatrix[x][y];
            }
        }
    
        return block4x4;
    }


    public Imageresizer(){
        ODM.createMatrix(Dmatriks, 16, 16);
        ODM.createMatrix(MatrixforImage, 16, 16);
        initializeMatrixWithZero(Dmatriks);
        initializeMatrixWithZero(MatrixforImage);
        titikI[0] = bicubic.new Titik(-1,-1);
        titikI[1] = bicubic.new Titik(0,-1);
        titikI[2] = bicubic.new Titik(1,-1);
        titikI[3] = bicubic.new Titik(2,-1);
        titikI[4] = bicubic.new Titik(-1,0);
        titikI[5] = bicubic.new Titik(0,0);
        titikI[6] = bicubic.new Titik(1,0);
        titikI[7] = bicubic.new Titik(2,0);
        titikI[8] = bicubic.new Titik(-1,1);
        titikI[9] = bicubic.new Titik(0,1);
        titikI[10] = bicubic.new Titik(1,1);
        titikI[11] = bicubic.new Titik(2,1);
        titikI[12] = bicubic.new Titik(-1,2);
        titikI[13] = bicubic.new Titik(0,2);
        titikI[14] = bicubic.new Titik(1,2);
        titikI[15] = bicubic.new Titik(2,2);
    }

    public int searchindexof (double x, double y){
        for (int i = 0; i < titikI.length; i++) {
            if (titikI[i].getX() == x && titikI[i].getY() == y) {
                return i; 
            }
        }
        return -1; 
    }

    public void makeDMatrix (){ //Menghitung a00 - a33 lalu disalin ke Xmatriks untuk [0..1]
        int row=0;
        for(int jenis=0; jenis<4; jenis++){
            for(int elem=0; elem<titikF.length; elem++){
                double x=titikF[elem].getX();
                double y=titikF[elem].getY();
                if (jenis==0){
                    int idxf= searchindexof(x,y);
                    Dmatriks.m[row][idxf]=1;
                }
                else if(jenis==1){
                    int idxfx= searchindexof(x+1,y);
                    int idxfy= searchindexof(x-1,y);
                    Dmatriks.m[row][idxfx]=0.5;
                    Dmatriks.m[row][idxfy]=-0.5;
                }
                
                else if(jenis==2){
                    int idxfx= searchindexof(x,y+1);
                    int idxfy= searchindexof(x,y-1);
                    Dmatriks.m[row][idxfx]=0.5;
                    Dmatriks.m[row][idxfy]=-0.5;
                }
                else if(jenis==3){
                    int idxfx= searchindexof(x+1,y+1);
                    int idxfy= searchindexof(x-1,y);
                    int idxfr= searchindexof(x,y-1);
                    int idxfz= searchindexof(x,y);
                    Dmatriks.m[row][idxfx]=0.25;
                    Dmatriks.m[row][idxfy]=-0.25;
                    Dmatriks.m[row][idxfr]=-0.25;
                    Dmatriks.m[row][idxfz]=-0.25;
                }
                row+=1;
            }
        }
    }

    public void makeMatrixForImage (){
        if (!MatrixforImageDoneProces){ //kalo belum pernah dibuat
            makeDMatrix();
            bicubic.makeCoeff();
            MatrixforImage=ODM.multiplyMatrix(bicubic.Xmatriks, Dmatriks);
            MatrixforImageDoneProces= true;
        }
    }


    public double[][] createVector16(double[][] block4x4) { //Mengirim 16 titik sekitar dalam 1 vektor
        makeMatrixForImage();
        Matrix result =new Matrix();
        ODM.createMatrix(result, 16, 1);
        double[][] vector16 = new double[16][1];
        int index = 0;
    
        // Mengambil nilai piksel asli disekitar
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                vector16[index][0] = block4x4[j][i]; 
                index++;
            }
        }
        result.m=vector16;
        result=ODM.multiplyMatrix(MatrixforImage, result);


        return result.m; 
    }

    public double[][] convertVectorTo4x4Matrix(double[][] vector16) { //konversi hasil multiply ke 4x4
        double[][] matrix4x4 = new double[4][4];
        int index = 0;
    
        // Mengisi matriks koefisien 4x4 dari vektor 16 elemen
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix4x4[i][j] = vector16[index++][0];
            }
        }
    
        return matrix4x4;
    }




    public double bicubicSplineInterpolate(double[][] coefficients, double deltaX, double deltaY) { //menghitung berdasarkan matriks 4x4 coeff
        double result = 0;
    
        // Mencari hasil interpolasi dengan mengalikan dengan coeff
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += coefficients[i][j] * Math.pow(deltaX, i) * Math.pow(deltaY, j);
            }
        }

        return Math.min(Math.max(result, 0), 1); // Batasi hasil ke rentang [0, 1]
    }

    public void resizer(double scalex, double scaley) {
        int newWidth = (int) Math.round(image.getWidth() * scalex);
        int newHeight = (int) Math.round(image.getHeight() * scaley);
        double[][][] resultImage = new double[4][newHeight][newWidth]; 
    
        // Membuat thread pool untuk menjalankan proses secara paralel
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> tasks = new ArrayList<>();
    
        for (int row = 0; row < newHeight; row++) {
            final int currentRow = row; 
            tasks.add(executor.submit(() -> {
                try {
                    for (int col = 0; col < newWidth; col++) {
                        double originalX = (double) col / scalex;         
                        double originalY = (double) currentRow / scaley; 
            
                        int baseRow = (int) Math.floor(originalY); //Agar dapat mencari seberapa jauh titik yang akan
                        int baseCol = (int) Math.floor(originalX); //diinterpolasi dengan titik awalnya 
            
                        for (int arr3d = 0; arr3d < 4; arr3d++) { //looping untuk interpolate nilai di semua layer gambar
                            double[][] block4x4 = get4x4(imagePixel[arr3d], baseRow, baseCol);
                            double[][] coefficients = convertVectorTo4x4Matrix(createVector16(block4x4));
                            double interpolatedValue = bicubicSplineInterpolate(coefficients, originalX - baseCol, originalY - baseRow);
                            resultImage[arr3d][currentRow][col] = interpolatedValue;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Cetak detail error
                }
                return null;
            }));
        }
    
        // Menunggu semua thread selesai
        for (Future<Void> task : tasks) {
            try {
                task.get(); // Menunggu hingga setiap task selesai
            } 
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    
        // Tutup thread pool
        executor.shutdown();
    
        // Buat gambar baru dari hasil interpolasi
        BufferedImage finalImage = mergeRGB(resultImage, newWidth, newHeight);
    
        // Simpan dan tampilkan gambar baru
        try {
            ImageIO.write(finalImage, "png", new File("test/HasilResizenibos.png"));
            System.out.println("Gambar berhasil diresize dan disimpan.");
        } 
        catch (IOException e) {
            System.out.println("Gagal menyimpan gambar: " + e.getMessage());
        }
    
        displayImage("test/HasilResizenibos.png");
    }
    

    public BufferedImage mergeRGB(double[][][] resultImage, int width, int height) {
        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
        // Iterasi setiap piksel dan gabungkan nilai RGB-nya
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Mengembalikan nilai ARGB ke semula
                int Alpha = clamp((int) (resultImage[0][row][col] * 255), 0, 255); // Transparansi (Alpha)
                int Red = clamp((int) (resultImage[1][row][col] * 255), 0, 255);
                int Green = clamp((int) (resultImage[2][row][col] * 255), 0, 255);
                int Blue = clamp((int) (resultImage[3][row][col] * 255), 0, 255);

    
                // Gabungkan komponen ARGB 
                int rgb = (Alpha << 24) | (Red << 16) | (Green << 8) | Blue;
    
                // Set piksel pada gambar final
                finalImage.setRGB(col, row, rgb);
            }
        }
    
        return finalImage;
    }

    public void displayImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } 
        catch (IOException e) {
            System.out.println("Gagal memuat gambar: " + e.getMessage());
            return;
        }

        // Buat JFrame (jendela utama)
        JFrame frame = new JFrame("Hasil Resize Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Tutup program saat jendela ditutup
        frame.setSize(image.getWidth(), image.getHeight());  // Ukuran sesuai dengan gambar

        // Buat JLabel untuk menampilkan gambar
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label); 

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  // Tampilkan GUI
    }
}

