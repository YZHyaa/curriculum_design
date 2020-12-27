package tool;

import java.io.IOException;

public class ToolMain {

    public static void main(String[] args) {
        try {
            new Tool().start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
