package org.nick;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static int port = 9110;

    public static String parse(String req) {
        String command = req.substring(0, 3);
        System.out.println("Command = " + command);
        String resp = "";
        switch (command) {
            case "/c/":
                resp = "Connection success";
                break;
            case "/x/":
                String body = req.substring(3, req.length());
                System.out.println("body = " + body);
                resp = Parser.xParse(body);
                break;
            case "/q/":
                resp = "Connection will close now!";
                break;
            default:
                break;
        }
        return resp;
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("SERVER STARTED!");
            ConnectionHelper connectionHelper = new ConnectionHelper(serverSocket);
            while (true) {
                try {
                    String request = connectionHelper.readLine();
                    System.out.println("request = " + request);
                    String response = parse(request);
                    System.out.println("response = " + response);
                    connectionHelper.writeLine(response);
                    if(response.equals("Connection will close now!")) {
                        connectionHelper.close();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Parser {
        private static String xParse(String req) {
            String pattern = "[^-\\s:]";
            return req.replaceAll(pattern, "X");
        }
    }
}
