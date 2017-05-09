package cn.levacr.is.image_encr_decr_load_demo.glide;

import android.Manifest;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

/**
 * Created by hello on 2017/5/8.
 */

public class LockNameFatcher implements DataFetcher<InputStream> {


    //要加载的model
    private LockName mLockName;

    //已经解密的输入流
    private FileInputStream mFileInputStream;
    private InputStream mInputStream;


    public LockNameFatcher(LockName mLockName) {
        this.mLockName = mLockName;
    }

    //获取图片的数据流
    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, mLockName.getMkey());
        try {
            mFileInputStream = new FileInputStream(mLockName.getmName());
            return new CipherInputStream(mFileInputStream,cipher);

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanup() {
        if (mInputStream != null || mFileInputStream != null) {
            try {
                mInputStream.close();
                mFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mInputStream = null;
                mFileInputStream = null;
            }
        }
    }

    @Override
    public String getId() {
        return mLockName.getmName();
    }

    @Override
    public void cancel() {
        //中断加载图片要调用的函数
    }
}
