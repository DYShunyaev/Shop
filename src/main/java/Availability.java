import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Availability {
    private static String url = "jdbc:mysql://localhost:3306/shop";
    private static String user = "root";
    private static String pass = "Pbvf4820!";
    private static String SELECT = "SELECT*FROM `shop`.`products`";

    public String getShopAvailability() {
        StringBuilder result = new StringBuilder();
        {
            try (Connection connection = DriverManager.getConnection(url, user, pass)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
                ResultSet resultSet = preparedStatement.executeQuery();
                result.append("Ассортимент Магазина:\n");
                result.append("id  Название  Цена  Кол-во в наличии  Категория\n");
                while (resultSet.next()){
                    result.append(resultSet.getString("id") + ")  ");
                    result.append(resultSet.getString("Product_name") + "      ");
                    result.append(resultSet.getString("Price") + "    ");
                    result.append(resultSet.getString("Availability") + "          ");
                    result.append(resultSet.getString("Category")+ "\n");
                }
                result.append("\nЧтобы добавить товар в корзину введите \"id\" товара.");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }
}