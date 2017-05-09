package cn.levacr.is.image_encr_decr_load_demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.levacr.is.image_encr_decr_load_demo.glide.LockName;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    Button but_encr;
    Button but_lode;
    Button but_decr;
    Button but_lodEncr;
    File imgSrc, imgEncred, imgDecred;


    private static Key toKey(byte[] key) {
        //生成密钥
        return new SecretKeySpec(key, "AES");
    }

    public static byte[] get(Context context) {
        try {
            InputStream is = context.getAssets().open("aes.key");
            byte[] k = new byte[16];
            is.read(k);
            return k;
        } catch (IOException e) {
            Log.e("aes", "jkajkajdjk");
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] initSecretKey() {
        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        kg.init(128);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(1000*1000);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(runnable).start();

        byte[] a = initSecretKey();
        for (int i = 0; i < a.length; i++)
            Log.e("test", a[i] + "");

        final Key key = toKey(a);


        but_encr = (Button) findViewById(R.id.id_encrImg);
        but_decr = (Button) findViewById(R.id.decrImg);
        but_lode = (Button) findViewById(R.id.lodeImg);
        but_lodEncr = (Button) findViewById(R.id.loadencrImg);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = path + "/Download/";
        String pathfrom = path + "1.jpg";
        String pathto = path + "a.jpg.lock";
        String pathDecred = path + "b.jpg";

        imgSrc = new File(pathfrom);
        imgEncred = new File(pathto);
        imgDecred = new File(pathDecred);

        iv = (ImageView) findViewById(R.id.iv);

        but_encr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            encrImg(key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();

            }
        });
        but_lode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImg();
            }
        });
        but_decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    decrImg(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        but_lodEncr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lodEncrImg(key);
            }
        });

        but_lode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImg();
            }
        });
    }


    //加密1.jpg并且输出为2.jpg.lock
    private void encrImg(Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);//初始化加密
        CipherInputStream i = new CipherInputStream(new FileInputStream(imgSrc), cipher);

        OutputStream o = new FileOutputStream(imgEncred);
        int m = 0;
        while (m != -1) {
            m = i.read();
            o.write(m);
        }
        i.close();
        o.close();
    }

    //解密2.jpg.lock为3.jpg
    public void decrImg(Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        CipherInputStream i = new CipherInputStream(new FileInputStream(imgEncred), cipher);
        OutputStream o = new FileOutputStream(imgDecred);
        int m = 0;
        while (m != -1) {
            m = i.read();
            o.write(m);
        }
        i.close();
        o.close();
    }

    public void loadImg() {
        Glide.with(this)
                .load(imgDecred)
                .skipMemoryCache(true)
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);

    }

    public void lodEncrImg(Key key) {


        Glide.with(this)
                .from(LockName.class)
                .fitCenter().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .load(new LockName(key,Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/a.jpg.lock"))
                .into(iv);
    }

}
