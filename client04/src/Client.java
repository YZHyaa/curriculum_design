import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client  {

    private Socket client = null;

    private static String name = Client.class.getName();
    private static Logger log = Logger.getLogger(name);

    public void start() throws IOException {
        Map<String, Object> input = input();
        client = new Socket((String)input.get("ip"), (Integer)input.get("port"));
        log.info("客户端启动（quit退出）");
        if (login((String)input.get("token"))) {
            log.info("登录成功！");
        } else {
            log.info("登录失败！");
            return;
        }

        sendMsg();
    }

    public Map<String,Object> input() {
        HashMap<String, Object> map = new HashMap<>();

        Scanner in = new Scanner(System.in);
        System.out.print("服务器IP：");
        map.put("ip",in.next());
        System.out.print("服务器端口：");
        map.put("port",in.nextInt());
        System.out.print("口令：");
        map.put("token",in.next());

        return map;
    }

    public Boolean login(String token) throws IOException {
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(token);
        printWriter.flush();
        log.info("账号口令已发送");

        boolean state = false;
        InputStream inputStream = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        if (Integer.valueOf(reader.readLine()) == 1 ) {
            state = true;
        }

        return state;
    }

    public void sendMsg() throws IOException {
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("客户端：");
            String msg = in.nextLine();
            printWriter.println(msg);
            printWriter.flush();
            if (msg.equalsIgnoreCase("quit")) {
                log.info("客户端退出。。。");
                break;
            }
        }
        printWriter.close();
        outputStream.close();
    }
}
