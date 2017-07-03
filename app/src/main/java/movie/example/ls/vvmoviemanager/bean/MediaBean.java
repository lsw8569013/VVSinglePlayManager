package movie.example.ls.vvmoviemanager.bean;

import java.io.Serializable;

/**
 * 发布瞬间 音频 图片类信息
 * Created by Administrator on 2016/11/23.
 */
public class MediaBean implements Serializable{

    private String mediaType;
    private String mediaUrl;
    private int width;
    private int height;
    private int duration;


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {

        this.mediaUrl = mediaUrl;
    }

    public MediaBean(String mediaType, String mediaUrl, int width, int height, int duration) {
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.width = width;
        this.height = height;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MediaBean{" +
                "mediaType='" + mediaType + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                '}';
    }
}
