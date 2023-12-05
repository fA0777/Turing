import java.util.ArrayList;
import java.util.Scanner;

public class Delete {
    static Scanner sc=new Scanner(System.in);
    static void deleteStudent(Student s,ArrayList<Performance> performances,ArrayList<Student> students){
        System.out.println("===删除学生===");
        //先删除该学生的所有成绩
        Delete.deletePerformances(s,performances);
        //再将该学生从学生集合中删除
        students.remove(s);
        System.out.println("删除学生成功！");
    }
    static void deletePerformance(Student s,ArrayList<Performance> performances) {
        System.out.println("===删除学生单科成绩===");
        if (performances.size()<=1){
            System.out.println("当前系统中无成绩信息，请先录入");
            return;
        }
        System.out.println("请输入课程号：");
        String courseId = sc.next();
        //看是否有该课程，返回null，则输出提示
        Course c = OperatingSystem.searchCourseByCourseId(courseId);
        if (c == null) {
            System.out.println("该课程不存在，请重新操作！");
            return;
        } else {
            //根据学号和课程号去成绩集合中找,将该生该课程的成绩删去
            for (int i = 0; i < performances.size(); i++) {
                Performance p=performances.get(i);
                if (p.getStudentId().equals(s.getStudentId()) && p.getCourseId().equals(c.getCourseId())) {
                    performances.remove(p);
                    System.out.println("删除该学生"+c.getName()+"成绩成功！");
                    //该生该课程的成绩数据只有一个，找到并删除了就可以return了
                    return;
                }
            }
            //如果找了一圈都找不到，输出提示
            System.out.println("该学生该课程无成绩，请重新操作！");
        }
    }
    static void deletePerformances(Student s, ArrayList<Performance> performances){
        System.out.println("===删除学生全部成绩===");
        if (performances.size()<=1){
            System.out.println("当前系统中无成绩信息，请先录入");
            return;
        }
        //根据该生学号查找并删除与该生相关联的成绩数据
        for (int i = 0; i < performances.size(); i++) {
            //此处若用增强for会出ConcurrentModificationException异常
            Performance p=performances.get(i);
            if (p.getStudentId().equals(s.getStudentId())){
                performances.remove(p);
            }
        }
        System.out.println("删除学生全部成绩成功！");
    }
}
