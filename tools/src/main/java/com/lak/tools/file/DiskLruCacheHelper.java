package com.lak.tools.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import com.jakewharton.disklrucache.DiskLruCache;
import com.lak.logger.CLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by lawrence
 *
 * @link DiskLruCache https://github.com/JakeWharton/DiskLruCache
 */

public class DiskLruCacheHelper {
    private static final int DEFAULT_MAX_COUNT = 5 * 1024 * 1024;
    private static final int DEFAULT_APP_VERSION = 1;
    // 同一个key可以对应多少文件
    private static final int DEFAULT_VALUE_COUNT = 1;

    private DiskLruCache mDiskLruCache = null;

    private DiskLruCacheHelper(Builder builder) throws IOException {
        if (builder.mDirectory == null)
            throw new NullPointerException("Directory can not be null.");
        if (!builder.mDirectory.exists())
            throw new IllegalArgumentException(builder.mDirectory + " does not exists.");
        if (!builder.mDirectory.isDirectory())
            throw new IllegalArgumentException(builder.mDirectory + " is not a directory.");
        mDiskLruCache = DiskLruCache.open(builder.mDirectory,
                builder.mAppVersion, builder.mValueCount, builder.mMaxSize);
    }

    @Nullable
    public DiskLruCache.Editor editor(String key) {
        String hashKey = KeyTools.hashKeyForDisk(key);
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(hashKey);
            if (editor == null)
                CLog.d("the entry spcified key:" + key + " is editing by other . ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor;
    }

    @Nullable
    public DiskLruCache.Snapshot snapshot(String key) {
        String hashKey = KeyTools.hashKeyForDisk(key);
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(hashKey);
            if (snapshot == null)
                CLog.d("DiskLruCache. Not find entry , or entry.readable = false");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return snapshot;
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<< String 数据 读写 <<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void putString(String key, String value) {
        DiskLruCache.Editor editor = editor(key);
        if (editor != null) {
            try {
                editor.set(0, value);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    editor.abort(); // 释放编辑锁
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public String getString(String key) {
        String result = "";
        DiskLruCache.Snapshot snapshot = snapshot(key);
        try {
            if (snapshot != null)
                result = snapshot.getString(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<< byte[] 数据 读写 <<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void putBytes(String key, byte[] bytes) {
        DiskLruCache.Editor editor = editor(key);
        OutputStream outputStream = null;
        if (editor != null) {
            try {
                outputStream = editor.newOutputStream(0);
                outputStream.write(bytes);
                outputStream.flush();
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    editor.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Nullable
    public byte[] getBytes(String key) {
        byte[] bytes = null;
        DiskLruCache.Snapshot snapshot = snapshot(key);
        InputStream inputStream = null;
        if (snapshot != null) {
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                inputStream = snapshot.getInputStream(0);
                byte[] buffer = new byte[256];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                bytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bytes;
    }



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<< Serializable 数据 读写 <<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void putSerializable(String key, Serializable serializable) {
        DiskLruCache.Editor editor = editor(key);
        ObjectOutputStream objectOutputStream = null;
        if (editor != null) {
            try {
                OutputStream outputStream = editor.newOutputStream(0);
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(serializable);
                objectOutputStream.flush();
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    editor.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Nullable
    public <T> T getSerializable(String key) {
        T result = null;
        DiskLruCache.Snapshot snapshot = snapshot(key);
        ObjectInputStream objectInputStream = null;
        if (snapshot != null) {
            try {
                InputStream inputStream = snapshot.getInputStream(0);
                objectInputStream = new ObjectInputStream(inputStream);
                result = (T) objectInputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<< Serializable 数据 读写 <<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public void putBitmap(String key, Bitmap bitmap) {
        if (bitmap != null) {
            byte[] bytes = null;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                bytes = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (bytes != null && bytes.length > 0) putBytes(key, bytes);
        }
    }

    @Nullable
    public Bitmap getBitmap(String key) {
        byte[] bytes = getBytes(key);
        if (bytes != null && bytes.length > 0)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<< 其他方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public boolean remove(String key) {
        String hashKey = KeyTools.hashKeyForDisk(key);
        try {
            return mDiskLruCache.remove(hashKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws IOException {
        mDiskLruCache.close();
    }

    public void delete() throws IOException {
        mDiskLruCache.delete();
    }

    public void flush() throws IOException {
        mDiskLruCache.flush();
    }

    public boolean isClosed() {
        return mDiskLruCache.isClosed();
    }

    public long size() {
        return mDiskLruCache.size();
    }

    public void setMaxSize(long maxSize) {
        mDiskLruCache.setMaxSize(maxSize);
    }

    public File getDirectory() {
        return mDiskLruCache.getDirectory();
    }

    public long getMaxSize() {
        return mDiskLruCache.getMaxSize();
    }





    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // <<<<<<<<<<<< DiskLruCacheHelper 建造者 <<<<<<<<<<<<<<
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public static class Builder {
        private File mDirectory = null;                   // 指定数据缓存地址
        private int mAppVersion = DEFAULT_APP_VERSION;    // APP版本号，当版本号改变时，缓存数据会被清除
        private int mValueCount = DEFAULT_VALUE_COUNT;    // 同一个key可以对应多少文件【目前只支持一个】
        private long mMaxSize = DEFAULT_MAX_COUNT;        // 最大可以缓存的数据量

        public Builder directory(File directory) {
            this.mDirectory = directory;
            return this;
        }

        public Builder appVersion(int appVersion) {
            this.mAppVersion = appVersion;
            return this;
        }

        public Builder maxSize(long maxSize) {
            this.mMaxSize = maxSize;
            return this;
        }

        public DiskLruCacheHelper build() throws IOException {
            return new DiskLruCacheHelper(this);
        }

        public DiskLruCacheHelper build(Context ctx) throws IOException {
            mDirectory = FileTools.appCacheFolder(ctx);

            return new DiskLruCacheHelper(this);
        }

        public DiskLruCacheHelper build(Context ctx, String dirName) throws IOException {
            String rootDir = FileTools.appCachePath(ctx);
            String cacheDir = rootDir + File.separator + dirName;
            mDirectory = FileTools.getFolder(cacheDir);

            return new DiskLruCacheHelper(this);
        }

    }

}
