# ShortURL Project
The ShortURL project is a service that offers efficient access to shortened URLs by effectively managing URL mappings. It has been implemented using Java Spring Boot, a RESTful API architecture, and PostgreSQL for data storage.

## Features

- URL Shortening: The core functionality of the project is the ability to convert long URLs into short, more manageable forms.
- URL Mapping: The service maintains a mapping between the original URLs and their corresponding shortened versions, allowing for easy redirection.
- API Interface: The project exposes a RESTful API for interacting with the URL shortening service programmatically.
- Data Persistence: The PostgreSQL database is utilized to store and manage the URL mappings securely.
- Analytics (Optional): An optional feature could include basic analytics tracking, such as click counts for shortened URLs.

## Technologies

- Java Spring Boot
- RESTful API
- PostgreSQL

## Installation

1. Clone the Repository: Clone this repository to your local machine using git clone.
2. Database Configuration: Modify the application.properties file to configure the PostgreSQL database connection (URL, username, password).
3. Build and Run: Use a Java development environment to build and run the Spring Boot application.

## API Endpoints

- POST /shorturl/create: Endpoint for shortening a long URL. Accepts a JSON request with the original URL. Returns the shortened URL.
- GET /{shortCode}: Redirects to the original URL associated with the given short code.
