package com.example.apiordenadores.controladores;

import com.example.apiordenadores.ApiOrdenadoresApplication;
import com.example.apiordenadores.entidades.Laptop;
import com.example.apiordenadores.repositorios.LaptopRepository;
import net.minidev.json.JSONObject;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.*;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //para poder Ordenar la ejecucion de los metodos
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder builder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setPort(){
        builder = builder.rootUri("http://localhost:"+port);

        testRestTemplate = new TestRestTemplate(builder);

    }


    @Test
    @Order(3)
    void findAllTest() {

        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/laptops",Laptop[].class);

        assertEquals(HttpStatus.OK,response.getStatusCode());

        List<Laptop> lista = Arrays.asList(response.getBody());

        System.out.println(lista.size());

        assertTrue(1 == lista.size());
    }

    @Test
    @Order(2)
    void findOneByIdTest() {

        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/laptops/1",Laptop.class);

        Laptop result = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());

        assertEquals(1L, result.getId());
    }

    @Test
    @Order(1)
    void createTest() {


        HttpHeaders headers = new HttpHeaders();

        //configuramos lo que vamos enviar y a recibir
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //configuramos el objeto que vamos a guardar
        JSONObject json = new JSONObject();
        json.put("brand", "HP");
        json.put("price", 888.888);
        json.put("processor", "Intel i10");
        json.put("screen", 18);

        //creamos la peticion
        HttpEntity<JSONObject> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/laptops",HttpMethod.POST,request,Laptop.class);

        Laptop result = response.getBody();

        System.out.println(result.getId());

        assertTrue(1L == result.getId());
    }

    @Test
    @Order(6)
    void deleteAllTest(){

        HttpHeaders headers = new HttpHeaders();

        //configuramos lo que vamos enviar y a recibir
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //configuramos el objeto que vamos a guardar
        JSONObject json = new JSONObject();


        //creamos la peticion
        HttpEntity<JSONObject> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/laptops",HttpMethod.DELETE,request,Laptop.class);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());



    }

    @Test
    @Order(5)
    void deleteById(){
        HttpHeaders headers = new HttpHeaders();

        //configuramos lo que vamos enviar y a recibir
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //configuramos el objeto que vamos a guardar
        JSONObject json = new JSONObject();
        json.put("id",1L);

        //creamos la peticion
        HttpEntity<JSONObject> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/laptops/1",HttpMethod.DELETE,request,Laptop.class);



        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

    }

    @Test
    @Order(4)
    void updateTest() {


        HttpHeaders headers = new HttpHeaders();

        //configuramos lo que vamos enviar y a recibir
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //configuramos el objeto que vamos a guardar
        JSONObject json = new JSONObject();
        json.put("id",1L);
        json.put("brand", "lenovo");
        json.put("price", 999.888);
        json.put("processor", "Intel i5");
        json.put("screen", 16);

        //creamos la peticion
        HttpEntity<JSONObject> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/laptops",HttpMethod.PUT,request,Laptop.class);

        Laptop result = response.getBody();

        System.out.println(result.getId());

        assertTrue(1L == result.getId());
    }

}