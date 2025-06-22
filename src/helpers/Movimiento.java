package helpers;

import models.Robot;
import models.Cofre;

public class Movimiento {
	private Robot robot;
	private Cofre origen;
	private Cofre destino;
	private String item;
	private int cantidad;
	private double distancia;
	private double energiaConsumida;
	
	
	public Movimiento getMovimiento() {
		return this;
	}
}
