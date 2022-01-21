package pw.mihou.amaririsu.models;

import pw.mihou.amaririsu.features.reflective.annotations.AmaririsuContract;

import java.util.List;

@AmaririsuContract
public class Story {

    private int id;
    private String title;
    private String synopsis;
    private String url;
    private String image;
    private String creator;
    private String views;
    private Double rating;
    private long favorites;
    private int chapters;
    private int chaptersPerWeek;
    private int ratings;
    private int readers;
    private List<String> genres;
    private List<String> tags;

    /**
     * Retrieves the story's RSS feed URL.
     * @return RSS feed URL.
     */
    public String getRSSURL(){
        return "https://www.scribblehub.com/rssfeed.php?type=series&sid=" + id;
    }

    /**
     * Returns the story's identification number.
     * @return the story's identification number.
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the title of the story.
     * @return the title of the story.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the tags of the story.
     * @return the tags of the story.
     */
    public List<String> getTags(){
        return tags;
    }

    /**
     * Gets the genres of the story.
     * @return genres of the story.
     */
    public List<String> getGenres(){
        return genres;
    }

    /**
     * Gets the synopsis of the story.
     * @return synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Retrieves the URL of the story as string form.
     * @return url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the thumbnail of the story.
     * @return image url.
     */
    public String getThumbnail() {
        return image;
    }

    /**
     * Returns the story creator.
     * @return story creator's name.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Get's the story rating.
     * @return story rating.
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Gets the total view count of the story.
     * @return the total view count of the story.
     */
    public String getViews() {
        return views;
    }

    /**
     * Gets the total favorites of the story.
     * @return the total favorites of the story.
     */
    public long getFavorites() {
        return favorites;
    }

    /**
     * Gets the total chapter count of the story.
     * @return the total chapter count.
     */
    public int getChapters() {
        return chapters;
    }

    /**
     * Gets the chapter per week count of the story.
     * @return the chapter per week.
     */
    public int getChapterPerWeek() {
        return chaptersPerWeek;
    }

    /**
     * Gets the reader count of the story.
     * @return the reader count of the story.
     */
    public int getReaders() {
        return readers;
    }

    /**
     * Gets the total rating of the story.
     * @return the total rating.
     */
    public int getRatings() {
        return ratings;
    }


}
