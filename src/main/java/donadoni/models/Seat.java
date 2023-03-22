package donadoni.models;

public class Seat {
    private Long seat_id = null;


    public Seat() {
        this(null);
    }

    public Seat(Long seat_id) {
        this.seat_id = seat_id;
    }

    public Long getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Long seat_id) {
        this.seat_id = seat_id;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" +
                '}';
    }

}
