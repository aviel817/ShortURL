package com.aviel.ShortURL.controller;

import com.aviel.ShortURL.model.ShortURL;
import com.aviel.ShortURL.service.ShortURLService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ShortURLController {

    private final ShortURLService shortURLService;
    private final HttpServletRequest request;

    @Autowired
    public ShortURLController(ShortURLService shortURLService, HttpServletRequest request) {
        this.shortURLService = shortURLService;
        this.request = request;
    }

    @GetMapping("/")
    public String sayHello(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return "Hello there! Client IP Address: " + ipAddress;
    }

    @GetMapping("/test")
    public ResponseEntity test(@RequestBody String originalURL) {
        try {
            String res = shortURLService.createShortURL(originalURL);
            return new ResponseEntity(res, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/shorturl/create")
    public ResponseEntity getShortURL(@RequestBody String originalURL) {
        String shortURL = "";
        do {
            shortURL = shortURLService.createShortURL(originalURL);
        } while (shortURLService.checkIfShortURLExists(shortURL));
        ShortURL shortURLObj = new ShortURL(originalURL, shortURL);
        shortURLService.saveShortURL(shortURLObj);
        return new ResponseEntity("Created new short link: " + request.getLocalAddr() + shortURLObj.getShortURL(), HttpStatus.OK);
    }

    @GetMapping("/redirect/{shortURL}")
    public RedirectView redirect(@PathVariable String shortURL) {
        ShortURL shortURLObj = shortURLService.getShortURLObjByShortURL(shortURL);
        if (shortURLObj == null) {
            return new RedirectView("/");
        }
        String redirectURL = shortURLObj.getOriginalURL();
        System.out.println(redirectURL);

        if(!redirectURL.startsWith("www.") && !redirectURL.startsWith("http://")) {
            redirectURL = "www."+redirectURL;
        }
        if (!redirectURL.startsWith("http://")){
            redirectURL = "http://"+redirectURL;
        }
        shortURLService.increaseViewsByOne(shortURLObj.getId());
        return new RedirectView(redirectURL);

    }

}
