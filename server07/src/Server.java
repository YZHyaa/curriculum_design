import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {

    private static String name = Server.class.getName();
    private static Logger log = Logger.getLogger(name);


    public void start() throws IOException {
        ServerSocket server = new ServerSocket(8080);
        log.info("服务器在8080端口启动");

        while (true) {
            Socket conn = server.accept();
            new ServerThread(conn).start();
        }
    }
}
