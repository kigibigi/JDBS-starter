package com.bmolc.jdbc.startet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        String hostname = "localhost";

        String dbName = "test";
        String userName = "root";
        String password = "root";

        String connectionURL = "jdbc:mysql://" + hostname + ":3306/" + dbName;
//        String connectionURL = "jdbc:mysql://" + hostname + ":3306";

        Connection connection = DriverManager.getConnection(connectionURL, userName, password);

        return connection;

//        попробовал вариант где идет подключение к конкретной базе данных в хосте(так сооветует сайт)
//        и где не закоменчено вариант без конкретной БД.
    }
}
