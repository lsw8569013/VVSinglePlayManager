package movie.example.ls.vvmoviemanager.utils;

/**
 * 泛型观察者
 * Created By: lsw
 * Author : lsw
 * Date :  2017/3/30
 * Email : lsw
 */
public interface Observer<T> {
    void onUpdate(Observable<T> observable, T data);
}
