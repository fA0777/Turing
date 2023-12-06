import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OperatingSystem {

    Scanner sc=new Scanner(java.lang.System.in);
    ArrayList<Student> students=new ArrayList<>();
    static ArrayList<Course> courses=new ArrayList<>();
    ArrayList<Performance> performances=new ArrayList<>();
    ArrayList specialtyList=new ArrayList();
    static Map specialtyMap=new HashMap();

    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OperatingSystem() throws IOException, ClassNotFoundException {
        //读取本地文件中的信息,并放到集合当中
        readInfo();
        //主界面
        start();
    }

    private void readInfo() throws IOException, ClassNotFoundException {
        //创建对象字节输入流对象，包装基础的字节输入流，把对象集合从文档中读出来
        ObjectInputStream ois1=new ObjectInputStream(new FileInputStream("Turing\\src\\studentInfo.txt"));
        students = (ArrayList<Student>) ois1.readObject();
        ObjectInputStream ois2=new ObjectInputStream(new FileInputStream("Turing\\src\\courseInfo.txt"));
        courses = (ArrayList<Course>) ois2.readObject();
        ObjectInputStream ois3=new ObjectInputStream(new FileInputStream("Turing\\src\\performanceInfo.txt"));
        performances= (ArrayList<Performance>)ois3.readObject();
        //先从文件中读取专业信息
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("Turing\\src\\specialtyInfo.txt"), "UTF-8"));
        String line;
        while ((line=br.readLine())!=null){
            //每读取到一行专业信息，依据"    "分割成字符串数组，存入list集合中
            String[] s = line.split("    ");
            for (int i = 0; i < s.length; i++) {
                specialtyList.add(s[i]);
            }
        }
        //遍历list集合，将一个专业名与一个专业编码作为一组键值对存入Map集合
        //注意从第三个元素开始存
        for (int i = 2; i < specialtyList.size(); i+=2) {
            specialtyMap.put(specialtyList.get(i+1),specialtyList.get(i));
        }

        //读完关流
        ois1.close();
        ois2.close();
        ois3.close();
        br.close();
    }

    public void start(){
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
            System.out.println("11.学生信息删除");
            //删除学生(删除学生，删除该生成绩)，删除学生单科成绩，删除学生全部成绩
            System.out.println("12.课程信息删除");
            //删除课程(删除与该课程相关联的成绩)
            System.out.println("13.存档");
            System.out.println("请选择：");

            int command=sc.nextInt();
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
                    break;
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
                    break;
                case 11:
                    //学生信息删除
                    deleteStudentInfo();
                    break;
                case 12:
                    //课程信息删除
                    deleteCourseInfo();
                    break;
                case 13:
                    //存档
                    saveFiles();
                    break;
                default:
                    System.out.println("输入有误，请重新输入");

        }
        }
    }

    private void saveFiles() {
        try(//创建对象字节输出流对象，包装基础的字节输出流，把对象集合存入到文档中
            ObjectOutputStream oos1=new ObjectOutputStream(new FileOutputStream("Turing\\src\\studentInfo.txt"));
            ObjectOutputStream oos2=new ObjectOutputStream(new FileOutputStream("Turing\\src\\courseInfo.txt"));
            ObjectOutputStream oos3=new ObjectOutputStream(new FileOutputStream("Turing\\src\\performanceInfo.txt"));
        ) {
            oos1.writeObject(students);
            oos2.writeObject(courses);
            oos3.writeObject(performances);
            System.out.println("存档成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteCourseInfo() {
        System.out.println("====课程信息删除====");
        if (courses.size()<=1){
            System.out.println("当前系统中无课程信息，请先录入");
            return;
        }
        System.out.println("请输入课程号");
        String courseId=sc.next();
        Course c = searchCourseByCourseId(courseId);
        //若为null，输出提示
        if (c==null){
            System.out.println("该课程不存在，请重新操作！");
            return;
        }
        //若存在，则删除
        courses.remove(c);
        System.out.println("课程信息删除成功！");
    }

    private void showPerformanceRanking(){
        System.out.println("====展示成绩排名====");
        if (performances.size()<=1){
            System.out.println("当前系统中无成绩信息，请先录入");
            return;
        }
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        Course c=searchCourseByCourseId(courseId);
        //若为null，输出提示
        if (c==null){
            System.out.println("该课程不存在，请重新操作！");
            return;
        }
        ArrayList<Performance> performances1=new ArrayList<>();
        for (Performance p : performances) {
            if (p.getCourseId().equals(c.getCourseId())){
                //将该课程的所有成绩信息存入新集合
                performances1.add(p);
            }
        }
        Performance t1,t2;
        //冒泡排序,按成绩由高到低排名
        for (int i = 0; i < performances1.size()-1; i++) {
            for (int j = 0; j < performances1.size()-i-1; j++) {
                if (performances1.get(j).getScore()<performances1.get(j+1).getScore()){
                    t1=performances1.get(j);
                    t2=performances1.get(j+1);
                    performances1.set(j,t2);
                    performances1.set(j+1,t1);
                }
            }
        }
        System.out.println("课程："+c.getName()+" 成绩排名如下：");
        for (Performance p : performances1) {
            System.out.println(p);
        }
    }
    private void deleteStudentInfo() {
        System.out.println("====学生信息删除====");
        if (students.size()<=1){
            System.out.println("当前系统中无学生信息，请先录入");
            return;
        }
        System.out.println("请输入学号");
        String studentId=sc.next();
        Student s=searchStudentByStudentId(studentId);
        //若学生为null，输出提示
        if (s==null){
            System.out.println("该学生不存在，请重新操作！");
            return;
        }
        System.out.println("1.删除学生");
        //该学生的成绩数据也要删除
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
        if (courses.size()<=1){
            System.out.println("当前系统中无课程信息，请先录入");
            return;
        }
        for (Course c : courses) {
            System.out.println("---------------");
            System.out.println(c);
        }
    }

    private void showAllStudentsInfo() {
        System.out.println("====展示全部学生信息====");
        if (students.size()<=1){
            System.out.println("当前系统中无学生信息，请先录入");
            return;
        }
        for (Student s : students) {
            System.out.println("---------------");
            //打印出学生基本个人信息
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
        if (courses.size()<=1){
            System.out.println("当前系统中无课程信息，请先录入");
            return;
        }
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        Course c=searchCourseByCourseId(courseId);
        //若课程为null，输出提示
        if (c==null){
            System.out.println("该课程不存在，请先录入");
            return;
        }
        //重写课程的toString方法,打印出课程信息
        System.out.println(c);
    }

    private void modifyCourseInfo() {
        System.out.println("====课程信息修改====");
        if (courses.size()<=1){
            System.out.println("当前系统中无课程信息，请先录入");
            return;
        }
        System.out.println("请输入课程号");
        String courseId=sc.next();
        Course c=searchCourseByCourseId(courseId);
        //若课程为null，输出提示
        if (c==null){
            System.out.println("该课程不存在，请先录入");
            return;
        }else{
            System.out.println("1.修改课程名");
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
                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
    }

    private void modifyStudentInfo() {
        System.out.println("====学生信息修改====");
        if (students.size()<=1){
            System.out.println("当前系统中无学生信息，请先录入");
            return;
        }
        System.out.println("请输入学号");
        String studentId=sc.next();
        Student s=searchStudentByStudentId(studentId);
        //若学生为null，输出提示
        if (s==null){
            System.out.println("该学生不存在，请先录入学生");
            return;
        }else {
            System.out.println("1.修改姓名");
            System.out.println("2.修改学号");
            //与学号相关联的信息也要修改
            System.out.println("3.修改邮箱");
            System.out.println("4.修改成绩");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command) {
                //每个case中修改成功后都重新设置最后修改时间
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
                    Modify.modifyPerformance(s, performances);
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
    }

    private void searchStudentInfo() {
        System.out.println("====学生信息查询====");
        //若集合中无学生，输出提示
        if (students.size()<=1){
            System.out.println("当前系统中无学生信息，请先录入");
            return;
        }
        System.out.println("请输入学号：");
        String studentId=sc.next();
        Student s=searchStudentByStudentId(studentId);
        //若学生为null，输出提示
        if (s==null){
            System.out.println("该学生不存在，请先录入学生");
            return;
        }else{
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
    }

    private void inputPerformance() {
        System.out.println("====学生成绩录入====");
        if (students.size()<=1){
            System.out.println("当前系统中无学生信息，请先录入");
            return;
        }
        System.out.println("请输入学号：");
        String studentId=sc.next();
        //查询该学生是否为null，若是，输出提示
        Student s=searchStudentByStudentId(studentId);
        if (s==null){
            System.out.println("该学生不存在，请先录入学生");
            return;
        }else{
            System.out.println("请输入课程号：");
            String courseId=sc.next();
            //查询该课程是否为null，若是，输出提示
            Course c=searchCourseByCourseId(courseId);
            if (c==null){
                System.out.println("该课程不存在，请先录入课程");
                return;
            }else{
                for (Performance p : performances) {
                    //校验该学生的成绩集合中是否有该课程的成绩数据，如果有，输出提示
                    if (p.getStudentId().equals(s.getStudentId())){
                        if (p.getCourseId().equals(c.getCourseId())){
                            System.out.println("该学生该课程已有成绩，如下：");
                            System.out.println(c);
                            System.out.println("若要修改成绩，请移步操作6.学生信息修改");
                            return;
                        }
                    }
                }
                //成绩集合中无相同成绩信息，则录入
                System.out.println("请输入成绩：");
                double score=sc.nextDouble();
                //校验成绩是否合理
                if (score<0||score>100){
                    System.out.println("输入的成绩有误，请重新操作！");
                    return;
                }
                Performance p=new Performance();
                p.setCourseId(c.getCourseId());
                p.setStudentId(s.getStudentId());
                p.setScore(score);
                //根据score评grade，并录入p中
                setGradeByScore(p);
                //将这份成绩录入performance集合中
                performances.add(p);
                System.out.println("学生成绩录入成功！");
            }

        }

    }

    static void setGradeByScore(Performance p) {
        double score = p.getScore();
        if (score>=90&&score<=100){
            p.setGrade("优秀");
        }else if (score>=80&&score<90){
            p.setGrade("良好");
        }else if (score>=60&&score<80){
            p.setGrade("及格");
        }else if (score>=0&&score<60){
            p.setGrade("不及格");
        }
    }

    static Course searchCourseByCourseId(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)){
                return course;
            }
        }
        return null;
    }

    private Student searchStudentByStudentId(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)){
                return student;
            }
        }
        return null;
    }

    private void inputCourseInfo() {
        System.out.println("====课程信息录入====");
        System.out.println("请输入课程号：");
        String courseId=sc.next();
        //判断系统中是否有课程，若还无课程，则直接添加该课程(若不做这一步，第一次添加课程时会出现空指针异常)
        //初始化时，添加了一个对象，因此判断有无应与1比较
        if (courses.size()>1){
            //根据课程号判断是否已经存在
            for (Course course : courses) {
                if (course.getCourseId().equals(courseId)){
                    System.out.println("该课程已存在，请重新操作！");
                    return;
                }
            }
        }
        Course c=new Course();
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
        System.out.println("请输入学生姓名：");
        String name=sc.next();
        System.out.println("请输入学生学号：(注：学号规范：前四位数字为入学年份，接下来四位为专业码，接下来一位为班级，再接一位：1为男（0女），最后两位为班级序号)");
        String studentId=sc.next();

        //判断系统中是否有学生，若还无学生，则直接添加该生(若不做这一步，第一次添加学生时会出现空指针异常(Initial类中创建对象时用有参构造器就可以不做这步))
        //初始化时，添加了一个对象，因此判断有无应与1比较
        if (students.size()>1){
            //根据学号判断该生是否已存在，若存在，打印提示
            for (Student student : students) {
                if (student.getStudentId().equals(studentId)){
                    System.out.println("该学生已存在，请重新操作！");
                    return;
                }
            }
        }
        //用正则表达式校验学号是否符合规范：2020为入学年，0006为专业码，4为班级，1为男（0女），26班级序号
        if (!checkStudentId(studentId)){
            System.out.println("输入的学号有误，请重新操作!");
            return;
        }
        //比对是否与专业码匹配
        String specialty=searchSpecialtyByNum(studentId);
        if (specialty!=null){
            Student s=new Student();
            s.setName(name);
            s.setStudentId(studentId);
            System.out.println("请输入学生邮箱：");
            String mail=sc.next();
            //用正则表达式校验邮箱是否符合规范
            if (checkMail(mail)){
                s.setMail(mail);
            }else {
                System.out.println("输入的邮箱有误，请重新操作！");
                return;
            }
            //由获取到的学号设置学生性别、班级、专业
            setStudentInfo(s);
            s.setSpecialty(specialty);
            //获取系统当前时间为信息建立时间和最后修改时间
            LocalDateTime d=LocalDateTime.now();
            String date=formatter.format(d);
            s.setSetUpTime(date);
            s.setLastModifiedTime(date);
            students.add(s);
            System.out.println("学生基本信息录入成功！");
        }else{
            System.out.println("输入的专业码有误，请重新操作！");
            return;
        }

    }

    private boolean checkStudentId(String studentId) {
        //用正则表达式校验学号是否符合规范  2020为入学年，0006为专业码，4为班级，1为男（0女），26班级序号
        String regex="\\d{9}[0|1]\\d{2}";
        return studentId.matches(regex);
    }

    private boolean checkMail(String mail) {
        //用正则表达式校验邮箱是否符合规范
        String regex="\\w+@[\\w&&[^_]]{2,}(\\.[a-zA-Z]{2,3}){1,2}";
        return mail.matches(regex);
    }

    static String searchSpecialtyByNum(String studentId) {
        //根据专业编码查找专业
        String specialty="";
        for (int i = 4; i < 8; i++) {
            specialty+=studentId.charAt(i);
            //获取了输入的专业编码
        }
        Set keySet = specialtyMap.keySet();
        //获取专业编码集合
        for (Object o : keySet) {
            if (o.equals(specialty)){
                return (String) specialtyMap.get(o);
                //返回根据专业码找到的专业名
            }
        }
        return null;
    }


    static void setStudentInfo(Student s) {
        int classNum=s.getStudentId().charAt(8)-'0';
        int sex=s.getStudentId().charAt(9)-'0';
        s.setClassNum(classNum);
        if (sex==0){
            s.setSex('女');
        }else{
            s.setSex('男');
        }
    }
}
