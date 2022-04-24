package wordsearcher.server;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Server {

    static ServerSocket server;
    static Socket con;
    public static List<String> lines;
    public static List<Integer> indices;

    public static void main(String[] args) throws IOException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("Server started");
            }
        });
        String filePath = args[0];
        lines = WordSearcher.readLines(filePath);
        serverConnection();
    }

    private static void serverConnection() throws IOException {
        server = new ServerSocket(8080);

        con = server.accept();
        while (true) {
            try {
                DataInputStream input = new DataInputStream(con.getInputStream());
                String string = input.readUTF();
                System.out.println("Client: " + string);
                indices = WordSearcher.findIndices(string, lines);
                try {
                    DataOutputStream output = new DataOutputStream(con.getOutputStream());
                    String serverResponse = indices.stream().map(Object::toString)
                            .collect(Collectors.joining("\n"));
                    output.writeUTF(serverResponse);
                } catch (IOException e1) {
                    try {
                        Thread.sleep(2000);
                        System.exit(0);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception ev) {
                try {
                    Thread.sleep(2000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
