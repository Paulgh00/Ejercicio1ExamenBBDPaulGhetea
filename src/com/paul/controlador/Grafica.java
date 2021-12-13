package com.paul.controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.util.Scanner;
import org.jfree.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.paul.transversal.Conexion;

public class Grafica {
	public void mostrar_grafica() {
		int edad[] ;
		int id[];

		
		//nombre_array = new tipo_dato[tamanio]
		Conexion BD = new Conexion();
		Connection conectar = BD.conectar();
		ResultSet resultado = null;
		Statement sentencia = null;
		int count=0, posicion=0;

		try {
			String cadenaSQL = "select count(*)  as contador from usuarios;";
			if (conectar != null) {
				sentencia = conectar.createStatement();
				resultado = sentencia.executeQuery(cadenaSQL);
			}
			while (resultado.next()) {
				count=resultado.getInt("contador");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		edad = new int[count];
		id = new int[count];

			try {
				String cadenaSQL1 = "Select * from usuarios";
				if (conectar != null) {
					sentencia = conectar.createStatement();
					
					resultado = sentencia.executeQuery(cadenaSQL1);
				}
				while (resultado.next()) {
					id[posicion] = resultado.getInt("idUsuario");
					edad[posicion] = resultado.getInt("edad");
					posicion++;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			XYSeries series = new XYSeries("edad");
			for (int i = 0; i < count; i++) {
				series.add(i, edad[i]);
			}
			
			Scanner sc = new Scanner(System.in);
	
			DateFormatSymbols dfs = new DateFormatSymbols();
			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			JFreeChart chart = ChartFactory.createXYLineChart(
					"Edad Usuarios ", 
					"Edad",
					"Id",
					dataset, 
					PlotOrientation.VERTICAL,
					true,
					false,
					false
					);
			ChartFrame frame = new ChartFrame("Gráfica Edad Usuarios", chart);
			frame.pack();
			frame.setVisible(true);
		}
}
