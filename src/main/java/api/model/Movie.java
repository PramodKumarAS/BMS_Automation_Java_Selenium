package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    private String movieName;
    private String description;
    private int duration; // response = number
    private String genre;
    private String language;
    private String releaseDate;
    private String poster;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("__v")
    private int version;

    public String getMovieName() { return movieName; }
    public String getDescription() { return description; }
    public int getDuration() { return duration; }
    public String getGenre() { return genre; }
    public String getLanguage() { return language; }
    public String getReleaseDate() { return releaseDate; }
    public String getPoster() { return poster; }
    public String getId() { return id; }
    public int getVersion() { return version; }
}