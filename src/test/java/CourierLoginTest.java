import Courier.Courier;
import Courier.CourierClient;
import Courier.CourierGenerator;
import Courier.Credentials;
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

    @Test
    public void loginCourier() {
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(200).and().body("id", notNullValue());
    }

    @Test
    public void loginWithInvalidPassword() {
        Courier courier = CourierGenerator.authorization();
        courier.setPassword(courier.getPassword() + "lksla12");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithInvalidLogin() {
        Courier courier = CourierGenerator.authorization();
        courier.setLogin("hattori123");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLogin() {
        Courier courier = CourierGenerator.authorization();
        courier.setLogin(null);
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithEmptyPassword() {
        Courier courier = CourierGenerator.authorization();
        courier.setPassword("");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginEmptyLogin() {
        Courier courier = CourierGenerator.authorization();
        courier.setLogin("");
        Response response = courierClient.loginCourier(Credentials.from(courier));
        response.then().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }
}
