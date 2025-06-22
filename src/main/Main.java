package main;

import helpers.LectorArchivo;
import models.*;
import java.util.*;

//import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			List<Cofre> cofres = LectorArchivo.cofres("src/in/cofres/caso1.json");
			List<Robot> robots = LectorArchivo.robots("src/in/robots/caso1.json");
			List<Robopuerto> robopuertos = LectorArchivo.robopuertos("src/in/robopuertos/caso1.json");
			
			List<RedLogistica> redes = RedLogistica.getRedes(cofres, robopuertos, robots);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}