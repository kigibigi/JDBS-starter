package com.bmolc.jdbc.startet.dao;

import com.bmolc.jdbc.startet.entity.Car;
import com.bmolc.jdbc.startet.util.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao {

    private static final CarDao INSTANCE = new CarDao();
    private static final String DELETE_SQL = """
            DELETE FROM task_from_book.car_table
            WHERE car_id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO task_from_book.car_table (VIN, make, mo, color, year, price) 
            VALUES (?, ?, ?, ?, ?, ?) 
            """;
    private static final String UPDATE_SQL = """
            UPDATE task_from_book.car_table
            SET VIN = ?,
                make = ?,
                mo = ?,
                color = ?,
                year = ?,
                price = ?
            WHERE car_id = ?
            """;

    // для проверки update(выше) нужен метод который будет выдавать запрос по идентификатору(ниже)
    private static final String FIND_BY_CAR_ID = """
            SELECT car_id,
                    VIN,
                    make,
                    mo,
                    color,
                    year,
                    price 
            FROM task_from_book.car_table
            WHERE car_id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT * FROM task_from_book.car_table
            """;

    private static final String CREATE_USER_TABLE = """
            CREATE TABLE user1(
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(30),
                lastname VARCHAR(35),
                age INT
            )
            """;

    private static final String DROP_USER_TABLE = """
            DROP TABLE user1;
            """;
    private CarDao() {
    }

    public void createUser1Table() {
        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_TABLE)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUser1Table() {
        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_USER_TABLE)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Car> findAll() {
        try(Connection connection = ConnectionUtils.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            // в отличии от поиска по id тут не нужно ничего выделять
            ResultSet resultSet = preparedStatement.executeQuery();

            // т.к. значений будет несколько, то уже собираем всё в лист
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(buildCar(resultSet));
                // чтобы не переписывать через комбинацию Ctrl+Alt+M и
                // выделить значение которые мы подставляли ранее в методе
                // ниже просто в отдельный метод buildCar
            }

            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Car> findByCarId(Integer carId) {
        // если метод может вернуть null выдавать нужно Optional (в случае с
        // коллекциями то пустую коллекцию)
        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CAR_ID)) {

            preparedStatement.setInt(1, carId);

            ResultSet resultSet = preparedStatement.executeQuery();

            // resultSet в моем случае вернет строку из таблицы есть ли под
            // таким Id есть значение, а если нет то ниже создан car = null который
            // и вернет метод
            Car car = null;
            if (resultSet.next()) {
                car = buildCar(resultSet);
            }

            return Optional.ofNullable(car);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Car buildCar(ResultSet resultSet) throws SQLException {
        return new Car(
                resultSet.getInt("car_id"),
                resultSet.getString("VIN"),
                resultSet.getString("make"),
                resultSet.getString("mo"),
                resultSet.getObject("color", String.class),
                resultSet.getInt("year"),
                resultSet.getDouble("price")
        );
    }

    public boolean delete(Integer carId) {
        try(Connection connection = ConnectionUtils.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        )
        {
            preparedStatement.setInt(1, carId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Car save(Car car) {
        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, car.getVin());
            preparedStatement.setString(2, car.getMake());
            preparedStatement.setString(3, car.getMo());
            preparedStatement.setObject(4, car.getColor());
            preparedStatement.setInt(5, car.getYear());
            preparedStatement.setDouble(6, car.getPrice());

            preparedStatement.executeUpdate();

            // для получения идентификаторов нужно передать константку Statement.RETURN_GENERATED_KEYS в
            // параметры preparedStatement'а там где передается sql запрос
            // и добавить код ниже

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) { // хотя в моем случае он все равно будет
                car.setCarId(generatedKeys.getInt(1));
            }

            return car;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Car car) {
        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, car.getVin());
            preparedStatement.setString(2, car.getMake());
            preparedStatement.setString(3, car.getMo());
            preparedStatement.setObject(4, car.getColor());
            preparedStatement.setInt(5, car.getYear());
            preparedStatement.setDouble(6, car.getPrice());

            // и установка моего идентификатора по которому будут удаляться поля
            preparedStatement.setInt(7, car.getCarId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static CarDao getInstance() {
        return INSTANCE;
    }
}
