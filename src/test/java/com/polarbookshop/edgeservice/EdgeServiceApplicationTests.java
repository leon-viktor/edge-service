package com.polarbookshop.edgeservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EdgeServiceApplicationTests {

	private static final int REDIS_PORT = 6379;

	@Container
	static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.2"))
			.withExposedPorts(REDIS_PORT);

	@MockitoBean
	ReactiveClientRegistrationRepository clientRegistrationRepository;

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", () -> redis.getHost());
		registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
	}

	@Test
	void verifyThatSpringContextLoads() {
	}

}
