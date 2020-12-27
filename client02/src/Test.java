import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        new Test().generate(0,"", 6, strings);
        System.out.println(strings.size());
        System.out.println(strings);
    }

    public void generate(int level, String s, int n,List<String> res) {
        if (level == n) {
            res.add(s);
            return;
        }

        for (int i = 0; i <= 9; i++) {
            generate(level + 1, s + i, n, res);
        }
    }
}
