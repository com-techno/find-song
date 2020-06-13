package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.MyDatabase;
import database.SQLDatabase;
import util.HashUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static util.ArticleUtils.*;
import static util.DecodeUtils.writeJson;
import static util.DecodeUtils.writeFile;
import static util.StreamUtils.readStream;
import static util.UserUtils.signIn;
import static util.UserUtils.signUp;

public class APIHandler implements HttpHandler {

    MyDatabase database = new SQLDatabase();
    Gson gson = new Gson();
    String filepath;

    public APIHandler(String path) throws SQLException, ClassNotFoundException {
        this.filepath = path;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String[] path = exchange.getRequestURI().getPath().substring(1).split("/");
            String json = readStream(exchange.getRequestBody());
            Headers headers = exchange.getRequestHeaders();
            switch (path[0]) {
                case "":
                    writeFile(exchange, new File(filepath + "html\\", "hub.html"));
                    break;
                case "login":
                    writeFile(exchange, new File(filepath + "html\\", "sign_in.html"));
                    break;
                case "register":
                    writeFile(exchange, new File(filepath + "html\\", "sign_up.html"));
                    break;
                case "addsong":
                    writeFile(exchange, new File(filepath + "html\\", "add_song.html"));
                    break;
                case "static":
                    if (path.length == 1)
                        writeJson(exchange, "message", "Static files");
                    else if (path.length == 2)
                        writeJson(exchange, "message", "Error");
                    else
                        switch (path[1]) {
                            case "css":
                                writeFile(exchange, new File(filepath + "css\\", path[2]));
                                break;
                            case "song":
                                writeFile(exchange, new File(filepath + "song\\", path[2]));
                                break;
                            case "assets":
                                writeFile(exchange, new File(filepath + "assets\\", path[2]));
                                break;
                        }
                    break;
                case "api":
                    if (path.length == 1)
                        writeJson(exchange, "message", "This is techno's API");
                    else
                        switch (path[1]) {
                            case "sign_up":
                                signUp(exchange, gson, database, json);
                                break;
                            case "sign_in":
                                signIn(exchange, gson, database, json);
                                break;
                            case "new_song":
                                checkToken(headers);
                                addArticle(exchange, gson, database, json);
                                break;
                            case "get_article":
                                checkToken(headers);
                                getArticle(exchange, gson, database);
                                break;
                            case "delete_article":
                                checkToken(headers);
                                deleteArticle(exchange, gson, database, json);
                                break;
                            case "like":
                                checkToken(headers);
                                like(exchange, gson, database, json);
                        }
                default:
                    exchange.sendResponseHeaders(404, 0);
            }
        } catch (Exception e) {
            writeJson(exchange, "error", e.getMessage());
        } finally {
            exchange.close();
        }
    }

    private void checkToken(Headers headers) throws Exception {
        String token = headers.getFirst("Token");
        if (token == null) throw new Exception("Token expected");
        if (!HashUtils.checkToken(token)) throw new Exception("Invalid token");
    }
}


