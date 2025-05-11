package models;

import helpers.Posicion;
import contracts.IPosicionable;

public class Robot implements IPosicionable {
    private int id;
    private int capacidadCarga;
    private double bateriaMaxima;
    private double bateriaActual;
    private Posicion posicion;
}

