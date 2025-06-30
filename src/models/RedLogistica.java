package models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import contracts.Posicionable;
import helpers.Constantes.TipoCofre;
import helpers.Movimiento;

public class RedLogistica {

	private String idRed; // Ej: "RL1"
	private List<Robopuerto> robopuertos; // NEW
	private EnumMap<TipoCofre, List<Cofre>> cofresPorTipo; // NEW

	private List<Robot> robots;
	private Map<String, List<Arista>> listaAdyacencia;

	private Map<String, Map<String, Double>> distMin;
	private Map<String, Map<String, String>> predecesor;

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

	}*/

	public boolean esProcesable() {
		// Para cada solicitud pendiente, verificar si al menos un robot puede atenderla
		for (Cofre cofre : cofresPorTipo.get(TipoCofre.SOLICITUD)) {
			for (Item item : cofre.getItemsSolicitados()) {
				if (item.getCantidad() > 0) {
					Cofre proveedor = buscarMejorProveedor(item.getNombre(), item.getCantidad());
					if (proveedor == null) continue;
					for (Robot robot : robots) {
						String desde = proveedor.getId();
						String hasta = cofre.getId();
						List<String> camino = reconstruirCamino(desde, hasta);
						if (camino.isEmpty()) continue;
						String rpCercano = getRobopuertoMasCercano(cofre.getId());
						List<String> caminoRegreso = reconstruirCamino(cofre.getId(), rpCercano);
						if (caminoRegreso.isEmpty()) continue;
						if (puedeRecorrerCaminoTodoONada(robot, camino, caminoRegreso)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	 

	public EnumMap<TipoCofre, List<Cofre>> getCofresPorTipo() {
		return cofresPorTipo;
	}
	
	public List<Cofre> getAllCofres() {
		List<Cofre> todosLosCofres = new ArrayList<>();
		for (List<Cofre> cofres : cofresPorTipo.values()) {
			todosLosCofres.addAll(cofres);
		}
		return todosLosCofres;
	}

	public Cofre buscarMejorProveedor(String item, int cantidad) {
		// Prioridad: PA > PP > Buffer > Almacenamiento
		TipoCofre[] prioridades = {TipoCofre.PROVISION_ACTIVA, TipoCofre.PROVISION_PASIVA, TipoCofre.BUFFER, TipoCofre.ALMACENAMIENTO};
		
		for (TipoCofre tipo : prioridades) {
			for (Cofre cofre : cofresPorTipo.get(tipo)) {
				if (cofre.tieneItemDisponible(item, cantidad)) {
					return cofre;
				}
			}
		}
		return null; // No se encontró proveedor
	}
	
	public void asignarSolicitudes() {
		List<Cofre> solicitudes = new ArrayList<>();
		solicitudes.addAll(cofresPorTipo.get(TipoCofre.SOLICITUD));
		solicitudes.addAll(cofresPorTipo.get(TipoCofre.BUFFER));
		for (Cofre solicitud : solicitudes) {
			for (Item itemSolicitado : solicitud.getItemsSolicitados()) {
				if (itemSolicitado.getCantidad() <= 0) continue; // Solo procesar si hay cantidad pendiente
				Cofre proveedor = buscarMejorProveedor(itemSolicitado.getNombre(), itemSolicitado.getCantidad());
				if (proveedor != null) {
					Robot mejorRobot = null;
					double menorCosto = Double.MAX_VALUE;
					List<String> mejorCamino = null;
					List<String> mejorCaminoRegreso = null;
					for (Robot robot : robots) {
						String desde = proveedor.getId();
						String hasta = solicitud.getId();
						List<String> camino = reconstruirCamino(desde, hasta);
						if (camino.isEmpty()) continue;
						String rpCercano = getRobopuertoMasCercano(solicitud.getId());
						List<String> caminoRegreso = reconstruirCamino(solicitud.getId(), rpCercano);
						if (caminoRegreso.isEmpty()) continue;
						if (puedeRecorrerCaminoTodoONada(robot, camino, caminoRegreso)) {
							double costo = getDistanciaMinima(desde, hasta) + getDistanciaMinima(solicitud.getId(), rpCercano);
							if (costo < menorCosto) {
								menorCosto = costo;
								mejorRobot = robot;
								mejorCamino = camino;
								mejorCaminoRegreso = caminoRegreso;
							}
						}
					}
					if (mejorRobot != null) {
						int cantidadDisponible = proveedor.getItemsOfrecidos().stream()
							.filter(it -> it.getNombre().equals(itemSolicitado.getNombre()))
							.findFirst().map(Item::getCantidad).orElse(0);
						int cantidadSolicitada = itemSolicitado.getCantidad();
						int cantidadTransportada = Math.min(Math.min(cantidadSolicitada, cantidadDisponible), mejorRobot.getCapacidadCarga());
						if (cantidadTransportada > 0) {
							ejecutarAsignacionConCamino(mejorRobot, proveedor, solicitud, itemSolicitado, mejorCamino, mejorCaminoRegreso, cantidadTransportada);
							return; // Solo un movimiento por ciclo
						}
					}
				}
			}
		}
	}

	private String getRobopuertoMasCercano(String desdeId) {
		String rpCercano = null;
		double menorDist = Double.POSITIVE_INFINITY;
		for (Robopuerto rp : robopuertos) {
			double dist = getDistanciaMinima(desdeId, rp.getId());
			if (dist < menorDist) {
				menorDist = dist;
				rpCercano = rp.getId();
			}
		}
		return rpCercano;
	}

	private boolean puedeRecorrerCaminoTodoONada(Robot robot, List<String> camino, List<String> caminoRegreso) {
		double bateria = robot.getBateriaActual();
		String actual = camino.get(0);
		for (int i = 1; i < camino.size(); i++) {
			String siguiente = camino.get(i);
			double distancia = getDistanciaMinima(actual, siguiente);
			double consumo = robot.calcularConsumoEnergia(distancia);
			if (bateria < consumo) return false;
			bateria -= consumo;
			if (esRobopuerto(siguiente)) bateria = robot.getBateriaMaxima();
			actual = siguiente;
		}
		// Simular regreso
		for (int i = 1; i < caminoRegreso.size(); i++) {
			String siguiente = caminoRegreso.get(i);
			double distancia = getDistanciaMinima(actual, siguiente);
			double consumo = robot.calcularConsumoEnergia(distancia);
			if (bateria < consumo) return false;
			bateria -= consumo;
			if (esRobopuerto(siguiente)) bateria = robot.getBateriaMaxima();
			actual = siguiente;
		}
		return true;
	}

	private boolean esRobopuerto(String id) {
		for (Robopuerto rp : robopuertos) if (rp.getId().equals(id)) return true;
		return false;
	}

	private void ejecutarAsignacionConCamino(Robot robot, Cofre proveedor, Cofre solicitud, Item item, List<String> camino, List<String> caminoRegreso, int cantidadTransportada) {
		// Actualizar inventarios
		for (Item it : proveedor.getItemsOfrecidos()) {
			if (it.getNombre().equals(item.getNombre())) {
				it.setCantidad(it.getCantidad() - cantidadTransportada);
				break;
			}
		}
		for (Item it : solicitud.getItemsSolicitados()) {
			if (it.getNombre().equals(item.getNombre())) {
				it.setCantidad(Math.max(0, it.getCantidad() - cantidadTransportada));
				break;
			}
		}
		// Simular consumo de batería y actualizar posición
		double bateria = robot.getBateriaActual();
		String actual = camino.get(0);
		double distanciaTotal = 0;
		double energiaTotal = 0;
		for (int i = 1; i < camino.size(); i++) {
			String siguiente = camino.get(i);
			double distancia = getDistanciaMinima(actual, siguiente);
			double consumo = robot.calcularConsumoEnergia(distancia);
			distanciaTotal += distancia;
			energiaTotal += consumo;
			bateria -= consumo;
			if (esRobopuerto(siguiente)) bateria = robot.getBateriaMaxima();
			actual = siguiente;
		}
		// Simular regreso
		for (int i = 1; i < caminoRegreso.size(); i++) {
			String siguiente = caminoRegreso.get(i);
			double distancia = getDistanciaMinima(actual, siguiente);
			double consumo = robot.calcularConsumoEnergia(distancia);
			distanciaTotal += distancia;
			energiaTotal += consumo;
			bateria -= consumo;
			if (esRobopuerto(siguiente)) bateria = robot.getBateriaMaxima();
			actual = siguiente;
		}
		robot.setBateriaActual(bateria);
		// Actualizar posición del robot al último nodo del regreso
		for (Robopuerto rp : robopuertos) {
			if (rp.getId().equals(actual)) {
				robot.setPosicion(rp.getPosicion());
				break;
			}
		}
		Movimiento mov = new Movimiento(robot, proveedor, solicitud, item.getNombre(), cantidadTransportada, distanciaTotal, energiaTotal, camino, caminoRegreso);
		System.out.println(mov);
	}

	public void calcularCaminosMinimos() {
		// Inicializar matrices
		distMin = new HashMap<>();
		predecesor = new HashMap<>();
		// Nodos: todos los cofres y robopuertos
		List<String> nodos = new ArrayList<>();
		for (Robopuerto rp : robopuertos) nodos.add(rp.getId());
		for (TipoCofre tipo : cofresPorTipo.keySet())
			for (Cofre c : cofresPorTipo.get(tipo)) nodos.add(c.getId());
		// Inicializar distancias
		for (String u : nodos) {
			distMin.put(u, new HashMap<>());
			predecesor.put(u, new HashMap<>());
			for (String v : nodos) {
				if (u.equals(v)) {
					distMin.get(u).put(v, 0.0);
					predecesor.get(u).put(v, null);
				} else {
					distMin.get(u).put(v, Double.POSITIVE_INFINITY);
					predecesor.get(u).put(v, null);
				}
			}
		}
		// Cargar aristas
		for (String u : listaAdyacencia.keySet()) {
			for (Arista arista : listaAdyacencia.get(u)) {
				distMin.get(u).put(arista.getDestino(), arista.getPeso());
				predecesor.get(u).put(arista.getDestino(), u);
			}
		}
		// Floyd-Warshall
		for (String k : nodos) {
			for (String i : nodos) {
				for (String j : nodos) {
					double alt = distMin.get(i).get(k) + distMin.get(k).get(j);
					if (alt < distMin.get(i).get(j)) {
						distMin.get(i).put(j, alt);
						predecesor.get(i).put(j, predecesor.get(k).get(j));
					}
				}
			}
		}
	}

	public double getDistanciaMinima(String origen, String destino) {
		return distMin.getOrDefault(origen, Map.of()).getOrDefault(destino, Double.POSITIVE_INFINITY);
	}

	public List<String> reconstruirCamino(String origen, String destino) {
		List<String> camino = new ArrayList<>();
		if (predecesor.get(origen).get(destino) == null) return camino;
		String actual = destino;
		while (!actual.equals(origen)) {
			camino.add(0, actual);
			actual = predecesor.get(origen).get(actual);
		}
		camino.add(0, origen);
		return camino;
	}

	public List<Cofre> getPeticiones() {
		List<Cofre> peticiones = new ArrayList<>();
		for (TipoCofre tipo : new TipoCofre[]{TipoCofre.SOLICITUD, TipoCofre.BUFFER}) {
			for (Cofre cofre : cofresPorTipo.get(tipo)) {
				for (Item item : cofre.getItemsSolicitados()) {
					if (item.getCantidad() > 0) {
						peticiones.add(cofre);
						break;
					}
				}
			}
		}
		return peticiones;
	}

}

