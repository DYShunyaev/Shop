import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

public class Basket {

    private static String url = "jdbc:mysql://localhost:3306/shop";
    private static String user = "root";
    private static String pass = "Pbvf4820!";
    ArrayList<String> cart = new ArrayList<>();

    public String addItemsToBasket(String id) {
        StringBuilder result = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT*FROM `shop`.`products` where id = " + id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                result.append(resultSet.getString("id") + ")  ");
                result.append(resultSet.getString("Product_name") + "      ");
                result.append(resultSet.getString("Price") + "    ");
                result.append(resultSet.getString("Availability") + "          ");
                result.append(resultSet.getString("Category")+ "\n");
            }
            cart.add(result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }

    public String showBasket() {
        return String.valueOf(cart);
    }

}
