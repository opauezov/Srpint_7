package Courier;

import Client.Client;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private final String CREATE_COURIER = "/api/v1/courier";
    private final String LOGIN_COURIER = "/api/v1/courier/login";
    private final String DELETE_COURIER = "api/v1/courier/{jsonID}";

    public Response createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    public Response loginCourier(Credentials courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    public void deleteCourier(int id) {
        given()
                .spec(getSpec())
                .when()
                .delete(DELETE_COURIER, id)
                .then().statusCode(200);
    }
}