package com.logistica.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	classes = LogisticaBackApplication.class)
public class materialTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
 
    @Test
    public void materialList() {
		ResponseEntity<String> resOrec = this.restTemplate.exchange("/material/listar", HttpMethod.GET, null, String.class);
        assertThat(resOrec.getBody()).contains("tijera");
    }
}