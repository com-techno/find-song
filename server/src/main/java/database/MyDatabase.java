package database;

import objects.*;
import objects.forms.*;

import java.util.List;

public interface MyDatabase {
    void signUp(NewUserForm newUser) throws Exception;
    String signIn(User user) throws Exception;

    void addSong(NewSongForm newSong) throws Exception;
    Song getSong(int id) throws Exception;
    List<Song> getTop(int count) throws Exception;
    void like(LikeForm like) throws Exception;

    void deleteSong(DeleteSongForm deleteSong) throws Exception;
}