import java.io.Serializable;

public class Performance implements Serializable {
    private static final long serialVersionUID= -4002876450381367836L;
    private String courseId;
    private String studentId;
    private double score;
    private String grade;

    @Override
    public String toString() {
        return "Performance{" +
                "courseId='" + courseId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", score=" + score +
                ", grade='" + grade + '\'' +
                '}';
    }

    public Performance() {
    }

    public Performance(String courseId, String studentId, double score, String grade) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.score = score;
        this.grade = grade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
