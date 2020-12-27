import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties = new Properties();
    private static final String  FILE_PATH = "server01/src/root.properties";

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            InputStream inputStream = new BufferedInputStream(fileInputStream);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getRejectIps() {
        String ips = (String)properties.get("reject.ip");
        return Arrays.asList(ips.split(","));
    }

    public static List<String> getAllowAccounts() {
        String accounts = (String)properties.get("allow.account");
        return Arrays.asList(accounts.split(","));
    }

    public static List<String> getRejectAccounts() {
        String ips = (String)properties.get("reject.account");
        return Arrays.asList(ips.split(","));
    }
}
