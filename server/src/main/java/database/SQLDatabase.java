package database;

import objects.Song;
import objects.User;
import objects.forms.DeleteSongForm;
import objects.forms.LikeForm;
import objects.forms.NewSongForm;
import objects.forms.NewUserForm;
import util.DatabaseUtils;
import util.HashUtils;

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
                    "\"" + new String(encode(newUser.getPassword())) + "\"" + (newUser.getEmail() == null ? "" : ", " +
                    "\"" + newUser.getEmail() + "\"") + ");";
            System.out.println(query);
            db.execSqlUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String signIn(User user) throws Exception {
        String query = "SELECT * FROM user WHERE login=\"" + user.login + "\"";
        System.out.println(query);
        ResultSet resSet = db.execSqlQuery(query);
        if (resSet.isClosed()) throw new Exception("Account doesn't exists");
        String passhash = resSet.getString("passhash");
        resSet.close();
        if (new String(encode(user.passHash)).equals(passhash))
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
        } catch (SQLException e){
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

    }

    @Override
    public void deleteSong(DeleteSongForm deleteArticle) throws Exception {
        
    }
}
