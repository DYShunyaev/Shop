import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        BufferedReader readerReq = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try (Socket socket = new Socket("127.0.0.1",8000);
                 BufferedWriter writer = new BufferedWriter(
                         new OutputStreamWriter(
                                 socket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(
                                 socket.getInputStream()))
            ){
                System.out.println("Введите запрос:");
                String request = readerReq.readLine();
                System.out.println("Запрос: " + request);
                writer.write(request);
                writer.newLine();
                writer.flush();
                if (Objects.equals(request, "В корзину")){
                    for (int i = 0; i < 15; i++) {
                        System.out.print("Введите id товара: ");
                        String number = readerReq.readLine();
                        writer.write(number);
                        writer.newLine();
                        writer.flush();
                        if (Objects.equals(number, "0")) break;
                    }
                }

                List<String> response = reader.lines().collect(Collectors.toList());
                response.forEach(System.out::println);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
