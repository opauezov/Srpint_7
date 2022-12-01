import courier.Courier;
import courier.CourierClient;
import courier.CourierGenerator;
import courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierCreateTest {
    private Courier courier;
    private CourierClient courierClient;
    private int expectedStatusCode;
    private boolean expectedResult;

    public CourierCreateTest(Courier courier, int expectedStatusCode, boolean expectedResult) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][]{
                {CourierGenerator.getDefault(), 201, true},
                {CourierGenerator.getInUppercase(), 201, true}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }

    @After
    public void cleanUp() {
        Response response = courierClient.loginCourier(Credentials.from(courier));
        Integer id = response.then().extract().path("id");
        if (id != 0) {
            courierClient.deleteCourier(id);
        }

    }
    @DisplayName("Создание курьера")
    @Test
    public void checkPostCreateCourier() {
        Response responseCreateCourier = courierClient.createCourier(courier);
        responseCreateCourier.then().assertThat().statusCode(expectedStatusCode)
                .and().body("ok", equalTo(expectedResult));

    }
    @DisplayName("Создание курьера, логин занят")
    @Test
    public void checkPostCreateSameCourier() {
        courierClient.createCourier(courier);
        Response responseCreateSameCourier = courierClient.createCourier(courier);
        responseCreateSameCourier.then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}