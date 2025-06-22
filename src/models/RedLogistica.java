package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedLogistica {
	private List<Cofre> cofres;
	private List<Robot> robots;
	private List<Robopuerto> robopuertos;

	public void ejecutarCiclo() {
	};

	public void simularHastaEstadoEstable() {
	};

	public static List<RedLogistica> getRedes(List<Cofre> cofres, List<Robopuerto> robopuertos, List<Robot> robots) {
		List<RedLogistica> redes = new ArrayList<>();
	    
	    // 1. Crear mapa de adyacencias para robopuertos
	    Map<Robopuerto, List<Robopuerto>> adyacentes = new HashMap();
	    for (Robopuerto r1 : robopuertos) {
	        for (Robopuerto r2 : robopuertos) {
	            if (!r1.equals(r2) && r1.seSolapaCon(r2)) {
	                adyacentes.computeIfAbsent(r1, k -> new ArrayList<>()).add(r2);
	                adyacentes.computeIfAbsent(r2, k -> new ArrayList<>()).add(r1);
	            }
	        }
	    }

	    // 2. Buscar componentes conexas con DFS o BFS
	    Set<Robopuerto> visitados = new HashSet();
	    for (Robopuerto r : robopuertos) {
	        if (!visitados.contains(r)) {
	            Set<Robopuerto> componente = new HashSet<>();
	            dfs(r, adyacentes, visitados, componente);

	            // 3. Crear la red con estos robopuertos
	            RedLogistica red = new RedLogistica();
	            red.robopuertos.addAll(componente);

	            // 4. Agregar cofres cubiertos
	            for (Cofre c : cofres) {
	                if (componente.stream().anyMatch(rp -> rp.cubre(c))) {
	                    red.cofres.add(c);
	                }
	            }

	            // 5. Agregar robots que empiecen dentro de la red
	            for (Robot robot : robots) {
	                if (componente.stream().anyMatch(rp -> rp.cubre(robot))) {
	                    red.robots.add(robot);
	                }
	            }

	            redes.add(red);
	        }
	    }

	    return redes;
	}
	
	private static void dfs(Robopuerto actual, Map<Robopuerto, List<Robopuerto>> grafo, Set<Robopuerto> visitados, Set<Robopuerto> componente) {
	    visitados.add(actual);
	    componente.add(actual);
	    for (Robopuerto vecino : grafo.getOrDefault(actual, List.of())) {
	        if (!visitados.contains(vecino)) {
	            dfs(vecino, grafo, visitados, componente);
	        }
	    }
	}
}
