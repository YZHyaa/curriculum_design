import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    private Socket conn;
    private String keyWord;

    private static final String name = ServerThread.class.getName();
    private static final Logger log = Logger.getLogger(name);

    public ServerThread(Socket conn,String keyWord) {
        this.conn = conn;
        this.keyWord = keyWord;
    }

    @Override
    public void run() {
        try {
            work();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void work() throws IOException {
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        if (!check(reader.readLine().trim())) return;

        while (true) {
            String msg;
            if ((msg = reader.readLine()).contains(keyWord)) {
                System.out.println(conn.getInetAddress().getHostAddress() + " - 敏感：" + msg);
            } else if (msg.equalsIgnoreCase("quit")) {
                log.info(conn.getInetAddress().getHostAddress() + " 连接断开。。。");
                break;
            }
        }

        reader.close();
        inputStream.close();
    }

    public Boolean check(String token) throws IOException {
        File file = new File("server04/src/user.txt");
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        OutputStream outputStream = conn.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        String info;
        while ((info = bufferedReader.readLine()) != null) {
            if (info.equals(token)) {
                log.info(conn.getInetAddress().getHostAddress() + " 登录成功");
                printWriter.println(1);
                printWriter.flush();
                return true;
            }
        }
        log.info(conn.getInetAddress().getHostAddress() + " 登录失败");
        printWriter.println(0);
        printWriter.flush();

        printWriter.close();
        outputStream.close();
        bufferedReader.close();
        reader.close();
        return false;
    }



}