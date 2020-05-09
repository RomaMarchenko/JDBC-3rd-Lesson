package lesson3HW;

import exceptions.BadRequestException;
import lesson2HW.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.ceffzvpakwhb.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "main2001";

    private final String GET_PRODUCTS_WIH_EMPTY_DESCRIPTION = "SELECT * FROM PRODUCT WHERE DESCRIPTION IS NULL";

    public List<Product> findProductsByPrice(int price, int delta) {
        try  (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE PRICE BETWEEN ? AND ?");

            preparedStatement.setInt(1, price - delta);
            preparedStatement.setInt(2, price + delta);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(mapProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findProductsByName(String word) throws BadRequestException {
        try  (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE NAME = ?");
            checkWord(word);

            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(mapProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findProductsWithEmptyDescription() {
        try  (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_PRODUCTS_WIH_EMPTY_DESCRIPTION);
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(mapProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private Product mapProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet.getLong("ID"), resultSet.getString("NAME"), resultSet.getString("DESCRIPTION"), resultSet.getInt("PRICE"));
        return product;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void checkWord(String word) throws BadRequestException {
        if(word.contains(" "))
            throw new BadRequestException("Requested word: \"" + word + "\" contains more than one word");

        else if(word.length() < 3)
            throw  new BadRequestException("Requested word: \"" + word + "\" contains less than 3 characters");

        for (Character ch : word.toCharArray()) {
            if(!Character.isLetter(ch) && !Character.isDigit(ch)) {
                throw  new BadRequestException("Requested word: \"" + word + "\" contains inappropriate characters");
            }
        }
    }
}
