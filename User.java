public class User {
    private String id;
    private String name;
    private String gender;
    private String address;
    private String contact;

    public User(String id, String name, String gender, String address, String contact) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
    }

    // Getters and setters
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
