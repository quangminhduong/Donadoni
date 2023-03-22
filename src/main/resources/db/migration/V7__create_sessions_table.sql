CREATE TABLE sessions(
    session_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT NOT NULL,
    cinema_id INT NOT NULL,
    CONSTRAINT fk_movie_id FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    CONSTRAINT fk_cinema_id FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id),
    available INT NOT NULL,
    showing TIMESTAMP NOT NULL,
    status VARCHAR(255)
 );


