package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable{

    private BufferedReader in;
    private boolean exit = false;

    public ClientThread(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        while(!exit){
            try {
                System.out.println(in.readLine());
            } catch (IOException e) {
                System.out.println("Chat closed");
                exit = true;
            }
        }
    }
}
