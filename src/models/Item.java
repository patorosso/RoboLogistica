package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
	@JsonProperty("nombre")
	private String nombre;
	@JsonProperty("cantidad")
	private int cantidad;

}
