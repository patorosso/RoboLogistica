package main;

import helpers.GeneradorDeRedes;
import helpers.LectorArchivo;
import models.*;
import java.util.*;

//import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			/********************************************************************************************/
			//LEO ROBOBOPUERTOS
			List<Robopuerto> robopuertos = LectorArchivo.robopuertos("src/in/robopuertos/caso1.json");	
			
			//MUESTRO POR CONSOLA LOS ROBOPUERTOS PARA VERIFICAR CORRECTA LECTURA
			for (Robopuerto rp : robopuertos) {
			    System.out.println(rp);
			}
			
			//GENERO UN MAP/LISTADO DE REDES LOGISTICAS Y MUESTRO LAS GENERADAS
			Map<String, RedLogistica> redes = GeneradorDeRedes.construirRedesLogisticas(robopuertos);
			for (RedLogistica red : redes.values()) {
			    red.mostrarNodos();
			}

			//GENERO LAS ARISTAS ENTRE ROBOPUERTOS DE LA MISMA RED
			for (RedLogistica red : redes.values()) {
			    red.generarAristasEntreRobopuertos(); // método que ya definimos
			}
			/*********************************************************************************************/
			
			//LEO LOS COFRES
			List<Cofre> cofres = LectorArchivo.cofres("src/in/cofres/caso1.json");

			//MUESTRO POR CONSOLA LOS COFRES PARA VERIFICAR CORRECTA LECTURA
			for (Cofre cofre : cofres) {
			    System.out.println(cofre);
			}
			
			//GENERO LAS ARISTAS ENTRE COFRES DE LA MISMA RED
			GeneradorDeRedes.procesarCofresEnRedes(cofres, redes);
			
			//MUESTRO POR CONSOLA LAS ARISTAS PARA VERIFICAR CORRECTA LECTURA
			for (RedLogistica red : redes.values()) {
			    System.out.println("\n ▶ Aristas en " + red.getIdRed());
			    red.getListaAdyacencia().forEach((id, lista) -> {
			        System.out.println("  " + id + " → " + lista);//id de cada nodo=cofre=robopuerto
			    });													//el resto es el toString de Arista
			}

			
			/*********************************************************************************************/
			
			//LEO LOS ROBOTS
			List<Robot> robots = LectorArchivo.robots("src/in/robots/caso1.json");
			
			GeneradorDeRedes.asignarRobotsASusRedes(robots, redes);
			
			//MUESTRO POR CONSOLA LOS ROBOTS PARA VERIFICAR CORRECTA LECTURA
			for (Robot robot : robots) {
			    System.out.println(robot);
			}
			
			//MUESTRO QUE LOS ROBOTS FUERON ASIGNADOS A LAS REDES CORRECTAMENTE
			for (RedLogistica red : redes.values()) {
			    red.mostrarRobots();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("\n SUCESSFUL RUN!!!");
	}
}