package movie.example.ls.vvmoviemanager.utils;

/**
 * 单例转化器
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/10
 * Email : lsw
 */
public abstract class SingletonUtils<T> {

    private T instance;

    protected abstract T newInstance();

    public final T getInstance() {
        if (instance == null) {
            synchronized (SingletonUtils.class) {
                if (instance == null) {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
