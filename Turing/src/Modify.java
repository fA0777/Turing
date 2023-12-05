import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Modify {
    static Scanner sc=new Scanner(System.in);
    static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static void modifyStudentName(Student s){
        System.out.println("===修改名字===");
        System.out.println("请输入新名字：");
        //待办：检查是否符合规范
        String name=sc.next();
        s.setName(name);
        String date=formatter.format(LocalDateTime.now());
        s.setLastModifiedTime(date);
        System.out.println("修改名字成功！");
    }
    static void modifyStudentId(Student s){
        System.out.println("===修改学号===");
        System.out.println("请输入新学号：");
        //待办：检查是否符合规范
        String studentId=sc.next();
        s.setStudentId(studentId);
        //与学号相关联的信息也要修改
        OperatingSystem.setStudentInfo(s);
        String date=formatter.format(LocalDateTime.now());
        s.setLastModifiedTime(date);
        System.out.println("修改学号成功！");
    }
    static void modifyMail(Student s){
        System.out.println("===修改邮箱===");
        System.out.println("请输入新邮箱：");
        //待办：检查是否符合规范
        String mail=sc.next();
        s.setMail(mail);
        String date=formatter.format(LocalDateTime.now());
        s.setLastModifiedTime(date);
        System.out.println("修改邮箱成功！");
    }
    static void modifyPerformance(Student s, ArrayList<Performance> performances){
        System.out.println("===修改成绩===");
        if (performances.size()<=1){
            System.out.println("当前系统中无成绩信息，请先录入");
            return;
        }
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        //看是否有该课程，返回null，则输出提示
        Course c= OperatingSystem.searchCourseByCourseId(courseId);
        if (c==null){
            System.out.println("该课程不存在，请先录入");
            return;
        }else{
            //根据学号和课程号去成绩集合中找
            for (Performance p : performances) {
                if (p.getStudentId().equals(s.getStudentId())&&p.getCourseId().equals(c.getCourseId())){
                    System.out.println("请输入新成绩：");
                    double score=sc.nextDouble();
                    //待办；校验数据是否合理
                    p.setScore(score);
                    //根据score重新设置grade
                    OperatingSystem.setGradeByScore(p);
                    String date=formatter.format(LocalDateTime.now());
                    s.setLastModifiedTime(date);
                    System.out.println("修改成绩成功！");
                    return;
                }
            }
            //如果找了一圈都找不到，输出提示
            System.out.println("该学生该课程无成绩，请先录入");
        }

    }
    static void modifyCourseName(Course c){
        System.out.println("===修改课程名===");
        System.out.println("请输入新名字：");
        //待办：检查是否符合规范
        String name=sc.next();
        c.setName(name);
        System.out.println("修改课程名成功！");
    }
    static void modifyCourseId(Course c){
        System.out.println("===修改课程号===");
        System.out.println("请输入新课程号：");
        //待办：检查是否符合规范
        String courseId=sc.next();
        c.setCourseId(courseId);
        System.out.println("修改课程号成功！");
    }
    static void modifyCourseCredit(Course c){
        System.out.println("===修改学分===");
        System.out.println("请输入新学分：");
        //待办：检查是否符合规范
        int courseCredit=sc.nextInt();
        c.setCourseCredit(courseCredit);
        System.out.println("修改学分成功！");
    }
    static void modifyCreditHour(Course c){
        System.out.println("===修改学时===");
        System.out.println("请输入新学时：");
        //待办：检查是否符合规范
        int creditHour=sc.nextInt();
        c.setCreditHour(creditHour);
        System.out.println("修改学时成功！");
    }
}
