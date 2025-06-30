package models;

import helpers.Constantes;

public class CofreBufer extends Cofre {
    @Override
    public Constantes.TipoCofre getTipo() {
        return Constantes.TipoCofre.BUFFER;
    }
} 