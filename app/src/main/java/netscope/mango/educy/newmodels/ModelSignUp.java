package netscope.mango.educy.newmodels;

public class ModelSignUp {

    private String id, name, number, image, email, password, qualification, experience, location, subject, fee;
    private String Type;

    public ModelSignUp() {
    }

    public ModelSignUp(String name, String number, String image, String email, String password, String qualification, String experience, String location, String subject, String fee, String type) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.email = email;
        this.password = password;
        this.qualification = qualification;
        this.experience = experience;
        this.location = location;
        this.subject = subject;
        this.fee = fee;
        this.Type = type;
    }

    public ModelSignUp(String image, String email, String password, String type) {
        this.image = image;
        this.email = email;
        this.password = password;
        this.Type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
