package co.edu.ing.escuela.backamongus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.util.Collections;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class BackAmongUsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app= new SpringApplication(BackAmongUsApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));
        app.run(args);
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080; //returns default port if PORT isn't set (i.e. on localhost)
    }

    @Override
    public void run(String... args) throws Exception {
        // This method is executed after the application context is loaded
        // You can add any initialization logic here if needed
        System.out.println("BackAmongUsApplication started successfully!");
    }
}
