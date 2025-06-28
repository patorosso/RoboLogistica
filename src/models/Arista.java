package models;

public class Arista {
    private String destino;
    private double peso;

    public Arista(String destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return " → " + destino + " (" + peso + ")";
    }
}
