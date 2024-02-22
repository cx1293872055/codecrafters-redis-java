import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chenxin
 * @since 2024/2/22 星期四 13:26
 */
public class Main2 {
    public static void main(String[] args) {
        int port = 8888;

        try {
            // 创建ServerSocket并绑定到指定端口
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("等待客户端连接...");

            // 接受客户端连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端已连接：" + clientSocket.getInetAddress().getHostAddress());

            // 获取输入输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // 读取客户端发送的消息
            String clientMessage = reader.readLine();
            System.out.println("客户端消息: " + clientMessage);

            // 向客户端发送回应
            String responseMessage = "Hello from server!";
            writer.println(responseMessage);
            System.out.println("已发送回应给客户端: " + responseMessage);

            // 关闭连接
            reader.close();
            writer.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
