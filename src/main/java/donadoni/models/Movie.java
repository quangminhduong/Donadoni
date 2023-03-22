package donadoni.models;

/**
 * Class to represent a Movie
 *
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class Movie {
    private Long movie_id = null;
    private String title = null;
    private Integer releaseYear = null;
    private String synopsis = null;


    public Movie() {
        this(null, null);
    }

    public Movie(String title, Integer releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.synopsis = null;
    }

    public Long getId() {
        return movie_id;
    }

    public void setId(Long id) {
        this.movie_id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + movie_id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }

}
