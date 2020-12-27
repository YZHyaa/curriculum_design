import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

    private static String name = Client.class.getName();
    private static Logger log = Logger.getLogger(name);

    public void start() throws IOException {
        Map<String, Object> input = input();
        Socket client = new Socket((String)input.get("ip"), (Integer)input.get("port"));
        log.info("客户端启动。。。");
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println((String)input.get("token"));
        printWriter.flush();
        log.info("账号口令已发送");
    }

    public Map<String,Object> input() {
        HashMap<String, Object> map = new HashMap<>();

        Scanner in = new Scanner(System.in);
        System.out.print("服务器IP：");
        map.put("ip",in.next());
        System.out.print("服务器端口：");
        map.put("port",in.nextInt());
        System.out.print("账号：");
        map.put("token",in.next());

        return map;
    }
}
