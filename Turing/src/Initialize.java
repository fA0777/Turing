import java.io.*;
import java.util.ArrayList;

public class Initialize {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Student> students1=new ArrayList<>();
        students1.add(new Student());
        ArrayList<Course> courses1=new ArrayList<>();
        courses1.add(new Course());
        ArrayList<Performance> performances1=new ArrayList<>();
        performances1.add(new Performance());
        ObjectOutputStream oos1=new ObjectOutputStream(new FileOutputStream("Turing\\src\\studentInfo.txt"));
        oos1.writeObject(students1);
        ObjectOutputStream oos2=new ObjectOutputStream(new FileOutputStream("Turing\\src\\courseInfo.txt"));
        oos2.writeObject(courses1);
        ObjectOutputStream oos3=new ObjectOutputStream(new FileOutputStream("Turing\\src\\performanceInfo.txt"));
        oos3.writeObject(performances1);
        System.out.println("成功写入");
        ObjectInputStream ois1=new ObjectInputStream(new FileInputStream("Turing\\src\\studentInfo.txt"));
        ArrayList<Student>students2 = (ArrayList<Student>) ois1.readObject();
        ObjectInputStream ois2=new ObjectInputStream(new FileInputStream("Turing\\src\\courseInfo.txt"));
        ArrayList<Course>courses2= (ArrayList<Course>) ois2.readObject();
        ObjectInputStream ois3=new ObjectInputStream(new FileInputStream("Turing\\src\\performanceInfo.txt"));
        ArrayList<Performance>performance2 = (ArrayList<Performance>) ois3.readObject();
        System.out.println("成功读取");
        //待办：关闭流
    }
}
