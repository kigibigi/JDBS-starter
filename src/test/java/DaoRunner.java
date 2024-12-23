import com.bmolc.jdbc.startet.dao.CarDao;
import com.bmolc.jdbc.startet.entity.Car;

import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {
//        List<Car> carList = CarDao.getInstance().findAll();
//        System.out.println();
        CarDao carDao = CarDao.getInstance();
//        carDao.createUser1Table();
        carDao.dropUser1Table();
    }

    private static void updateTest() {
        //проверка на то что нам вытащили по нужному id строку
        CarDao carDao = CarDao.getInstance();
        Optional<Car> byCarId = carDao.findByCarId(4);
        System.out.println(byCarId);

        // проверяем на наличие и меняем значение
        byCarId.ifPresent(car -> {
            car.setColor("черный");
            carDao.update(car);
        });
    }

    private static void deleteTest(int id) {
        CarDao carDao = CarDao.getInstance();
        boolean delete = carDao.delete(id);
        System.out.println(delete);
    }

    private static void saveTest() {
        CarDao carDao = CarDao.getInstance();
        Car carGranta = new Car();

        carGranta.setVin("RTE277GB99SDF123");
        carGranta.setMake("Lada");
        carGranta.setMo("Granta");
        carGranta.setColor("белый");
        carGranta.setYear(2012);
        carGranta.setPrice(1100.37);

        Car savedCar = carDao.save(carGranta);
        System.out.println(savedCar);
    }
}
