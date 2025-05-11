package models;

import helpers.Posicion;
import contracts.Posicionable;

public class Robot implements Posicionable {
	private int id;
	private int capacidadCarga;
	private double bateriaMaxima;
	private double bateriaActual;
	private Posicion posicion;
}
