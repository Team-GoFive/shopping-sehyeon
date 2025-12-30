package com.kt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Tag;
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

	@Test
	@Tag("authEmail")
	public void test2() {
		// 실패하는 테스트
		assertThat(true).isEqualTo(false);
	}
}
