package com.paul.controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

public class Ventana extends JFrame{
	/**
	 * Contructor para establecer los parámetros de Ventana
	 * @throws WriterException
	 */
	public Ventana() throws WriterException {
        BufferedImage imagen = crearQR("pruebecilla", 300, 300);
        ImageIcon icono = new ImageIcon(imagen);
        JLabel etiqueta = new JLabel("");
         
        etiqueta.setIcon(icono);
         
        this.setIconImage(imagen);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ejemplo de codigo QR");
        this.getContentPane().add(etiqueta);
        this.pack();        
    }
	/**
	 * función para crear el QR y retornar la imagen al constructor
	 * @param datos
	 * @param ancho
	 * @param altura
	 * @return
	 * @throws WriterException
	 */
	public BufferedImage crearQR(String datos, int ancho, int altura) throws WriterException {
        BitMatrix matrix;
        Writer escritor = new QRCodeWriter();
        matrix = escritor.encode(datos, BarcodeFormat.QR_CODE, ancho, altura);
         
        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);
         
        for(int y = 0; y < altura; y++) {
            for(int x = 0; x < ancho; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                imagen.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }
        return imagen;        
    }
	/**
	 * Procedimiento para escribir el QR con los datos y tamaño
	 * @param text
	 * @param pathname
	 * @throws WriterException
	 * @throws IOException
	 */
	 public void writeQR(String text, String pathname) throws WriterException, IOException {

	        int width = 600;
	        int height = 400;

	        String imageFormat = "png"; // "jpeg" "gif" "tiff"

	        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
	        FileOutputStream outputStream = new FileOutputStream(new File(pathname));
	        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, outputStream);

    }
	 /**
	  * Función para leer el QR y retornar los datos 
	  * @param pathname
	  * @return
	  * @throws FormatException
	  * @throws ChecksumException
	  * @throws NotFoundException
	  * @throws IOException
	  */
    public String readQR(String pathname) throws FormatException, ChecksumException, NotFoundException, IOException {

        InputStream qrInputStream = new FileInputStream(pathname);
        BufferedImage qrBufferedImage = ImageIO.read(qrInputStream);

        LuminanceSource source = new BufferedImageLuminanceSource(qrBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        Result stringBarCode = reader.decode(bitmap);

        return stringBarCode.getText();
    }
}
