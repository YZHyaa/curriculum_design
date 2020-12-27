import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    private static String name = ServerThread.class.getName();
    private static Logger log = Logger.getLogger(name);

    private static final Integer ERROR_USER = -1;
    private static final Integer ERROR_PWD = 0;
    private static final Integer SUCCESS = 1;

    private Socket conn;

    public ServerThread(Socket conn) {
        this.conn = conn;
        log.info(conn.getInetAddress().getHostAddress() + "尝试连接ing。。。");
    }

    @Override
    public void run() {
        try {
            write(read());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int read() throws IOException {
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        OutputStream outputStream = conn.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        String username = reader.readLine();

        boolean[][] chart = new boolean[4][4];

        String position;
        while (!(position = reader.readLine()).equalsIgnoreCase("verify")) {
            String[] split = position.split("-");
            int x = Integer.valueOf(split[0]), y = Integer.valueOf(split[1]);
            chart[x][y] = chart[x][y] ? false : true;
            String data = "";
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (chart[i][j]) {
                        String s = i + "-" + j;
                        data = data + s + ",";
                    }
                }
            }
            if (! data.equals(""))
                data = data.substring(0,data.length() - 1);
            printWriter.println(data);
            printWriter.flush();
        }

        return check(username, chart);
    }

    public int check(String username, boolean [][] chart) throws IOException {
        boolean[][] rightChart = parseUserFile(username);
        if (rightChart == null)
            return ERROR_USER;
        for (int i = 0; i < rightChart.length; i++) {
            if (! Arrays.equals(rightChart[i],chart[i]))
                return ERROR_PWD;
        }
        return SUCCESS;
    }

    public boolean[][] parseUserFile(String username) throws IOException {
        File file = new File("server07/src/user.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        boolean findUser = false;
        boolean[][] rightChart = new boolean[4][4];
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(":");
            if (! split[0].equals(username))
                continue;
            findUser = true;
            String[] poses = split[1].split(",");
            for (int i = 0; i < poses.length; i++) {
                String[] pos = poses[i].split("-");
                int x = Integer.valueOf(pos[0]);
                int y = Integer.valueOf(pos[1]);
                rightChart[x][y] = true;
            }
        }

        return findUser ? rightChart : null;
    }

    public void write(int checkCode) throws IOException{
        OutputStream outputStream = conn.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        if (checkCode == SUCCESS) {
            log.info(conn.getInetAddress().getHostAddress() + " 登陆成功");
            printWriter.println(checkCode);
            printWriter.flush();
        } else {
            log.info(conn.getInetAddress().getHostAddress() + " 登录失败");
            printWriter.println(checkCode);
            printWriter.flush();
        }

        printWriter.close();
        outputStream.close();
    }
}
