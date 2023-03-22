package donadoni.models;

/**
 * Class to represent a Movie
 *
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class Cinema {
    private Long cinema_id = null;
    private String cinema_name = null;
    private String cinema_address = null;
    private String cinema_phone = null;


    public Cinema() {
        this(null, null, null);
    }

    public Cinema(String name, String address, String phone) {
        this.cinema_name = name;
        this.cinema_address = address;
        this.cinema_phone = phone;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_address() {
        return cinema_address;
    }

    public void setCinema_address(String cinema_address) {
        this.cinema_address = cinema_address;
    }

    public String getCinema_phone() {
        return cinema_phone;
    }

    public void setCinema_phone(String cinema_phone) {
        this.cinema_phone = cinema_phone;
    }

    public Long getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(Long cinema_id) {
        this.cinema_id = cinema_id;
    }
}


