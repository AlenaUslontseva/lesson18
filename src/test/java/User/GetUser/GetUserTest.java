package User.GetUser;

import User.UserBaseTest;
import dto.CreateUser;
import dto.GetUser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class GetUserTest extends UserBaseTest {
    CreateUser user;
    ObjectMapper mapper = new ObjectMapper();
    @Test
    public void getExistingUser() throws IOException {
        CreateUser userPattern = mapper.readValue(new File("src/main/resources/user.json"), CreateUser.class);
        user = CreateUser.builder()
                .userStatus(userPattern.getUserStatus())
                .email(userPattern.getEmail())
                .firstName(userPattern.getFirstName())
                .lastName(userPattern.getLastName())
                .id(userPattern.getId())
                .username(userPattern.getUsername())
                .password(userPattern.getPassword())
                .phone(userPattern.getPhone())
                .build();

        userApi.createUser(user)
                .then()
                .body("code", equalTo(200));

        GetUser responseBody = userApi.getUser("tester")
                .then()
                .statusCode(200)
                .extract().body().as(GetUser.class);

        Assertions.assertEquals(userPattern.getUsername(), responseBody.getUsername());
        Assertions.assertEquals(userPattern.getFirstName(), responseBody.getFirstName());
        Assertions.assertEquals(userPattern.getLastName(), responseBody.getLastName());
        Assertions.assertEquals(userPattern.getEmail(), responseBody.getEmail());
        Assertions.assertEquals( userPattern.getUserStatus(), responseBody.getUserStatus());
        Assertions.assertEquals(userPattern.getPassword(), responseBody.getPassword());
        Assertions.assertEquals(userPattern.getPhone(), responseBody.getPhone());
    }

    @Test
    public void getNotExistingUser(){
        userApi.getUser("fffrfvdfsf")
                .then()
                .statusCode(404);
    }
}
