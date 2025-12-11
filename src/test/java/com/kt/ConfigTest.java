package com.kt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kt.config.CommonProperties;
import com.kt.config.TestProperties;

@ActiveProfiles("test")
@SpringBootTest
public class ConfigTest {

	@Autowired
	CommonProperties commonProperties;

	@Autowired
	TestProperties testProperties;

	@Test
	public void test() {

		System.out.println(commonProperties.commonValue());
		System.out.println(commonProperties.commonValueParam());

		System.out.println(testProperties.myValue());
		System.out.println(testProperties.myValueParam());
	}
}
