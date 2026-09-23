import java.util.Random;

public class GeradorClientes implements Runnable {

    public static final double JanelaSimulada = 2 * 60 * 60;
    public static final double ChegadaMin = 5;
    public static final double ChegadaMax = 50;

    private final FilaClientes fila;
    private final RelogioSimulado relogio;
    private final Random random;

    private int totalGerado = 0;

    public GeradorClientes(FilaClientes fila, RelogioSimulado relogio, long seed) {
        this.fila = fila;
        this.relogio = relogio;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        int id = 0;
        while (relogio.tempoAtual() < JanelaSimulada) {
            double intervalo = ChegadaMin + random.nextDouble() * (ChegadaMax - ChegadaMin);
            relogio.dormir(intervalo);

            double agora = relogio.tempoAtual();
            if (agora >= JanelaSimulada) break;

            id++;
            fila.colocarCliente(new Cliente(id, agora));
        }
        totalGerado = id;
        fila.encerrar(); 
    }

    public int getTotalGerado() {
        return totalGerado;
    }
}