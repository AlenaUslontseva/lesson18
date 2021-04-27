package User.CreateUser;

import dto.CreateUser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import services.UserApi;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    CreateUser user;
    ObjectMapper mapper = new ObjectMapper();


    /**
     * Тест1 . Проверка создания пользователя со всеми полями
     *Отправить запрос на /user со всеми заполненвми полями.
     *Проверить, что статус 200, в поле message возвращается id  пользователя.
     * */
    @Test
    public void createNewUserWithAllFields() throws IOException {
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

        UserApi userApi = new UserApi();
        userApi.createUser(user)
               .then()
               .body("code", equalTo(200))
                .body("message", equalTo("12"));
    }

    /**
     *  Тест2. Проверить, что успешно создасться пользователь с двумя параметрами(username и id)
     *  Отправить запрос на /user с username и id.
     *  Проверить, что статус 200, в поле message возвращается id  пользователя.
     */
    @Test
    public void createNewUserWithTwoFields() {
        user = CreateUser.builder()
                .id((long) 17)
                .username("testname2")
                .build();

        UserApi userApi = new UserApi();
        userApi.createUser(user)
                .then()
              .body("code", equalTo(200))
               .body("message", equalTo("17"));
    }
}
