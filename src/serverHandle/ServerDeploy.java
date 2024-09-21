package serverHandle;

import Define.KeywordDefine;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerDeploy {
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(KeywordDefine.port)) {
            System.out.println("服务器已启动，等待连接...");
            Map<String, PrintWriter> clients = new HashMap<>();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端连接： " + socket.getInetAddress());
                // 创建一个新线程来处理客户端请求
                new Thread(new ServerTask(socket, clients)).start();
            }
        } catch (IOException e) {
            System.err.println("服务器错误: " + e.getMessage());
        }
    }
}
