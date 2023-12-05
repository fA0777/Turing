import java.util.ArrayList;
import java.util.Scanner;

public class Modify {
    static Scanner sc=new Scanner(System.in);
     static void modifyStudentName(Student s){
        System.out.println("===修改名字===");
        System.out.println("请输入新名字：");
        //待办：检查是否符合规范
        String name=sc.next();
        s.setName(name);
        System.out.println("修改名字成功！");
    }
    static void modifyStudentId(Student s){
        System.out.println("===修改学号===");
        System.out.println("请输入新学号：");
        //待办：检查是否符合规范
        String studentId=sc.next();
        s.setStudentId(studentId);
    }
    static void modifyMail(Student s){
        System.out.println("===修改邮箱===");
        System.out.println("请输入新邮箱：");
        //待办：检查是否符合规范
        String mail=sc.next();
        s.setMail(mail);
    }
    static void modifyPerformance(Student s, ArrayList<Performance> performances){
        System.out.println("===修改成绩===");
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        //待办：看是否有该课程，返回null，则输出提示
        Course c=Operator.searchCourseByCourseId(courseId);
        if (c==null){

        }else{
            //根据学号和课程号去成绩集合中找
            for (Performance p : performances) {
                if (p.getStudentId().equals(s.getStudentId())&&p.getCourseId().equals(c.getCourseId())){
                    System.out.println("请输入新成绩：");
                    double score=sc.nextDouble();
                    //待办；校验数据是否合理
                    p.setScore(score);
                    //待办：根据score重新设置grade
                }
            }
            //待办：如果找了一圈都找不到，输出提示
        }

    }
    static void modifyCourseName(Course c){

    }
    static void modifyCourseId(Course c){

    }
    static void modifyCourseCredit(Course c){

    }
    static void modifyCreditHour(Course c){

    }
}
