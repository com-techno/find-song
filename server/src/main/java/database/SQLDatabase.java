package database;

import objects.Song;
import objects.User;
import objects.forms.DeleteSongForm;
import objects.forms.LikeForm;
import objects.forms.NewSongForm;
import objects.forms.NewUserForm;
import util.DatabaseUtils;
import util.HashUtils;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static util.HashUtils.encode;

public class SQLDatabase implements MyDatabase {

    DatabaseUtils db;

    public SQLDatabase() throws SQLException, ClassNotFoundException {
        db = new DatabaseUtils();
    }

    @Override
    public void signUp(NewUserForm newUser) throws Exception {
        try {
            String query = "INSERT INTO user (login, passhash" + (newUser.getEmail() == null ? "" : ", email") + ") " +
                    "VALUES (\"" + newUser.getLogin() + "\", " +
                    "\"" + new String(encode(newUser.getPassword()), StandardCharsets.UTF_16) + "\"" + (newUser.getEmail() == null ? "" : ", " +
                    "\"" + newUser.getEmail() + "\"") + ");";
            System.out.println(query);
            db.execSqlUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String signIn(User user) throws Exception {
        String pass = new String(encode(user.passHash), StandardCharsets.UTF_16);
        String query = "SELECT * FROM user WHERE login=\'" + user.login + "\'";
        System.out.println(query);
        ResultSet resSet = db.execSqlQuery(query);
        if (resSet.isClosed()) throw new Exception("Account doesn't exists");
        String passhash = resSet.getString("passhash");
        resSet.close();
        if (pass.equals(passhash))
            return HashUtils.getToken(user);
        else throw new Exception("Password is incorrect");
    }

    @Override
    public void addSong(NewSongForm newSong) throws Exception {
        try {
            String query = "INSERT INTO song (author, title, lyrics, icon) " +
                    "VALUES (\"" + newSong.getAuthor() + "\", " +
                    "\"" + newSong.getTitle() + "\", " +
                    "\"" + newSong.getText() + "\", " +
                    "\"" + newSong.getIcon() + "\");";
            System.out.println(query);
            db.execSqlUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Song getSong(int id) throws Exception {
        String query = "SELECT * FROM song WHERE id=" + id + ";";
        System.out.println(query);
        ResultSet rs = db.execSqlQuery(query);
        return new Song(rs.getString("author"), rs.getString("title"), rs.getString("icon"),
                rs.getString("lyrics"), rs.getString("published"), rs.getString("edited"),
                rs.getInt("id"), rs.getInt("likes"), rs.getInt("dislikes"));
    }

    @Override
    public List<Song> getTop(int count) throws Exception {
        List<Song> top = new LinkedList<>();
        ResultSet rs = db.execSqlQuery("SELECT * INTO song ORDER BY likes, dislikes");
        if (rs.isClosed()) throw new Exception("No data found");
        for (int i = 0; i < count && !rs.isClosed(); i++) {
            Song topSong = new Song(rs.getString("author"), rs.getString("title"), rs.getString("icon"),
                    rs.getString("lyrics"), rs.getString("published"), rs.getString("edited"),
                    rs.getInt("id"), rs.getInt("likes"), rs.getInt("dislikes"));
            top.add(topSong);
            rs.next();
        }
        return top;
    }

    @Override
    public void like(LikeForm like) throws Exception {
        String query = "UPDATE song SET ";
        switch (like.getLike()){
            case -2:
                query+="dislikes=dislikes-1 WHERE dislikes != 0 AND id= ";
            case -1:
                query+="dislikes=dislikes+1 WHERE id= ";
                break;
            case 1:
                query+="likes=likes+1 WHERE id= ";
                break;
            case 2:
                query+="likes=likes-1 WHERE likes != 0 AND id= ";
                break;
        }
        query+= like.getArticleId();
        db.execSqlUpdate(query);
    }

    @Override
    public List<Song> search(String query) throws Exception {
        query = query.replace(" ", "")
                .replace(",", "")
                .replace(".", "")
                .replace(":", "");
        List<Song> top = new LinkedList<>();
        ResultSet rs = db.execSqlQuery("SELECT * INTO song");
        if (rs.isClosed()) throw new Exception("No data found");
        do {
            String lyrics = rs.getString("lyrics")
                    .replace(" ", "")
                    .replace(",", "")
                    .replace(".", "")
                    .replace(":", "");
            if (lyrics.contains(query)) {
                Song topSong = new Song(rs.getString("author"), rs.getString("title"), rs.getString("icon"),
                        rs.getString("lyrics"), rs.getString("published"), rs.getString("edited"),
                        rs.getInt("id"), rs.getInt("likes"), rs.getInt("dislikes"));
                top.add(topSong);
                rs.next();
            }

        } while (!rs.isClosed());
        return top;
    }

    @Override
    public void deleteSong(DeleteSongForm deleteArticle) throws Exception {
        if (db.execSqlQuery("SELECT author INTO song WHERE id=" + deleteArticle.getSongId()).getString("author").equals(deleteArticle.getAuthor()))
            db.execSqlUpdate("DELETE FROM song WHERE id=" + deleteArticle.getSongId());
        else throw new Exception("It is not your song");
    }
}
