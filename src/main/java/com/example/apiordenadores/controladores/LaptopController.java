package com.example.apiordenadores.controladores;

import com.example.apiordenadores.entidades.Laptop;
import com.example.apiordenadores.repositorios.LaptopRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private LaptopRepository repository;

    public LaptopController(LaptopRepository repository) {
        this.repository = repository;
    }

    @Value("${app.message}")
    String message;


    @GetMapping("/hello")
    public String hello(){
        System.out.println(message);
        return "hello desde controlador";
    }


    @GetMapping("/laptops")
    public List<Laptop> findAll(){
        return repository.findAll();
    }

    @GetMapping("/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){
        Optional<Laptop> optional = repository.findById(id);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){

        if(laptop.getId()!=null){
            return ResponseEntity.badRequest().build();
        }else{
            Laptop laptop1 = repository.save(laptop);
            return ResponseEntity.ok(laptop1);
        }
    }


    @PutMapping("/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){

        if(laptop.getId()==null){
            return ResponseEntity.badRequest().build();

        }if(!repository.existsById(laptop.getId())){
            return ResponseEntity.notFound().build();

        }else{
            Laptop laptop1 = repository.save(laptop);
            return ResponseEntity.ok(laptop1);
        }

    }

    @DeleteMapping("/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){

        repository.deleteById(id);

        return ResponseEntity.noContent().build();


    }


    @DeleteMapping("/laptops")
    public ResponseEntity<Laptop> deleteAll(){

        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }




}
