package server;

import server.utils.Config;
import server.utils.LoginUtil;

import java.io.*;
import java.net.Socket;

/**
 * Created by GaryLee on 2018-08-19 20:26.
 */
public class MyResponse {
    private String uri;
    private PrintStream printStream;
    private MyRequest request;
    private Socket socket;
    private String fileName;
    private String postStr;

    public MyResponse(MyRequest request) {
        this.request = request;
        this.socket = request.getSocket();
        this.printStream = request.getPrintStream();
        this.uri = request.getUri();
        this.fileName = request.getFileName();
        this.postStr = request.getPostStr();

        init();

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public MyRequest getRequest() {
        return request;
    }

    public void setRequest(MyRequest request) {
        this.request = request;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void init(){
        //判断如果为form的post请求，则验证用户真实性并返回
        if("/login".equals(uri)&&postStr!=null){
            System.out.println("用户post请求为:"+postStr);
            if(LoginUtil.login(postStr)){
                fileName = "success.html";
            }
            else {
                fileName = "fail.html";
            }
        }else if("/upload".equals(uri)){

        }
        File file = new File(Config.staticPath+"\\"+fileName);
        if(file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] bytes = new byte[(int) file.length()];
                this.printStream.println("HTTP/1.1 200 OK");
//                this.printStream.println("charset=utf-8");//加上后无法解析图片
                this.printStream.println("");
                while ((fileInputStream.read(bytes)) != -1) {

                    printStream.write(bytes);
                }
//                this.printStream.flush();
                if(socket != null)
                    socket.close();//没有这一步网页将一直刷新
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("静态资源"+uri+"不存在!");
            this.printStream.println("HTTP/1.1 404 fileNotFound");
            this.printStream.println("Content-type:text/html;charset=utf-8");
            this.printStream.println("");
            this.printStream.println("<h1>未能找到指定的资源!请重试!</h1>");
            try {
                if(socket != null)
                    socket.close();//没有这一步网页将一直刷新
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

    }
}
