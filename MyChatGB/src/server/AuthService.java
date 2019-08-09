package server;

import java.sql.*;

//авторизационный сервис, который умеет подключаться к бд, умеет дисконнект делать и умеет вытаскивать никнейм,
//который нам нужен

interface AuthService {

    //метод проверяющий в базе данных есть ли такой никнейм
    String getNick(String login, String pass);

    void connect(); //метод подключиться к бд

    void disconnect(); //метод отключиться от бд
}
class AuthServiceImpl implements AuthService {

    //пакет java.sql для работы с sql языком и у него есть Connection, Statment и ResultSet - три основных
    //объекта, необходимых для работы с jdbc - технология для работы с бд
    private static Connection connection;
    private static Statement statement;


    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNick(String login, String pass) {
        String query = String.format("select nick from users\n"
                  +"where login = '%s'\n"
                + " and password = '%s'\n", login, pass);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) { //если строка есть
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
