package cn.sangedon.eslearn;

import cn.sangedon.eslearn.util.ESClient;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EsLearnApplicationTests {
    String index = "person";

    String type = "doc";

    @Test
    void contextLoads() {
        System.out.println(111);
    }

    @Test
    void testConnect1() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("姓名" + i);
            user.setAge(i);
            users.add(user);
        }
        users.stream().reduce((sum, age) -> {
            sum.setAge(age.getAge());
            return sum;
        });
        /*Stream.of(1, 2, 3, 4, 5, 6).reduce((sum, num) -> {
            sum += num;
            System.out.println("sum: " + sum);
            System.out.println("num: " + num);
            return sum;
        });*/
    }

    @Test
    void testConnect() {
        RestHighLevelClient client = ESClient.getClient();
        System.out.println("ok");
    }

    public User createUser() {
        System.out.println("create user ...");
        return new User();
    }

    @Test
    public void testOptional() {

        String url = "https://www.baidu.com:8080/demo/demo1";
        String pattern = "(https://\\.+(:\\d+)?)(.*)";
        final Pattern compile = Pattern.compile(pattern);
        final Matcher matcher = compile.matcher(url);
        final String group = matcher.group(1);
        final boolean matches = matcher.matches();
        System.out.println(111);
        // 按指定模式在字符串查找
//        String line = "This order was placed for QT3000! OK?";
//        String pattern = "(\\D*)(\\d+)(.*)";
//
//        // 创建 Pattern 对象
//        Pattern r = Pattern.compile(pattern);
//
//        // 现在创建 matcher 对象
//        Matcher m = r.matcher(line);
//        if (m.find()) {
//            System.out.println("Found value: " + m.group(0));
//            System.out.println("Found value: " + m.group(1));
//            System.out.println("Found value: " + m.group(2));
//            System.out.println("Found value: " + m.group(3));
//        } else {
//            System.out.println("NO MATCH");
//        }
    }
}
