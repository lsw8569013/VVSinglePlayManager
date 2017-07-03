package movie.example.ls.vvmoviemanager.bean;


import java.io.Serializable;
import java.util.List;

/**
 * 返回发布的瞬间 的详细信息
 * Created by Administrator on 2016/11/23.
 */
public class ReleaseCommontBack implements Serializable {
    private String createdBy;
    private String updatedBy;
    private String createdTime;
    private String updatedTime;
    private String createdByClient;
    private String updatedByClient;
    private String id;
    private String publisherId;
    private String content;
    private GeoInfo geoInfo;
    private String accessScope; // 访问范围
    private String timestamp;
    private Properties properties;
    private String rev;
    private List<MediaBean> mediaList;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedByClient() {
        return createdByClient;
    }

    public void setCreatedByClient(String createdByClient) {
        this.createdByClient = createdByClient;
    }

    public String getUpdatedByClient() {
        return updatedByClient;
    }

    public void setUpdatedByClient(String updatedByClient) {
        this.updatedByClient = updatedByClient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GeoInfo getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(GeoInfo geoInfo) {
        this.geoInfo = geoInfo;
    }

    public String getAccessScope() {
        return accessScope;
    }

    public void setAccessScope(String accessScope) {
        this.accessScope = accessScope;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public List<MediaBean> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaBean> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public String toString() {
        return "ReleaseCommontBack{" +
                "createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", createdByClient='" + createdByClient + '\'' +
                ", updatedByClient='" + updatedByClient + '\'' +
                ", id='" + id + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", content='" + content + '\'' +
                ", geoInfo=" + geoInfo +
                ", accessScope='" + accessScope + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", properties=" + properties +
                ", rev='" + rev + '\'' +
                ", mediaList=" + mediaList +
                '}';
    }
}
