package models;

import java.util.Objects;

import contracts.Posicionable;
import helpers.Posicion;

public class Nodo {
    private String id;
    private Posicionable entidad;

    public Nodo(Posicionable obj) {
        this.id = (obj instanceof Robopuerto) ? ((Robopuerto)obj).getId()
                                              : ((Cofre)obj).getId();
        this.entidad = obj;
    }

    public String getId() {
        return id;
    }

    public Posicion getPosicion() {
        return entidad.getPosicion();
    }

    public Posicionable getEntidad() {
        return entidad;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Nodo n && this.id.equals(n.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
