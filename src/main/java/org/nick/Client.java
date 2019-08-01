package org.nick;

public class Client {
    private static int port = 9110;
    private static String ip = "127.0.0.1";

    public static void main(String[] args) {
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        ConnectionHelper connectionHelper = new ConnectionHelper(ip, port);
        int i = 0;
        while (true) {
            String request = "/c/";
            System.out.println("request = " + request);
            connectionHelper.writeLine(request);
            String response = connectionHelper.readLine();
            System.out.println("response = " + response);
            String request1 = "/x/ACCV:CCER LLSE-PPEW";
            System.out.println("request = " + request1);
            connectionHelper.writeLine(request1);
            String response1 = connectionHelper.readLine();
            System.out.println("response = " + response1);
            i++;
            if(i == 10) {
                String request2 = "/q/";
                System.out.println("request = " + request2);
                connectionHelper.writeLine(request2);
                String response2 = connectionHelper.readLine();
                System.out.println("response = " + response2);
            }
        }
    }
}