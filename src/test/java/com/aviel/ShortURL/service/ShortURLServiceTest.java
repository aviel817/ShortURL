package com.aviel.ShortURL.service;


import com.aviel.ShortURL.model.ShortURL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ShortURLServiceTest {

    @Autowired
    private ShortURLService service;
    private static String exampleURL;

    @BeforeAll
    static void setup() {
        exampleURL = "http://www.google.com";
    }

    @Test
    void createShortURL() {

        String shortURL = service.createShortURL(exampleURL);
        System.out.println(shortURL);
        assertThat(shortURL).isNotNull();
        assertThat(shortURL).isNotEqualTo(exampleURL);
        assertThat(shortURL.length()).isEqualTo(8);
    }

    @Test
    void saveShortURL() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate date = LocalDate.now();
        System.out.println(date.format(formatter));
        String shortURL = service.createShortURL(exampleURL);

        ShortURL shortURLObj = new ShortURL(exampleURL, shortURL, 0, date.format(formatter), date.plusDays(7).format(formatter));
        System.out.println(shortURLObj.toString());
        assertThat(shortURLObj).isNotNull();

        service.saveShortURL(shortURLObj);

        Optional<ShortURL> savedShortURLOpt = service.getShortURLById(shortURLObj.getId()); // Replace 'getshortURL' with the actual method to retrieve the saved object
        assertThat(savedShortURLOpt).isPresent();

        ShortURL savedShortURLObj = savedShortURLOpt.get();
        System.out.println(savedShortURLObj.toString());
        assertThat(savedShortURLObj).isNotNull();
        assertThat(savedShortURLObj.getOriginalURL()).isEqualTo(exampleURL);
        assertThat(savedShortURLObj).isEqualTo(shortURLObj);
    }

    @Test
    void increaseViewsByOne() {
        String shortURL = service.createShortURL(exampleURL);
        ShortURL shortURLObj = new ShortURL(exampleURL, shortURL);
        assertThat(shortURLObj.getVisits()).isEqualTo(0);
        service.saveShortURL(shortURLObj);
        System.out.println(shortURLObj.toString());

        for (int i=0; i < 100; i++) {
            service.increaseViewsByOne(shortURLObj.getId());
        }
        Optional<ShortURL> updatedshortURLOpt = service.getShortURLById(shortURLObj.getId());
        assertThat(updatedshortURLOpt).isPresent();
        ShortURL updatedShortURLObj = updatedshortURLOpt.get();

        assertThat(updatedShortURLObj.getVisits()).isEqualTo(100);
        System.out.println(updatedShortURLObj.toString());
    }

    @Test
    void checkIfShortURLExists() {
        String shortURL = service.createShortURL(exampleURL);
        ShortURL shortURLObj1 = new ShortURL(exampleURL, shortURL);
        ShortURL shortURLObj2 = new ShortURL(exampleURL, shortURL);

        if (!service.checkIfShortURLExists(shortURL)) {
            service.saveShortURL(shortURLObj1);
        }

        assertThat(shortURLObj1.getId()).isNotNull();
        Optional<ShortURL> updatedShortURLOpt1 = service.getShortURLById(shortURLObj1.getId());
        assertThat(updatedShortURLOpt1).isPresent();


        if (!service.checkIfShortURLExists(shortURL)) {
            service.saveShortURL(shortURLObj2);
        }

        assertThat(shortURLObj2.getId()).isNull();

    }

/**
    @Test
    void getRedirectURL() {
        String shortURL = service.createShortURL(exampleURL);
        ShortURL shortURLObj = new ShortURL(exampleURL, shortURL);
        service.saveShortURL(shortURLObj);
        String redirectURL = service.getRedirectURL(shortURL);
        assertThat(redirectURL).isNotEqualTo("");
    }
    **/
}