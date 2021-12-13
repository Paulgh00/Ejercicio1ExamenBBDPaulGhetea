package com.paul.transversal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
*
*/

/**
 * @author Administrador
 *
 */
public class Conexion {

	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String BD = "BDXML?serverTimezone=UTC";
	private final String USER = "root";
	private final String PASSWORD = "Adivinala1.";
	public Connection conexion = null;

	public Connection conectar() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(URL + BD, USER, PASSWORD);
			if (conexion != null) {
				//System.out.println("¡Conexión exitosa!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conexion != null) {
					return conexion;
				} else {
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}
	}

}
