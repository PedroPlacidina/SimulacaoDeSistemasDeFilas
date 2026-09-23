import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        double escala = args.length > 0 ? Double.parseDouble(args[0]) : 0.6;
        int nMin = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        int nMax = args.length > 2 ? Integer.parseInt(args[2]) : 10;
        int replicacoes = args.length > 3 ? Integer.parseInt(args[3]) : 3;
        String arquivoSaida = args.length > 4 ? args[4] : "resultados.csv";

        Simulador simulador = new Simulador(escala);
        List<ResultadoSimulacao> todos = new ArrayList<>();

        System.out.println("n | rep | gerados | atendidos | espera_media(s) | espera_max(s) | %<=120s");

        for (int n = nMin; n <= nMax; n++) {
            for (int rep = 1; rep <= replicacoes; rep++) {
                long seed = 1000L * n + rep; 
                ResultadoSimulacao r = simulador.executar(n, rep, seed);
                todos.add(r);
                System.out.printf(java.util.Locale.US,
                        "%d | %d | %d | %d | %.1f | %.1f | %.1f%n",
                        r.numeroAtendentes, r.replicacao, r.clientesGerados, r.clientesAtendidos,
                        r.esperaMedia, r.esperaMaxima, r.percentualDentroMeta);
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivoSaida))) {
            pw.println(ResultadoSimulacao.cabecalhoCsv());
            for (ResultadoSimulacao r : todos) {
                pw.println(r.toCsvLine());
            }
        }

        System.out.println("\nResultados salvos em " + arquivoSaida);
    }
}
