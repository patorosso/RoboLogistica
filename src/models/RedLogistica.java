package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import contracts.Posicionable;

public class RedLogistica {

	    private String idRed; // Ej: "RL1"
	    private Set<Nodo> nodos;
	    private List<Robot> robots;
	    private Map<String, List<Arista>> listaAdyacencia;

	    public RedLogistica(String idRed) {
	        this.idRed = idRed;
	        this.nodos = new HashSet<>();
	        this.robots = new ArrayList<>();
	        this.listaAdyacencia = new HashMap<>();
	    }

	    public String getIdRed() {
	        return idRed;
	    }

	    public Set<Nodo> getNodos() {
	        return nodos;
	    }

	    public List<Robot> getRobots() {
	        return robots;
	    }

	    public Map<String, List<Arista>> getListaAdyacencia() {
	        return listaAdyacencia;
	    }

	    public void agregarNodo(Posicionable obj) {
	        if (obj instanceof Robopuerto || obj instanceof Cofre) {
	            nodos.add(new Nodo(obj)); // asumimos que Nodo envuelve a robopuertos y cofres
	        }
	    }

	    public void agregarRobot(Robot robot) {
	        robots.add(robot);
	    }
	    
	    public void generarAristasEntreRobopuertos() {
	        List<Nodo> robopuertos = nodos.stream()
	            .filter(n -> n.getEntidad() instanceof Robopuerto)
	            .toList();

	        for (int i = 0; i < robopuertos.size(); i++) {
	            Nodo a = robopuertos.get(i);
	            listaAdyacencia.putIfAbsent(a.getId(), new ArrayList<>()); // ⚠ asegurar clave aunque esté solo

	            for (int j = i + 1; j < robopuertos.size(); j++) {
	                Nodo b = robopuertos.get(j);

	                double distancia = a.getPosicion().distanciaA(b.getPosicion());
	                agregarArista(a.getId(), b.getId(), distancia);
	            }
	        }
	    }



	    public void agregarArista(String idOrigen, String idDestino, double peso) {
	        listaAdyacencia
	            .computeIfAbsent(idOrigen, k -> new ArrayList<>())
	            .add(new Arista(idDestino, peso));

	        listaAdyacencia
	            .computeIfAbsent(idDestino, k -> new ArrayList<>())
	            .add(new Arista(idOrigen, peso)); // grafo no dirigido
	    }

	    @Override
	    public String toString() {
	        return "\n🔗 Red logística " + idRed +
	               "\n   - Nodos: " + nodos.size() +
	               "\n   - Robots: " + robots.size();
	    }
	    
	    public void mostrarNodos() {
	        System.out.print("\n" + idRed + " → [");
	        String lista = nodos.stream()
	                            .map(Nodo::getId)
	                            .collect(Collectors.joining(", "));
	        System.out.println(lista + "]");
	    }
	    /*	Recorre todos los Nodo de la red,
			Toma su id,
			Los concatena separados por coma,
			Y los imprime con el prefijo de la red logística.
			*/

	    public void mostrarRobots() {
	        System.out.println("\n 🤖 Robots en " + idRed + ": " + robots.size());
	        for (Robot robot : robots) {
	            System.out.println("   - " + robot.getId() + " (Inicio: " + robot.getIdRobopuertoInicial() + ")");
	        }
	    }

	}

	

	

