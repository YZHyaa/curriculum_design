package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public void start() throws IOException {
        String serverInfo = input();
        useTool(serverInfo);
        String pwd = serverInfo.split(":")[2] + ":" + recvPwd();
        login(pwd);
    }

    public String input() {
        String serverInfo = "";
        System.out.print("服务器IP：");
        Scanner in = new Scanner(System.in);
        serverInfo = serverInfo + in.nextLine() + ":";
        System.out.print("端口：");
        serverInfo = serverInfo + in.nextLine() + ":";
        System.out.print("账号：");
        serverInfo = serverInfo + in.next();
        return serverInfo;
    }

    public void useTool(String serverInfo) throws IOException {
        DatagramSocket socket = new DatagramSocket(6666);
        DatagramPacket packet = new DatagramPacket(serverInfo.getBytes(), serverInfo.length(),
                InetAddress.getByName("localhost"), 1234);
        socket.send(packet);
        System.out.println("等待破解密码中...");

        socket.close();
    }

    public String recvPwd () throws IOException {
        DatagramSocket socket = new DatagramSocket(6666);
        DatagramPacket packet = new DatagramPacket(new byte[32], 32);
        socket.receive(packet);
        if (new String(packet.getData()).trim().isEmpty()) {
            System.out.println("破解失败。。。");
            System.exit(0);
        }
        String pwd = new String(packet.getData());
        System.out.println("获取到破解密码：" + pwd);
        return pwd;
    }

    public void login(String pwd) throws IOException {
        Socket client = new Socket("127.0.0.1", 8080);
        System.out.println("尝试连接...");
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(pwd.trim());
        printWriter.flush();

        InputStream inputStream = client.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        if (Integer.valueOf(bufferedReader.readLine()) == 1 ) {
            System.out.println("连接成功！");
        } else {
            System.out.println("连接失败！");
        }

        printWriter.close();
        outputStream.close();
        bufferedReader.close();
        inputStream.close();
        client.close();
    }
}
