import java.io.IOException;

public class ServerMain  {

    public static void main(String[] args) {
        try {
            new Server().start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
