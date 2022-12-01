import io.qameta.allure.junit4.DisplayName;
import order.Order;
import order.OrderClient;
import order.OrderGenerator;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Response response;
    private OrderClient orderClient;
    private Order order;
    private int expectedStatusCode;

    public OrderCreateTest(Order order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {

        return new Object[][]{
                {OrderGenerator.colourBlackAndGray(), 201},
                {OrderGenerator.colourBlack(), 201},
                {OrderGenerator.colourGray(), 201},
                {OrderGenerator.colourNull(), 201},
                {OrderGenerator.colourEmpty(), 201}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.colourBlackAndGray();
    }

    @After
    public void cleanUp() {
        Integer track = response.then().extract().path("track");
        orderClient.orderDelete(order, track);
    }
    @DisplayName("Создание заказа")
    @Test
    public void orderCreate() {
        response = orderClient.orderCreate(order);
        System.out.println(OrderGenerator.setDeliveryDateOrder());
        response.then().statusCode(expectedStatusCode).and().body("track", notNullValue());
    }
}