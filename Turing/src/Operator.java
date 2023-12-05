import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Operator {
    Scanner sc=new Scanner(System.in);
    ArrayList<Student> students=new ArrayList<>();
    ArrayList<Course> courses=new ArrayList<>();
    ArrayList<Performance> performances=new ArrayList<>();
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Operator() throws IOException, ClassNotFoundException {
        //读取本地文件中的用户信息,并放到集合当中
        readInfo();
        //主界面
        start();
    }

    private void readInfo() throws IOException, ClassNotFoundException {
        //创建对象字节输入流对象，包装基础的字节输入流，把对象集合从文档中读出来
        ObjectInputStream oos1=new ObjectInputStream(new FileInputStream("Turing\\src\\studentInfo.txt"));
        students = (ArrayList<Student>) oos1.readObject();
        ObjectInputStream oos2=new ObjectInputStream(new FileInputStream("Turing\\src\\courseInfo.txt"));
        courses = (ArrayList<Course>) oos2.readObject();
        ObjectInputStream oos3=new ObjectInputStream(new FileInputStream("Turing\\src\\performanceInfo.txt"));
        performances= (ArrayList<Performance>)oos3.readObject();
        oos1.close();
        oos2.close();
        oos3.close();
    }

    public void start(){
        LocalDateTime dateTime=LocalDateTime.now();
        formatter.format(dateTime);
        while (true) {
            System.out.println("=====学生成绩管理系统=====");
            System.out.println("1.学生基本信息录入");
            System.out.println("2.课程信息录入");
            System.out.println("3.学生成绩录入");
            System.out.println("4.学生信息查询");
            System.out.println("5.课程信息查询");
            System.out.println("6.学生信息修改");
            System.out.println("7.课程信息修改");
            System.out.println("8.展示全部学生信息");
            System.out.println("9.展示全部课程信息");
            System.out.println("10.展示成绩排名");
            System.out.println("11.学生信息删除");//删除学生(删除学生，删除该生成绩)，删除学生单科成绩，删除学生全部成绩
            System.out.println("12.课程信息删除");//删除课程(删除与该课程相关联的成绩)
            System.out.println("请选择：");

            int command=sc.nextInt();
            //待办：存档功能，每个课程的学生成绩排名
            switch (command){
                case 1:
                    //学生基本信息录入
                    inputStudentBasicInfo();
                    break;
                case 2:
                    //课程信息录入
                    inputCourseInfo();
                    break;
                case 3:
                    //学生成绩录入
                    inputPerformance();
                case 4:
                    //学生信息查询
                    searchStudentInfo();
                    break;
                case 5:
                    //课程信息查询
                    searchCourseInfo();
                    break;
                case 6:
                    //学生信息修改
                    modifyStudentInfo();
                    break;
                case 7:
                    //课程信息修改
                    modifyCourseInfo();
                    break;
                case 8:
                    //展示全部学生信息
                    showAllStudentsInfo();
                    break;
                case 9:
                    //展示全部课程信息
                    showAllCoursesInfo();
                    break;
                case 10:
                    //展示成绩排名
                    showPerformanceRanking();
                case 11:
                    //学生信息删除
                    deleteStudentInfo();
                    break;
                case 12:
                    //课程信息删除
                    deleteCourseInfo();
                default:
                    System.out.println("输入有误，请重新输入");

        }
        }
    }

    private void deleteCourseInfo() {
    }

    private void showPerformanceRanking(){
        System.out.println("====展示成绩排名====");
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        Course c=searchCourseByCourseId(courseId);
        ArrayList<Performance> performances1=new ArrayList<>();
        for (Performance p : performances) {
            if (p.getCourseId().equals(c.getCourseId())){
                //将该课程的所有成绩信息存入新集合
                performances1.add(p);
            }
        }
        Performance t1,t2;
        for (int i = 0; i < performances1.size()-1; i++) {
            for (int j = 0; j < performances1.size()-i-1; j++) {
                if (performances1.get(j).getScore()>performances1.get(j+1).getScore()){
                    t1=performances1.get(j);
                    t2=performances1.get(j+1);
                    performances1.set(j,t2);
                    performances1.set(j+1,t1);
                }
            }
        }
        System.out.println("课程："+c.getName()+"成绩排名如下：");
        for (Performance p : performances1) {
            System.out.println(p);
        }
    }
    private void deleteStudentInfo() {
        System.out.println("====学生信息删除====");
        System.out.println("请输入学号");
        String studentId=sc.next();
        Student s=searchStudentByStudentId(studentId);
        //待办：若学生为null，输出提示
        System.out.println("1.删除学生");
        //与学号相关联的信息也要修改
        System.out.println("2.删除学生单科成绩");
        System.out.println("3.删除学生全部成绩");
        System.out.println("请选择：");
        int command=sc.nextInt();
        switch (command){
            case 1:
                Delete.deleteStudent(s,performances,students);
                break;
            case 2:
                Delete.deletePerformance(s,performances);
                break;
            case 3:
                Delete.deletePerformances(s,performances);
                break;
            default:
                System.out.println("输入有误，请重新输入");
        }

    }

    private void showAllCoursesInfo() {
        System.out.println("====展示全部课程信息====");
        for (Course c : courses) {
            System.out.println("---------------");
            System.out.println(c);
        }
    }

    private void showAllStudentsInfo() {
        System.out.println("====展示全部学生信息====");
        for (Student s : students) {
            System.out.println("---------------");
            System.out.println(s);
            //根据该生学号到Performance集合中找到该生的成绩，并打印出来
            for (Performance p : performances) {
                if (p.getStudentId().equals(s.getStudentId())){
                    System.out.println(p);
                }
            }
        }
    }

    private void searchCourseInfo() {
        System.out.println("====课程信息查询====");
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        Course c=searchCourseByCourseId(courseId);
        //待办：若课程为null，输出提示
        //重写课程的toString方法,打印出课程信息
        System.out.println(c);
    }

    private void modifyCourseInfo() {
        System.out.println("====课程信息修改====");
        System.out.println("请输入课程号");
        String courseId=sc.next();
        Course c=searchCourseByCourseId(courseId);
        //待办：若课程为null，输出提示
        System.out.println("1.修改课程名");
        //与学号相关联的信息也要修改
        System.out.println("2.修改课程号");
        System.out.println("3.修改学分");
        System.out.println("4.修改学时");
        System.out.println("请选择：");
        int command=sc.nextInt();
        switch (command){
            case 1:
                Modify.modifyCourseName(c);
                break;
            case 2:
                Modify.modifyCourseId(c);
                break;
            case 3:
                Modify.modifyCourseCredit(c);
                break;
            case 4:
                Modify.modifyCreditHour(c);

        }
    }

    private void modifyStudentInfo() {
        System.out.println("====学生信息修改====");
        System.out.println("请输入学号");
        String studentId=sc.next();
        Student s=searchStudentByStudentId(studentId);
        //待办：若学生为null，输出提示
        System.out.println("1.修改姓名");
        //与学号相关联的信息也要修改
        System.out.println("2.修改学号");
        System.out.println("3.修改邮箱");
        System.out.println("4.修改成绩");
        System.out.println("请选择：");
        int command=sc.nextInt();
        switch (command){
            //待办：每个case中修改成功后都重新设置最后修改时间
            case 1:
                Modify.modifyStudentName(s);
                break;
            case 2:
                Modify.modifyStudentId(s);
                break;
            case 3:
                Modify.modifyMail(s);
                break;
            case 4:
                Modify.modifyPerformance(s,performances);
                break;
        }
    }

    private void searchStudentInfo() {
        System.out.println("====学生信息查询====");
        System.out.println("请输入学号：");
        String studentId=sc.next();
        Student s=searchStudentByStudentId(studentId);
        //待办：若学生为null，输出提示
        //重写学生的toString方法,打印出学生基本信息
        System.out.println("该学生基本信息如下：");
        System.out.println(s);
        System.out.println("该学生成绩如下：");
        //根据该生学号到Performance集合中找到该生的成绩，并打印出来
        for (Performance p : performances) {
            if (p.getStudentId().equals(s.getStudentId())){
                System.out.println(p);
            }
        }
    }

    private void inputPerformance() {
        //待办：信息校验
        System.out.println("====学生成绩录入====");
        Performance p=new Performance();
        System.out.println("请输入学号：");
        String studentId=sc.next();
        //待办：需查询该学生是否为null，若是，输出提示，反之，返回该学生对象
        Student s=searchStudentByStudentId(studentId);
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        //待办：需查询该课程是否为null，若是，输出提示，反之，返回该课程对象
        //待办：校验该学生的成绩集合中是否有该课程的成绩数据，如果有，输出提示
        Course c=searchCourseByCourseId(courseId);
        System.out.println("请输入成绩：");
        double score=sc.nextDouble();
        //待办：校验成绩是否合理
        p.setScore(score);
        //待办：根据score评grade，并录入p中
        //将这份成绩录入performance集合中
        performances.add(p);
    }

    static Course searchCourseByCourseId(String courseId) {
        return null;
    }

    private Student searchStudentByStudentId(String studentId) {
        return null;
    }

    private void inputCourseInfo() {
        //待办：信息校验
        System.out.println("====课程信息录入====");
        Course c=new Course();
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        c.setCourseId(courseId);
        System.out.println("请输入课程名：");
        String name=sc.next();
        c.setName(name);
        System.out.println("请输入学分：");
        int courseCredit=sc.nextInt();
        c.setCourseCredit(courseCredit);
        System.out.println("请输入学时：");
        int creditHour=sc.nextInt();
        c.setCreditHour(creditHour);
        courses.add(c);
        System.out.println("课程录入成功！");
    }


    private void inputStudentBasicInfo() {
        System.out.println("====学生基本信息录入====");
        Student s=new Student();
        System.out.println("请输入学生姓名：");
        String name=sc.next();
        //待办！用正则表达式校验姓名是否符合规范：2-6个中文汉字/大写开头的英文 不能有数字
        s.setName(name);
        System.out.println("请输入学生学号：");
        String studentId=sc.next();
        //待办！用正则表达式校验学号是否符合规范：2020为入学年，0006为专业码，4为班级，1为男（0女），26班级序号
        s.setStudentId(studentId);
        System.out.println("请输入学生邮箱：");
        String mail=sc.next();
        //待办！用正则表达式校验邮箱是否符合规范
        s.setMail(mail);
        //待办：由获取到的学号设置学生性别、班级
        //待办：获取系统当前时间为信息建立时间和最后修改时间
        students.add(s);
        System.out.println("学生基本信息录入成功！");
    }
}
