//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package donadoni.models;

public class Waitlist {
    private Long wait_id = null;
    private Long session_id = null;
    private Long user_id = null;

    public Waitlist() {
        this((Long) null, (Long) null);
    }

    public Waitlist(Long session_id, Long user_id) {
        this.session_id = session_id;
        this.user_id = user_id;
    }

    public Long getWait_id() {
        return this.wait_id;
    }

    public void setWait_id(Long wait_id) {
        this.wait_id = wait_id;
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
}
