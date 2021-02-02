public class Main {
    static public String decimalToAny(Integer number, int baseDst) {
        String result = "";
        int tmp;
        while (number > 0) {
            tmp = number % baseDst;
            result += tmp;
            number = (number - tmp) / baseDst;
        }
        return new StringBuilder(result).reverse().toString();
    }

    static public int anyToDecimal(String nb, int baseSrc) {
        String[] number = nb.split("");
        int result = 0;
        Integer tmp = 0;
        for (int i = 0; i < number.length; i++) {
            if (number[i].matches("[a-f]+")) {
                tmp = Integer.parseInt(number[i], 16);
            } else {
                tmp = Integer.parseInt(number[i]);
            }
            result += (tmp * Math.pow(baseSrc, number.length - i - 1));
        }
        return result;
    }

    static public String itoBase(String nb, String baseSrc, String baseDst) {
        int notationSrc = baseSrc.length();
        int notationDst = baseDst.length();

        if (baseSrc.equals(baseDst)) return nb;

        if (notationSrc != 10) {
            Integer t = anyToDecimal(nb, notationSrc);

            if (notationDst != 10) {
                return decimalToAny(t, notationDst);

            }
            return t.toString();
        } else if (notationDst != 10) {
            return decimalToAny(Integer.parseInt(nb), notationDst).toString();
        }
        return "usage";
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: Число (0-9a-f) Система исчисления из какой конвертировать (0123456789abcdef) Система исчисления куда конвертировать (0123456789abcdef)");
        } else if (args[1].matches("[a-fA-F0-9]+") && args[2].matches("[a-fA-F0-9]+")){
            for (String tmp: args[0].split("")) {
                if (!args[1].contains(tmp)) {
                    System.out.printf("Введено некорректное число: %s не соответсвует системе исчесления %s", tmp, args[1]);
                    return;
                }
            }
            System.out.println(itoBase(args[0], args[1], args[2]));
        } else {
            System.out.println("Введена некорректная система исчисления");
        }
    }
}
