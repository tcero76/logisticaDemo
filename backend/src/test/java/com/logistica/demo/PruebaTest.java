package com.logistica.demo;

import com.logistica.demo.payload.LoginRequest;
import com.logistica.demo.payload.UbicacionesReq;
import com.logistica.demo.payload.UbicacionesRes;
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
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.AssertTrue;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticaBackApplication.class,webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PruebaTest {

    private static final Logger log = LoggerFactory.getLogger(PruebaTest.class);

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @Before
    public void before() throws SQLException, JSONException {
        ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(),
                new ClassPathResource("create.sql"));
        LoginRequest loginRequest = new LoginRequest("llastra","Waves6_");
        String response = restTemplate
                .postForObject("/signin",loginRequest,String.class);
        JSONObject json = new JSONObject(response);
        assertTrue(json.getString("accessToken")!=null);
        RestTemplate restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","Bearer  "+ json.getString("accessToken"));
        log.info(response);
    }

    @Test
    public void materialesTest() throws JSONException {
        ResponseEntity<String> response = this.restTemplate.exchange("/materiales",
                HttpMethod.GET, new HttpEntity<>(null,headers), String.class);
        log.info("el primer material es {}", response.toString());
        JSONAssert.assertEquals("[{idmaterial:1,nombre:\"tijera\",precio:5066}]",
                response.getBody(), true);
    }

    @Test
    public void getAlmacenesTest() throws JSONException  {
        ResponseEntity<String> response = this.restTemplate
                .exchange("/almacenes",HttpMethod.GET,
                        new HttpEntity<>(null,headers),
                        String.class);
        JSONArray json = new JSONArray("[{\"nombre\":\"Y\",\"id\":2,\"niveles\":[{\"nombre\":\"1\",\"id\":1,\"poses\":[{\"nombre\":\"A\",\"id\":1,\"ubicacion\":\"Y/1/A\"}]}]}]");
        JSONAssert.assertEquals(json, new JSONArray(response.getBody()),true);
        log.info(response.toString());
    }

    @Test
    public void postAlmacencesTest() throws JSONException {
        Map<String,Object> pos = new HashMap<>();
        pos.put("nombre","A");
        pos.put("id",1);
        List<Object> poses = new ArrayList<>();
        poses.add(pos);
        Map<String,Object> nivel = new HashMap<>();
        nivel.put("poses",poses);
        nivel.put("nombre","A");
        nivel.put("id",1);
        List<Object> niveles = new ArrayList<>();
        niveles.add(nivel);
        UbicacionesReq ubicacionesReq = new UbicacionesReq("zonabonita",2,niveles);
        List<UbicacionesReq> ubicacionesReqs = new ArrayList<>();
        ubicacionesReqs.add(ubicacionesReq);

        HttpEntity<List<UbicacionesReq>> httpEntity = new HttpEntity<>(ubicacionesReqs, headers);
        ResponseEntity<String> response = this.restTemplate
                .exchange("/almacenes",HttpMethod.POST, httpEntity,String.class);
        log.info(response.getBody());
        assertTrue((new JSONObject(response.getBody())).getBoolean("confirmacion"));
        ResponseEntity<String> resultado = this.restTemplate
                .exchange("/almacenes",HttpMethod.GET,
                        new HttpEntity<>(null,headers),
                        String.class);
        JSONArray json = new JSONArray("[{\"nombre\":\"zonabonita\",\"id\":2,\"niveles\":[{\"nombre\":\"A\",\"id\":1,\"poses\":[{\"nombre\":\"A\",\"id\":1,\"ubicacion\":\"zonabonita/A/A\"}]}]}]");
        JSONAssert.assertEquals(json, new JSONArray(resultado.getBody()),true);
        log.info(resultado.getBody());
    }
}
