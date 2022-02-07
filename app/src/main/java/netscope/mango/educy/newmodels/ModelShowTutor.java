package netscope.mango.educy.newmodels;

public class ModelShowTutor {

    public String uid, name, number, email, qualification, experience, location, subject, fee, image, token;


    public ModelShowTutor() {
    }

    public ModelShowTutor(String uid, String image,String number, String name, String email, String qualification, String experience, String location, String subject, String fee, String token) {
        this.uid = uid;
        this.image = image;
        this.number = number;
        this.name = name;
        this.email = email;
        this.qualification = qualification;
        this.experience = experience;
        this.location = location;
        this.subject = subject;
        this.fee = fee;
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
