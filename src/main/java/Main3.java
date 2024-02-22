import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author chenxin
 * @since 2024/2/22 星期四 13:30
 */
public class Main3 {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8888;

        try {
            // 连接到服务器
            Socket socket = new Socket(serverAddress, serverPort);

            // 获取输入输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // 向服务器发送消息
            String messageToSend = "Hello from client!";
            writer.println(messageToSend);
            System.out.println("已发送消息给服务器: " + messageToSend);

            // 读取服务器的回应
            String serverResponse = reader.readLine();
            System.out.println("服务器回应: " + serverResponse);

            // 关闭连接
            reader.close();
            writer.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
