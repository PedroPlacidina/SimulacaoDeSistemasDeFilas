public class RegistroAtendimento {

    public final int clienteId;
    public final double tempoChegada;
    public final double inicioAtendimento;
    public final double fimAtendimento;

    public RegistroAtendimento(int clienteId, double tempoChegada,
                                double inicioAtendimento, double fimAtendimento) {
        this.clienteId = clienteId;
        this.tempoChegada = tempoChegada;
        this.inicioAtendimento = inicioAtendimento;
        this.fimAtendimento = fimAtendimento;
    }

    public double espera() {
        return inicioAtendimento - tempoChegada;
    }

    public double duracaoAtendimento() {
        return fimAtendimento - inicioAtendimento;
    }

    public double leadTime() {
        return fimAtendimento - tempoChegada;
    }
}