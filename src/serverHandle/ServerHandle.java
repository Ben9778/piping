package serverHandle;

import Define.KeywordDefine;
import util.MessageParser;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ServerHandle{
    private final Socket socket;
    private final Map<String,PrintWriter>clients;
    public ServerHandle(Socket socket,Map<String,PrintWriter> clients){
        this.socket = socket;
        this.clients = clients;
    }
    public void serverManage(){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String message;
            int line;
            char[] buffer = new char[1024];
            while ((line = in.read(buffer)) != -1) {
                message = new String(buffer, 0, line);
                if(MessageParser.tokenParser(message)){
                    processValidateMessage(out);
                } else if (MessageParser.MethodParser(message)) {
                    processForwardMessage(out,message);
                }else {
                    processResponseMessage(message);
                }
            }
        } catch (IOException e) {
            System.err.println("客户端处理错误: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("关闭套接字时出错: " + e.getMessage());
            }
        }
    }
    protected void processValidateMessage(PrintWriter out) {
        clients.put(KeywordDefine.authToken, out);
        System.out.println("客户端" + socket.getInetAddress() + "已连接");
        out.println("连接成功");
        out.flush();
    }

    protected void processForwardMessage(PrintWriter out,String message) {
        String hostAddress = socket.getInetAddress().getHostAddress();
        clients.put(hostAddress, out);
        System.out.println("客户端" + socket.getInetAddress() + "已连接");
        PrintWriter printWriter = clients.get(KeywordDefine.authToken);
        printWriter.println(message + "hostAddress:" + hostAddress + "\r");
        printWriter.flush();
    }

    protected void processResponseMessage(String message) {
        System.out.println("收到" + socket.getInetAddress() + "的返回数据");
        String responseMessage = MessageParser.responseParser(message);
        String originAddress = MessageParser.addressParser(message);
        PrintWriter clientOut = clients.get(originAddress);
        clientOut.println(responseMessage);
        clientOut.flush();
    }
}
