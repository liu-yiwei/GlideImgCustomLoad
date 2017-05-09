package cn.levacr.is.image_encr_decr_load_demo.glide;

import android.content.Context;
import android.renderscript.ScriptGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.InputStream;

import cn.levacr.is.image_encr_decr_load_demo.R;

/**
 * Created by hello on 2017/5/8.
 */

public class LockNameModule implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ViewTarget.setTagId(R.id.glide_tag_id);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(LockName.class, InputStream.class,new LockNameLoader.Factory());
    }
}
