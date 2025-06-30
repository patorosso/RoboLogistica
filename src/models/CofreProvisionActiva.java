package models;

import helpers.Constantes;

public class CofreProvisionActiva extends Cofre {
    @Override
    public Constantes.TipoCofre getTipo() {
        return Constantes.TipoCofre.PROVISION_ACTIVA;
    }
} 