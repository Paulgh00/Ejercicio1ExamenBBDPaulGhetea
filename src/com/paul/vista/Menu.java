package com.paul.vista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.pdfbox.exceptions.COSVisitorException;

import com.google.zxing.WriterException;
import com.paul.controlador.CRUD;
import com.paul.controlador.Csv;
import com.paul.controlador.Email;
import com.paul.controlador.Grafica;
import com.paul.controlador.PDF;
import com.paul.controlador.QR;
import com.paul.controlador.Xml;
import com.paul.modelo.POJO;

public class Menu {
	Scanner teclado = new Scanner(System.in);
	CRUD crud = new CRUD();
	QR qr = new QR();
	POJO user1=new POJO();
	PDF pdf= new PDF();
	Email correo = new Email();
	Grafica grafica = new Grafica();
	Csv csv= new Csv();
	Xml xml = new Xml();

	public void eleccion() throws SQLException, WriterException, COSVisitorException, IOException {
		int respuesta = 0;
		System.out.println("BASE DE DATOS 'USUARIOS'");
		while (respuesta != 17) {
			System.out.println("¿Qué desea hacer?");
			System.out.println("1. Añadir usuario");
			System.out.println("2. Modificar un usuario");
			System.out.println("3. Buscar un usuario");
			System.out.println("4. Lista de usuarios");
			System.out.println("5. Eliminar un usuario");
			System.out.println("6. Listar usuarios borrados");
			System.out.println("7. Recuperar un usuario borrado");
			System.out.println("8. Generar/Actualizar XML");
			System.out.println("9. Leer XML");
			System.out.println("10. Generar/Actualizar CSV");
			System.out.println("11. Leer CSV");
			System.out.println("12. Crear QR");
			System.out.println("13. Crear PDF");
			System.out.println("14. Leer PDF");
			System.out.println("15. Enviar datos por correo");
			System.out.println("16. Mostrar gráfica de la edad de los usuarios");
			System.out.println("17. Salir");
			respuesta = teclado.nextInt();

			if (respuesta == 1) {
				crud.agregar();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 2) {
				crud.editar();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 3) {
				crud.buscar();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 4) {
				crud.mostrar_usuarios();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 5) {
				crud.eliminar();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 6) {
				crud.listar_borrados();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 7) {
				crud.recuperar_borrado();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 8) {
				xml.crear_xml();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 9) {
				xml.mostrar_xml();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 10) {
				csv.generarCSV();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 11) {
				csv.leerCSV();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 12) {
				qr.generarQR();
				System.out.println("-------------------------------------------------------------");
				
			}
			if (respuesta == 13) {
				pdf.crear_pdf();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 14) {
				pdf.abrir_pdf();
				System.out.println("-------------------------------------------------------------");
			}
			if (respuesta == 15) {
				correo.enviar_email();
				System.out.println("-------------------------------------------------------------");
				
			}
			if (respuesta == 16) {
				grafica.mostrar_grafica();
				System.out.println("-------------------------------------------------------------");
				
			}
			
			if (respuesta == 17) {
				crud.salir();
				System.out.println("Base de datos desconectada");
			}
		}
	}
}
