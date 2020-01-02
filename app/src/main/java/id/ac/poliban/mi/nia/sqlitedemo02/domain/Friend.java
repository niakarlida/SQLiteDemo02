package id.ac.poliban.mi.nia.sqlitedemo02.domain;

public class Friend {
    private int id;
    private String name;
    private String address;
    private String phone1;
    private String phone2;
    private String phone3;
    private String email;
    private String hobbies;

    public Friend(String name, String address, String phone1, String phone2, String phone3, String
            email, String hobbies) {
        this.name = name;
        this.address = address;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.email = email;
        this.hobbies = hobbies;
    }
    public Friend(int id, String name, String address, String phone1, String phone2, String phone3,
                  String email, String hobbies) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.email = email;
        this.hobbies = hobbies;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone1() {
        return phone1;
    }
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }
    public String getPhone2() {
        return phone2;
    }
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
    public String getPhone3() {
        return phone3;
    }
    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHobbies() {
        return hobbies;
    }
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    @Override
    public String toString() {
        return String.format("%-15s %-20s", getName(), getEmail());
    }
}
