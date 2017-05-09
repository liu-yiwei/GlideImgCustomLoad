package cn.levacr.is.image_encr_decr_load_demo.glide;

import java.security.Key;
import java.util.Locale;

/**
 * Created by hello on 2017/5/8.
 *
 * 这个是model的构造类
 */

public class LockName {
    private Key mkey;
    private String mName;


    public LockName(Key key, String name) {
        this.mkey = key;
        this.mName = name;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass()!=obj.getClass()){
            return false;
        }
        LockName lockName = (LockName) obj;
        return this.equals(lockName);

    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    public Key getMkey() {
        return mkey;
    }

    public void setMkey(Key mkey) {
        this.mkey = mkey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
