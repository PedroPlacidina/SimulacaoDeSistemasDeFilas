public class ResultadoSimulacao {

    public final int numeroAtendentes;
    public final int replicacao;
    public final int clientesGerados;
    public final int clientesAtendidos; 
    public final double esperaMedia;
    public final double esperaMaxima;
    public final double atendimentoMedio;
    public final double atendimentoMaximo;
    public final double leadTimeMedio;
    public final double percentualDentroMeta; 

    public ResultadoSimulacao(int numeroAtendentes, int replicacao, int clientesGerados,
                               int clientesAtendidos, double esperaMedia, double esperaMaxima,
                               double atendimentoMedio, double atendimentoMaximo,
                               double leadTimeMedio, double percentualDentroMeta) {
        this.numeroAtendentes = numeroAtendentes;
        this.replicacao = replicacao;
        this.clientesGerados = clientesGerados;
        this.clientesAtendidos = clientesAtendidos;
        this.esperaMedia = esperaMedia;
        this.esperaMaxima = esperaMaxima;
        this.atendimentoMedio = atendimentoMedio;
        this.atendimentoMaximo = atendimentoMaximo;
        this.leadTimeMedio = leadTimeMedio;
        this.percentualDentroMeta = percentualDentroMeta;
    }
}