import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class QueryThread extends Thread {

    private static final String name = ServerThread.class.getName();
    private static final Logger log = Logger.getLogger(name);

    @Override
    public void run() {
        try {
            log.info("日志已经开始写入login.log，查询请按 Q ！！！");
            readLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readLog() throws IOException {
        File file = new File("server03/src/login.log");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Scanner in = new Scanner(System.in);
        while ((in.next().equalsIgnoreCase("q"))) {
//            for(int i = 0; i < 15; i++) System.out.println();
            System.out.println("历史日志信息如下（login.log）");
            String log;
            while ((log = bufferedReader.readLine()) != null) {
                System.out.println(log);
            }
        }
    }
}
