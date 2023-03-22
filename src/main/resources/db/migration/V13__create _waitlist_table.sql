CREATE TABLE waitlist(
    wait_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    session_id INT NOT NULL,
    CONSTRAINT fk_session_id_wait FOREIGN KEY (session_id) REFERENCES sessions(session_id),
    CONSTRAINT fk_user_id_wait FOREIGN KEY (user_id) REFERENCES users(user_id)

 );


