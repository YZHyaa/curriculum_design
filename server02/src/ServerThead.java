import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThead extends Thread {

    private Socket conn;
    private Integer code;
    private Integer clientCount = 1;

    private static final Integer ERROR_USER = -1;
    private static final Integer ERROR_PWD = 0;
    private static final Integer SUCCESS = 1;

    private static String name = Server.class.getName();
    private static Logger log = Logger.getLogger(name);

    public ServerThead(Socket conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        try {
            while (this.code != ERROR_USER || this.code != SUCCESS) {
                    read();
                    write();
            }
            conn.close();
        } catch (IOException e) {
        }
    }

    public void read() throws IOException {
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String msg = bufferedReader.readLine();

        check(msg);
    }

    private void check(String msg) throws IOException {
        File file = new File("server02/user.txt");
        Reader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String info = bufferedReader.readLine();
        String[] InfoSplit = info.split(":");
        String[] msgSplit = msg.split(":");

        String reqAddr = conn.getInetAddress().getHostAddress();
        if (! InfoSplit[0].equals(msgSplit[0])) {
            this.code = ERROR_USER;
            log.info(reqAddr + " 账号错误，拒绝连接！");
        } else if (! InfoSplit[1].equals(msgSplit[1])) {
            this.code = ERROR_PWD;
            log.info(reqAddr + " 密码错误。。。");
        } else {
            this.code = SUCCESS;
            log.info("客户端" + clientCount + "（" + reqAddr + " ）连接成功！！！");
            clientCount++;
        }
    }

    public void write() throws IOException {
        OutputStream outputStream = conn.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(this.code);
        printWriter.flush();
    }
}
