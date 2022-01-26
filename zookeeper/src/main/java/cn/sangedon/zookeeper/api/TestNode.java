package cn.sangedon.zookeeper.api;

/**
 * 创建节点
 *
 * @author dongliangqiong 2021-10-28 21:58
 */
public class TestNode {

    public static void main(String[] args) {
        String str = "@姓名 今年@年龄 岁，生日是@生日 ； @下拉框 ， @单选框 ， @复选框 ，GPS位置为@GPS ";
        String[] split = str.split(" ");
        StringBuilder resContent = new StringBuilder();
        for (String fieldName : split) {
            if (fieldName.contains("@")) {
                resContent.append(fieldName.substring(0, fieldName.indexOf("@")));
                String realFieldName = fieldName.substring(fieldName.indexOf("@") + 1);
                System.out.println("realFieldName:" + realFieldName);
                //                resContent.append(fieldNameAndValueMap.getOrDefault(realFieldName, ""));
            } else {
                resContent.append(fieldName);
            }
        }
    }

    /*public static void main(String[] args) throws Exception {
        String sql = "select * from person where a = a and";
        String and = sql.substring(0, sql.lastIndexOf("and")).trim();

        List<String> list = null;
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println(and);
    }*/
}
