package com.aviel.ShortURL.service;

import com.aviel.ShortURL.repository.ShortURLRepository;
import com.aviel.ShortURL.model.ShortURL;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class ShortURLService {

    private ShortURLRepository shortURLRepository;
    private static Integer SHORT_URL_SIZE = 8;
    public ShortURLService() { }
    @Autowired
    public ShortURLService(ShortURLRepository shortURLRepository) {
        this.shortURLRepository = shortURLRepository;
    }
    public String createShortURL(String original_url) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < SHORT_URL_SIZE; i++) {
            char c = alphabet.charAt(r.nextInt(alphabet.length()));
            sb.append(c);
        }

        return sb.toString();
    }
    @Transactional
    public void saveShortURL(ShortURL shortURLObj) {
        shortURLRepository.save(shortURLObj);
    }

    public Optional<ShortURL> getShortURLById(Integer id) {
        return shortURLRepository.findById(id);
    }

    @Transactional
    public void deleteShortURL(Integer id) {
        shortURLRepository.deleteById(id);
    }

    @Transactional
    public void increaseViewsByOne(Integer id) {
        ShortURL shortURLObj = shortURLRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Short URL not found with id: " + id));

        shortURLObj.setVisits(shortURLObj.getVisits() + 1);
        shortURLRepository.save(shortURLObj);
    }

    public boolean checkIfShortURLExists(String shortURL) {
        return shortURLRepository.existsByShortURL(shortURL);
    }

    public ShortURL getShortURLObjByShortURL(String shortURL) {
        if(checkIfShortURLExists(shortURL)) {
            return shortURLRepository.findByShortURL(shortURL);
        }
        else {
            return null;
        }
    }

}
