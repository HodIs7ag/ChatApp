package com.example.chatapp4gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // create serverSocket class 
    private final ServerSocket serverSocket;
    private static final User serverUser = new User("Server");

    // constructor of ServerSocket class
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void serverStart(){

        try{
            // check and loop the serverSocket
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("New Friend Connected");
                // implemented an object which handle runnable class
                ChatConnectionHandler userHandler = new ChatConnectionHandler(socket);

                Thread thread = new Thread(userHandler);
                thread.start();
            }
        } catch (IOException e){
            System.out.println("Error initializing server socket");
            closerServer();
        }
    }
    // this will close the server
    public void closerServer(){

        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch(IOException e){
            System.out.println("Unable to close server Socket");
        }
    }

    public static User getServerUser() {
        return serverUser;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.serverStart();

    }
}