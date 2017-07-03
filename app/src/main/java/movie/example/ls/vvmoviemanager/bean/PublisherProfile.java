package movie.example.ls.vvmoviemanager.bean;

import java.io.Serializable;

/**
 * C页 请求瞬间列表List请求的 返回bean
 * Created by Administrator on 2016/12/2.
 */
public class PublisherProfile implements Serializable{

    private String userId;
    private String phone;
    private String avatar;
    private String nickname;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        if(avatar == null)
            return "";
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    
}
