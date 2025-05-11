package models;

import helpers.Posicion;
import contracts.IPosicionable;

public class Robopuerto implements IPosicionable {
    private int id;
    private Posicion posicion;
    private double radioCobertura;
}

