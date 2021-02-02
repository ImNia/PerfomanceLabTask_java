import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Введено не две строки");
            return;
        }
        if (args[0].equals(args[1])) {
            System.out.println("OK");
        }
        ArrayList<Character> second = new ArrayList<>();
        for (int i = 0; i < args[1].length(); i++) {
            if (args[1].charAt(i) == '*') {
                second.add('.');
                second.add('*');
            } else {
                second.add(args[1].charAt(i));
            }
        }
        String tmp = "";
        for (Character t: second) {
            tmp += t;
        }
        System.out.println(args[0].matches(tmp));
    }
}
