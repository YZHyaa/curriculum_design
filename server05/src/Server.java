import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server {

    private static Integer clientCount = 1;

    private static String name = Server.class.getName();
    private static Logger log = Logger.getLogger(name);

    private static final Integer REJECT_IP = -2;
    private static final Integer REJECT_ACCOUNT = -1;
    private static final Integer ERROR_ACCOUNT = 0;
    private static final Integer SUCCESS = 1;

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(8080);
        log.info("服务器在8080端口启动。。。");

        while (true) {
            Socket conn = server.accept();
            String requestAddress = conn.getInetAddress().getHostAddress();
            log.info("客户端-" + clientCount + "(" + requestAddress + " )尝试建立连接ing");
            new Thread(() -> {
                OutputStream outputStream = null;
                InputStream inputStream = null;
                PrintWriter printWriter = null;
                BufferedReader bufferedReader = null;
                try {
                    outputStream = conn.getOutputStream();
                    printWriter = new PrintWriter(outputStream);
                    inputStream= conn.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    if (PropertiesUtil.getRejectIps().contains(requestAddress)) {
                        printWriter.println(REJECT_IP);
                        printWriter.flush();
                        log.info("客户端-" + requestAddress + "属于黑名单IP，已拒绝");
                        return;
                    }

                    String account = bufferedReader.readLine();
                    if (PropertiesUtil.getRejectAccounts().contains(account)) {
                        printWriter.println(REJECT_ACCOUNT);
                        printWriter.flush();
                        log.info("客户端-" + clientCount + "(" + requestAddress + " )属于黑名单用户，已拒绝");
                    } else if ( ! PropertiesUtil.getAllowAccounts().contains(account)) {
                        printWriter.println(ERROR_ACCOUNT);
                        printWriter.flush();
                        log.info("客户端-" + clientCount + "(" + requestAddress + " )账号错误，本次连接中断");
                    } else {
                        printWriter.println(SUCCESS);
                        printWriter.flush();
                        log.info("客户端-" + clientCount + "(" + requestAddress + " )连接成功，准备服务！");

                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("客户端-" + clientCount + "：" + bufferedReader.readLine());
                        printWriter.println("好的好的~~");
                        printWriter.flush();
                        System.out.println("服务端：" + "好的好的~~");
                    }
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    clientCount++;
                    try {
                        bufferedReader.close();
                        inputStream.close();
                        printWriter.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
