import java.util.LinkedList;
import java.util.Queue;

public class FilaClientes {

    private final Queue<Cliente> clientes;
    private boolean encerrada;

    public FilaClientes() {
        clientes = new LinkedList<>();
    }
    
    public synchronized Cliente retirarCliente() throws InterruptedException {
        while (clientes.isEmpty()) {
            if (encerrada) {
                return null;
            }
            wait();
        }
        return clientes.poll();
    }

    public synchronized void colocarCliente(Cliente cliente) {
        clientes.add(cliente);
        notifyAll(); 
    }

    public synchronized void encerrar() {
        encerrada = true;
        notifyAll();
    }
}