package helpers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Cofre;

import models.RedLogistica;
import models.Robopuerto;
import models.Robot;

public class GeneradorDeRedes {

	public static Map<String, RedLogistica> construirRedesLogisticas(List<Robopuerto> robopuertos) {
	    UnionFind uf = new UnionFind();

	    // 1. Inicializar el union-find
	    for (Robopuerto rp : robopuertos) {
	        uf.agregar(rp.getId());
	    }

	    // 2. Agrupar por solapamiento
	    for (int i = 0; i < robopuertos.size(); i++) {
	        for (int j = i + 1; j < robopuertos.size(); j++) {
	            Robopuerto a = robopuertos.get(i);
	            Robopuerto b = robopuertos.get(j);
	            if (a.seSolapaCon(b)) {
	                uf.unir(a.getId(), b.getId());
	            }
	        }
	    }

	    // 3. Crear redes
	    Map<String, List<String>> componentes = uf.obtenerComponentes();
	    Map<String, RedLogistica> redes = new HashMap<>();

	    int contador = 1;
	    for (Map.Entry<String, List<String>> entry : componentes.entrySet()) {
	        String nombreRed = "RL" + contador++;
	        RedLogistica red = new RedLogistica(nombreRed);

	        for (String idRobopuerto : entry.getValue()) {
	            // buscás el robopuerto original por ID
	            Robopuerto rp = robopuertos.stream()
	                .filter(r -> r.getId().equals(idRobopuerto))
	                .findFirst().orElseThrow();
	            red.agregarRobopuerto(rp); // suponiendo que RedLogistica tiene agregarNodo(Robopuerto)
	        }

	        redes.put(nombreRed, red);
	    }

	    return redes;
	}
	
	public static void procesarCofresEnRedes(List<Cofre> cofres, Map<String, RedLogistica> redes) {	//NEW
	    for (Cofre cofre : cofres) {
	        boolean asignado = false;

	        for (RedLogistica red : redes.values()) {
	            for (Robopuerto rp : red.getRobopuertos()) {
	                if (rp.cubre(cofre)) {


	                    // Agregar el cofre a la red
	                    red.agregarCofre(cofre);

	                    // Crear aristas entre el cofre y todos los robopuertos de la red
	                    for (Robopuerto otroRp : red.getRobopuertos()) {
	                        double distancia = otroRp.getPosicion().distanciaA(cofre.getPosicion());
	                        red.agregarArista(cofre.getId(), otroRp.getId(), distancia);
	                    }

	                    asignado = true;
	                    break;
	                }
	            }

	            if (asignado) break;
	        }

	        if (!asignado) {
	            System.err.println("⚠ Cofre " + cofre.getId() + " no fue asignado a ninguna red logística.");
	        }
	    }
	}

	public static void asignarRobotsASusRedes(List<Robot> robots, Map<String, RedLogistica> redes) {
	    for (Robot robot : robots) {
	        for (RedLogistica red : redes.values()) {
	            for (Robopuerto rp : red.getRobopuertos()) {
	                if (rp.getId().equals(robot.getIdRobopuertoInicial())) {
	                    // Asignar robot a la red
	                    red.agregarRobot(robot);

	                    // Setear posición del robot al robopuerto inicial
	                    robot.setPosicion(rp.getPosicion());

	                    // (Opcional) Asignar la red al robot, si lo necesitás después
	                    // robot.setIdRedLogistica(red.getIdRed());

	                    break;
	                }
	            }
	        }
	    }
	}



}
