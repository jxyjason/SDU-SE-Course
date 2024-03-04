package Class;

public class User {
    String id;
    String name;
    String password;
    int grade;
    int isTeacher;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }

    public User(String id, String name, String password, int grade, int isTeacher) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.grade = grade;
        this.isTeacher = isTeacher;
    }

    public User(String id, String password, int isTeacher) {
        this.id = id;
        this.password = password;
        this.isTeacher = isTeacher;
    }
}
