package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer implements Runnable {
	private ServerSocket serverSocket = null;

	public HttpServer() throws IOException {
		serverSocket = new ServerSocket(4399);
		System.out.println("4399�˿ڼ���ing����");
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println("-----��������------");
				// System.out.println(bufferedReader.readLine());
				String content;
				while ((content = bufferedReader.readLine()) != null && !content.isEmpty()) {
					System.out.println(content);
				}
				PrintWriter pw = new PrintWriter(socket.getOutputStream());

				pw.println("HTTP/1.1 200 OK");
				pw.println("Content-type:text/html");
				pw.println();
				pw.println("<h1>���ʳɹ���</h1>");
				pw.println("<form method='post' action='/login'>");
				pw.println("�˺�:<input type='text' name='user'><br>");
				pw.println("����:<input type='password' name='password'><br>");
				pw.println("<input type='submit' value='��¼(post����)'>");
				pw.println("</form>");
				pw.println("<form method='get' action='/search'>");
				pw.println("<input type='text' name='id'><br>");
				pw.println("<input type='submit' value='����(get����)'>");
				pw.println("</form>");
				// pw.println("<form method='post' action='/login'
				// enctype='multipart/form-data'>");
				// pw.println("<input type='file' name='file'><br>");
				// pw.println("<input type='submit' value='�ļ��ϴ�'>");
				// pw.println("</form>");
				pw.println("<a href='test.gif'>ͼƬ����</a>");
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
