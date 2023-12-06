import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ttt {
    public static void main(String[] args) throws IOException {
        ArrayList specialtyList=new ArrayList();
        Map specialtyMap=new HashMap();
        //先从文件中读取专业信息
        InputStream is=new FileInputStream("Turing\\src\\specialtyInfo.txt");
        Reader isr=new InputStreamReader(is, "UTF-8");
        BufferedReader br=new BufferedReader(isr);
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
//        specialtyMap.forEach((k,v)->{
//            System.out.println(k+"->"+v);
//        });
    }
}
