import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

    private Socket client = null;

    private static String name = Client.class.getName();
    private static Logger log = Logger.getLogger(name);

    private static final Integer ERROR_USER = -1;
    private static final Integer ERROR_PWD = 0;
    private static final Integer SUCCESS = 1;

    public void start() throws IOException, ClassNotFoundException {
        Map<String, Object> input = input();
        client = new Socket((String)input.get("ip"), (Integer)input.get("port"));
        login((String)input.get("username"));
        client.close();
    }

    public Map<String,Object> input() {
        HashMap<String, Object> map = new HashMap<>();

        Scanner in = new Scanner(System.in);
        System.out.print("服务器IP：");
        map.put("ip",in.next());
        System.out.print("服务器端口：");
        map.put("port",in.nextInt());
        System.out.print("用户名：");
        map.put("username",in.next());

        return map;
    }

    public void login(String username) throws IOException{
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        InputStream inputStream = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        printWriter.println(username);

        printWriter.flush();
        printChart("");

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("输入(verify确定)：");
            String input = in.nextLine();
            if ( input.equals("verify")) {
                printWriter.println(input);
                printWriter.flush();
                break;
            }
            if ((input = createMsg(input)) == "") {
                continue;
            }
            printWriter.println(input);
            printWriter.flush();

            String str = reader.readLine();
            printChart(str);
        }

        int loginCode;
        if ((loginCode = Integer.valueOf(reader.readLine())) == SUCCESS) {
            log.info("登录成功！！！");
        } else if (loginCode == ERROR_PWD){
            log.info("登录失败，密码错误");
        } else {
            log.info("登录失败，无该用户");
        }

        printWriter.close();
        outputStream.close();
        reader.close();
        inputStream.close();
    }

    public void printChart(String pos) {
        boolean[][] chart = new boolean[4][4];
        if (pos != null && ! pos.isEmpty()) {
            String[] split = pos.split(",");
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("-");
                int x = Integer.valueOf(split1[0]);
                int y = Integer.valueOf(split1[1]);
                chart[x][y] = true;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (chart[i][j]) {
                    System.out.print(" x ");
                } else {
                    System.out.print(" * ");
                }
            }
            System.out.println();
        }
    }

    public String createMsg(String input) {
        try {
            input = input.substring(input.indexOf("(") + 1,input.indexOf(")"));
            String[] split = input.split(",");
            int x = Integer.valueOf(split[0]) - 1;
            int y = Integer.valueOf(split[1]) - 1;
            if (x > 3 || x < 0  || y > 3 || y < 0) {
                throw new Exception();
            }
            return x + "-" + y;
        } catch (Exception e) {
            System.out.println("输入错误（格式：set(x,y)）！请重新输入");
            return "";
        }
    }
}
