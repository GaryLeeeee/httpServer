package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GaryLee on 2018-08-19 20:26.
 */
public class MyRequest {
    private Map<String,String> headersMap;//headers
    private BufferedReader bufferedReader;//字符输入流
    private String method;//请求方法(get/post)
    private String uri;//请求资源
    private Socket socket;//客户端socket
    private PrintStream printStream;//字节输出流
    private int contentLength;//请求长度
    private String postStr;//post请求的内容
    private String fileName;//请求文件名
    public MyRequest(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printStream = new PrintStream(socket.getOutputStream());

            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getPostStr() {
        return postStr;
    }

    public void setPostStr(String postStr) {
        this.postStr = postStr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void init(){
        try {
            headersMap = new HashMap<>();
            String str;
            while ((str = bufferedReader.readLine()) != null && !str.isEmpty()) {
                if(str.endsWith("HTTP/1.1")){
                    String[] parts = str.split(" ");
                    this.method = parts[0];
                    this.uri = parts[1];
                    System.out.println("用户正在请求uri:"+uri);
                    this.fileName = parts[1].split("[?]")[0].substring(1);//获取文件名(取?前，以及去掉第一个/)
                }else{
                    String[] parts = str.split(": ");
                    headersMap.put(parts[0],parts[1]);//存放headers初始化
                }
//                if(str.contains("/favicon.ico"))
//                    break;
            }
            if(("POST").equalsIgnoreCase(method)){
                contentLength = Integer.parseInt(headersMap.get("Content-Length"));
                StringBuffer stringBuffer = new StringBuffer();
                for(int i=0;i<contentLength;i++){
                    stringBuffer.append((char)bufferedReader.read());
                }
                postStr = stringBuffer.toString();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String uri = "/add?a=2&b=4";
        System.out.println(uri.split("[?]")[0]);
    }
}
