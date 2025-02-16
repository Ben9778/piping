package serverHandle;

import define.KeywordDefine;
import util.MessageParser;
import util.ValidateToken;

import java.io.*;
import java.net.Socket;
import java.util.Map;
/**
 * this class is used to handle the socket connection and process the message from client
 */
public class ServerHandle{
    private final Socket socket;
    private final Map<String,PrintWriter>clients;
    public ServerHandle(Socket socket,Map<String,PrintWriter> clients){
        this.socket = socket;
        this.clients = clients;
    }
    /**
     * receive the message from client and process it
     */
    public void serverManage(){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String message;
            int line;
            char[] buffer = new char[1024];
            while ((line = in.read(buffer)) != -1) {
                message = new String(buffer, 0, line);
                if(MessageParser.tokenParser(message)){
                    if(ValidateToken.isValid(message)){
                        processValidateMessage(out);
                    }else {
                       out.println("authToken Error");
                       out.flush();
                    }
                } else if (MessageParser.MethodParser(message)) {
                    processForwardMessage(out,message);
                }else {
                    processResponseMessage(message);
                }
            }
        } catch (IOException e) {
            System.err.println("client handle error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("close socket error: " + e.getMessage());
            }
        }
    }
    /**
     * if message from client contains the authToken values,
     * then add the client to the clients map and send the success message to the client
     * @param out the output stream of the socket
     */
    protected void processValidateMessage(PrintWriter out) {
        clients.put(KeywordDefine.authToken, out);
        System.out.println("client" + socket.getInetAddress() + "connected");
        out.println("connect success");
        out.flush();
    }

    /**
     *  if message from client contains the method GET or POST or PUT or DELETE ,
     *  then add the client to the clients map and forward the request message to
     *  the authToken client
     *  @param out the output stream of the socket
     *  @param message the message from client
     */
    protected void processForwardMessage(PrintWriter out,String message) {
        assert message != null;
        String hostAddress = socket.getInetAddress().getHostAddress();
        clients.put(hostAddress, out);
        System.out.println("client" + socket.getInetAddress() + "connected");
        PrintWriter printWriter = clients.get(KeywordDefine.authToken);
        printWriter.println(message + "hostAddress:" + hostAddress + "\r");
        printWriter.flush();
    }
    /**
     * if message from client is not contains the authToken message and not the method message,
     * then find the client by the originAddress and send the response message to the client
     * @param message the message from client
     */
    protected void processResponseMessage(String message) {
        assert message != null;
        System.out.println("receive" + socket.getInetAddress() + "message");
        String responseMessage = MessageParser.responseParser(message);
        String originAddress = MessageParser.addressParser(message);
        PrintWriter clientOut = clients.get(originAddress);
        clientOut.println(responseMessage);
        clientOut.flush();
    }
}
