public class Cliente {

    private final int id;
    private final double tempoChegada; 

    public Cliente(int id, double tempoChegada) {
        this.id = id;
        this.tempoChegada = tempoChegada;
    }

    public int getId() {
        return id;
    }

    public double getTempoChegada() {
        return tempoChegada;
    }
}
