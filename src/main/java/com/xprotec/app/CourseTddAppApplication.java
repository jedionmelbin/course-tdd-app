package com.xprotec.app;

import com.xprotec.app.config.FileStorageProperties;
import com.xprotec.app.domain.Checkout;
import com.xprotec.app.domain.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class CourseTddAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CourseTddAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Product firstProduct = new Product(1, "USB Drive", "128 GB USB Drive", 19, 1);
        Product secondProduct = new Product(2, "Hard Drive", "External GB USB Drive", 19, 2);

        Checkout checkout = new Checkout();
        checkout.addToCart(firstProduct);
        checkout.addToCart(secondProduct);

        checkout.pay(90F);
        checkout.complete();
    }
}
