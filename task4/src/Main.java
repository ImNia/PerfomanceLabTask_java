import java.util.ArrayList;

public class Main {
    public static boolean firstStar = false;
    public static boolean lastStar = false;
    public static int checkSubArray(String first, String[] second, int subNumber) {
        if (second[0] == "") return 1;
        if (subNumber == second.length && first.equals("")) return 1;
        if (subNumber == second.length && first.length() > 0 && lastStar) return 1;
        if (subNumber == second.length && first.length() > 0) return -1;
        String firstSub;

        ArrayList<Integer> allSubString = new ArrayList<>();
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = first.indexOf(second[subNumber], lastIndex);
            if (lastIndex != -1) {
                allSubString.add(lastIndex);
                lastIndex++;
            }
        }

        for (int i = 0; i < allSubString.size(); i++) {
            firstSub = first.substring(first.indexOf(second[subNumber], allSubString.get(i)) + second[subNumber].length());


            if (subNumber == 0) {

                if (firstStar) {
                    if (first.indexOf(second[subNumber]) != 0) {
                        return 1;
                    }
                } else {
                    if (first.indexOf(second[subNumber]) != 0) {
                        return -1;
                    }
                }
            }

            if (checkSubArray(firstSub, second, subNumber + 1) != -1) {
                return 1;
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Введено меньше двух аргументов");
            return;
        }
        if (args[0].equals(args[1])) {
            System.out.println("OK");
            return;
        }

        String first = args[0];
        String tmp = "";
        for (int i = 0; i < args[1].length(); i++) {
            if (args[1].charAt(i) == '*' && (i + 1 == args[1].length() || args[1].charAt(i + 1) != '*')) {
                tmp += args[1].charAt(i);
            } else if (args[1].charAt(i) != '*') {
                tmp += args[1].charAt(i);
            }
        }

        if (tmp.charAt(0) == '*') {
            firstStar = true;
            if (tmp.length() > 1) {
                tmp = tmp.substring(1);
            }
        }
        if (tmp.charAt(tmp.length() - 1) == '*') {
            lastStar = true;
            tmp = tmp.substring(0, tmp.length() - 1);
        }
        String[] second = tmp.split("\\*");
        if (checkSubArray(first, second, 0) != -1) {
            System.out.println("OK");
        } else {
            System.out.println("KO");
        }
    }
}
