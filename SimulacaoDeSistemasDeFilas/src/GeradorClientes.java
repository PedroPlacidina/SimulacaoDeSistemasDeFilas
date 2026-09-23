import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class GeradorClientes implements Runnable {

    public static final double JANELA_SIMULADA_S = 2 * 60 * 60; 
    public static final double CHEGADA_MIN_S = 5;
    public static final double CHEGADA_MAX_S = 50;

    private final BlockingQueue<Cliente> fila;
    private final RelogioSimulado relogio;
    private final int numeroAtendentes;
    private final Random random;

    private int totalGerado = 0;

    public GeradorClientes(BlockingQueue<Cliente> fila, RelogioSimulado relogio,
                            int numeroAtendentes, long seed) {
        this.fila = fila;
        this.relogio = relogio;
        this.numeroAtendentes = numeroAtendentes;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        int id = 0;
        while (relogio.tempoAtual() < JANELA_SIMULADA_S) {
            double intervalo = CHEGADA_MIN_S + random.nextDouble() * (CHEGADA_MAX_S - CHEGADA_MIN_S);
            relogio.dormir(intervalo);

            double agora = relogio.tempoAtual();
            if (agora >= JANELA_SIMULADA_S) break;

            id++;
            Cliente cliente = new Cliente(id, agora);
            fila.add(cliente);
        }
        totalGerado = id;

        for (int i = 0; i < numeroAtendentes; i++) {
            fila.add(new Cliente(-1, -1));
        }
    }

    public int getTotalGerado() {
        return totalGerado;
    }
}
