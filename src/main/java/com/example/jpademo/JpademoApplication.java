package com.example.jpademo;

import com.example.jpademo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JpademoApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JpademoApplication.class);

    @Autowired
    private BookRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(JpademoApplication.class, args);
    }

    public void run(String... args){
        log.info("starting spring application");

        repository.save(new Book("Java"));
        repository.save(new Book("Python"));
        repository.save(new Book("Node"));

        System.out.println("\nfindAll()");
        repository.findAll().forEach(x -> System.out.println(x));

        System.out.println("\nfindById()");
        repository.findById(1L).ifPresent(x -> System.out.println(x));

        System.out.println("\nfindByName()");
        repository.findByName("Java").forEach(x -> System.out.println(x));


    }
}
