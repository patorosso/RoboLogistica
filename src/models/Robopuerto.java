package models;

import helpers.Posicion;

import com.fasterxml.jackson.annotation.JsonProperty;

import contracts.Posicionable;

public class Robopuerto implements Posicionable {
	@JsonProperty("id")
	private String id;
	@JsonProperty("posicion")
	private Posicion posicion;
	@JsonProperty("radioCobertura")
	private double radioCobertura;

	public boolean cubre(Posicionable obj) {
		return this.posicion.distanciaA(obj.getPosicion()) <= this.radioCobertura;
	}

	public boolean seSolapaCon(Robopuerto otro) {
		double distanciaCentros = this.posicion.distanciaA(otro.getPosicion());
		return distanciaCentros <= (this.radioCobertura + otro.radioCobertura);
	}
	//Dos robopuertos se solapan si la distancia entre sus centros es menor o 
	//igual a la suma de sus radios de cobertura.
	
	@Override
	public Posicion getPosicion() {
		return this.posicion;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
	    return "\nRobopuerto " + id +
	           "\n   - Posicion: (" + posicion.getX() + ", " + posicion.getY() + ")" +
	           "\n   - Alcance: " + radioCobertura;
	}

}
