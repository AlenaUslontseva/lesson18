package services;

import dto.CreateUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String PATH = "/user";
    private RequestSpecification spec;

    public UserApi() {
        spec = given()
                .baseUri(BASE_URI)
                .basePath(PATH)
                .contentType(ContentType.JSON);
    }

    public Response createUser(CreateUser user) {
        return
                given(spec)
                        .body(user)
                        .when()
                        .log().all()
                        .post();
    }

    public Response getUser(String name) {
        return given(spec)
                .pathParam("username", name)
                .log().all()
                .when().get("/{username}");
    }
}
