package server;

import server.handler.ServerHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;

/**
 * Created by GaryLee on 2018-08-20 22:27.
 */
public class ServerThread extends Thread{
    private MyRequest request;
    private MyResponse response;
    private Socket socket;
    private ServerSocket serverSocket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public MyRequest getRequest() {
        return request;
    }

    public void setRequest(MyRequest request) {
        this.request = request;
    }

    public MyResponse getResponse() {
        return response;
    }

    public void setResponse(MyResponse response) {
        this.response = response;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        this.setRequest(new MyRequest(this.socket));
        this.setResponse(new MyResponse(this.request));
    }
}
