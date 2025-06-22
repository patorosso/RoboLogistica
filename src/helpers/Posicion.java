package helpers;

public class Posicion {

	private int x;
	private int y;

	public Posicion() {
	}

	public Posicion(int x, int y) {
		this.x = x;
		this.y = y;
	}

    public double distanciaA(Posicion otra) {
        int dx = this.x - otra.x;
        int dy = this.y - otra.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
