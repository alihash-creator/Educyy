package netscope.mango.educy;

/**
 * Created by Anonymous on 8/29/2017.
 */
public class newsgetset {
    private String image;
    private String title;
    private String desc;
    private String date;
    private String author;


    public newsgetset(String image, String title, String desc, String date, String author) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

