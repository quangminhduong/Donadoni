CREATE TABLE seats(
    seat_id INT NOT NULL,
    cinema_id INT NOT NULL,
    CONSTRAINT fk_cinema_id_seats FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id),
    PRIMARY KEY (seat_id, cinema_id));
 );


