import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server {

    private static String name = Server.class.getName();
    private static Logger log = Logger.getLogger(name);

    public void start() throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(8080);
        log.info("服务器在8080端口启动");
        Scanner in = new Scanner(System.in);
        TimeUnit.SECONDS.sleep(1);
        System.out.print("请输入要过滤的敏感词：");
        String keyWord = in.next();
        System.out.println("保存成功！");

        while (true) {
            Socket conn = server.accept();
            new ServerThread(conn,keyWord).start();
        }

    }
}
