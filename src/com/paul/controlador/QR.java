package com.paul.controlador;

import com.paul.modelo.POJO;
import com.paul.transversal.Conexion;

import javax.swing.JFrame;

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

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class QR {
	CRUD crud=new CRUD();
	public void generarQR() throws SQLException, WriterException {
		Ventana ventana = new Ventana();
		String query = "";
		Conexion BD = new Conexion();
		Connection conectar = BD.conectar();
		ResultSet resultado = null;
		Statement sentencia = null;
		String nombre = "", apellido1 = "", apellido2 = "", dni = "", activo = "", borrado = "", correo = "";
		int contador = 1, edad = 0;
		Scanner t = new Scanner(System.in);
		
		System.out.println("Usuarios existentes:");
		crud.mostrar_usuarios();

		try {
			if (conectar != null) {
				sentencia = conectar.createStatement();
				System.out.println();

			} else {
				System.out.println("Ha habido un problema al conectar con la base de datos");
			}
			System.out.println("Introduzca el dni del usuario al cual desea crear el QR");
			String nombredni = t.next();
			query = "select * from usuarios where dni = '" + nombredni + "';";
			resultado = sentencia.executeQuery(query);

			while (resultado.next()) {
				nombre = resultado.getString("nombre");
				apellido1 = resultado.getString("apellido1");
				apellido1 = resultado.getString("apellido2");
				dni = resultado.getString("dni");
				activo = resultado.getString("activo");
				borrado = resultado.getString("borrado");
				correo = resultado.getString("correo");
				edad = resultado.getInt("edad");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Datos que se introducirán en el QR:");
		String textToQr = " " + nombre + " " + apellido1 + " " + apellido2 + " " + dni + " " + activo + " " + borrado + " " + correo ;
		System.out.println(textToQr);

		try {
			String pathname = "C:\\temp\\" + nombre + " " + apellido1 + " " + "QR.png";
			ventana.writeQR(textToQr, pathname);
			System.out.println("Código QR generado con éxito");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ficheroQR = "C:\\temp\\" + nombre + " " + apellido1 + " " + "QR.png";
		File path = new File(ficheroQR);
		try {
			Desktop.getDesktop().open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
