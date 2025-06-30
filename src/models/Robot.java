package models;

import helpers.Posicion;

import com.fasterxml.jackson.annotation.JsonProperty;

import contracts.Posicionable;
import java.util.Map;
import java.util.HashMap;

public class Robot implements Posicionable {
	@JsonProperty("id")
	private String id;
	@JsonProperty("bateriaMaxima")
	private int bateriaMaxima;
	@JsonProperty("capacidadCarga")	//de items
	private int capacidadCarga;
	@JsonProperty("factorConsumo")
	private int factorConsumo;
	@JsonProperty("idRobopuertoInicial")
	private String idRobopuertoInicial;
	
//	TODO: Plantear si es necesario:
	@JsonProperty("bateriaActual")
	private double bateriaActual;	//sirve para calcular en Dijkstra???
	
	private Posicion posicion;	//se la actualizamos al insertarlo en una red
	
	public String getId(){
		return this.id;
	}

	@Override
	public Posicion getPosicion() {
		return posicion;
	}
	
	public void setPosicion(Posicion posicion) {
	    this.posicion = posicion;
	}
	
	public String getIdRobopuertoInicial() {
		return this.idRobopuertoInicial;
	}
	
	public int getBateriaMaxima() {
		return this.bateriaMaxima;
	}
	
	public double getBateriaActual() {
		return this.bateriaActual;
	}
	
	public void setBateriaActual(double bateriaActual) {
		this.bateriaActual = bateriaActual;
	}
	
	public int getFactorConsumo() {
		return this.factorConsumo;
	}
	
	public int getCapacidadCarga() {
		return this.capacidadCarga;
	}
	
	public double calcularConsumoEnergia(double distancia) {
		return distancia * this.factorConsumo;
	}
	
	public void recargarBateria() {
		this.bateriaActual = this.bateriaMaxima;
	}
	
	public boolean puedeCompletarRuta(double distanciaTotal, int cantidadItems) {
		double energiaNecesaria = calcularConsumoEnergia(distanciaTotal);
		return this.bateriaActual >= energiaNecesaria && this.capacidadCarga >= cantidadItems;
	}
	
	public double calcularCostoRuta(double distanciaTotal, int cantidadItems, int prioridad) {
		double costoEnergia = calcularConsumoEnergia(distanciaTotal);
		double factorPrioridad = 1.0 / prioridad; // Mayor prioridad = menor factor
		double factorCantidad = cantidadItems / this.capacidadCarga; // Penalizar por usar más capacidad
		
		return costoEnergia * factorPrioridad * (1 + factorCantidad);
	}
	
	@Override
	public String toString() {
	    return "\n🤖 Robot " + id +
	           "\n   - Posición: (" + posicion.getX() + ", " + posicion.getY() + ")" + //se actualiza por 1ra vez cuando determine el robopuerto
	           "\n   - Batería: " + bateriaActual + " / " + bateriaMaxima +
	           "\n   - Capacidad de carga: " + capacidadCarga + " ítems" +
	           "\n   - Factor de consumo: " + factorConsumo +
	           "\n   - Nodo inicial: " + idRobopuertoInicial;
	}

}
