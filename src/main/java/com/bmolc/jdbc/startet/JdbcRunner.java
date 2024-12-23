package com.bmolc.jdbc.startet;

import com.bmolc.jdbc.startet.util.ConnectionUtils;
import com.mysql.jdbc.Driver;

import java.sql.*;

public class JdbcRunner {

    public static <connection> void main(String[] args) throws SQLException {

//        Class<Driver> driverClass = Driver.class;
//        String password = "root";
//        String username = "root";
//        String url = "jdbc:mysql://localhost:3306";


//        String sql = """
//                UPDATE info
//                SET data = 'TestTest'
//                WHERE ID = 5;
//                """;
//        INSERT INTO info(data)
//                VALUES ('Test1'),
//                        ('Test2'),
//                        ('Test3'),
//                        ('Test4');
//        CREATE TABLE info (
//                id INT PRIMARY KEY AUTO_INCREMENT ,
//                data BLOB
//        );
//        String sql = """
//                DROP TABLE info;
//                """;
//        CREATE DATABASE game;
//        DROP DATABASE game;
//        CREATE SCHEMA game;

        String sql = """
                SELECT * FROM test.book;
                """;
        System.out.println("Get connection...");

        try (Connection connection = ConnectionUtils.openConnection();
             Statement statement = connection.createStatement()) {

            System.out.println("\nConnection set!");
//            System.out.println(connection.getTransactionIsolation());
//            System.out.println(connection.getSchema() + "\n");

//            var executeResult = statement.executeUpdate(sql);
//            System.out.println(executeResult);

//            System.out.println(statement.getUpdateCount());
//            уже не нужен т.к. я перешел execute -> executeUpdate

            var executeResult = statement.executeQuery(sql);
            while (executeResult.next()) {
                System.out.println(executeResult.getLong("book_id"));
                System.out.println(executeResult.getString("title"));
                System.out.println(executeResult.getBigDecimal("price"));
                System.out.println("_________");
            }
//            System.out.println(executeResult);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        System.out.println("Close connection!");
    }
}

// Alt + Enter -> Inject language of reference - включает подсказчик в строке(на нужную тематику)

// execute можно использовать для всех операций SQL. тип
// возвращаемых данных boolean.
//  true - для select запросов(т.е. запросов который возвращают какие-то данные
//  false - для DDL и DML запросов - которые изменяют бд, таблицы(DML), данные в строках(DML)

// executeUpdate - подходит для изменения столбцов и строк. возвращает int
// - число говорит о количестве измененных строк

// executeQuery уже предназнавен для SELECT запросов. у него много методов
//  get(getLong, getString) - для получения данных из запросов. Тип возвращаемых
//  данных ResultSet - что походит на итератор, и чтобы вытаскивать информацию нужно пользоваться
//  методом next - который совмещает функции в итераторе hasNext() и next(). Поэтому
//  данные нужно вытаскивать через цикл если строк из запроса будет больше одной.
//  а get'ами получать нужную информацию.
// executeQuery не используют для DML и DDL запросов.но если такая необходимость есть
//  то в try Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
//  можно передавать параметры(screenshots)/
// !но на практике(сказано несколько раз) лучше его использовать тольо для select запросов


// Ctrl + F12 - позволяет обратиться к документации.