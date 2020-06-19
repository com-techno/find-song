package objects.forms;

import static util.HashUtils.getLoginFromToken;

public class NewSongForm {

    String singer;
    String title;
    String text;
    String icon;

    public String getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }
}
