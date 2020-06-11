package database;

import objects.User;

public interface MyDatabase {
    boolean signup(User user);
    String signIn(User user);
}