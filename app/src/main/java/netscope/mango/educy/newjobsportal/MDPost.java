package netscope.mango.educy.newjobsportal;

public class MDPost {

    private String image, title, description, contact;

    public MDPost(String title, String description, String contact) {
        this.title = title;
        this.description = description;
        this.contact = contact;
    }

    public MDPost() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
