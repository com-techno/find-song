package database;

import objects.*;
import objects.forms.*;

import java.util.List;

public interface MyDatabase {
    void signUp(NewUserForm newUser) throws Exception;
    String signIn(User user) throws Exception;

    int addArticle(NewSongForm newArticle) throws Exception;
    List<Song> getArticles() throws Exception;
    Song getArticle(int id) throws Exception;
    void like(LikeForm like) throws Exception;

    void deleteArticle(DeleteSongForm deleteArticle) throws Exception;
}