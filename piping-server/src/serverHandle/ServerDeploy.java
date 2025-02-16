package serverHandle;

import config.OutConfig;
import define.KeywordDefine;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to initialize and deploy the server.
 */
public class ServerDeploy {

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(KeywordDefine.port)) {
            OutConfig.out("Server started ...");
            Map<String, PrintWriter> clients = new ConcurrentHashMap<>();
            while (true) {
                Socket socket = serverSocket.accept();
                OutConfig.out("client connected： " + socket.getInetAddress());
                // create a threadPool to handle the client
                ThreadPool.ThreadPoolExecute(Thread.startVirtualThread(new ServerTask(socket, clients)));
            }
        } catch (IOException e) {
            OutConfig.out(e.getMessage());
        }
    }
}
