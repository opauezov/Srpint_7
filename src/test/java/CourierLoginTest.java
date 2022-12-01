import courier.Courier;
import courier.CourierClient;
import courier.CourierGenerator;
import courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.authorization();
        courierClient.createCourier(courier);

    }

    @After
    public void cleanUp() {
        Response response = courierClient.loginCourier(Credentials.from(courier));
        Integer id = response.then().extract().path("id");
        if (id != 0) {
            courierClient.deleteCourier(id);
        }
    }
    @DisplayName("Корректная авторизация существующего пользователя")
    @Test
    public void loginCourier() {
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(200).and().body("id", notNullValue());
    }
    @DisplayName("Авторизация пользователя, некорректный пароль")
    @Test
    public void loginWithInvalidPassword() {
        Courier courier = CourierGenerator.authorization();
        courier.setPassword(courier.getPassword() + "lksla12");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }
    @DisplayName("Авторизация пользователя, некорректный логин")
    @Test
    public void loginWithInvalidLogin() {
        Courier courier = CourierGenerator.authorization();
        courier.setLogin("hattori123");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }
    @DisplayName("Авторизация пользователя, пустой логин")
    @Test
    public void loginWithoutLogin() {
        Courier courier = CourierGenerator.authorization();
        courier.setLogin(null);
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
    @DisplayName("Авторизация пользователя, пустой пароль")
    @Test
    public void loginWithEmptyPassword() {
        Courier courier = CourierGenerator.authorization();
        courier.setPassword("");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
    @DisplayName("Авторизация пользователя, логин состоит только из пробела")
    @Test
    public void loginEmptyLogin() {
        Courier courier = CourierGenerator.authorization();
        courier.setLogin("");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
}
