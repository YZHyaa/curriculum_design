package tool;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolUtils {

    public static List<String> generateInfo(String username) throws IOException {
        List<String> pwdList = getPwdListByDFS();
        List<String> infoList = new ArrayList<>();

        for (int i = 0; i < pwdList.size(); i++) {
            String info = username + ":"+ pwdList.get(i);
            infoList.add(info);
        }
        return infoList;
    }

    private static List<String> getPwdListByFile() throws IOException {

        File file = new File("client02/src/tool/pwdLib.txt");
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);

        ArrayList<String> pwdList = new ArrayList<>();
        String pwd;
        while ((pwd = bufferedReader.readLine()) != null) {
            pwdList.add(pwd);
        }

        bufferedReader.close();
        reader.close();

        return pwdList;
    }

    // 暴力枚举
    private static List<String> getPwdListByDFS() {
        List<String> pwdList = new ArrayList<>();
        generate(0, "", 6, pwdList);
        return pwdList;
    }

    private static void generate(int level, String s, int n, List<String> res) {
        if (level == n) {
            res.add(s);
            return;
        }

        for (int i = 0; i <= 9; i++) {
            generate(level + 1, s + i, n, res);
        }
    }

    public static Map<String,Object> recvServerInfo() throws IOException {
        DatagramSocket socket = new DatagramSocket(1234);
        DatagramPacket packet = new DatagramPacket(new byte[256], 256);
        System.out.println("等待客户端请求...");
        socket.receive(packet);

        String serverInfo = new String(packet.getData());
        String[] split = serverInfo.split(":");
        HashMap<String, Object> infoMap = new HashMap<>();
        infoMap.put("ip",split[0]);
        infoMap.put("port",Integer.valueOf(split[1]));
        infoMap.put("username",split[2].trim());

        socket.close();
        return infoMap;
    }

    public static void sendPwd(String pwd) throws IOException {
        DatagramSocket socket = new DatagramSocket(1234);
        DatagramPacket packet = new DatagramPacket(pwd.getBytes(), pwd.length(), InetAddress.getByName("localhost"), 6666);
        socket.send(packet);
        System.out.println("密码已向客户端发送！");
        socket.close();
    }
}
