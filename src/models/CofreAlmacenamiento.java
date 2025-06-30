package models;

import helpers.Constantes;

public class CofreAlmacenamiento extends Cofre {
    @Override
    public Constantes.TipoCofre getTipo() {
        return Constantes.TipoCofre.ALMACENAMIENTO;
    }
} 