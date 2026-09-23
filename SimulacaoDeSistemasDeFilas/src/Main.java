import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        double escala = args.length > 0 ? Double.parseDouble(args[0]) : 0.7;
        int nMin = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        int nMax = args.length > 2 ? Integer.parseInt(args[2]) : 6;
        int replicacoes = args.length > 3 ? Integer.parseInt(args[3]) : 5;

        Simulador simulador = new Simulador(escala);
        List<ResultadoSimulacao> todos = new ArrayList<>();

        for (int n = nMin; n <= nMax; n++) {
            for (int rep = 1; rep <= replicacoes; rep++) {
                long seed = 1000L * n + rep;
                ResultadoSimulacao r = simulador.executar(n, rep, seed);
                todos.add(r);
                System.out.printf(Locale.US,
                        "n=%d rep=%d atendidos=%d espera_media=%.1fs espera_max=%.1fs dentro_meta=%.1f%%%n",
                        r.numeroAtendentes, r.replicacao, r.clientesAtendidos,
                        r.esperaMedia, r.esperaMaxima, r.percentualDentroMeta);
            }
        }

        imprimirResumo(todos, nMin, nMax);
    }

    private static void imprimirResumo(List<ResultadoSimulacao> todos, int nMin, int nMax) {
        System.out.println("\nRESUMO POR NUMERO DE ATENDENTES (media das replicacoes)");
        System.out.printf(Locale.US, "%3s %13s %11s %7s%n", "n", "esp.media(s)", "esp.max(s)", "%meta");

        int nRecomendado = -1;
        for (int n = nMin; n <= nMax; n++) {
            double somaEspera = 0, maxEspera = 0, somaPctMeta = 0;
            int qtde = 0;
            boolean todasDentroDaMeta = true;

            for (ResultadoSimulacao r : todos) {
                if (r.numeroAtendentes != n) continue;
                somaEspera += r.esperaMedia;
                if (r.esperaMaxima > maxEspera) maxEspera = r.esperaMaxima;
                somaPctMeta += r.percentualDentroMeta;
                if (r.percentualDentroMeta < 100.0) todasDentroDaMeta = false;
                qtde++;
            }

            System.out.printf(Locale.US, "%3d %13.2f %11.2f %6.1f%%%n",
                    n, somaEspera / qtde, maxEspera, somaPctMeta / qtde);

            if (nRecomendado == -1 && todasDentroDaMeta) {
                nRecomendado = n;
            }
        }

        System.out.println("\nNumero de atendentes recomendado: n = " + nRecomendado);
    }
}