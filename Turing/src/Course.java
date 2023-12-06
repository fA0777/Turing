import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID=1371216554879142405L;
    private String courseId;
    private String name;
    private int courseCredit;
    private int creditHour;

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", courseCredit=" + courseCredit +
                ", creditHour=" + creditHour +
                '}';
    }

    public Course(String courseId, String name, int courseCredit, int creditHour) {
        this.courseId = courseId;
        this.name = name;
        this.courseCredit = courseCredit;
        this.creditHour = creditHour;
    }

    public Course() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }
}
