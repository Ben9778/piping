package clientHandle;

import Entity.PortModel;
import util.Define;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * deal with server message and user input
 */
public class Handle {
    private final PortModel portModel;

    public Handle(PortModel portModel) {
        this.portModel = portModel;
    }

    public void clientManage() {
        try (Socket socket = new Socket(Define.HOST, Define.PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            // start client task receive message from server
            new Thread(new ServerHandleTask(in, out, portModel)).start();
            //deal with user input
            SystemInputHandle.InputHandle(userInput, out, portModel);
        } catch (IOException e) {
            System.err.println("connection error: " + e.getMessage());
        }
    }
}
