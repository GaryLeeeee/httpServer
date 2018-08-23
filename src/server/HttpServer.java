package server;

import server.handler.ServerHandler;
import server.utils.Config;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by GaryLee on 2018-08-19 20:26.
 */
public class HttpServer{
    private static ServerSocket serverSocket;
    private static BufferedReader bufferedReader;
    private static PrintStream printStream;
    private static Socket socket;
    public HttpServer(){
        try {
            serverSocket = new ServerSocket(Config.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void receive(){
//        try {
//            serverSocket = new ServerSocket(Config.port);
//            System.out.println(Config.port+"端口监听ing");
//            new ServerThread(serverSocket).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            socket = serverSocket.accept();
            System.out.println("achieve...");
            new ServerThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        System.out.println(Config.port+"端口监听ing...");
        while(true){
            httpServer.receive();
        }
    }
}
