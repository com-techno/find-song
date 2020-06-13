package util;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

import database.MyDatabase;
import objects.forms.*;

import static util.DecodeUtils.writeJson;

public class ArticleUtils {

    public static void addArticle(HttpExchange exchange, Gson gson, MyDatabase database, String json) throws Exception {
        NewSongForm newArticle = gson.fromJson(json, NewSongForm.class);
        writeJson(exchange, "id", database.addArticle(newArticle) + "");
    }

    public static void getArticle(HttpExchange exchange, Gson gson, MyDatabase database) throws Exception {
        String s = exchange.getRequestURI().getQuery();
        Map<String, String> params = DecodeUtils.paramsToMap(s == null ? "lol=kek" : s);
        if (params.containsKey("id"))
            writeJson(exchange, gson.toJson(database.getArticle(Integer.parseInt(params.get("id")))));
        else
            writeJson(exchange, gson.toJson(database.getArticles()));
    }

    public static void deleteArticle(HttpExchange exchange, Gson gson, MyDatabase database, String json) throws Exception{
        DeleteSongForm deleteArticle = gson.fromJson(json, DeleteSongForm.class);
        database.deleteArticle(deleteArticle);
        writeJson(exchange, "message", "Article deleted");
    }

    public static void like(HttpExchange exchange, Gson gson, MyDatabase database, String json) throws Exception {
        LikeForm like = gson.fromJson(json, LikeForm.class);
        database.like(like);
        writeJson(exchange, "message", "Liked");
    }

}
