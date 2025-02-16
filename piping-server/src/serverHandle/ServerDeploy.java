package serverHandle;

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
            System.out.println("server started...");
            Map<String, PrintWriter> clients = new ConcurrentHashMap<>();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("client connected： " + socket.getInetAddress());
                // create a threadPool to handle the client
                ThreadPool.ThreadPoolExecute(Thread.startVirtualThread(new ServerTask(socket, clients)));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
