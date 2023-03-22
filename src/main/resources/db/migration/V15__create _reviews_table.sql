CREATE TABLE reviews(
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating INT NOT NULL,
    comments VARCHAR,
    likes INT,
    CONSTRAINT fk_movie_id_reviews FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    CONSTRAINT fk_user_id_reviews FOREIGN KEY (user_id) REFERENCES users(user_id)

 );


