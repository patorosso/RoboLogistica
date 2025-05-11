package models;

import helpers.Constantes.TipoCofre;

public class CofreSolicitud extends Cofre {
	

	public CofreSolicitud() {
		
	}

	@Override
	public TipoCofre getTipo() {
		return TipoCofre.SOLICITUD;
	}

}
