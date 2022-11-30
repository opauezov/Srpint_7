import Courier.Courier;
import Courier.CourierClient;
import Courier.CourierGenerator;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierCreateWOPTest {
    private CourierClient courierClient;
    private Courier courier;
    private String expectedMessage;
    private int expectedStatusCode;

    public CourierCreateWOPTest(Courier courier, String expectedMessage, int expectedStatusCode) {
        this.courier = courier;
        this.expectedMessage = expectedMessage;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][]{
                {CourierGenerator.getWithoutLogin(), "Недостаточно данных для создания учетной записи", 400,},
                {CourierGenerator.getWithoutPassword(), "Недостаточно данных для создания учетной записи", 400,},
                {CourierGenerator.getEmptyLogin(), "Недостаточно данных для создания учетной записи", 400,},
                {CourierGenerator.getEmptyPassword(), "Недостаточно данных для создания учетной записи", 400,},
                {CourierGenerator.getEmptyLoginAndPassword(), "Недостаточно данных для создания учетной записи", 400,}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    public void checkPostCreateWithoutPasswordOrLogin() {
        Response response = courierClient.createCourier(courier);
        response.then().assertThat().statusCode(expectedStatusCode)
                .and()
                .body("message", equalTo(expectedMessage));

    }
}
