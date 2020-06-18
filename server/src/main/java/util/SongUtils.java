package util;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

import database.MyDatabase;
import objects.Song;
import objects.forms.*;

import static util.DecodeUtils.writeJson;

public class SongUtils {

    public static void addSong(HttpExchange exchange, Gson gson, MyDatabase database, String json) throws Exception {
        NewSongForm newSong = gson.fromJson(json, NewSongForm.class);
        writeJson(exchange, "message", "Added");
    }

    public static void getArticle(HttpExchange exchange, Gson gson, MyDatabase database) throws Exception {
        String s = exchange.getRequestURI().getQuery();
        if (s == null) throw new Exception("Params required");
        Map<String, String> params = DecodeUtils.paramsToMap(s);
        writeJson(exchange, gson.toJson(database.getSong(Integer.parseInt(params.get("id")))));
    }

    public static void deleteSong(HttpExchange exchange, Gson gson, MyDatabase database, String json) throws Exception {
        DeleteSongForm deleteSong = gson.fromJson(json, DeleteSongForm.class);
        database.deleteSong(deleteSong);
        writeJson(exchange, "message", "Song deleted");
    }

    public static void like(HttpExchange exchange, Gson gson, MyDatabase database, String json) throws Exception {
        LikeForm like = gson.fromJson(json, LikeForm.class);
        database.like(like);
        writeJson(exchange, "message", "Liked");
    }

    public static void top(HttpExchange exchange, Gson gson, MyDatabase database) throws Exception {
        String s = exchange.getRequestURI().getQuery();
        if (s == null) throw new Exception("Params required");
        Map<String, String> params = DecodeUtils.paramsToMap(s);
        List<Song> result = database.getTop(Integer.parseInt(params.get("count")));
        writeJson(exchange, gson.toJson(result));
    }

    public static void search(HttpExchange exchange, Gson gson, MyDatabase database) throws Exception {
        String s = exchange.getRequestURI().getQuery();
        if (s == null) throw new Exception("Params required");
        Map<String, String> params = DecodeUtils.paramsToMap(s);

    }

}
