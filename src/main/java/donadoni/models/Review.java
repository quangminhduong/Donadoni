//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package donadoni.models;

public class Review {
    private Long review_id = null;
    private Long movie_id = null;
    private Long user_id = null;
    private Integer rating = null;
    private String comments = null;
    private Integer likes = null;

    public Review() {
        this((Long) null, (Long) null, null,null,null);
    }

    public Review(Long user_id, Long movie_id, Integer rating, String comments, Integer likes) {
        this.movie_id = movie_id;
        this.user_id = user_id;
        this.rating = rating;
        this.comments = comments;
        this.likes = likes;
    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}