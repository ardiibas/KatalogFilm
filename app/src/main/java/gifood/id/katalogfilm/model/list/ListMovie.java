package gifood.id.katalogfilm.model.list;

public class ListMovie {

    private int id;
    private String image;
    private String title;
    private String overview;
    private String release;
    private Double vote;

    public ListMovie(int id, String image, String title, String overview, String release, Double vote) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.overview = overview;
        this.release = release;
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Double getVote() {
        return vote;
    }

    public void setVote(Double vote) {
        this.vote = vote;
    }
}
