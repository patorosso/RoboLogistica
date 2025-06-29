package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import helpers.Posicion;
import contracts.Posicionable;
import helpers.Constantes;

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
	@JsonProperty("tipo")
	private Constantes.TipoCofre tipo;
	@JsonProperty("itemsOfrecidos")
	protected List<Item> itemsOfrecidos;
	@JsonProperty("itemsSolicitados")
	protected List<Item> itemsSolicitados;
	@JsonProperty("itemsAlmacenados")
	protected List<Item> itemsAlmacenados;


	public Constantes.TipoCofre getTipo() {
	    return tipo;
	}

	@Override
	public Posicion getPosicion() {
		return this.posicion;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String mostrarItemsComoTexto() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("\n  📦 Items Solicitados: ");
	    if (itemsSolicitados != null && !itemsSolicitados.isEmpty()) {
	        for (Item item : itemsSolicitados) {
	            sb.append("\n    - ").append(item);
	        }
	    } else {
	        sb.append(" (ninguno)");
	    }

	    sb.append("\n  📤 Items Ofrecidos: ");
	    if (itemsOfrecidos != null && !itemsOfrecidos.isEmpty()) {
	        for (Item item : itemsOfrecidos) {
	            sb.append("\n    - ").append(item);
	        }
	    } else {
	        sb.append(" (ninguno)");
	    }

	    sb.append("\n  🗃️ Items Almacenados: ");
	    if (itemsAlmacenados != null && !itemsAlmacenados.isEmpty()) {
	        for (Item item : itemsAlmacenados) {
	            sb.append("\n    - ").append(item);
	        }
	    } else {
	        sb.append(" (ninguno)");
	    }

	    return sb.toString();
	}
	    
	
	@Override
	public String toString() {
	    return "\n Cofre{" +
	            "id=" + id +
	            ", tipo=" + getTipo()+
	            ", x=" + posicion.getX() +
	            ", y=" + posicion.getY() +
	            mostrarItemsComoTexto() + 
	            '}';
	}

}
