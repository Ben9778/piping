package clientHandle;

import Entity.PortModel;
import util.Define;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * deal with user input
 */
public class SystemInputHandle {

    public static void InputHandle(BufferedReader userInput, PrintWriter out, PortModel portModel) throws IOException {
        String userMessage;
        System.out.println(Define.INPUT_TIP);
        while ((userMessage = userInput.readLine()) != null) {
            if (userMessage.startsWith("authToken")) {
                out.println(userMessage);
                out.flush();
            } else if (userMessage.startsWith("port")) {
                String parserPort = userMessage.split(" ")[1];
                portModel.setPort(Integer.parseInt(parserPort));
                System.out.println(Define.PIPING_TIP + portModel.getPort());
            } else if ("exit".equalsIgnoreCase(userMessage)) {
                break;
            }else {
                System.out.println(Define.ERROR_TIP);
            }

        }
    }
}
