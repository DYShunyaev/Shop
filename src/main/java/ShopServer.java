import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ShopServer {
    public static void main(String[] args) {
        Availability availability = new Availability();
        Basket basket = new Basket();
        try(ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started.");
            while (true)
                try (Socket socket = server.accept();
                     BufferedWriter writer =
                             new BufferedWriter(
                                     new OutputStreamWriter(
                                             socket.getOutputStream()));
                     BufferedReader reader =
                             new BufferedReader(
                                     new InputStreamReader(
                                             socket.getInputStream())))
                {
                    String request = reader.readLine();
                    System.out.println("Запрос: " + request);
                    String response;
                    if (Objects.equals(request, "Ассортимент")) {
                        response = availability.getShopAvailability();
                    } else if (Objects.equals(request,"В корзину")) {
                        for (int i = 0; i < 15; i++) {
                            String number = reader.readLine();
                            writer.newLine();
                            writer.flush();
                            if (Objects.equals(number, "0")) break;
                            response = basket.addItemsToBasket(number);
                            System.out.println("Ответ: \n" + response);
                            writer.write(Objects.requireNonNull(response));
                            writer.newLine();
                            writer.flush();
                        }
                        response = basket.showBasket();
                    } else if (Objects.equals(request,"Показать корзину")) {
                        response = basket.showBasket();
                    } else {
                        response = "Некоректный запрос, попробуйте ещё раз.";
                    }
                    System.out.println("Ответ: \n" + response);
                    writer.write(Objects.requireNonNull(response));
                    writer.newLine();
                    writer.flush();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
