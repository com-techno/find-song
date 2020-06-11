package database;

import com.google.gson.Gson;
import objects.User;

import java.io.File;
import java.io.FileOutputStream;

public class PoorDatabase implements MyDatabase {

    File dataRoot = new File("data");
    File usersDir = new File("data"+File.separator+"users");

    Gson gson = new Gson();

    public PoorDatabase(){
        if (!usersDir.exists())
            usersDir.mkdirs();
    }


    @Override
    public boolean signup(User user) {
        File userfile = new File(usersDir,user.getUsername());
        try{
            if (userfile.exists())
                return false;
            else {
                userfile.createNewFile();
                FileOutputStream fos = new FileOutputStream(userfile);

                fos.write(gson.toJson(user).getBytes());
                fos.flush();
                fos.close();
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String signIn(User user) {
        return null;
    }

}