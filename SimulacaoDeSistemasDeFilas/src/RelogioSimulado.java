import java.util.concurrent.TimeUnit;

public class RelogioSimulado {

    private final long inicioNano;
    private final double escalaMsPorSegundoSimulado;

    public RelogioSimulado(double escalaMsPorSegundoSimulado) {
        this.escalaMsPorSegundoSimulado = escalaMsPorSegundoSimulado;
        this.inicioNano = System.nanoTime();
    }

    public double tempoAtual() {
        long decorridoNano = System.nanoTime() - inicioNano;
        double decorridoMs = decorridoNano / 1_000_000.0;
        return decorridoMs / escalaMsPorSegundoSimulado;
    }

    public void dormir(double segundosSimulados) {
        long ms = Math.round(segundosSimulados * escalaMsPorSegundoSimulado);
        try {
            if (ms > 0) TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}