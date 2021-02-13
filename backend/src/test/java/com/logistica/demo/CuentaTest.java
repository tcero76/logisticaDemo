package com.logistica.demo;


import com.logistica.demo.payload.LoginRequest;
import com.logistica.demo.payload.ReqCuenta;
import com.logistica.demo.payload.UbicacionesReq;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticaBackApplication.class,
        webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CuentaTest {

    private static final Logger log = LoggerFactory.getLogger(CuentaTest.class);

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @Before
    public void before() throws SQLException, JSONException {
        ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource("init.sql"));
        ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource("cuenta.sql"));
        LoginRequest loginRequest = new LoginRequest("llastra","Waves6_");
        String response = restTemplate
                .postForObject("/signin",loginRequest,String.class);
        JSONObject json = new JSONObject(response);
        assertTrue(json.getString("accessToken")!=null);
        RestTemplate restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","Bearer "+ json.getString("accessToken"));
        log.info(response);
    }

    @Test
    public void getCuenta() throws JSONException {
        ResponseEntity<String> response = this.restTemplate.exchange("/cuentas",
                HttpMethod.GET, new HttpEntity<>(null,headers), String.class);
        log.info(response.toString());
        JSONAssert.assertEquals("[{\"idcuenta\":1,\"idzona\":2,\"nombre\":\"Y\",\"status\":\"PENDIENTE\"}]",
                response.getBody(), false);
    }

    @Test
    public void postCuenta() throws JSONException {
        ReqCuenta reqCuenta = new ReqCuenta(1);

        HttpEntity<ReqCuenta> httpEntity = new HttpEntity<>(reqCuenta, headers);
        ResponseEntity<String> response = this.restTemplate
                .exchange("/cuentas",HttpMethod.POST, httpEntity,String.class);
        log.info(response.getBody());
        JSONArray json = new JSONArray("[{\"idcuenta\":1,\"idzona\":2,\"nombre\":\"Y\",\"status\":\"PENDIENTE\"},{\"idcuenta\":2,\"idzona\":1,\"nombre\":\"X\",\"status\":\"PENDIENTE\"}]");
        JSONAssert.assertEquals(json,new JSONArray(response.getBody()),false);
        ResponseEntity<String> resultado = this.restTemplate
                .exchange("/cuentas",HttpMethod.GET,
                        new HttpEntity<>(null,headers),
                        String.class);
        JSONAssert.assertEquals(json, new JSONArray(resultado.getBody()),false);
        log.info(resultado.getBody());

    }
}
