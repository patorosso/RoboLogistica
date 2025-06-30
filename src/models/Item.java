package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
	@JsonProperty("nombre")
	private String nombre;
	@JsonProperty("cantidad")
	private int cantidad;

	public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " (x" + cantidad + ")";
    }
}
