CREATE TABLE reservations(
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    session_id INT NOT NULL,
    seat_id INT NOT NULL,
    cinema_id INT NOT NULL,
    CONSTRAINT fk_session_id FOREIGN KEY (session_id) REFERENCES sessions(session_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_seat_id FOREIGN KEY (seat_id, cinema_id) REFERENCES seats(seat_id, cinema_id)

 );


