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

	@Override
	public Posicion getPosicion() {
		return this.posicion;
	}
}
