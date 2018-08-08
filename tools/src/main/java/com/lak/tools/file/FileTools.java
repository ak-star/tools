package com.lak.tools.file;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lak.logger.CLog;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by lawrence
 */

public class FileTools {

    /**
     * @return true-SD卡可用，false-SD卡不可用
     */
    public static boolean isSdCardExist() {
        return Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState());
    }

    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @return 优先返回SD卡下，应用的cache文件夹；
     * SD卡不可用下，返回内存存储应用下的cache文件夹；
     */
    public static File appCacheFolder(Context ctx) {
        if (ctx != null) {
            if (!isSdCardExist())
                return ctx.getCacheDir();
            else
                return ctx.getExternalCacheDir();
        }
        return null;
    }

    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @return 优先返回SD卡下，应用的cache文件夹路径；
     * SD卡不可用下，返回内存存储应用下的cache文件夹路径；
     */
    public static String appCachePath(Context ctx) {
        File file = appCacheFolder(ctx);
        return file != null ? file.getAbsolutePath() : "";
    }

    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @return 优先返回SD卡下，应用的files文件夹；
     * SD卡不可用下，返回内存存储应用下的files文件夹；
     */
    public static File appFilesFolder(Context ctx) {
        if (ctx != null) {
            if (!isSdCardExist())
                return ctx.getFilesDir();
            else
                return ctx.getExternalFilesDir(null);
        }
        return null;
    }

    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @return 优先返回SD卡下，应用的files文件夹路径；
     * SD卡不可用下，返回内存存储应用下的files文件夹路径；
     */
    public static String appFilesPath(Context ctx) {
        File file = appCacheFolder(ctx);
        return file != null ? file.getAbsolutePath() : "";
    }

    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @param type {@code null}
     *             {@link Environment#DIRECTORY_MUSIC},
     *             {@link Environment#DIRECTORY_PODCASTS},
     *             {@link Environment#DIRECTORY_RINGTONES},
     *             {@link Environment#DIRECTORY_ALARMS},
     *             {@link Environment#DIRECTORY_NOTIFICATIONS},
     *             {@link Environment#DIRECTORY_PICTURES}, or
     *             {@link Environment#DIRECTORY_MOVIES}.
     * @return 优先返回SD卡下，应用的files文件夹路径；
     * SD卡不可用下，返回内存存储应用下的files文件夹路径；
     */
    public static File appSDCardFilesFolder(Context ctx, @Nullable String type) {
        if (ctx != null && isSdCardExist())
            return ctx.getExternalFilesDir(type);

        return null;
    }

    /**
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @param type {@code null}
     *             {@link Environment#DIRECTORY_MUSIC},
     *             {@link Environment#DIRECTORY_PODCASTS},
     *             {@link Environment#DIRECTORY_RINGTONES},
     *             {@link Environment#DIRECTORY_ALARMS},
     *             {@link Environment#DIRECTORY_NOTIFICATIONS},
     *             {@link Environment#DIRECTORY_PICTURES}, or
     *             {@link Environment#DIRECTORY_MOVIES}.
     * @return 优先返回SD卡下，应用的files文件夹路径；
     * SD卡不可用下，返回内存存储应用下的files文件夹路径；
     */
    public static String appSDCardFilesPath(Context ctx, @Nullable String type) {
        File sdFile = appSDCardFilesFolder(ctx, type);

        return sdFile != null ? sdFile.getAbsolutePath() : "";
    }

    /**
     * @return 返回文件夹 {@link File}
     */
    @Nullable
    public static File getFolder(String folderPath) {
        File folder = null;
        if (!TextUtils.isEmpty(folderPath)) {
            folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();
        }
        return folder;
    }

    /**
     * @return 返回文件 {@link File}
     */
    @Nullable
    public static File getFile(String filePath) {
        File file = null;
        if (!TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
            if (!file.exists()) {
                File folder = getFolder(file.getParent());
                if (folder != null) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        file = null;
                    }
                } else { file = null; }
            }
        }
        return file;
    }

    /**
     * 递归删除文件、文件夹下的所有数据
     */
    public static void deleteFile(File file) {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File subFile : files) deleteFile(subFile);
            }
        }
        file.delete();
        CLog.d("delete file：" + file.getAbsolutePath());
    }

    /**
     * 递归删除文件、文件夹下的所有数据
     */
    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) return;
        File file = new File(filePath);
        if (file != null) deleteFile(file);
    }

    /**
     * @return 返回文件、文件夹的大小
     */
    public static long folderSize(File file) {
        long size = 0;
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    size += folderSize(subFile);
                }
            }
            size = file.length();
            CLog.d("size=" + size + "\tfile=" + file.getAbsolutePath());
        }
        return size;
    }

    /**
     * @return 返回文件、文件夹的大小
     */
    public static long folderSize(String filePath) {
        long size = 0;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            size = folderSize(file);
        }
        return size;
    }

    /**
     * @return 返回文件、文件夹的大小
     */
    public static String formatFileSize(File file) {
        long size = folderSize(file);
        return formatSize(size);
    }

    /**
     * @return 返回文件、文件夹的大小
     */
    public static String formatFileSize(String file) {
        long size = folderSize(file);
        return formatSize(size);
    }

    /**
     * @return 格式化大小，返回{B、KB、MB、GB、TB}
     */
    public static String formatSize(long size) {
        if (size <= 0) return 0 + "B";
        if (size < 1024) return size + "B";             // B

        double kiloByte = size * 1.0 / 1024;            // KB
        if (kiloByte < 1024) {
            BigDecimal result = new BigDecimal(Double.toString(kiloByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double megaByte = kiloByte * 1.0 / 1024;        // MB
        if (megaByte < 1024) {
            BigDecimal result = new BigDecimal(Double.toString(megaByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double gigaByte = megaByte * 1.0 / 1024;        // GB
        if (gigaByte < 1024) {
            BigDecimal result = new BigDecimal(Double.toString(gigaByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }

        double teraBytes = gigaByte * 1.0 / 1024;        // TB
        BigDecimal result = new BigDecimal(Double.toString(teraBytes));
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
