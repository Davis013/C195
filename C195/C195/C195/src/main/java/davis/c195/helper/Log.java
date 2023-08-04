package davis.c195.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public class Log{

    private static final String FILENAME = "log.txt";

    public Log() {}

    public static void log (String username, boolean success) {

        try ( FileWriter fw = new FileWriter ("log.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)) {
        pw.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure" ));
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }

    }
}

