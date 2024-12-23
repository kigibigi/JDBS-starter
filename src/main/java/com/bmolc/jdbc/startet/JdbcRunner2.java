package com.bmolc.jdbc.startet;

import com.bmolc.jdbc.startet.util.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner2 {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String author= "Булгаков М.А.";
        var result = getTitleByBookId(author);
        System.out.println(result);

        var result2 = getTitleByAuthor(author);
        System.out.println(result2);
    }

    // метод через statement

// но обычным statement'ом не пользуются т.к. его легко атаковать просто добавив
// DDL команду и он выполнить её вместе со строковой
// String author= "Булгаков М.А. OR 1 = 1"; String author= "Булгаков М.А. DROP TABLE...";

    private static List<String> getTitleByBookId(String author) throws SQLException, ClassNotFoundException {

        // SQL запрос
        String sql = """
                SELECT title
                FROM book
                WHERE author = '%s';
                """.formatted(author);

        // результирующий список в который будут закладываться инф-ия с запроса
        List<String> result = new ArrayList<>();

        try (var connection = ConnectionUtils.openConnection();
             var statement = connection.createStatement()) {

            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(resultSet.getObject("title", String.class));
                //  если бостать нужно примитив, и в столбе NOT NULL то
                //  можно доставать через примитивы(ниже)
                //  result.add(resultSet.getInt("id"));
            }

            return result;
        }
    }

    // метод через PrepareStatement


    private static List<String> getTitleByAuthor(String author) throws SQLException, ClassNotFoundException {

        String sql = """
                SELECT title
                FROM test.book
                WHERE author = ?
                """;
        // в отлии от statement'а здесь ставят знак вопроса куда будет
        // подстовляться значение sql запроса

        List<String> result = new ArrayList<>();

        try(var connection = ConnectionUtils.openConnection();
            // сам sql запрос уже нужно подставлять в try(ниже)
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1 ,author);
            // индекс это в какой знак вопроса будет подставляться значение
            // т.е. если было ? AND ?
            // то для второго ? вновь вызывался но с индексом 2
            // preparedStatement.setString(2 ,author);


            var resultSet = preparedStatement.executeQuery();
            // опять же executeQuery потому что нужно вытащить данные, а не изменить
            while (resultSet.next()) {
                result.add(resultSet.getString("title"));
            }
        }

        return result;
    }
}

// так же в блоке try можно установаить следующие важные значения
// preparedStaement.setFetchSize(50) - устанавливает какое количество
//      итераций за одно соединение с сервером возьмет наше приложение, чтобы не
//      перегрузить приложение(обычно устанавливают 50-100, мало тоже плохо из-за постоянных соединений с сервером)

// preparedStatement.setQueryTimeout(10) - устанавливают максимальную длительность соеденения
//      опять же чтобы не ожидать бесконечный запрос

// preparedStatememnt.setMaxRows(100) - устанавливает ограничение на максимальный возможный запрос
//      в случае даже если с выборкой произойдёт преодоление этого запроса.