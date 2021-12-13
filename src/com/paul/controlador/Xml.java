package com.paul.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.paul.transversal.Conexion;

public class Xml {
	String cadenaSQL = "";
	Conexion BD = new Conexion();
	Connection conectar = BD.conectar();
	ResultSet resultado = null;
	Statement sentencia = null;
	String nombre = "", apellido1 = "", apellido2 = "", dni = "", correo ="";
	boolean  borrado,activo;
	int edad = 0, id=0;
	public void crear_xml() throws SQLException {
		try {
			String destino = "usuarios.xml";
			boolean existe = new File(destino).exists();
			if (!existe) {
				System.out.println("Creando el archivo usuarios.xml");
			} else {
				System.out.println("Añadiendo datos al archivo usuarios.xml");
			}

			FileWriter fp = null;
			PrintWriter pw = null;
			fp = new FileWriter(destino);
			pw = new PrintWriter(fp);

			pw.println("<?xml version=\"1.0\"?>");
			pw.println("<usuarios>");
			cadenaSQL = "Select * from usuarios";
			sentencia = conectar.createStatement();
			resultado = sentencia.executeQuery(cadenaSQL);

			while (resultado.next()) {
				id = resultado.getInt("idUsuario");
				nombre = resultado.getString("nombre");
				apellido1 = resultado.getString("apellido1");
				apellido2 = resultado.getString("apellido2");
				dni = resultado.getString("dni");
				activo = resultado.getBoolean("activo");
				borrado = resultado.getBoolean("borrado");
				correo = resultado.getString("correo");
				edad = resultado.getInt("edad");
				pw.println("<usuario>");
				pw.println("<id>" + id + "</id>");
				pw.println("<nombre>" + nombre + "</nombre>");
				pw.println("<apellido1>" + apellido1 + "</apellido1>");
				pw.println("<apellido2>" + apellido2 + "</apellido2>");
				pw.println("<dni>" + dni + "</dni>");
				pw.println("<activo>" + activo + "</activo>");
				pw.println("<borrado>" + borrado + "</borrado>");
				pw.println("<correo>" + correo + "</correo>");
				pw.println("<edad>" + edad + "</edad>");
				pw.println("</usuario>");

			}
			pw.println("</usuarios>");
			pw.close();
			fp.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void mostrar_xml() {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("usuarios.xml");
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("usuario");
			System.out.println();
			for (int i = 0; i < list.size(); i++) {
				Element tabla = (Element) list.get(i);
				List lista_campos = tabla.getChildren();
				String Id = tabla.getChildText("idUsuario");
				String nombre = tabla.getChildText("nombre");
				String apellido1 = tabla.getChildText("apellido1");
				String apellido2 = tabla.getChildText("apellido2");
				String dni = tabla.getChildText("dni");
				String activo = tabla.getChildText("activo");
				String borrado = tabla.getChildText("borrado");
				String correo = tabla.getChildText("correo");
				String edad = tabla.getChildText("edad");
				System.out.println();
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

	}
}
