package com.ecsail.connection;

import java.io.IOException;

public class Mail {

    public static void composeEmail(String receiver, String subject, String body)  {
        //Generating mailto-URI. Subject and body (message) has to encoded.
        String mailto = "mailto:" + receiver;
        mailto += "?subject=" + uriEncode(subject);
        mailto += "&body=" + uriEncode(body);

        //Create OS-specific run command
        String cmd = "";
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            cmd = "cmd.exe /c start " + mailto;
        }
        else if (os.contains("mac os x")){
            cmd = "open " + mailto;
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){
            cmd = "xdg-open " + mailto;
        }
        //Call default mail client with paramters
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String uriEncode(String in) {
        String out = "";
        for (char ch : in.toCharArray()) {
            out += Character.isLetterOrDigit(ch) ? ch : String.format("%%%02X", (int)ch);
        }
        return out;
    }
}
