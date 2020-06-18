package objects;

import java.util.Calendar;

import objects.forms.LikeForm;
import objects.forms.NewSongForm;

public class Song {

    String author;
    String title;

    String icon;

    String audio;
    String text;
    TimeStamp publishTime;
    TimeStamp editTime = null;
    int id;
    int likes;
    int dislikes;


    public Song(String author, String title, String icon, String text, String publishTime, String editTime, int id, int likes, int dislikes) {
        this.author = author;
        this.title = title;
        this.icon = icon;
        this.audio = audio;
        this.text = text;
        this.publishTime = new TimeStamp(publishTime);
        this.editTime = new TimeStamp(editTime);
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Song(NewSongForm newArticle) throws Exception {
        checkCompletion(newArticle);
        this.author = newArticle.getAuthor();
        this.title = newArticle.getTitle();
        this.text = newArticle.getText();
        this.icon = newArticle.getIcon();
        this.publishTime = new TimeStamp(Calendar.getInstance());
    }

    public void checkCompletion(NewSongForm newArticle) throws Exception {
        if (newArticle.getAuthor() == null || newArticle.getTitle() == null || newArticle.getText() == null)
            throw new Exception("Form is incomplete");
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishTime(TimeStamp time) {
        this.publishTime = time;
    }

    public String getAuthor() {
        return author;
    }

    public void giveId(int id) {
        this.id = id;
    }


    public void changeLikes(LikeForm like) {
        switch (like.getLike()) {
            case -2:
                if (dislikes != 0) dislikes--;
                break;
            case -1:
                dislikes++;
                break;
            case 1:
                likes++;
                break;
            case 2:
                if (likes != 0) likes--;
                break;
        }
    }
}
