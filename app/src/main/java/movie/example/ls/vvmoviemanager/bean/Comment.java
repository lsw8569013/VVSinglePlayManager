package movie.example.ls.vvmoviemanager.bean;

import java.io.Serializable;

/**
 * bean pinglun
 * Created by Administrator on 2016/12/22.
 */
public class Comment implements Serializable{

    private  String id;
    private  String type;
    private  String targetId;
    private  String fromUser;
    private  String replyUser;
    private  String content;
    private  String accessType;
    private  String createdTime;
    private  String redpacketId;
    private  String redpacketSalt;

    private String timestamp; // 评论创建时间(170531)

    private MediaBean media;

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public String getRedpacketSalt() {
        return redpacketSalt;
    }

    public void setRedpacketSalt(String redpacketSalt) {
        this.redpacketSalt = redpacketSalt;
    }

    public String getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(String redpacketId) {
        this.redpacketId = redpacketId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", targetId='" + targetId + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", replyUser='" + replyUser + '\'' +
                ", content='" + content + '\'' +
                ", accessType='" + accessType + '\'' +
                '}';
    }
}
