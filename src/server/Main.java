package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static final int PORT = 9000;

    public static ArrayList<String> usernames = new ArrayList<>();
    public static ArrayList<Socket> sockets = new ArrayList<>();

    public static ArrayList<String> messagesHistory = new ArrayList<>();
    public static int MSGHISTORYSIZE = 100;

    public static ArrayList<String> censored = new ArrayList<>();

    public static final Object LOCK = new Object();
    public static final Object MSGLOCK = new Object();

    public static void main(String[] args) {
        //add censored words here
        censored.add("parolaccia");
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            while(true){
                System.out.println("Server waiting for connections");
                Socket socket = serverSocket.accept();
                System.out.println("Server accepted a connection");
                Thread serverThread = new Thread(new ServerThread(socket));
                serverThread.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
