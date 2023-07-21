package com.aviel.ShortURL.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String originalURL;
    @Column(unique = true)
    private String shortURL;
    private Integer visits;
    private String creationDate;
    private String expirationDate;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    protected ShortURL() { }

    public ShortURL(String originalURL, String shortURL, Integer visits, String creationDate, String expirationDate) {
        this.originalURL = originalURL;
        this.shortURL = shortURL;
        this.visits = visits;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public ShortURL(String originalURL, String shortURL) {
        LocalDate date = LocalDate.now();
        this.originalURL = originalURL;
        this.shortURL = shortURL;
        this.visits = 0;
        this.creationDate = date.format(formatter);
        this.expirationDate = date.plusDays(7).format(formatter);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortURL shortURL1 = (ShortURL) o;
        return originalURL.equals(shortURL1.originalURL) && shortURL.equals(shortURL1.shortURL) && visits.equals(shortURL1.visits) && creationDate.equals(shortURL1.creationDate) && expirationDate.equals(shortURL1.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalURL, shortURL, visits, creationDate, expirationDate);
    }

    @Override
    public String toString() {
        return "ShortURL{" +
                "id=" + id +
                ", originalURL='" + originalURL + '\'' +
                ", shortURL='" + shortURL + '\'' +
                ", visits=" + visits +
                ", creationDate='" + creationDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
