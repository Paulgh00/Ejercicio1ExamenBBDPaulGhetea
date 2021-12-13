package com.paul.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.paul.transversal.Conexion;

public class Csv {
	String query = "";
	Conexion BD = new Conexion();
	Connection conectar = BD.conectar();
	ResultSet resultado = null;
	Statement sentencia = null;
	String nombre = "", apellido1 = "", apellido2 = "", dni = "", activo = "", borrado = "", correo = "";
	int contador = 1, edad = 0;

	public void generarCSV() {
		String query = "";
		String fichero = "usuarios.csv";
		boolean existe = new File(fichero).exists();
		if (!existe) {
			System.out.println("Creando archivo usuarios.csv");
		} else {
			System.out.println("Añadiendo datos al archivo usuarios.csv");
		}
		try {
			CsvWriter t2 = new CsvWriter(new FileWriter(fichero, true), ',');

			query = "SELECT * FROM usuarios;";
			Connection conexion = BD.conectar();
			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);
				try {
					if (!existe) {
						t2.write("idUsuario");
						t2.write("nombre");
						t2.write("apellido1");
						t2.write("apellido2");
						t2.write("dni");
						t2.write("activo");
						t2.write("borrado");
						t2.write("correo");
						t2.write("edad");
						t2.endRecord();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				while (resultado.next()) {
					try {
						t2.write("" + resultado.getInt("idUsuario"));
						t2.write("" + resultado.getString("nombre"));
						t2.write("" + resultado.getString("apellido1"));
						t2.write("" + resultado.getString("apellido2"));
						t2.write("" + resultado.getString("dni"));
						t2.write("" + resultado.getBoolean("activo"));
						t2.write("" + resultado.getBoolean("borrado"));
						t2.write("" + resultado.getString("correo"));
						t2.write("" + resultado.getString("edad"));
						t2.endRecord();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				t2.close();
			} else {
				System.out.println("Ha habido un problema al conectar con a base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (sentencia != null) {
				try {
					sentencia.close();
					resultado.close();
					BD.conexion.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public void leerCSV() {
		String fichero = "usuarios.csv";
		try {
			boolean existe = new File(fichero).exists();
			if (!existe) {

				System.out.println("El archivo no existe");
				String letra = "e";
			}
			CsvReader t1 = new CsvReader(fichero);
			t1.readHeaders();
			while (t1.readRecord()) {
				String id = t1.get("idUsuario");
				nombre = t1.get("nombre");
				apellido1 = t1.get("apellido1");
				apellido2 = t1.get("apellido2");
				dni = t1.get("dni");
				String activo = t1.get("activo");
				String borrado = t1.get("borrado");
				correo = t1.get("correo");
				String edad = t1.get("edad");

				System.out.println("ID=" + id);
				System.out.println("<nombre>" + nombre + "</nombre>");
				System.out.println("<apellido1>" + apellido1 + "</apellido1>");
				System.out.println("<apellido2>" + apellido2 + "</apellido2>");
				System.out.println("<dni>" + dni + "</dni>");
				System.out.println("<activo>" + activo + "</activo>");
				System.out.println("<borrado>" + borrado + "</borrado>");
				System.out.println("<correo>" + correo + "</correo>");
				System.out.println("<edad>" + edad + "</edad>");

			}
			t1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
