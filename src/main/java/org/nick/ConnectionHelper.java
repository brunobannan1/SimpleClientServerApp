package org.nick;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHelper {
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final Socket socket;

    public ConnectionHelper(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.bufferedReader = createBufferedReader();
            this.bufferedWriter = createBufferedWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectionHelper(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.bufferedReader = createBufferedReader();
            this.bufferedWriter = createBufferedWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private BufferedWriter createBufferedWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private BufferedReader createBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void writeLine(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() {
        try {
            String line = bufferedReader.readLine();
            return line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
    }
}