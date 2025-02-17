package Request;

import util.Define;

import java.net.Socket;
import java.io.IOException;

/**
 * request local server,and receive response
 */
public class RequestExecutor {

    public static String executor(String body, int port) {
        assert body != null;
        String response = null;
        try (Socket socket = new Socket(Define.HOST, port)) {
            socket.getOutputStream().write(body.getBytes());
            socket.shutdownOutput();
            //receive response
            byte[] buffer = new byte[1024];
            int len = socket.getInputStream().read(buffer);
            if (len != -1) {
                response = new String(buffer, 0, len);
            }
        } catch (IOException e) {
            System.err.println("request error: " + e.getMessage());
        }
        return response;
    }
}
