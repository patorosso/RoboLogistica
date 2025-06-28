package helpers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Cofre;
import models.Nodo;
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
	            red.agregarNodo(rp); // suponiendo que RedLogistica tiene agregarNodo(Robopuerto)
	        }

	        redes.put(nombreRed, red);
	    }

	    return redes;
	}

	public static void procesarCofresEnRedes(List<Cofre> cofres, Map<String, RedLogistica> redes) {
	    for (Cofre cofre : cofres) {
	        boolean asignado = false;

	        for (RedLogistica red : redes.values()) {
	            // Buscar UN robopuerto de esta red que cubra al cofre
	            for (Nodo nodo : red.getNodos()) {
	                if (nodo.getEntidad() instanceof Robopuerto rp && rp.cubre(cofre)) {

	                    // Asignar la red al cofre
	                    cofre.setIdRedLogistica(red.getIdRed());
	                    red.agregarNodo(cofre); // solo una vez

	                    // Conectar el cofre a TODOS los robopuertos de esa red
	                    red.getNodos().stream()
	                        .filter(n -> n.getEntidad() instanceof Robopuerto)
	                        .forEach(n -> {
	                            double distancia = n.getPosicion().distanciaA(cofre.getPosicion());
	                            red.agregarArista(cofre.getId(), n.getId(), distancia);
	                        });

	                    asignado = true;
	                    break; // Ya no necesitamos seguir en esta red
	                }
	            }

	            if (asignado) break; // Ya no necesitamos seguir buscando en otras redes
	        }

	        if (!asignado) {
	            System.err.println("⚠ Cofre " + cofre.getId() + " no fue asignado a ninguna red logística.");
	        }
	    }
	}
	
	public static void asignarRobotsASusRedes(List<Robot> robots, Map<String, RedLogistica> redes) {
	    for (Robot robot : robots) {
	        for (RedLogistica red : redes.values()) {
	            if (red.getListaAdyacencia().containsKey(robot.getIdRobopuertoInicial())) {
	                // Asignar robot a la red
	                red.agregarRobot(robot);

	                // Buscar el Nodo robopuerto correspondiente y setear posición
	                Nodo nodo = red.getNodos().stream()
	                    .filter(n -> n.getId().equals(robot.getIdRobopuertoInicial()))
	                    .findFirst()
	                    .orElse(null);

	                if (nodo != null) {
	                    robot.setPosicion(nodo.getPosicion());
	                } else {
	                    System.err.println("⚠ No se encontró el robopuerto " + robot.getIdRobopuertoInicial() + " en " + red.getIdRed());
	                }

	                break;
	            }
	        }
	    }
	}



}
