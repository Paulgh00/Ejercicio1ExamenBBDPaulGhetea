package com.paul.controlador;

import java.awt.Panel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import com.paul.transversal.Conexion;

public class Email {
	Conexion BD = new Conexion();
	Connection conectar = BD.conectar();
	ResultSet resultado = null;
	Statement sentencia = null;
	CRUD crud = new CRUD();

	public void enviar_email() throws SQLException {
		Scanner teclado = new Scanner(System.in);
		String email = "";
		String correoEnvia = "grupoinfoclub@gmail.com";
		String contrasena = "Adivinala1.";
		String receptor = email;
		String asunto = "Datos usuario";
		String mensaje = "Buenos días. Aquí tiene los datos del usuario";
		
		System.out.println("Usuarios existentes:");
		System.out.println();

		System.out.println("¿Qué dato desea enviar?");
		System.out.println("1.XML");
		System.out.println("2.PDF");
		System.out.println("3.QR");
		int respuesta = teclado.nextInt();
		// Mostramos todos los Usuarios y sus correos
		crud.mostrar_usuarios();
		// Pedimos el dni para buscar a quién mandar el correo
		System.out.println("Introduzca el dni del usuario que desee enviar el correo");
		String respuestacorreo = teclado.next();
		
		String cadenaSQL1 = "Select * from usuarios WHERE dni='" + respuestacorreo + "';";
		sentencia = conectar.createStatement();
		resultado = sentencia.executeQuery(cadenaSQL1);
		while (!resultado.next()) {
			email = resultado.getString("correo");
			System.out.println("Email del destinatario: " + email);

		}
		//email="paulghetea@outlook.es";
		System.out.println(email + "--");
		System.out.println("Preparando configuración");

		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "465");
		propiedad.setProperty("mail.smtp.auth", "true");
		propiedad.setProperty("mail.smtp.user", correoEnvia);
		propiedad.setProperty("mail.smtp.password", contrasena);
		propiedad.setProperty("mail.smtp.protocol", "smtp");
		propiedad.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		propiedad.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session sesion = Session.getInstance(propiedad, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(correoEnvia, contrasena);
			}
		});
		System.out.println("Configuración OK");
		System.out.println("Sesión iniciada");

		try {
			MimeMessage mail = new MimeMessage(sesion);
			mail.setFrom(new InternetAddress(correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			mail.setSubject(asunto);
			mail.setText(mensaje);
			System.out.println("Adjuntando .......................");
//			if (respuesta == 1) {
//				Multipart multipart = new MimeMultipart();
//				MimeBodyPart attachPart = new MimeBodyPart();
//				String attachFile = "C:\\temp\\QR.png";
//
//				try {
//					attachPart.attachFile(attachFile);
//				} catch (IOException e) {
//					e.printStackTrace();
//					System.out.println("Error");
//					return;
//				}
//				multipart.addBodyPart(attachPart);
//				mail.setContent(multipart);
//				System.out.println("Archivo adjunto preparado....");
//			}
//			if (respuesta == 2) {
//				Multipart multipart = new MimeMultipart();
//				MimeBodyPart attachPart = new MimeBodyPart();
//				String attachFile = "C:\\temp\\QR.png";
//
//				try {
//					attachPart.attachFile(attachFile);
//				} catch (IOException e) {
//					e.printStackTrace();
//					System.out.println("Error");
//					return;
//				}
//				multipart.addBodyPart(attachPart);
//				mail.setContent(multipart);
//				System.out.println("Archivo adjunto preparado....");
//			}
			if (respuesta == 3) {
				Multipart multipart = new MimeMultipart();
				MimeBodyPart attachPart = new MimeBodyPart();
				System.out.println("Introduzca el nombre y el apellido de los datos que desea enviar");
				String nombre1=teclado.next();
				String apellido11=teclado.next();
				String ficheroQR = "C:\\temp\\" + nombre1 + " " + apellido11 + " " + "QR.png";

				try {
					attachPart.attachFile(ficheroQR);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error");
					return;
				}
				multipart.addBodyPart(attachPart);
				mail.setContent(multipart);
				System.out.println("Archivo adjunto preparado....");
			}

			System.out.println("Enviando");
			Transport transportar = sesion.getTransport("smtp");
			System.out.println("Enviando 2");
			transportar.connect(correoEnvia, contrasena);
			System.out.println("Enviando 3");
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
			transportar.close();

			JOptionPane.showMessageDialog(null, "Listo, revise su correo");

		} catch (AddressException ex) {
			Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(ex.getMessage());
		} catch (MessagingException ex) {
			Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(ex.getMessage());
		}
	}

}
