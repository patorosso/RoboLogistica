package models;
import java.util.Map;

import helpers.Posicion;
import contracts.IPosicionable;
import helpers.Constantes.TipoCofre;

public abstract class Cofre implements IPosicionable {
	protected String id;
	protected Posicion posicion;
	protected Map<String, Integer> itemsOfrecidos;
	protected Map<String, Integer> itemsSolicitados;
	protected Map<String, Integer> itemsAlmacenados;

	public abstract TipoCofre getTipo();
	
	public Posicion getPosicion() {
		return this.posicion;
	}
}
