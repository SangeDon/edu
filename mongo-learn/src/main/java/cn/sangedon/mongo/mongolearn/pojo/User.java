package cn.sangedon.mongo.mongolearn.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author dongliangqiong
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String message;

    private Date sendTime;

    // 这里手写字母大写，只是为了测试使用，是不符合java规范的
    private String NodeName;

    private List<Integer> intList;

    public User() {
        super();
    }

    public User(int id, String message, Date sendTime) {
        super();
        this.id = id;
        this.message = message;
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", message=" + message + ", sendTime=" + sendTime + ", intList=" + intList + "]";
    }

}
