package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static final int PORT = 9000;

    public static boolean verified = false;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Thread receiveThread = null;

        boolean exit = false;

        try{
            socket = new Socket("127.0.0.1", PORT);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            Scanner scanner = new Scanner(System.in);

            String received = null;

            while(!verified) {
                received = in.readLine();
                System.out.println(received);
                out.println(scanner.nextLine());
                received = in.readLine();

                if (received.startsWith("Welcome ")) {
                    verified = true;
                }

                System.out.println(received);
            }

            receiveThread = new Thread(new ClientThread(in));
            receiveThread.start();

            while(!exit) {
                String msg = scanner.nextLine();
                if (msg.equals("~exit"))exit = true;
                else
                    out.println(msg);
            }
        }
        catch(IOException e)
        {
            System.out.println("There has been an error with the server, sorry :(");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("Server closed.");
                }
            }

            if (out != null) {
                out.close();
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Server closed.");
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Server closed.");
                }
            }
        }
    }
}
