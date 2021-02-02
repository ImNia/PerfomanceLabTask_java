import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    static int currentValueBarrel = 0, startValueBarrel = 0;

    public static String[] parsingFile(String line, Date dateStart, Date dateEnd){
        String dateTmp = line.substring(0, line.indexOf("T")) + " " + line.substring(line.indexOf("T") + 1, line.indexOf("."));

        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateTmp);
            String valueTmp = line.substring(line.indexOf("wanna"));
            String value = "";
            for (String t : valueTmp.split("")) {
                if (t.matches("[0-9]")) {
                    value += t;
                }
            }
            if (valueTmp.contains("успех") && date.before(dateEnd)) {
                currentValueBarrel += valueTmp.contains("top up") ? Integer.parseInt(value) : (-1) * Integer.parseInt(value);
            }
            if (date.after(dateStart) && date.before(dateEnd)) {
                if (startValueBarrel == 0) {
                    startValueBarrel = currentValueBarrel;
                }
                return new String[]{valueTmp.contains("top up") ? "top up" : "scoop", value, (valueTmp.substring(valueTmp.indexOf("(") + 1, valueTmp.indexOf(")")))};
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("usage: \nИмя файла лога \nНачало временного интервала yyyy-MM-ddTHH:mm:ss \nКонец временного интервала yyyy-MM-ddTHH:mm:ss");
            return;
        }
        String fileName = args[0];
        Date dateStart = null, dateEnd = null;
        try {
            String timeTmp = args[1].substring(0, args[1].indexOf("T")) + " " + args[1].substring(args[1].indexOf("T") + 1);
            dateStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(timeTmp);
            timeTmp = args[2].substring(0, args[2].indexOf("T")) + " " + args[2].substring(args[2].indexOf("T") + 1);
            dateEnd = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(timeTmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fileLine;
        String[] resultParsing = new String[2];
        TopUp topUp = new TopUp();
        Scoop scoop = new Scoop();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            while ((fileLine = fileReader.readLine()) != null) {
                if (fileLine.contains("username")) {
                    resultParsing = parsingFile(fileLine, dateStart, dateEnd);
                    if (resultParsing == null) continue;
                    if (resultParsing[0].equals("top up")) {
                        topUp.actionProgress(Integer.parseInt(resultParsing[1]), resultParsing[2].equals("успех"));
                    } else if (resultParsing[0].equals("scoop")) {
                        scoop.actionProgress(Integer.parseInt(resultParsing[1]), resultParsing[2].equals("успех"));
                    }
                } else if (fileLine.contains("текущий объем воды")) {
                    String tmp = "";
                    for (String t : fileLine.split("")) {
                        if (t.matches("[0-9]")) {
                            tmp += t;
                        }
                    }
                    currentValueBarrel = Integer.parseInt(tmp);
                }
            }
        } catch (IOException ex) {
            ex.getMessage();
        }

        if (startValueBarrel == 0) currentValueBarrel = 0;
        try (FileWriter writer = new FileWriter("result.csv")) {
            StringBuilder writeResult = new StringBuilder();
            writeResult.append("Количество попыток налить воду за период\t");
            writeResult.append("Процент ошибок\t");
            writeResult.append("Объем налитой воды\t");
            writeResult.append("Объем не налитой воды\t");
            writeResult.append("Количество попыток забрать воду\t");
            writeResult.append("Процент ошибок\t");
            writeResult.append("Объем забранной воды\t");
            writeResult.append("Объем не забранной воды\t");
            writeResult.append("Объем воды в начале периода\t");
            writeResult.append("Объем воды в конце периода\t");
            writeResult.append("\n");

            writeResult.append(topUp.getCountSuccess() + topUp.getCountFail() + "\t");
            writeResult.append(Double.valueOf(topUp.getCountFail()) / Double.valueOf(topUp.getCountSuccess() + topUp.getCountFail()) * 100 + "\t");
            writeResult.append(topUp.getValueSuccess() + "\t");
            writeResult.append(topUp.getValueFail() + "\t");

            writeResult.append(scoop.getCountSuccess() + scoop.getCountFail() + "\t");
            writeResult.append((Double.valueOf(scoop.getCountFail()) / Double.valueOf(scoop.getCountSuccess() + scoop.getCountFail())) * 100 + "\t");
            writeResult.append(scoop.getValueSuccess() + "\t");
            writeResult.append(scoop.getValueFail() + "\t");
            writeResult.append(startValueBarrel + "\t");
            writeResult.append(currentValueBarrel + "\t");
            writeResult.append("\n");

            writer.write(writeResult.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}