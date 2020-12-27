import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Client {
    private static String name = Client.class.getName();
    private static Logger log = Logger.getLogger(name);

    private static final Integer REJECT_IP = -2;
    private static final Integer REJECT_ACCOUNT = -1;
    private static final Integer ERROR_ACCOUNT = 0;
    private static final Integer SUCCESS = 1;

    public void start() {
        Map<String, Object> input = input();
        Socket client = null;
        try {
            client = new Socket((String)input.get("ip"),(Integer)input.get("port"));
        } catch (IOException e) {
            log.info("连接失败！IP或端口可能错误");
            return;
        }
        log.info("客户端 " + client.getInetAddress().getHostAddress() + " 启动");

        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        try {
            outputStream = client.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.println((String)input.get("account"));
            printWriter.flush();
            log.info("请求连接中...");

            getConnectCode(client);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            printWriter.close();
            outputStream.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,Object> input() {
        HashMap<String, Object> map = new HashMap<>();

        Scanner in = new Scanner(System.in);
        System.out.print("服务器IP：");
        map.put("ip",in.next());
        System.out.print("服务器端口：");
        map.put("port",in.nextInt());
        System.out.print("账号：");
        map.put("account",in.next());

        return map;
    }

    public void getConnectCode(Socket client) throws IOException {
        InputStream inputStream = client.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int code = Integer.valueOf(bufferedReader.readLine());
        if (code == REJECT_IP) {
            log.info("黑名单IP，拒绝连接！");
        } else if (code == REJECT_ACCOUNT) {
            log.info("黑名单用户，拒绝服务！");
        } else if (code == ERROR_ACCOUNT) {
            log.info("账号错误，想清楚再来吧。。。");
        } else if (code == SUCCESS){
            log.info("连接成功，准备干活！");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            work(client);
        } else {
            log.info("未知问题。。。");
        }
    }

    public void work(Socket client) throws IOException {
        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
        printWriter.println("快给我服务！！！");
        printWriter.flush();
        System.out.println("客户端：快给我服务！！！");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String response = bufferedReader.readLine();
        System.out.println("服务端：" + response);

        bufferedReader.close();
        printWriter.close();
    }
}
