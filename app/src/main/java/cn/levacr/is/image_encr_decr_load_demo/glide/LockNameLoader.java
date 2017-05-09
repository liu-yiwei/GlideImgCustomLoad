package cn.levacr.is.image_encr_decr_load_demo.glide;

import android.content.Context;
import android.os.Build;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorFileLoader;

import java.io.InputStream;

/**
 * Created by hello on 2017/5/8.
 */

public class LockNameLoader implements ModelLoader<LockName,InputStream>{

    private final ModelCache<LockName, LockName> mModelCache;

    public LockNameLoader(){this(null);}

    public LockNameLoader(ModelCache<LockName, LockName> modelCache){
        mModelCache = modelCache;
    }



    @Override
    public DataFetcher<InputStream> getResourceFetcher(LockName model, int width, int height) {
        LockName lockName = model;
        if (mModelCache != null) {
            lockName = mModelCache.get(model, 0, 0);
            if (lockName == null) {
                mModelCache.put(model, 0, 0, model);
                lockName = model;
            }
        }
        return new LockNameFatcher(lockName);
    }




    public static class Factory implements ModelLoaderFactory<LockName,InputStream>{

        public Factory(){}


        //缓存
        private final ModelCache<LockName, LockName> mModelCache = new ModelCache<>(500);
        @Override
        public ModelLoader<LockName, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new LockNameLoader(mModelCache);
        }



        @Override
        public void teardown() {

        }
    }

}
