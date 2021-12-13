package com.paul.principal;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import com.google.zxing.WriterException;
import com.paul.controlador.CRUD;
import com.paul.transversal.Conexion;
import com.paul.vista.Menu;

public class Main {

	public static void main(String[] args) throws SQLException, WriterException, COSVisitorException, IOException {
		Conexion conexion = new Conexion();
		if (conexion != null) {
			System.out.println("Conexión exitosa");
		}
		Menu menu = new Menu();
		menu.eleccion();
		conexion.conectar();

	}

}
