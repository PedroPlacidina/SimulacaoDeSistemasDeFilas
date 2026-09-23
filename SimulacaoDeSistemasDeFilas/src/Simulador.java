import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class Simulador {

    public static final double META_ESPERA_S = 120.0; 

    private final double escalaMsPorSegundoSimulado;

    public Simulador(double escalaMsPorSegundoSimulado) {
        this.escalaMsPorSegundoSimulado = escalaMsPorSegundoSimulado;
    }

    public ResultadoSimulacao executar(int numeroAtendentes, int replicacao, long seedBase) throws InterruptedException {
        BlockingQueue<Cliente> fila = new LinkedBlockingQueue<>();
        List<RegistroAtendimento> registros = Collections.synchronizedList(new ArrayList<>());
        RelogioSimulado relogio = new RelogioSimulado(escalaMsPorSegundoSimulado);

        GeradorClientes gerador = new GeradorClientes(fila, relogio, numeroAtendentes, seedBase);
        Thread threadGerador = new Thread(gerador, "Gerador");

        Thread[] atendentes = new Thread[numeroAtendentes];
        for (int i = 0; i < numeroAtendentes; i++) {
            Atendente a = new Atendente(fila, relogio, registros, seedBase + i + 1);
            atendentes[i] = new Thread(a, "Atendente-" + (i + 1));
        }

        threadGerador.start();
        for (Thread t : atendentes) t.start();

        threadGerador.join();
        for (Thread t : atendentes) t.join();

        return agregar(numeroAtendentes, replicacao, gerador.getTotalGerado(), registros);
    }

    private ResultadoSimulacao agregar(int n, int replicacao, int clientesGerados,
                                        List<RegistroAtendimento> registros) {
        int atendidos = registros.size();
        double somaEspera = 0, maxEspera = 0;
        double somaAtendimento = 0, maxAtendimento = 0;
        double somaLead = 0;
        int dentroMeta = 0;

        for (RegistroAtendimento r : registros) {
            double espera = r.espera();
            double atendimento = r.duracaoAtendimento();
            somaEspera += espera;
            somaAtendimento += atendimento;
            somaLead += r.leadTime();
            if (espera > maxEspera) maxEspera = espera;
            if (atendimento > maxAtendimento) maxAtendimento = atendimento;
            if (espera <= META_ESPERA_S) dentroMeta++;
        }

        double esperaMedia = atendidos > 0 ? somaEspera / atendidos : 0;
        double atendimentoMedio = atendidos > 0 ? somaAtendimento / atendidos : 0;
        double leadMedio = atendidos > 0 ? somaLead / atendidos : 0;
        double pctMeta = atendidos > 0 ? (100.0 * dentroMeta / atendidos) : 0;

        return new ResultadoSimulacao(n, replicacao, clientesGerados, atendidos,
                esperaMedia, maxEspera, atendimentoMedio, maxAtendimento, leadMedio, pctMeta);
    }
}
