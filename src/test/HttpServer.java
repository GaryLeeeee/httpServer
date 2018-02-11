package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer implements Runnable {
	private ServerSocket serverSocket = null;
	String uri = null;
	String method = null;
	public HttpServer() throws IOException {
		serverSocket = new ServerSocket(4399);
		System.out.println("4399端口监听ing……");
		new Thread(this).start();
	}
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println("");
				// System.out.println(bufferedReader.readLine());
				String content;
				int contentLength = 0;
				//输出request
				while ((content = bufferedReader.readLine()) != null && !content.isEmpty()) {
					//获取方法和资源
					if(content.endsWith("HTTP/1.1")){
						String[] parts = content.split(" ");
						method = parts[0];
						uri = parts[1];
					}
					//获取"POST"请求时的参数长度
					if(content.startsWith("Content-Length")){
						contentLength = Integer.parseInt(content.substring("Content-Length: ".length()));
					}
					
					System.out.println(content);
				}
				System.out.println("请求结束!");
				System.out.format("用户请求的method为'%s',uri为'%s'\r\n",method,uri);
				StringBuffer sb=new StringBuffer();
				//获取并输出"POST"的参数内容
				if ("POST".equalsIgnoreCase(method)) {  
                    for (int i = 0; i < contentLength; i++) {
                        sb.append((char)bufferedReader.read());
                    }
					System.out.println("POST参数是："+sb.toString());
                } 
				//response
				PrintWriter pw = new PrintWriter(socket.getOutputStream());

				pw.println("HTTP/1.1 200 OK");
				pw.println("Content-type:text/html");
				pw.println();
				pw.println("<h1>访问成功！</h1>");
				pw.println("<form method='post' action='/login'>");
				pw.println("账号:<input type='text' name='user'><br>");
				pw.println("密码:<input type='password' name='password'><br>");
				pw.println("<input type='submit' value='登录(post测试)'>");
				pw.println("</form>");
				pw.println("<form method='get' action='/search'>");
				pw.println("<input type='text' name='id'><br>");
				pw.println("<input type='submit' value='搜索(get测试)'>");
				pw.println("</form>");
				// pw.println("<form method='post' action='/login'
				// enctype='multipart/form-data'>");
				// pw.println("<input type='file' name='file'><br>");
				// pw.println("<input type='submit' value='文件上传'>");
				// pw.println("</form>");
				pw.println("<a href='test.gif'>图片测试</a>");
				pw.flush();
				socket.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new HttpServer();
	}

}
