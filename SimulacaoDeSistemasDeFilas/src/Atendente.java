import java.util.List;
import java.util.Random;

public class Atendente implements Runnable {

    public static final double ServicoMin = 30;
    public static final double ServicoMax = 120;

    private final FilaClientes fila;
    private final RelogioSimulado relogio;
    private final List<RegistroAtendimento> registros;
    private final Random random;

    public Atendente(FilaClientes fila, RelogioSimulado relogio,
                      List<RegistroAtendimento> registros, long seed) {
        this.fila = fila;
        this.relogio = relogio;
        this.registros = registros;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            Cliente cliente;
            while ((cliente = fila.retirarCliente()) != null) {
                double inicio = relogio.tempoAtual();
                double duracao = ServicoMin + random.nextDouble() * (ServicoMax - ServicoMin);
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