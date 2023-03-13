package server;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;

public class ServerThread implements Runnable{

    private Socket socket;
    private boolean exit = false;

    private boolean verified = false;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    void broadcastMsg(String msg) throws IOException {
        for (Socket socket1 : Main.sockets){
            if (socket1.equals(socket))continue;
            PrintWriter o = new PrintWriter(new OutputStreamWriter(socket1.getOutputStream()), true);
            o.println(msg);
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        String username = "";

        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            while(!verified) {
                out.println("Your username:");
                username = in.readLine();

                synchronized (Main.LOCK){
                    if (!username.equals("") && !Main.usernames.contains(username)){
                        verified = true;
                        Main.usernames.add(username);
                    }
                }

                if (!verified) out.println("Sorry that username is already in use.");
            }

            out.println("Welcome " + username + ", have fun in this chat room.");

            synchronized (Main.MSGLOCK)
            {
                for (String msg : Main.messagesHistory){
                    out.println(msg);
                }
            }

            broadcastMsg("User " + username + " has joined the chat.");

            Main.sockets.add(socket);

            while (!exit){
                String input = in.readLine();

                if (input.equals("~exit")){
                    exit = true;
                    Main.sockets.remove(socket);
                    Main.usernames.remove(username);
                    break;
                }

                String msg = Calendar.getInstance().getTime() + " | " +  username + ": " + input;

                for (String censor : Main.censored){
                    StringBuilder replacement = new StringBuilder();
                    for (int i = 0; i < censor.length(); i++){
                        if (i == 0 || i == censor.length()-1) {
                            replacement.append(censor.charAt(i));
                            continue;
                        }
                        replacement.append("*");
                    }
                    msg = msg.replaceAll(censor, replacement.toString());
                }

                synchronized (Main.MSGLOCK)
                {
                    if (Main.messagesHistory.size() == Main.MSGHISTORYSIZE)
                    {
                        Main.messagesHistory.remove(0);
                    }
                    Main.messagesHistory.add(msg);
                }
                broadcastMsg(msg);
            }

        }
        catch (IOException e){
            System.out.println("Something went wrong :(");
        } finally {
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

            if (this.socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Server closed.");
                }
            }
        }
    }
}
