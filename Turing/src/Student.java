import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String studentId;
    private char sex;
    private int classNum;
    private String mail;
    //待办：根据学号中的专业码设置专业信息
    private String setUpTime;
    private String lastModifiedTime;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", studentId='" + studentId + '\'' +
                ", sex=" + sex +
                ", classNum=" + classNum +
                ", mail='" + mail + '\'' +
                ", setUpTime='" + setUpTime + '\'' +
                ", lastModifiedTime='" + lastModifiedTime + '\'' +
                '}';
    }

    public String getSetUpTime() {
        return setUpTime;
    }

    public void setSetUpTime(String setUpTime) {
        this.setUpTime = setUpTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Student() {
    }

    public Student(String name, String studentId, String mail) {
        this.name = name;
        this.studentId = studentId;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
