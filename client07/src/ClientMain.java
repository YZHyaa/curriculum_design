import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        try {
            new Client().start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
