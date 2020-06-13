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
            db.execSqlUpdate("INSERT INTO user (login, passhash" + (newUser.getEmail() == null ? "" : ", email") + ") " +
                    "VALUES (\"" + newUser.getLogin() + "\", \"" + new String(encode(newUser.getPassword())) + "\"" + (newUser.getEmail() == null ? "" : ",\"" + newUser.getEmail() + "\"") + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String signIn(User user) throws Exception {
        String q = "SELECT * FROM user WHERE login=\"" + user.login + "\"";
        ResultSet resSet = db.execSqlQuery(q);
        if (resSet.isClosed()) throw new Exception("Account doesn't exists");
        String passhash = resSet.getString("passhash");
        resSet.close();
        if (new String(encode(user.passHash)).equals(passhash))
            return HashUtils.getToken(user);
        else throw new Exception("Password is incorrect");
    }

    @Override
    public int addArticle(NewSongForm newSong) throws Exception {
        return 0;
    }

    @Override
    public List<Song> getArticles() throws Exception {
        return null;
    }

    @Override
    public Song getArticle(int id) throws Exception {
        return null;
    }

    @Override
    public void like(LikeForm like) throws Exception {

    }

    @Override
    public void deleteArticle(DeleteSongForm deleteArticle) throws Exception {

    }
}
