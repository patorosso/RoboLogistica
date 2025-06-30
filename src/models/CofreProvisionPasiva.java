package models;

import helpers.Constantes;

public class CofreProvisionPasiva extends Cofre {
    @Override
    public Constantes.TipoCofre getTipo() {
        return Constantes.TipoCofre.PROVISION_PASIVA;
    }
} 