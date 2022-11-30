package Order;

import Client.Client;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private final String ORDER_CREATE = "/api/v1/orders";
    private final String ORDER_DELETE = "/api/v1/orders/cancel?track={jsonTrack}";
    private final String ORDER_LIST = "/api/v1/orders";

    public Response orderCreate(Order order) {
        Response response = given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_CREATE);
        return response;
    }

    public void orderDelete(Order order, int track) {
        Response response = given()
                .spec(getSpec())
                .body(order)
                .when()
                .put(ORDER_DELETE, track);
        response.then().statusCode(200);
    }

    public Response orderList() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER_LIST);
    }
}