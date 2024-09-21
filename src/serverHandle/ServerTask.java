package serverHandle;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ServerTask implements Runnable {
    private final Socket socket;
    private final Map<String, PrintWriter> clients;

    public ServerTask(Socket socket, Map<String, PrintWriter> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        ServerHandle serverHandle = new ServerHandle(socket, clients);
        serverHandle.serverManage();
    }
}

