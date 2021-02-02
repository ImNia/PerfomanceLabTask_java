import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String tmp = "";
        try(FileWriter writer = new FileWriter("log.log", false)) {
            tmp = "META DATA:\n";
            writer.append(tmp);

            int fullSize = 100 + (int) (Math.random() * 1000);
            tmp = fullSize + " (объем бочки)\n";
            writer.append(tmp);

            int currentSize = (int) (Math.random() * fullSize);
            tmp = currentSize + " (текущий объем воды в бочке)\n";
            writer.append(tmp);

            Date date = new Date();

            int volume = 0;
            for (int i = 0; i < 20000; i++) {
                tmp = (new SimpleDateFormat("yyyy-MM-dd")).format(date) + "T";
                date.setSeconds(date.getSeconds() + (int) (Math.random() * 1000));
                tmp += (new SimpleDateFormat("kk:mm:ss")).format(date) + "." +
                        (100 + (int) (Math.random() * 1000)) + "Z";
                tmp += " - [username" + (1 + (int) (Math.random() * 3)) + "] - wanna ";
                volume = 1 + (int) (Math.random() * 100);
                if ((int) (Math.random() * 100) % 2 == 0) {
                    tmp += currentSize + volume <= fullSize ? "top up " + volume + "l(успех)"
                            : "top up " + volume + "l(фейл)";
                    currentSize += currentSize + volume <= fullSize ? volume : 0;
                } else {
                    tmp += currentSize - volume >= 0 ? "scoop " + volume + "l(успех)"
                            : "scoop " + volume + "l(фейл)";
                    currentSize -= currentSize - volume >= 0 ? volume : 0;
                }
                writer.append(tmp + "\n");
            }

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
