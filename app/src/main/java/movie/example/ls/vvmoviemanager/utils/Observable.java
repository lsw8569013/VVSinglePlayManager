package movie.example.ls.vvmoviemanager.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型被观察者
 * Created By: lsw
 * Author : lsw
 * Date :  2017/3/30
 * Email : lsw
 */
public class Observable<T> {

    List<Observer<T>> mObservers=new ArrayList<>();

    public void register(Observer<T> observer){
        if(observer==null){
            throw new NullPointerException("observer is null");
        }
        synchronized (this){
            if(!mObservers.contains(observer)){
                mObservers.add(observer);
            }
        }
    }

    public synchronized void unregister(Observer<T> observer){
        mObservers.remove(observer);
    }

    public void notifyObservers(T data){
        for(Observer<T> t:mObservers){
            t.onUpdate(this,data);
        }
    }

}
