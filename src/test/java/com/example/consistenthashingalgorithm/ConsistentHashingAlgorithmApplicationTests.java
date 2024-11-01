package com.example.consistenthashingalgorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
class ConsistentHashingAlgorithmApplicationTests {

    @Test
    public void compare() throws NoSuchAlgorithmException {
        ConsistentHashing chTest = new ConsistentHashing(3);

        long hash = chTest.GetHash("veeresh");

        Assertions.assertEquals((long) 36725312,hash);
        Assertions.assertNotEquals((long) 123456, hash);
    }
}
