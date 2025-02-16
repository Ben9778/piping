package config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

public class OutConfig {
    private static final String fileName="./piping-server.log";
    public static void out(String s){
        PrintStream printStream;
        try {
            printStream = new PrintStream(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        printStream.append(outFormat(s)).append("\r\n");
        printStream.flush();
        printStream.close();
    }
    public static String outFormat(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(System.currentTimeMillis());
        return format+" "+s;
    }
}