package models;

import helpers.Posicion;

import com.fasterxml.jackson.annotation.JsonProperty;

import contracts.Posicionable;

public class Robot implements Posicionable {
	@JsonProperty("id")
	private String id;
	@JsonProperty("capacidadCarga")
	private int capacidadCarga;
	@JsonProperty("capacidadTraslado")
	private int capacidadTraslado;
	
//	TODO: Plantear si es necesario:
	@JsonProperty("bateriaActual")
	private double bateriaActual;
	
	@JsonProperty("posicion")
	private Posicion posicion;

	@Override
	public Posicion getPosicion() {
		return posicion;
	}
}
