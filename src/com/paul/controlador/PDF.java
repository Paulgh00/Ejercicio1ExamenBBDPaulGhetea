package com.paul.controlador;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.*;

import com.paul.modelo.POJO;

public class PDF {

	public void crear_pdf() throws IOException, COSVisitorException {
		int pdfs = 600;
		PDDocument doc = null;
		CRUD crud = new CRUD();
		try {
			doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);
			PDFont font = PDType1Font.COURIER_BOLD_OBLIQUE;
			Color color = Color.blue;
			PDPageContentStream contentStream = null;

			contentStream = new PDPageContentStream(doc, page);

			contentStream.beginText();
			contentStream.setFont(font, 16);
			contentStream.setNonStrokingColor(color);
			contentStream.moveTextPositionByAmount(160, 600);
			contentStream.drawString("Usuarios");
			contentStream.endText();

			List<String> usuarios = crud.mostrar_usuarios();
			pdfs=570;
			for (int i = 0; i < usuarios.size(); i++) {
				contentStream.beginText();
				contentStream.setFont(font, 7);
				contentStream.setNonStrokingColor(color);
				contentStream.moveTextPositionByAmount(40,pdfs);
				//insertamos el texto de 'usuarios' en el pdf
				contentStream.drawString(usuarios.get(i));
				pdfs = pdfs - 30;
				contentStream.endText();
			}
			contentStream.close();
			doc.save("C:\\temp\\usuarios.pdf");
			System.out.println("El pdf se ha generado correctamente");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (doc != null) {
				try {
					doc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public void abrir_pdf() {
		String ficheroPDF ="c:\\temp\\usuarios.pdf";
		File path = new File(ficheroPDF);
		try {
			Desktop.getDesktop().open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
