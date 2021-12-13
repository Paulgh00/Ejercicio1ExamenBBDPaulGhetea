package com.paul.controlador;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.paul.modelo.POJO;
import com.paul.transversal.Conexion;
import com.paul.vista.Menu;

public class CRUD {
	String cadenaSQL = "";
	Conexion BD = new Conexion();
	Connection conectar = BD.conectar();
	POJO usuario = new POJO();
	ResultSet resultado = null;
	Statement sentencia = null;

	int id = 0;
	String nombre = "";
	String apellido1 = "";
	String apellido2 = "";
	String dni = "";
	boolean activo;
	boolean borrado;
	String correo = "";
	int edad = 0;

	Scanner teclado = new Scanner(System.in);

	public void agregar() throws SQLException {
		System.out.println("Introduzca los datos del usuario");
		System.out.println("Nombre= ");
		nombre = teclado.next();
		System.out.println("Apellido 1= ");
		apellido1 = teclado.next();
		System.out.println("Apellido 2= ");
		apellido2 = teclado.next();
		System.out.println("DNI= ");
		dni = teclado.next();
		System.out.println("Correo= ");
		correo = teclado.next();
		activo = true;
		borrado = false;
		System.out.println("Edad = ");
		edad=teclado.nextInt();

		if (conectar != null) {
			cadenaSQL = "insert into usuarios(nombre,apellido1,apellido2,dni,activo,borrado,correo,edad) values (" + "'" + nombre
					+ "'" + "," + "'" + apellido1 + "'" + "," + "'" + apellido2 + "'" + "," + "'" + dni + "'" + ","
					+ activo + "," + borrado + "," + correo +  "," + edad +" );";
			sentencia = conectar.createStatement();

			if (sentencia.executeUpdate(cadenaSQL) > 0) {
				System.out.println("El registro se insertó exitosamente.");
			} else {
				System.out.println("No se pudo insertar el registro.");
			}
		} else {
			System.out.println("Ha habido un problema al conectar con la base de datos");
		}
		try {
			cadenaSQL = "Select * from usuarios where nombre ='" + nombre + "' and apellido1 = '" + apellido1
					+ "' and apellido2 = '" + apellido2 + "';";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
			}
			while (resultado.next()) {
				int id = resultado.getInt("idUsuario");
				nombre = resultado.getString("nombre");
				apellido1 = resultado.getString("apellido1");
				apellido2 = resultado.getString("apellido2");
				dni = resultado.getString("dni");
				correo=resultado.getString("correo");
				int activo = resultado.getInt("activo");
				int borrado = resultado.getInt("borrado");
				correo = resultado.getString("correo");
				edad = resultado.getInt("edad");

				System.out.println(
						"ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1 + " | apellido2 = "
								+ apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo + " | BORRADO = " + borrado + "| Correo = " + correo +  " | edad = " + edad );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void editar() {
		System.out.println("Introduzca el nombre del usuario que desea editar");
		String nombre_editado = teclado.next();
		System.out.println("Introduzca el apellido del usuario que desea editar");
		String apellido_editado = teclado.next();
		System.out.println("Inserte el dato que desea modificar");
		System.out.println("1.Nombre");
		System.out.println("2.Apellido");
		System.out.println("3.Segundo Apellido");
		System.out.println("4.DNI");
		System.out.println("5.Correo");
		System.out.println("6.Correo");
		System.out.println("7.Volver al menú");
		int respuesta_editar = teclado.nextInt();
		try {
			if (respuesta_editar == 1) {
				System.out.println("Inserte el nuevo nombre");
				String nombre_nuevo = teclado.next();
				cadenaSQL = "UPDATE usuarios SET nombre ='" + nombre_nuevo + "' WHERE nombre='" + nombre_editado
						+ "' and apellido1='" + apellido_editado + "';";
			}
			if (respuesta_editar == 2) {
				System.out.println("Inserte el nuevo primer apellido");
				String apellido1_nuevo = teclado.next();
				cadenaSQL = "UPDATE usuarios SET apellido2 ='" + apellido1_nuevo + "' WHERE nombre='" + nombre_editado
						+ "' and apellido1='" + apellido_editado + "';";
			}
			if (respuesta_editar == 3) {
				System.out.println("Inserte el nuevo segundo apellido");
				String apellido2_nuevo = teclado.next();
				cadenaSQL = "UPDATE usuarios SET apellido2 ='" + apellido2_nuevo + "' WHERE nombre='" + nombre_editado
						+ "' and apellido1='" + apellido_editado + "';";
			}
			if (respuesta_editar == 4) {
				System.out.println("Inserte el nuevo DNI");
				String dni_nuevo = teclado.next();
				cadenaSQL = "UPDATE usuarios SET dni ='" + dni_nuevo + "' WHERE nombre='" + nombre_editado
						+ "' and apellido1='" + apellido_editado + "';";
			}
			if (respuesta_editar == 5) {
				System.out.println("Inserte el nuevo Correo");
				String correo_nuevo = teclado.next();
				cadenaSQL = "UPDATE usuarios SET correo ='" + correo_nuevo + "' WHERE nombre='" + nombre_editado
						+ "' and apellido1='" + apellido_editado + "';";
			}
			if (respuesta_editar == 6) {
				System.out.println("Inserte la nueva edad");
				String edad_nuevo = teclado.next();
				cadenaSQL = "UPDATE usuarios SET edad ='" + edad_nuevo + "' WHERE nombre='" + nombre_editado
						+ "' and apellido1='" + apellido_editado + "';";
			}

			if (conectar != null) {
				sentencia = conectar.prepareStatement(cadenaSQL);
				if (sentencia.executeUpdate(cadenaSQL) > 0) {
					System.out.println("El registro se actualizó exitosamente.");
				} else {
					System.out.println("No se pudo insertar el registro.");
				}
			} else {
				System.out.println("Ha habido un problema al conectar con la base de datos");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscar() {
		int respuesta_buscar = 0;
		String busqueda = "";
		System.out.println("¿Por qué dato desea buscar?");
		System.out.println("1.Nombre");
		System.out.println("2.Apellido");
		System.out.println("3.Segundo Apellido");
		System.out.println("4.DNI");
		System.out.println("5.Correo");
		System.out.println("6.edad");
		System.out.println("7.Volver al menú");
		respuesta_buscar = teclado.nextInt();
		try {
			if (respuesta_buscar == 1) {
				System.out.println("Introduzca el nombre:");
				busqueda = teclado.next();
				cadenaSQL = "Select * from usuarios WHERE nombre='" + busqueda + "';";
			}
			if (respuesta_buscar == 2) {
				System.out.println("Introduzca el primer apellido:");
				busqueda = teclado.next();
				cadenaSQL = "Select * from usuarios WHERE apellido1='" + busqueda + "';";
			}
			if (respuesta_buscar == 3) {
				System.out.println("Introduzca el segundo apellido:");
				busqueda = teclado.next();
				cadenaSQL = "Select * from usuarios WHERE apellido2='" + busqueda + "';";
			}
			if (respuesta_buscar == 4) {
				System.out.println("Introduzca el DNI:");
				busqueda = teclado.next();
				cadenaSQL = "Select * from usuarios WHERE dni='" + busqueda + "';";
			}
			if (respuesta_buscar == 5) {
				System.out.println("Introduzca el correo:");
				busqueda = teclado.next();
				cadenaSQL = "Select * from usuarios WHERE correo='" + busqueda + "';";
			}
			if (respuesta_buscar == 6) {
				System.out.println("Introduzca la edad:");
				busqueda = teclado.next();
				cadenaSQL = "Select * from usuarios WHERE edad='" + busqueda + "';";
			}
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
				System.out.println();
				while (!resultado.next())
				id = resultado.getInt("idUsuario");
				nombre = resultado.getString("nombre");
				apellido1 = resultado.getString("apellido1");
				apellido2 = resultado.getString("apellido2");
				dni = resultado.getString("dni");
				activo = resultado.getBoolean("activo");
				borrado = resultado.getBoolean("borrado");
				correo = resultado.getString("borrado");
				edad = resultado.getInt("edad");

				System.out.println(
						"ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1 + " | apellido2 = "
								+ apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo + " | BORRADO = " + borrado
								+ "| Correo = " + correo + "| Edad = " + edad);
			} else {
				System.out.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (respuesta_buscar == 7) {
		}
	}

	public List<String> mostrar_usuarios() throws SQLException {
		List<String> usuarios= new ArrayList<>();
		String registro= "";
		try {
			cadenaSQL = "Select * from usuarios";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
				System.out.println();
				// hacer con un método mostrar
				while (resultado.next()) {
					int id = resultado.getInt("idUsuario");
					nombre = resultado.getString("nombre");
					apellido1 = resultado.getString("apellido1");
					apellido2 = resultado.getString("apellido2");
					dni = resultado.getString("dni");
					int activo = resultado.getInt("activo");
					int borrado = resultado.getInt("borrado");
					correo = resultado.getString("correo");
					edad = resultado.getInt("edad");
					registro = "ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1
							+ " | apellido2 = " + apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo
							+ " | BORRADO = " + borrado + "| Correo = " + correo + "| Edad = " + edad;
					System.out.println(registro);
					usuarios.add(registro.toString());
				}

			} else {
				System.out.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return usuarios;
	}

	public void eliminar() throws SQLException {
		System.out.println("Introduzca el nombre del usuario que desea eliminar");
		String nombre_buscado = teclado.next();
		System.out.println("Introduzca el apellido del usuario que desea eliminar");
		String apellido_buscado = teclado.next();
		try {
			cadenaSQL = "UPDATE usuarios SET borrado = true, activo = false where nombre = '" + nombre_buscado
					+ "' and apellido1 = '" + apellido_buscado + "';";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				sentencia.executeUpdate(cadenaSQL);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			cadenaSQL = "Select * from usuarios where nombre ='" + nombre_buscado + "' and apellido1 = '"
					+ apellido_buscado + "';";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
			}
			while (resultado.next()) {
				int id = resultado.getInt("idUsuario");
				nombre = resultado.getString("nombre");
				apellido1 = resultado.getString("apellido1");
				apellido2 = resultado.getString("apellido2");
				dni = resultado.getString("dni");
				activo = resultado.getBoolean("activo");
				borrado = resultado.getBoolean("borrado");
				correo = resultado.getString("correo");
				edad = resultado.getInt("edad");

				System.out.println(
						"ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1 + " | apellido2 = "
								+ apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo + " | BORRADO = " + borrado
								+ " | correo = " + correo + " | edad = " + edad);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void listar_borrados() {
		try {
			cadenaSQL = "Select * from usuarios where borrado = 0";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
			}
			while (resultado.next()) {
				int id = resultado.getInt("idUsuario");
				nombre = resultado.getString("nombre");
				apellido1 = resultado.getString("apellido1");
				apellido2 = resultado.getString("apellido2");
				dni = resultado.getString("dni");
				activo = resultado.getBoolean("activo");
				borrado = resultado.getBoolean("borrado");
				correo = resultado.getString("correo");
				edad = resultado.getInt("edad");

				System.out.println(
						"ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1 + " | apellido2 = "
								+ apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo + " | BORRADO = " + borrado
								+ " | correo = " + correo + " | Edad = " + edad);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void recuperar_borrado() {
		System.out.println("Introduzca el nombre del usuario que desea eliminar");
		String nombre_recuperado = teclado.next();
		System.out.println("Introduzca el apellido del usuario que desea eliminar");
		String apellido_recuperado = teclado.next();
		try {
			cadenaSQL = "UPDATE usuarios SET borrado=0 where nombre = '" + nombre_recuperado + "' and apellido1 = '"
					+ apellido_recuperado + "';";

			if (conectar != null) {
				sentencia = conectar.createStatement();
				sentencia.executeUpdate(cadenaSQL);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			cadenaSQL = "Select * from usuarios where nombre = '" + nombre_recuperado + "' and apellido1 = '"
					+ apellido_recuperado + "';";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
				System.out.println(
						"ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1 + " | apellido2 = "
								+ apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo + " | BORRADO = " + borrado
								+ " | Edad = " + edad);
			}
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

				System.out.println(
						"ID = " + id + " | nombre =  " + nombre + " | apellido1 = " + apellido1 + " | apellido2 = "
								+ apellido2 + " | DNI = " + dni + " | ACTIVO =  " + activo + " | BORRADO = " + borrado
								+ " | correo = " + correo + " | edad = " + edad);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


	public void salir() {
		try {
			if (sentencia != null) {
				try {
					sentencia.close();
					BD.conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
