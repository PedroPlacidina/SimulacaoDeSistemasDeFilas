import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Atendente implements Runnable {

    public static final double SERVICO_MIN_S = 30;
    public static final double SERVICO_MAX_S = 120;

    private final BlockingQueue<Cliente> fila;
    private final RelogioSimulado relogio;
    private final List<RegistroAtendimento> registros;
    private final Random random;

    public Atendente(BlockingQueue<Cliente> fila, RelogioSimulado relogio,
                      List<RegistroAtendimento> registros, long seed) {
        this.fila = fila;
        this.relogio = relogio;
        this.registros = registros;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Cliente cliente = fila.take();
                if (cliente.getId() == -1) break; 

                double inicio = relogio.tempoAtual();
                double duracao = SERVICO_MIN_S + random.nextDouble() * (SERVICO_MAX_S - SERVICO_MIN_S);
                relogio.dormir(duracao);
                double fim = relogio.tempoAtual();

                registros.add(new RegistroAtendimento(
                        cliente.getId(), cliente.getTempoChegada(), inicio, fim));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
