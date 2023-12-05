import java.util.ArrayList;
import java.util.Scanner;

public class Delete {
    static Scanner sc=new Scanner(System.in);
    static void deleteStudent(Student s,ArrayList<Performance> performances,ArrayList<Student> students){
        System.out.println("===删除学生===");
        Delete.deletePerformances(s,performances);
        students.remove(s);
        System.out.println("删除学生成功！");
    }
    static void deletePerformance(Student s,ArrayList<Performance> performances) {
        System.out.println("===删除学生单科成绩===");
        System.out.println("请输入课程号：");
        String courseId = sc.next();
        //待办：看是否有该课程，返回null，则输出提示
        Course c = OperatingSystem.searchCourseByCourseId(courseId);
        if (c == null) {

        } else {
            //根据学号和课程号去成绩集合中找
            for (Performance p : performances) {
                if (p.getStudentId().equals(s.getStudentId()) && p.getCourseId().equals(c.getCourseId())) {
                    performances.remove(p);
                }
            }
        }//待办：如果找了一圈都找不到，输出提示
    }
    static void deletePerformances(Student s, ArrayList<Performance> performances){
        System.out.println("===删除学生全部成绩===");
        for (Performance p : performances) {
            if (p.getStudentId().equals(s.getStudentId())){
                performances.remove(p);
            }
        }
        System.out.println("删除学生全部成绩成功！");
    }
}
