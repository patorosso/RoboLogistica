package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import helpers.Posicion;
import contracts.Posicionable;
import helpers.Constantes.TipoCofre;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({ @JsonSubTypes.Type(value = CofreSolicitud.class, name = "COFRE_SOLICITUD"),
//		  @JsonSubTypes.Type(value = CofreProvisionActiva.class, name = "COFRE_PROVISION_ACTIVA"),
//		  @JsonSubTypes.Type(value = CofreProvisionPasiva.class, name = "COFRE_PROVISION_PASIVA"),
//		  @JsonSubTypes.Type(value = CofreAlmacenamiento.class, name = "COFRE_ALMACENAMIENTO"),
//		  @JsonSubTypes.Type(value = CofreBufer.class, name = "COFRE_BUFER")
})
public abstract class Cofre implements Posicionable {
	@JsonProperty("id")
	protected String id;
	@JsonProperty("posicion")
	protected Posicion posicion;
	@JsonProperty("itemsOfrecidos")
	protected List<Item> itemsOfrecidos;
	@JsonProperty("itemsSolicitados")
	protected List<Item> itemsSolicitados;
	@JsonProperty("itemsAlmacenados")
	protected List<Item> itemsAlmacenados;

	public abstract TipoCofre getTipo();

	@Override
	public Posicion getPosicion() {
		return this.posicion;
	}
}
