package models;

import by.bsac.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserModelTestCase {

    private User user = new User();

    @Test
    void getters_setters() {

        user.setUserId(3);
        user.setUserIdAlias("admin");

        Assertions.assertEquals(3, user.getUserId());
        Assertions.assertEquals("admin", user.getUserIdAlias());
    }
}
