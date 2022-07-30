package com.learn2code.Shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {

	@Test
	public void test(){
		int a = 2;
		int b = 3;
		Assertions.assertEquals(5,a+b);
	}

}
