package com.aviel.ShortURL.repository;

import com.aviel.ShortURL.model.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortURLRepository extends JpaRepository<ShortURL, Integer> {

    boolean existsByShortURL(String shortURL);

    @Query(value = "SELECT * FROM ShortURL WHERE shortURL=?1", nativeQuery = true)
    ShortURL findByShortURL(String shortURL);
}
