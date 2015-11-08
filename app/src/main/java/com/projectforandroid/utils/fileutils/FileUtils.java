package com.projectforandroid.utils.fileutils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.data.StarBean;
import com.projectforandroid.utils.MD5Tools;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 大灯泡 on 2015/9/20.
 * 文件类工具类
 */
public class FileUtils {

    /**
     * SD卡是否存在
     */
    public static boolean isSDexist() {
        //SD卡是否存在
        boolean isExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        return isExist;
    }

    /**
     * 获取SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     */
    public static long getSDCardAllSize() {
        if (isSDexist()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 创建文件夹
     *
     * @param folderName 目录名字，如果创建多个目录，传入的参数请这样写 "XXX/XXX/XXx/XXX"
     * 默认情况下，有SD卡的时候，默认目录为SD卡下，已经初始化好，因此传参无需写入SD啊目录 允许在文件夹存在时创建
     * @return 创建好的目录的路径
     */
    public static String createFolder(String rootPath, String folderName) {
        String ROOT_PATH = "";
        // 判断SD卡
        boolean isSdCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        // 默认根路径
        if (!rootPath.equals("")) {
            if (rootPath.valueOf(rootPath.length()).equals("/")) {
                ROOT_PATH = rootPath;
            } else {
                ROOT_PATH = rootPath + "/";
            }
        } else {
            if (isSdCard) {
                ROOT_PATH = Environment.getExternalStorageDirectory() + File.separator;
            } else {
                ROOT_PATH = "/data/data/" + ProjectApplication.context.getPackageName() + "/";
            }
        }
        // 将String路径根据“/”抽取
        String[] PATHS = folderName.split("/");
        List<String> pathsList = new ArrayList<>();
        // 将path处理后加到list里面
        for (int i = 0; i < PATHS.length; i++) {
            if (i == 0 && !PATHS[0].equals("")) {
                pathsList.add(0, ROOT_PATH + PATHS[0]);
            } else if (i > 0 && !PATHS[i].equals("")) {
                pathsList.add(i, pathsList.get(i - 1) + "/" + PATHS[i]);
            }
        }
        for (int i = 0; i < pathsList.size(); i++) {
            if (i == 0) {
                File firstFolder = new File(pathsList.get(0));
                if (!firstFolder.exists()) {
                    firstFolder.mkdir();
                }
            } else {
                File preFolder = new File(pathsList.get(i - 1));
                File curFolder = new File(pathsList.get(i));
                if (preFolder.exists()) {
                    curFolder.mkdir();
                } else {
                    preFolder.mkdir();
                    curFolder.mkdir();
                }
            }
        }
        String str = "";
        for (int i = 0; i < pathsList.size(); i++) {
            str += "成功创建文件夹" + i + ",该文件夹路径为：" + pathsList.get(i) + '\n';
        }
        return str;
    }

    /**
     * @param fromFile 源文件路径，如"/sdcard/xxx/xxx.txt"
     * @param toFile 目标文件路径，如"xxx/xxx/xxx.txt"
     * @param rewrite 可否重写，如果可以，则会覆盖文件.
     * @return String信息
     * @throws IOException 异常处理
     */
    public static String copyfile(String fromFile, String toFile, Boolean rewrite)
        throws IOException {
        File from = new File(fromFile);
        File to = new File(toFile);
        if (!from.isFile()) {
            return "错误，请注意填写路径";
        }
        if (!from.exists()) {
            return "错误，文件不存在，请注意填写路径";
        }
        if (!from.canRead()) {
            return "错误，文件不可读，请注意权限";
        }
        // 判断目标路径的父文件夹是否存在，不存在就建一个
        if (!to.getParentFile().exists()) {
            to.getParentFile().mkdir();
        }
        // 判断目标文件是否存在以及是否可以复写，如果都满足，则删除原来的目标文件，否则，则在原文件后面加上-new
        if (to.exists() && rewrite) {
            to.delete();
        } else if (to.exists() && !rewrite) {
            String newToFile = getFileNameNoEx(toFile) + "-new." + getExtensionName(toFile);
            File newFile = new File(newToFile);
            to = newFile;
        }

        BufferedInputStream fromInPut = null;
        BufferedOutputStream toOutPut = null;
        // 以下为为复制
        try {
            fromInPut = new BufferedInputStream(new FileInputStream(from));
            toOutPut = new BufferedOutputStream(new FileOutputStream(to));
            int reader = 0;
            int bytesCopied = 0;
            while ((reader = fromInPut.read()) != -1) {
                toOutPut.write((byte) reader);
                bytesCopied++;
            }
            return "成功复制文件，一共复制了:" + bytesCopied + "bytes" + '\n' + "源路径：" + from.getAbsolutePath()
                + '\n' + "目标路径：" + to.getAbsolutePath();
        } finally {
            fromInPut.close();
            toOutPut.close();
        }
    }

    // 获取后缀名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    // 获取无扩展名的文件名
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取指定文件夹指定的后缀的文件路径，并添加前缀且存放到list中（建议在子线程中完成）
     *
     * @param folderPath 文件夹的路径
     * @param prefixion 前缀，如file://
     * @param type 类型，请输入后缀名，如jpg,png甚至txt什么的
     * @return List<Sring>
     */
    public static List<String> getFilesFromFolder(String folderPath, String prefixion,
        String type) {
        String extensionName = getExtensionName(type);// 统一后缀格式（去掉.）
        List<String> fileList = new ArrayList<String>();
        File[] files;
        String path = "";
        // 判断文件夹是否存在
        if ((new File(folderPath).isDirectory())) {
            files = new File(folderPath).listFiles();
            for (File f : files) {
                path = f.getAbsolutePath();
                if (!extensionName.equals("*")) {
                    if (!f.isDirectory() && path.endsWith(extensionName)) {
                        fileList.add(prefixion + path);
                    }
                } else {
                    if (extensionName.equals("*")) {
                        if (!f.isDirectory()) {
                            fileList.add(prefixion + path);
                        }
                    }
                }
            }
        } else {
            return null;
        }
        return fileList;
    }

    // 保存字节到文件
    public static void saveBytesToSD(String filePath, String fileName, byte[] data) {
        if (data != null) {
            FileOutputStream fos = null;
            try {
                File file = new File(filePath, fileName);
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                try {
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 从SD得到文字内容文件
    public static byte[] getBytesFromSD(String filePath) {
        FileInputStream fis = null;
        byte[] buf = null;
        try {
            File file = new File(filePath);
            if (file.exists() == true) {
                fis = new FileInputStream(file);
                buf = new byte[(int) file.length()];
                fis.read(buf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception e) {
            }
        }
        return buf;
    }

    //将文件夹中的文件按修改时间进行排序
    public static void Filecompositor(File[] files) {
        for (int i = 0; i < files.length; i++) {
            for (int j = i + 1; j < files.length; j++) {
                if (files[i].lastModified() < files[j].lastModified()) {
                    File f = files[j];
                    files[j] = files[i];
                    files[i] = f;
                }
            }
        }
    }

    //------------------------------------------从本地获取收藏新闻的数据-----------------------------------------------
    public static List<JSONObject> getDataFromCache() {
        List<JSONObject> list = new ArrayList<>();
        String CollectCachePath = ProjectApplication.getLocalStarPath();
        File dir = new File(CollectCachePath);
        File[] files = dir.listFiles();
        FileUtils.Filecompositor(files);
        for (int i = 0; i < files.length; i++) {
            byte[] mBytes = FileUtils.getBytesFromSD(files[i].toString());
            if (mBytes != null && mBytes.length >= 0) {
                JSONObject object[] = { null };
                try {
                    object[0] = new JSONObject(new String(mBytes));
                    list.add(object[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //------------------------------------------获取本地收藏文件-----------------------------------------------
    public static Map<String, StarBean> getLocalStarJson() {
        Map<String, StarBean> starMap = new HashMap<>();
        String CollectCachePath = ProjectApplication.getLocalStarPath();
        File dir = new File(CollectCachePath);
        File[] files = dir.listFiles();
        FileUtils.Filecompositor(files);
        for (int i = 0; i < files.length; i++) {
            byte[] mBytes = FileUtils.getBytesFromSD(files[i].toString());
            StarBean bean = new StarBean();
            if (mBytes != null && mBytes.length >= 0) {
                JSONObject object[] = { null };
                try {
                    object[0] = new JSONObject(new String(mBytes));
                    bean.setJson(object[0]);
                    bean.setPath(files[i].getAbsolutePath());
                    starMap.put(MD5Tools.hashKey(object[0].optString("url")), bean);
                    bean.setIsStar(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("", "error to get starJson");
                }
            }
        }
        return starMap;
    }

    //------------------------------------------根据文件名删除文件-----------------------------------------------
    public static void delete(String filename, String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].toString().equals(path + "/" + filename)) {
                files[i].delete();
            }
        }
    }


}
