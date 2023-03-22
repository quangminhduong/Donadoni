//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package donadoni.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Session {
    private Long session_id = null;
    private Long movie_id = null;
    private Long cinema_id = null;
    private Integer available = null;
    private LocalDateTime showing = null;
    private String status = null;

    public Session() {
        this((Long) null,null, (Integer) null, (LocalDateTime) null);
    }

    public Session(Long movie_id, Long cinema_id, Integer available, LocalDateTime showing) {
        this.movie_id = movie_id;
        this.cinema_id = cinema_id;
        this.available = available;
        this.showing = showing;
        this.status = null;
    }

    public Long getSession_id() {
        return this.session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public Long getMovie_id() {
        return this.movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getAvailable() {
        return this.available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public LocalDateTime getShowing() {
        return this.showing;
    }

    public void setShowing(LocalDateTime showing) {
        this.showing = showing;
    }

    public java.lang.String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(Long cinema_id) {
        this.cinema_id = cinema_id;
    }

    public void updateStatus() {
        LocalDateTime currentdate = LocalDateTime.now();

        long diffDays = ChronoUnit.DAYS.between(currentdate, showing);
        long diffMinutes = ChronoUnit.MINUTES.between(showing, currentdate);

        if (currentdate.isBefore(showing)) {
            if (diffDays <= 14) {
                this.status = "OPEN";
            } else {
                this.status = "SCHEDULED";
            }
        } else {
            this.status = "CLOSED";
        }

        return;
    }

    public String toString() {
        return "Session{id=" + this.session_id + ", movie='" + this.movie_id + "', showing=" + this.showing + ", availability=" + this.available + "}";
    }
}
