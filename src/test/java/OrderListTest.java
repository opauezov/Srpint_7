import Order.OrderClient;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void checkOrderList() {
        Response response = orderClient.orderList();
        response.then().statusCode(200).and().body("orders", notNullValue());
        response.then().body("orders.id", hasSize(30));
    }
}