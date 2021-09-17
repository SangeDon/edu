package cn.sangedon.eslearn;

import cn.sangedon.eslearn.util.ESClient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
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
        ArrayList<User> users = null;
        /*for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("sange" + i);
            user.setAge(i);
            users.add(user);
        }*/

        Optional.ofNullable(users).orElseGet(() -> new ArrayList<>()).forEach(e -> {
            System.out.println(e);
        });

        User user = new User();
        user.setName("ddddd");

        char[] chars = Optional.ofNullable(user).map(User::getName).map(n -> n.toCharArray()).orElseGet(() -> "sange".toCharArray());
        Integer integer = Optional.ofNullable(user.getAge()).orElseGet(() -> 1);

        user.setAge(10);
        Integer integer1 = Optional.ofNullable(user.getAge()).orElseGet(() -> 1);
        System.out.println(integer);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = df.format(date);
        System.out.println(date);
    }
}
