package objects;

public class User {

    String username;

    String passHash;

    public String getUsername() {
        return username;
    }

    public String getPassHash() {
        return passHash;
    }

    public User(String username, String passHash) {
        this.username = username;
        this.passHash = passHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", passHash='" + passHash + '\'' +
                '}';
    }
}
