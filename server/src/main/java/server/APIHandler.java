package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.MyDatabase;
import database.PoorDatabase;

import java.io.IOException;
import java.io.OutputStream;

public class APIHandler implements HttpHandler {

    MyDatabase database = new PoorDatabase();
    Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestURI().getPath()){
                case "/signup":
                    break;
                case "/signin":
                    break;
                default:
                    writeJson(exchange,"{\"error\": \"404\"}");
            }
        }catch (Exception e){
            writeJson(exchange,"{\"error\": \""+e.getStackTrace().toString()+"\"}");
        }


    }

    private void signup(HttpExchange exchange) throws Exception{
        exchange.getRequestBody();
    }

    private void signin(HttpExchange exchange) throws Exception{

    }

    private void writeJson(HttpExchange exchange, String json) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, json.length());
        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}