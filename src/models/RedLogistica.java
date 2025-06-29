package models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import contracts.Posicionable;
import helpers.Constantes.TipoCofre;

public class RedLogistica {

	private String idRed; // Ej: "RL1"
	private List<Robopuerto> robopuertos; // NEW
	private EnumMap<TipoCofre, List<Cofre>> cofresPorTipo; // NEW

	private List<Robot> robots;
	private Map<String, List<Arista>> listaAdyacencia;

	public RedLogistica(String idRed) { // NEW
		this.idRed = idRed;
		this.robopuertos = new ArrayList<>();
		this.robots = new ArrayList<>();
		this.listaAdyacencia = new HashMap<>();
		this.cofresPorTipo = new EnumMap<>(TipoCofre.class);
		for (TipoCofre tipo : TipoCofre.values()) {
			cofresPorTipo.put(tipo, new ArrayList<>());
		}

	}

	public void agregarRobopuerto(Robopuerto rp) { // NEW
		robopuertos.add(rp);
		listaAdyacencia.putIfAbsent(rp.getId(), new ArrayList<>());
	}

	public void agregarCofre(Cofre cofre) { // NEW
		cofresPorTipo.get(cofre.getTipo()).add(cofre);
		listaAdyacencia.putIfAbsent(cofre.getId(), new ArrayList<>()); // opcional pero seguro
	}

	public String getIdRed() {
		return idRed;
	}

	public List<Robopuerto> getRobopuertos() { // NEW
		return robopuertos;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public Map<String, List<Arista>> getListaAdyacencia() {
		return listaAdyacencia;
	}

	public void agregarRobot(Robot robot) {
		robots.add(robot);
	}

	public void generarAristasEntreRobopuertos() {
		for (int i = 0; i < robopuertos.size(); i++) {
			Robopuerto rp1 = robopuertos.get(i);
			listaAdyacencia.putIfAbsent(rp1.getId(), new ArrayList<>()); // aseguro su entrada

			for (int j = i + 1; j < robopuertos.size(); j++) {
				Robopuerto rp2 = robopuertos.get(j);
				double distancia = rp1.getPosicion().distanciaA(rp2.getPosicion());
				agregarArista(rp1.getId(), rp2.getId(), distancia);
			}
		}
	}

	public void agregarArista(String idOrigen, String idDestino, double peso) {
		listaAdyacencia.computeIfAbsent(idOrigen, k -> new ArrayList<>()).add(new Arista(idDestino, peso));

		listaAdyacencia.computeIfAbsent(idDestino, k -> new ArrayList<>()).add(new Arista(idOrigen, peso)); // grafo no
																											// dirigido
	}

	public void mostrarRobopuertos() { // NEW
		System.out.println("📡 Robopuertos en " + idRed + ":");
		for (Robopuerto rp : robopuertos) {
			System.out.println(
					"   - " + rp.getId() + " @ (" + rp.getPosicion().getX() + ", " + rp.getPosicion().getY() + ")");
		}
	}

	public void mostrarCofresPorTipo() {
		System.out.println("🧱 Cofres en " + idRed + ":");
		for (TipoCofre tipo : TipoCofre.values()) {
			List<Cofre> lista = cofresPorTipo.get(tipo);
			if (!lista.isEmpty()) {
				System.out.println("  - " + tipo + ":");
				for (Cofre c : lista) {
					System.out.println("     · " + c.getId());
				}
			}
		}
	}

	public void mostrarRobots() {
		System.out.println("\n 🤖 Robots en " + idRed + ": " + robots.size());
		for (Robot robot : robots) {
			System.out.println("   - " + robot.getId() + " (Inicio: " + robot.getIdRobopuertoInicial() + ")");
		}
	}
	
	/*
	 * public List<Movimiento> procesarPeticion(Cofre peticion) throws Exception {
		List<Movimiento> movimientos = new ArrayList<Movimiento>();

		// TODO:
		switch (peticion.getTipo()) {
		case PROVISION_ACTIVA:
			movimientos = this.resolverProvisionActiva(peticion);
			break;
		// lógica para PROVISION_PASIVA
		case SOLICITUD:
			// lógica para SOLICITUD
			break;
		case BUFFER:
			break;
		case ALMACENAMIENTO:
		case PROVISION_PASIVA:
			throw new Exception("Hay cofres pasivos o almacenamiento en la lista de peticiones.");
		default:
			// opcional: lógica para casos no contemplados (aunque no suele ocurrir con
			// enums exhaustivos)
			break;
		}

		return movimientos;
	}

	public List<Movimiento> resolverProvisionActiva(Cofre peticion) {
//		for(buffers)
//		{
//			
//			
//			for(robots) {
//				// var pude = intentoDejarLoQueTengo();
//				// 1. si coincide lo que pide
//				// 2. si puedo llegar hasta ahí
//				if(pude)
//					break;
//				
//				boolean pudo = robot.puedoViajarHastaAlla();
//				// esa funcion hace:
//				// 1. chequea si tiene el warshall para el robot, si no lo tiene lo crea y lo usa
//				// 2. se fija si puede el camino teniendo en cuenta la bateria y nada eso.
//				// 2.1 si no pudo, pasa al proximo robot ... y bueno tiraremos excepcion NetworkNoProcessable
//				// 3. esa funcion se encarga de actualizar sus valores y trackea movimientos.
//				
//				
//			}
//		}

		return null;

	}

	public boolean esProcesable() {
		// TODO:
		return true;
	}
	 */

}
