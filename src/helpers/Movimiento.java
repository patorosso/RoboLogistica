package helpers;

import models.Robot;
import models.Cofre;
import java.util.List;

public class Movimiento {
	private Robot robot;
	private Cofre origen;
	private Cofre destino;
	private String item;
	private int cantidad;
	private double distancia;
	private double energiaConsumida;
	private List<String> camino;
	private List<String> caminoRegreso;

	public Movimiento(Robot robot, Cofre origen, Cofre destino, String item, int cantidad, double distancia, double energiaConsumida, List<String> camino, List<String> caminoRegreso) {
		this.robot = robot;
		this.origen = origen;
		this.destino = destino;
		this.item = item;
		this.cantidad = cantidad;
		this.distancia = distancia;
		this.energiaConsumida = energiaConsumida;
		this.camino = camino;
		this.caminoRegreso = caminoRegreso;
	}

	public Robot getRobot() { return robot; }
	public Cofre getOrigen() { return origen; }
	public Cofre getDestino() { return destino; }
	public String getItem() { return item; }
	public int getCantidad() { return cantidad; }
	public double getDistancia() { return distancia; }
	public double getEnergiaConsumida() { return energiaConsumida; }
	public List<String> getCamino() { return camino; }
	public List<String> getCaminoRegreso() { return caminoRegreso; }

	@Override
	public String toString() {
		return "Movimiento: Robot " + robot.getId() +
			" transporta " + cantidad + " " + item +
			" de " + origen.getId() + " a " + destino.getId() +
			" | Camino: " + camino +
			" | Regreso: " + caminoRegreso +
			" | Distancia: " + distancia +
			" | Energia: " + energiaConsumida;
	}
}
