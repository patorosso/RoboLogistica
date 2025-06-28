package models;

import helpers.Posicion;

import com.fasterxml.jackson.annotation.JsonProperty;

import contracts.Posicionable;

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
