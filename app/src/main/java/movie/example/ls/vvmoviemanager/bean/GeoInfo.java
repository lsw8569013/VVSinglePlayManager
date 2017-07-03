package movie.example.ls.vvmoviemanager.bean;

import java.io.Serializable;

/**
 * 发布瞬间 坐标信息
 * Created by Administrator on 2016/11/23.
 */
public class GeoInfo implements Serializable {

    private  String lat;
    private  String lng;
    private  String des;
    private  String addr;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public GeoInfo(String lat, String lng, String des, String addr) {
        this.lat = lat;
        this.lng = lng;
        this.des = des;
        this.addr = addr;
    }
}
