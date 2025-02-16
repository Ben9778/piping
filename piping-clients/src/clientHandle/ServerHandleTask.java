package clientHandle;

import Entity.PortModel;
import Request.RequestExecutor;
import util.Define;
import util.MessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * this task is to handle the server response and process the request if it is a
 * GET or POST or PUT or DELETE request.
 */
public class ServerHandleTask implements Runnable {
    private final BufferedReader in;
    private final PrintWriter out;
    private final PortModel portModel;

    public ServerHandleTask(BufferedReader in, PrintWriter out, PortModel portModel) {
        this.in = in;
        this.out = out;
        this.portModel = portModel;
    }

    @Override
    public void run() {
        try {
            this.handleServerResponse();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleServerResponse() throws IOException {
        int line;
        char[] buffer = new char[1024];
        while ((line = in.read(buffer)) != -1) {
            String serverResponse = new String(buffer, 0, line);
            if (MessageParser.MethodParser(serverResponse)) {
                processGetRequest(serverResponse);
            } else {
                System.out.println(serverResponse);
            }
        }
    }

    private void processGetRequest(String serverResponse) {
        String newServerResponse = MessageParser.replaceHost(serverResponse, portModel.getPort());
        String address = MessageParser.parserHostAddress(newServerResponse);
        String newResponse = MessageParser.getNewResponse(newServerResponse);
        String result = RequestExecutor.executor(newResponse, portModel.getPort());
        out.println(result + Define.ORIGIN_ADDRESS + address + "\r");
        out.flush();
    }
}
