package com.novatech.cybertech;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class CybertechApplicationTests {

	@Test
	void contextLoads() {
		// TODO: contet load test
	}

}
