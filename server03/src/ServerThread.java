import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    private Socket conn;

    private static final String name = ServerThread.class.getName();
    private static final Logger log = Logger.getLogger(name);

    public ServerThread(Socket conn) {
        this.conn = conn;
        log.info(conn.getInetAddress().getHostAddress() + " 尝试登录ing");
    }

    @Override
    public void run() {
        try {
            writeLog(check(read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() throws IOException {
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String token = bufferedReader.readLine();

        bufferedReader.close();
        inputStream.close();

        return token;
    }

    public Boolean check(String token) throws IOException {
        File file = new File("server03/src/user.txt");
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String info;
        while ((info = bufferedReader.readLine()) != null) {
            if (info.equals(token)) {
                log.info(conn.getInetAddress().getHostAddress() + " 登录成功");
                return true;
            }
        }
        log.info(conn.getInetAddress().getHostAddress() + " 登录失败");
        return false;
    }

    public void writeLog(Boolean success) throws IOException {
        File file = new File("server03/src/login.log");
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = simpleDateFormat.format(new Date());
        String addr = conn.getInetAddress().getHostAddress();
        String msg = success ? "登录成功" : "登录失败";
        printWriter.println("客户端" + addr + " 在 " + now + msg);

        printWriter.close();
        fileWriter.close();
    }

}
