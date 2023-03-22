//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package donadoni.models;

public class Reservation {
    private Long reservation_id = null;
    private Long session_id = null;
    private Long user_id = null;
    private Long seat_id = null;

    public Reservation() {
        this((Long) null, (Long) null, null);
    }

    public Reservation(Long session_id, Long user_id, Long seat_id) {
        this.session_id = session_id;
        this.user_id = user_id;
        this.seat_id = seat_id;
    }

    public Long getReservation_id() {
        return this.reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Long getSession_id() {
        return this.session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getSeat_id() {
        return this.seat_id;
    }

    public void setSeat_id(Long seat_id) {
        this.seat_id = seat_id;
    }

}
