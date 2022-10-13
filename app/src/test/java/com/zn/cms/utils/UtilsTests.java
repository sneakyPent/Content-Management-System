package com.zn.cms.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.zn.cms.utils.Utils.generateRandomPasswordToken;

@SpringBootTest
class UtilsTests {

    @Test
    void generateRandomPasswordTokenTest() {
        String generateRandomPasswordToken = generateRandomPasswordToken();
        assert generateRandomPasswordToken.matches("[ -~]+");
    }
}
