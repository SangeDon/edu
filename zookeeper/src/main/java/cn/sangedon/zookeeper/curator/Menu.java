package cn.sangedon.zookeeper.curator;

/**
 * TODO
 *
 * @author dongliangqiong 2021-11-02 10:33
 */
public class Menu {
    public static void main(String[] args) {
        for (int i = 0; i < 40; i++) {
            System.out.println("menu_" + SecretUtil.uuid());
        }
    }
}
