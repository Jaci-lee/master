package com.canbot.u05.sdk.clientdemo.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Formatter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : 文件相关工具类
 * </pre>
 */
public class FileUtils {

        /**
         * KB与Byte的倍数.
         */
        public static final int KB = 1024;

        private FileUtils() {
                throw new UnsupportedOperationException("u can't fuck me...");
        }

        /**
         * 根据文件路径获取文件.
         *
         * @param filePath 文件路径
         * @return 文件
         */
        public static File getFileByPath(String filePath) {
                return StringUtils.isSpace(filePath) ? null : new File(filePath);
        }

        /**
         * 判断文件是否存在.
         *
         * @param filePath 文件路径
         * @return {@code true}: 存在<br>
         * {@code false}: 不存在
         */
        public static boolean isFileExists(String filePath) {
                return isFileExists(getFileByPath(filePath));
        }

        /**
         * 判断文件是否存在.
         *
         * @param file 文件
         * @return {@code true}: 存在<br>
         * {@code false}: 不存在
         */
        public static boolean isFileExists(File file) {
                return file != null && file.exists();
        }

        /**
         * 判断是否是目录.
         *
         * @param dirPath 目录路径
         * @return {@code true}: 是<br>
         * {@code false}: 否
         */
        public static boolean isDir(String dirPath) {
                return isDir(getFileByPath(dirPath));
        }

        /**
         * 判断是否是目录.
         *
         * @param file 文件
         * @return {@code true}: 是<br>
         * {@code false}: 否
         */
        public static boolean isDir(File file) {
                return isFileExists(file) && file.isDirectory();
        }

        /**
         * 判断是否是文件.
         *
         * @param filePath 文件路径
         * @return {@code true}: 是<br>
         * {@code false}: 否
         */
        public static boolean isFile(String filePath) {
                return isFile(getFileByPath(filePath));
        }

        /**
         * 判断是否是文件.
         *
         * @param file 文件
         * @return {@code true}: 是<br>
         * {@code false}: 否
         */
        public static boolean isFile(File file) {
                return isFileExists(file) && file.isFile();
        }

        /**
         * 判断目录是否存在，不存在则判断是否创建成功.
         *
         * @param dirPath 文件路径
         * @return {@code true}: 存在或创建成功<br>
         * {@code false}: 不存在或创建失败
         */
        public static boolean createOrExistsDir(String dirPath) {
                return createOrExistsDir(getFileByPath(dirPath));
        }

        /**
         * 判断目录是否存在，不存在则判断是否创建成功.
         *
         * @param file 文件
         * @return {@code true}: 存在或创建成功<br>
         * {@code false}: 不存在或创建失败
         */
        public static boolean createOrExistsDir(File file) {
                // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
                return file != null
                        && (file.exists() ? file.isDirectory() : file.mkdirs());
        }

        /**
         * 判断文件是否存在，不存在则判断是否创建成功.
         *
         * @param filePath 文件路径
         * @return {@code true}: 存在或创建成功<br>
         * {@code false}: 不存在或创建失败
         */
        public static boolean createOrExistsFile(String filePath) {
                return createOrExistsFile(getFileByPath(filePath));
        }

        /**
         * 判断文件是否存在，不存在则判断是否创建成功.
         *
         * @param file 文件
         * @return {@code true}: 存在或创建成功<br>
         * {@code false}: 不存在或创建失败
         */
        public static boolean createOrExistsFile(File file) {
                if (file == null) {
                        return false;
                }

                // 如果存在，是文件则返回true，是目录则返回false
                if (file.exists()) {
                        return file.isFile();
                }

                if (!createOrExistsDir(file.getParentFile())) {
                        return false;
                }

                try {
                        return file.createNewFile();
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        /**
         * 判断文件是否存在，存在则在创建之前删除.
         *
         * @param filePath 文件路径
         * @return {@code true}: 创建成功<br>
         * {@code false}: 创建失败
         */
        public static boolean createFileByDeleteOldFile(String filePath) {
                return createFileByDeleteOldFile(getFileByPath(filePath));
        }

        /**
         * 判断文件是否存在，存在则在创建之前删除.
         *
         * @param file 文件
         * @return {@code true}: 创建成功<br>
         * {@code false}: 创建失败
         */
        public static boolean createFileByDeleteOldFile(File file) {
                if (file == null) {
                        return false;
                }

                // 文件存在并且删除失败返回false
                if (file.exists() && file.isFile() && !file.delete()) {
                        return false;
                }

                // 创建目录失败返回false
                if (!createOrExistsDir(file.getParentFile())) {
                        return false;
                }

                try {
                        return file.createNewFile();
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        /**
         * 复制或移动目录.
         *
         * @param srcDirPath  源目录路径
         * @param destDirPath 目标目录路径
         * @param isMove      是否移动
         * @return {@code true}: 复制或移动成功<br>
         * {@code false}: 复制或移动失败
         */
        private static boolean copyOrMoveDir(String srcDirPath, String destDirPath,
                                             boolean isMove) {
                return copyOrMoveDir(getFileByPath(srcDirPath),
                        getFileByPath(destDirPath), isMove);
        }

        /**
         * 复制或移动目录.
         *
         * @param srcDir  源目录
         * @param destDir 目标目录
         * @param isMove  是否移动
         * @return {@code true}: 复制或移动成功<br>
         * {@code false}: 复制或移动失败
         */
        private static boolean copyOrMoveDir(File srcDir, File destDir,
                                             boolean isMove) {
                if (srcDir == null || destDir == null) {
                        return false;
                }

                // 如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束
                // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
                // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
                // 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
                String srcPath = srcDir.getPath() + File.separator;
                String destPath = destDir.getPath() + File.separator;
                if (destPath.contains(srcPath)) {
                        return false;
                }

                // 源文件不存在或者不是目录则返回false
                if (!srcDir.exists() || !srcDir.isDirectory()) {
                        return false;
                }

                // 目标目录不存在返回false
                if (!createOrExistsDir(destDir)) {
                        return false;
                }

                File[] files = srcDir.listFiles();
                for (File file : files) {
                        File oneDestFile = new File(destPath + file.getName());
                        if (file.isFile()) {
                                // 如果操作失败返回false
                                if (!copyOrMoveFile(file, oneDestFile, isMove)) {
                                        return false;
                                }

                        } else if (file.isDirectory()) {
                                // 如果操作失败返回false
                                if (!copyOrMoveDir(file, oneDestFile, isMove)) {
                                        return false;
                                }

                        }
                }
                return !isMove || deleteDir(srcDir);
        }

        /**
         * 复制或移动文件.
         *
         * @param srcFilePath  源文件路径
         * @param destFilePath 目标文件路径
         * @param isMove       是否移动
         * @return {@code true}: 复制或移动成功<br>
         * {@code false}: 复制或移动失败
         */
        private static boolean copyOrMoveFile(String srcFilePath,
                                              String destFilePath, boolean isMove) {
                return copyOrMoveFile(getFileByPath(srcFilePath),
                        getFileByPath(destFilePath), isMove);
        }

        /**
         * 复制或移动文件.
         *
         * @param srcFile  源文件
         * @param destFile 目标文件
         * @param isMove   是否移动
         * @return {@code true}: 复制或移动成功<br>
         * {@code false}: 复制或移动失败
         */
        private static boolean copyOrMoveFile(File srcFile, File destFile,
                                              boolean isMove) {
                if (srcFile == null || destFile == null) {
                        return false;
                }

                // 源文件不存在或者不是文件则返回false
                if (!srcFile.exists() || !srcFile.isFile()) {
                        return false;
                }

                // 目标文件存在且是文件则返回false
                if (destFile.exists() && destFile.isFile()) {
                        return false;
                }

                // 目标目录不存在返回false
                if (!createOrExistsDir(destFile.getParentFile())) {
                        return false;
                }

                try {
                        return writeFileFromIS(destFile, new FileInputStream(srcFile),
                                false) && !(isMove && !deleteFile(srcFile));
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        /**
         * 复制目录.
         *
         * @param srcDirPath  源目录路径
         * @param destDirPath 目标目录路径
         * @return {@code true}: 复制成功<br>
         * {@code false}: 复制失败
         */
        public static boolean copyDir(String srcDirPath, String destDirPath) {
                return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
        }

        /**
         * 复制目录.
         *
         * @param srcDir  源目录
         * @param destDir 目标目录
         * @return {@code true}: 复制成功<br>
         * {@code false}: 复制失败
         */
        public static boolean copyDir(File srcDir, File destDir) {
                return copyOrMoveDir(srcDir, destDir, false);
        }

        /**
         * 复制文件.
         *
         * @param srcFilePath  源文件路径
         * @param destFilePath 目标文件路径
         * @return {@code true}: 复制成功<br>
         * {@code false}: 复制失败
         */
        public static boolean copyFile(String srcFilePath, String destFilePath) {
                return copyFile(getFileByPath(srcFilePath),
                        getFileByPath(destFilePath));
        }

        /**
         * 复制文件.
         *
         * @param srcFile  源文件
         * @param destFile 目标文件
         * @return {@code true}: 复制成功<br>
         * {@code false}: 复制失败
         */
        public static boolean copyFile(File srcFile, File destFile) {
                return copyOrMoveFile(srcFile, destFile, false);
        }

        /**
         * 移动目录.
         *
         * @param srcDirPath  源目录路径
         * @param destDirPath 目标目录路径
         * @return {@code true}: 移动成功<br>
         * {@code false}: 移动失败
         */
        public static boolean moveDir(String srcDirPath, String destDirPath) {
                return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
        }

        /**
         * 移动目录.
         *
         * @param srcDir  源目录
         * @param destDir 目标目录
         * @return {@code true}: 移动成功<br>
         * {@code false}: 移动失败
         */
        public static boolean moveDir(File srcDir, File destDir) {
                return copyOrMoveDir(srcDir, destDir, true);
        }

        /**
         * 移动文件.
         *
         * @param srcFilePath  源文件路径
         * @param destFilePath 目标文件路径
         * @return {@code true}: 移动成功<br>
         * {@code false}: 移动失败
         */
        public static boolean moveFile(String srcFilePath, String destFilePath) {
                return moveFile(getFileByPath(srcFilePath),
                        getFileByPath(destFilePath));
        }

        /**
         * 移动文件.
         *
         * @param srcFile  源文件
         * @param destFile 目标文件
         * @return {@code true}: 移动成功<br>
         * {@code false}: 移动失败
         */
        public static boolean moveFile(File srcFile, File destFile) {
                return copyOrMoveFile(srcFile, destFile, true);
        }

        /**
         * 删除目录.
         *
         * @param dirPath 目录路径
         * @return {@code true}: 删除成功<br>
         * {@code false}: 删除失败
         */
        public static boolean deleteDir(String dirPath) {
                return deleteDir(getFileByPath(dirPath));
        }

        /**
         * 删除目录.
         *
         * @param dir 目录
         * @return {@code true}: 删除成功<br>
         * {@code false}: 删除失败
         */
        public static boolean deleteDir(File dir) {
                if (dir == null) {
                        return false;
                }

                // 目录不存在返回true
                if (!dir.exists()) {
                        return true;
                }

                // 不是目录返回false
                if (!dir.isDirectory()) {
                        return false;
                }

                // 现在文件存在且是文件夹
                File[] files = dir.listFiles();
                for (File file : files) {
                        if (file.isFile()) {
                                if (!deleteFile(file)) {
                                        return false;
                                }

                        } else if (file.isDirectory()) {
                                if (!deleteDir(file)) {
                                        return false;
                                }

                        }
                }
                return dir.delete();
        }

        /**
         * 删除文件.
         *
         * @param srcFilePath 文件路径
         * @return {@code true}: 删除成功<br>
         * {@code false}: 删除失败
         */
        public static boolean deleteFile(String srcFilePath) {
                return deleteFile(getFileByPath(srcFilePath));
        }

        /**
         * 删除文件.
         *
         * @param file 文件
         * @return {@code true}: 删除成功<br>
         * {@code false}: 删除失败
         */
        public static boolean deleteFile(File file) {
                return file != null
                        && (!file.exists() || file.isFile() && file.delete());
        }

        /**
         * 获取目录下所有文件.
         *
         * @param dirPath     目录路径
         * @param isRecursive 是否递归进子目录
         * @return 文件链表
         */
        public static List<File> listFilesInDir(String dirPath,
                                                boolean isRecursive) {
                return listFilesInDir(getFileByPath(dirPath), isRecursive);
        }

        /**
         * 获取目录下所有文件.
         *
         * @param dir         目录
         * @param isRecursive 是否递归进子目录
         * @return 文件链表
         */
        public static List<File> listFilesInDir(File dir, boolean isRecursive) {
                if (isRecursive) {
                        return listFilesInDir(dir);
                }

                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                Collections.addAll(list, dir.listFiles());
                return list;
        }

        /**
         * 获取目录下所有文件包括子目录.
         *
         * @param dirPath 目录路径
         * @return 文件链表
         */
        public static List<File> listFilesInDir(String dirPath) {
                return listFilesInDir(getFileByPath(dirPath));
        }

        /**
         * 获取目录下所有文件包括子目录.
         *
         * @param dir 目录
         * @return 文件链表
         */
        public static List<File> listFilesInDir(File dir) {
                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                File[] files = dir.listFiles();
                for (File file : files) {
                        list.add(file);
                        if (file.isDirectory()) {
                                list.addAll(listFilesInDir(file));
                        }
                }
                return list;
        }

        /**
         * 获取目录下所有后缀名为suffix的文件.
         * <p>
         * 大小写忽略
         * </p>
         *
         * @param dirPath     目录路径
         * @param suffix      后缀名
         * @param isRecursive 是否递归进子目录
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(String dirPath,
                                                          String suffix, boolean isRecursive) {
                return listFilesInDirWithFilter(getFileByPath(dirPath), suffix,
                        isRecursive);
        }

        /**
         * 获取目录下所有后缀名为suffix的文件.
         * <p>
         * 大小写忽略
         * </p>
         *
         * @param dir         目录
         * @param suffix      后缀名
         * @param isRecursive 是否递归进子目录
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(File dir, String suffix,
                                                          boolean isRecursive) {
                if (isRecursive) {
                        return listFilesInDirWithFilter(dir, suffix);
                }

                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                File[] files = dir.listFiles();
                for (File file : files) {
                        if (file.getName().toUpperCase(Locale.getDefault()).endsWith(suffix.toUpperCase(Locale.getDefault()))) {
                                list.add(file);
                        }
                }
                return list;
        }

        /**
         * 获取目录下所有后缀名为suffix的文件包括子目录.
         * <p>
         * 大小写忽略
         * </p>
         *
         * @param dirPath 目录路径
         * @param suffix  后缀名
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(String dirPath,
                                                          String suffix) {
                return listFilesInDirWithFilter(getFileByPath(dirPath), suffix);
        }

        /**
         * 获取目录下所有后缀名为suffix的文件包括子目录.
         * <p>
         * 大小写忽略
         * </p>
         *
         * @param dir    目录
         * @param suffix 后缀名
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(File dir, String suffix) {
                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                File[] files = dir.listFiles();
                for (File file : files) {
                        if (file.getName().toUpperCase(Locale.getDefault()).endsWith(suffix.toUpperCase(Locale.getDefault()))) {
                                list.add(file);
                        }
                        if (file.isDirectory()) {
                                list.addAll(listFilesInDirWithFilter(file, suffix));
                        }
                }
                return list;
        }

        /**
         * 获取目录下所有符合filter的文件.
         *
         * @param dirPath     目录路径
         * @param filter      过滤器
         * @param isRecursive 是否递归进子目录
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(String dirPath,
                                                          FilenameFilter filter, boolean isRecursive) {
                return listFilesInDirWithFilter(getFileByPath(dirPath), filter,
                        isRecursive);
        }

        /**
         * 获取目录下所有符合filter的文件.
         *
         * @param dir         目录
         * @param filter      过滤器
         * @param isRecursive 是否递归进子目录
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(File dir,
                                                          FilenameFilter filter, boolean isRecursive) {
                if (isRecursive) {
                        return listFilesInDirWithFilter(dir, filter);
                }

                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                File[] files = dir.listFiles();
                for (File file : files) {
                        if (filter.accept(file.getParentFile(), file.getName())) {
                                list.add(file);
                        }
                }
                return list;
        }

        /**
         * 获取目录下所有符合filter的文件包括子目录.
         *
         * @param dirPath 目录路径
         * @param filter  过滤器
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(String dirPath,
                                                          FilenameFilter filter) {
                return listFilesInDirWithFilter(getFileByPath(dirPath), filter);
        }

        /**
         * 获取目录下所有符合filter的文件包括子目录.
         *
         * @param dir    目录
         * @param filter 过滤器
         * @return 文件链表
         */
        public static List<File> listFilesInDirWithFilter(File dir,
                                                          FilenameFilter filter) {
                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                File[] files = dir.listFiles();
                for (File file : files) {
                        if (filter.accept(file.getParentFile(), file.getName())) {
                                list.add(file);
                        }
                        if (file.isDirectory()) {
                                list.addAll(listFilesInDirWithFilter(file, filter));
                        }
                }
                return list;
        }

        /**
         * 获取目录下指定文件名的文件包括子目录.
         * <p>
         * 大小写忽略
         * </p>
         *
         * @param dirPath  目录路径
         * @param fileName 文件名
         * @return 文件链表
         */
        public static List<File> searchFileInDir(String dirPath, String fileName) {
                return searchFileInDir(getFileByPath(dirPath), fileName);
        }

        /**
         * 获取目录下指定文件名的文件包括子目录.
         * <p>
         * 大小写忽略
         * </p>
         *
         * @param dir      目录
         * @param fileName 文件名
         * @return 文件链表
         */
        public static List<File> searchFileInDir(File dir, String fileName) {
                if (dir == null || !isDir(dir)) {
                        return null;
                }

                List<File> list = new ArrayList<File>();
                File[] files = dir.listFiles();
                for (File file : files) {
                        if (file.getName().toUpperCase(Locale.getDefault()).equals(fileName.toUpperCase(Locale.getDefault()))) {
                                list.add(file);
                        }
                        if (file.isDirectory()) {
                                list.addAll(listFilesInDirWithFilter(file, fileName));
                        }
                }
                return list;
        }

        /**
         * 将输入流写入文件.
         *
         * @param filePath 路径
         * @param is       输入流
         * @param append   是否追加在文件末
         * @return {@code true}: 写入成功<br>
         * {@code false}: 写入失败
         */
        public static boolean writeFileFromIS(String filePath, InputStream is,
                                              boolean append) {
                return writeFileFromIS(getFileByPath(filePath), is, append);
        }

        /**
         * 将输入流写入文件.
         *
         * @param file   文件
         * @param is     输入流
         * @param append 是否追加在文件末
         * @return {@code true}: 写入成功<br>
         * {@code false}: 写入失败
         */
        public static boolean writeFileFromIS(File file, InputStream is,
                                              boolean append) {
                if (file == null || is == null) {
                        return false;
                }

                if (!createOrExistsFile(file)) {
                        return false;
                }

                OutputStream os = null;
                try {
                        os = new BufferedOutputStream(new FileOutputStream(file, append));
                        byte data[] = new byte[KB];
                        int len;
                        while ((len = is.read(data, 0, KB)) != -1) {
                                os.write(data, 0, len);
                        }
                        return true;
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                } finally {
                        closeIO(is, os);
                }
        }

        /**
         * 将字符串写入文件.
         *
         * @param filePath 文件路径
         * @param content  写入内容
         * @param append   是否追加在文件末
         * @return {@code true}: 写入成功<br>
         * {@code false}: 写入失败
         */
        public static boolean writeFileFromString(String filePath, String content,
                                                  boolean append) {
                Logger.v("file", "filePath:" + "content:" + content);
                return writeFileFromString(new File(filePath), content, append);
        }

        /**
         * 将字符串写入文件.
         *
         * @param file    文件
         * @param content 写入内容
         * @param append  是否追加在文件末
         * @return {@code true}: 写入成功<br>
         * {@code false}: 写入失败
         */
        public static boolean writeFileFromString(File file, String content,
                                                  boolean append) {

                if (file == null || content == null) {
                        Logger.v("U05", "NULL");
                        return false;
                }
                if (!createOrExistsFile(file)) {
                        Logger.v("U05", "createOrExistsFile");
                        return false;
                }
                FileWriter fileWriter = null;
                try {
                        fileWriter = new FileWriter(file, append);
                        fileWriter.write(content);
                        fileWriter.flush();
                        return true;
                } catch (IOException e) {
                        e.printStackTrace();
                        Logger.v("U05", "createOrExistsFile" + e.getMessage());
                        return false;
                } finally {
                        closeIO(fileWriter);
                }
        }


        /**
         * 读取文件.
         *
         * @param filename 文件名
         * @return 读取结果
         */
        public static String readFile(String filename) {
                File file = new File(filename);
                BufferedReader bufferedReader = null;
                String str = null;
                try {
                        if (file.exists()) {
                                bufferedReader = new BufferedReader(new FileReader(filename));
                                str = bufferedReader.readLine();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        closeIO(bufferedReader);
                }
                return str;
        }

        /**
         * 简单获取文件编码格式.
         *
         * @param filePath 文件路径
         * @return 文件编码
         */
        public static String getFileCharsetSimple(String filePath) {
                return getFileCharsetSimple(getFileByPath(filePath));
        }

        /**
         * 简单获取文件编码格式.
         *
         * @param file 文件
         * @return 文件编码
         */
        public static String getFileCharsetSimple(File file) {
                int p = 0;
                InputStream is = null;
                try {
                        is = new BufferedInputStream(new FileInputStream(file));
                        p = (is.read() << 8) + is.read();
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        closeIO(is);
                }
                switch (p) {
                        case 0xefbb:
                                return "UTF-8";
                        case 0xfffe:
                                return "Unicode";
                        case 0xfeff:
                                return "UTF-16BE";
                        default:
                                return "GBK";
                }
        }

        /**
         * 获取文件行数.
         *
         * @param filePath 文件路径
         * @return 文件行数
         */
        public static int getFileLines(String filePath) {
                return getFileLines(getFileByPath(filePath));
        }

        /**
         * 获取文件行数.
         *
         * @param file 文件
         * @return 文件行数
         */
        public static int getFileLines(File file) {
                int count = 1;
                InputStream is = null;
                try {
                        is = new BufferedInputStream(new FileInputStream(file));
                        byte[] buffer = new byte[KB];
                        int readChars;
                        while ((readChars = is.read(buffer, 0, KB)) != -1) {
                                for (int i = 0; i < readChars; ++i) {
                                        if (buffer[i] == '\n') {
                                                ++count;
                                        }
                                }
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        closeIO(is);
                }
                return count;
        }

        /**
         * 指定编码按行读取文件到List.
         *
         * @param filePath    文件路径
         * @param charsetName 编码格式
         * @return 文件行链表
         */
        public static List<String> readFile2List(String filePath,
                                                 String charsetName) {
                return readFile2List(getFileByPath(filePath), charsetName);
        }

        /**
         * 指定编码按行读取文件到List.
         *
         * @param file        文件
         * @param charsetName 编码格式
         * @return 文件行链表
         */
        public static List<String> readFile2List(File file, String charsetName) {
                return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
        }

        /**
         * 指定编码按行读取文件到List.
         *
         * @param filePath    文件路径
         * @param st          需要读取的开始行数
         * @param end         需要读取的结束行数
         * @param charsetName 编码格式
         * @return 包含制定行的list
         */
        public static List<String> readFile2List(String filePath, int st, int end,
                                                 String charsetName) {
                return readFile2List(getFileByPath(filePath), st, end, charsetName);
        }

        /**
         * 指定编码按行读取文件到List.
         *
         * @param file        文件
         * @param st          需要读取的开始行数
         * @param end         需要读取的结束行数
         * @param charsetName 编码格式
         * @return 包含从start行到end行的list
         */
        public static List<String> readFile2List(File file, int st, int end,
                                                 String charsetName) {
                if (file == null) {
                        return null;
                }

                if (st > end) {
                        return null;
                }

                BufferedReader reader = null;
                try {
                        String line;
                        int curLine = 1;
                        List<String> list = new ArrayList<String>();
                        if (StringUtils.isSpace(charsetName)) {
                                reader = new BufferedReader(new FileReader(file));
                        } else {
                                reader = new BufferedReader(new InputStreamReader(
                                        new FileInputStream(file), charsetName));
                        }
                        while ((line = reader.readLine()) != null) {
                                if (curLine > end) {
                                        break;
                                }

                                if (st <= curLine && curLine <= end) {
                                        list.add(line);
                                }

                                ++curLine;
                        }
                        return list;
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                } finally {
                        closeIO(reader);
                }
        }

        /**
         * 指定编码按行读取文件到字符串中.
         *
         * @param filePath    文件路径
         * @param charsetName 编码格式
         * @return 字符串
         */
        public static String readFile2String(String filePath, String charsetName) {
                return readFile2String(getFileByPath(filePath), charsetName);
        }

        /**
         * 指定编码按行读取文件到字符串中.
         *
         * @param file        文件
         * @param charsetName 编码格式
         * @return 字符串
         */
        public static String readFile2String(File file, String charsetName) {
                if (file == null) {
                        return null;
                }

                BufferedReader reader = null;
                try {
                        StringBuilder sb = new StringBuilder();
                        if (StringUtils.isSpace(charsetName)) {
                                reader = new BufferedReader(
                                        new InputStreamReader(new FileInputStream(file)));
                        } else {
                                reader = new BufferedReader(new InputStreamReader(
                                        new FileInputStream(file), charsetName));
                        }
                        String line;
                        while ((line = reader.readLine()) != null) {
                                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
                        }
                        // 要去除最后的换行符
                        return sb.delete(sb.length() - 2, sb.length()).toString();
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                } finally {
                        closeIO(reader);
                }
        }

        /**
         * 指定编码按行读取文件到字符串中.
         *
         * @param filePath 文件路径
         * @return StringBuilder对象
         */
        public static byte[] readFile2Bytes(String filePath) {
                return readFile2Bytes(getFileByPath(filePath));
        }

        /**
         * 指定编码按行读取文件到字符串中.
         *
         * @param file 文件
         * @return StringBuilder对象
         */
        public static byte[] readFile2Bytes(File file) {
                if (file == null) {
                        return null;
                }

                try {
                        return ConvertUtils.inputStream2Bytes(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        /**
         * 获取文件大小.
         *
         * @param filePath 文件路径
         * @return 文件大小
         */
        public static String getFileSize(String filePath) {
                return getFileSize(getFileByPath(filePath));
        }

        /**
         * 获取文件大小.
         * <p>
         * 例如：getFileSize(file, ConstUtils.MB); 返回文件大小单位为MB
         * </p>
         *
         * @param file 文件
         * @return 文件大小
         */
        public static String getFileSize(File file) {
                if (!isFileExists(file)) {
                        return "";
                }

                return ConvertUtils.byte2FitSize(file.length());
        }


        /**
         * 获取全路径中的最长目录.
         *
         * @param file 文件
         * @return filePath最长目录
         */
        public static String getDirName(File file) {
                if (file == null) {
                        return null;
                }

                return getDirName(file.getPath());
        }

        /**
         * 获取全路径中的最长目录.
         *
         * @param filePath 文件路径
         * @return filePath最长目录
         */
        public static String getDirName(String filePath) {
                if (StringUtils.isSpace(filePath)) {
                        return filePath;
                }

                int lastSep = filePath.lastIndexOf(File.separator);
                return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
        }

        /**
         * 获取全路径中的文件名.
         *
         * @param file 文件
         * @return 文件名
         */
        public static String getFileName(File file) {
                if (file == null) {
                        return null;
                }

                return getFileName(file.getPath());
        }

        /**
         * 获取全路径中的文件名.
         *
         * @param filePath 文件路径
         * @return 文件名
         */
        public static String getFileName(String filePath) {
                if (StringUtils.isSpace(filePath)) {
                        return filePath;
                }

                int lastSep = filePath.lastIndexOf(File.separator);
                return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
        }

        /**
         * 获取全路径中的不带拓展名的文件名.
         *
         * @param file 文件
         * @return 不带拓展名的文件名
         */
        public static String getFileNameNoExtension(File file) {
                if (file == null) {
                        return null;
                }

                return getFileNameNoExtension(file.getPath());
        }

        /**
         * 获取全路径中的不带拓展名的文件名.
         *
         * @param filePath 文件路径
         * @return 不带拓展名的文件名
         */
        public static String getFileNameNoExtension(String filePath) {
                if (StringUtils.isSpace(filePath)) {
                        return filePath;
                }

                int lastPoi = filePath.lastIndexOf('.');
                int lastSep = filePath.lastIndexOf(File.separator);
                if (lastSep == -1) {
                        return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
                }
                if (lastPoi == -1 || lastSep > lastPoi) {
                        return filePath.substring(lastSep + 1);
                }
                return filePath.substring(lastSep + 1, lastPoi);
        }

        /**
         * 获取全路径中的文件拓展名.
         *
         * @param file 文件
         * @return 文件拓展名
         */
        public static String getFileExtension(File file) {
                if (file == null) {
                        return null;
                }

                return getFileExtension(file.getPath());
        }

        /**
         * 获取全路径中的文件拓展名.
         *
         * @param filePath 文件路径
         * @return 文件拓展名
         */
        public static String getFileExtension(String filePath) {
                if (StringUtils.isSpace(filePath)) {
                        return filePath;
                }

                int lastPoi = filePath.lastIndexOf('.');
                int lastSep = filePath.lastIndexOf(File.separator);
                if (lastPoi == -1 || lastSep >= lastPoi) {
                        return "";
                }

                return filePath.substring(lastPoi);
        }

        /**
         * 快速复制.
         *
         * @param is 输入流
         * @param os 输出流
         * @throws IOException
         */
        public static void copyFileFast(FileInputStream is, FileOutputStream os)
                throws IOException {
                FileChannel in = is.getChannel();
                FileChannel out = os.getChannel();
                in.transferTo(0, in.size(), out);
        }

        /**
         * 分享文件.
         *
         * @param context  上下文
         * @param title    标题
         * @param filePath 文件路径
         */
        public static void shareFile(Context context, String title,
                                     String filePath) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                Uri uri = Uri.parse("file://" + filePath);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(intent, title));
        }

        /**
         * 压缩.
         *
         * @param is 输入流
         * @param os 输出流
         */
        public static void zip(InputStream is, OutputStream os) {
                GZIPOutputStream gzip = null;
                try {
                        gzip = new GZIPOutputStream(os);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = is.read(buf)) != -1) {
                                gzip.write(buf, 0, len);
                                gzip.flush();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        closeIO(is);
                        closeIO(gzip);
                }
        }

        /**
         * 解压缩.
         *
         * @param is 输入流
         * @param os 输出流
         */
        public static void unzip(InputStream is, OutputStream os) {
                GZIPInputStream gzip = null;
                try {
                        gzip = new GZIPInputStream(is);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = gzip.read(buf)) != -1) {
                                os.write(buf, 0, len);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        closeIO(gzip);
                        closeIO(os);
                }
        }

        /**
         * 格式化文件大小.
         *
         * @param context 上下文
         * @param size    大小
         * @return 格式化结果
         */
        public static String formatFileSize(Context context, long size) {
                return Formatter.formatFileSize(context, size);
        }

        /**
         * 将输入流写入到文件.
         *
         * @param is       输入流
         * @param fileName 文件名字
         */
        public static void Stream2File(InputStream is, String fileName) {
                byte[] b = new byte[1024];
                int len;
                FileOutputStream os = null;
                try {
                        os = new FileOutputStream(new File(fileName));
                        while ((len = is.read(b)) != -1) {
                                os.write(b, 0, len);
                                os.flush();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        closeIO(is);
                        closeIO(os);
                }
        }

        /**
         * 创建文件夹.
         *
         * @param filePath 文件夹路径
         * @return
         */
        public static boolean createFolder(String filePath) {
                return createFolder(filePath, false);
        }

        /**
         * 创建文件夹.
         *
         * @param filePath 文件夹路径
         * @param recreate 是否重新创建
         * @return
         */
        public static boolean createFolder(String filePath, boolean recreate) {
                String folderName = getFolderName(filePath);
                if (folderName == null || folderName.length() == 0
                        || folderName.trim().length() == 0) {
                        return false;
                }
                File folder = new File(folderName);
                if (folder.exists()) {
                        if (recreate) {
                                deleteFile(folderName);
                                return folder.mkdirs();
                        } else {
                                return true;
                        }
                } else {
                        return folder.mkdirs();
                }
        }

        /**
         * 获取文件夹名字.
         *
         * @param filePath 文件路径
         * @return 获取结果
         */
        public static String getFolderName(String filePath) {
                if (filePath == null || filePath.length() == 0
                        || filePath.trim().length() == 0) {
                        return filePath;
                }
                int filePos = filePath.lastIndexOf(File.separator);
                return (filePos == -1) ? "" : filePath.substring(0, filePos);
        }

        /**
         * 删除文件夹下的文件.
         *
         * @param folder 文件夹路径
         * @return 删除是否成功
         */
        public static boolean deleteFiles(String folder) {
                if (folder == null || folder.length() == 0
                        || folder.trim().length() == 0) {
                        return true;
                }
                File file = new File(folder);
                if (!file.exists()) {
                        return true;
                }
                if (file.isFile()) {
                        return file.delete();
                }
                if (!file.isDirectory()) {
                        return false;
                }
                for (File f : file.listFiles()) {
                        if (f.isFile()) {
                                f.delete();
                        } else if (f.isDirectory()) {
                                deleteFile(f.getAbsolutePath());
                        }
                }
                return file.delete();
        }

        /**
         * 打开图片 使用系统图片浏览器.
         *
         * @param mContext  上下文
         * @param imagePath 图片地址
         */
        public static void openImage(Context mContext, String imagePath) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromFile(new File(imagePath));
                intent.setDataAndType(uri, "image/*");
                mContext.startActivity(intent);
        }

        /**
         * 打开视频 使用系统播放器.
         *
         * @param mContext  上下文
         * @param videoPath 视频地址
         */
        public static void openVideo(Context mContext, String videoPath) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("oneshot", 0);
                intent.putExtra("configchange", 0);
                Uri uri = Uri.fromFile(new File(videoPath));
                intent.setDataAndType(uri, "video/*");
                mContext.startActivity(intent);
        }

        /**
         * 打开url链接.
         *
         * @param mContext 上下文
         * @param url      url链接
         */
        public static void openURL(Context mContext, String url) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
        }


        public static final String DOWNLOAD_DIR = "download";
        public static final String CACHE_DIR = "cache";
        public static final String ICON_DIR = "icon";

        /**
         * 鍒ゆ柇SD鍗℃槸鍚︽寕杞�.
         */
        public static boolean isSDCardAvailable() {
                if (Environment.MEDIA_MOUNTED.equals(Environment
                        .getExternalStorageState())) {
                        return true;
                } else {
                        return false;
                }
        }


        /**
         * 鍒涘缓鏂囦欢澶�.
         */
        public static boolean createDirs(String dirPath) {
                File file = new File(dirPath);
                if (!file.exists() || !file.isDirectory()) {
                        return file.mkdirs();
                }
                return true;
        }

        /**
         * 澶嶅埗鏂囦欢锛屽彲浠ラ�鎷╂槸鍚﹀垹闄ゆ簮鏂囦欢.
         */
        public static boolean copyFile(String srcPath, String destPath,
                                       boolean deleteSrc) {
                File srcFile = new File(srcPath);
                File destFile = new File(destPath);
                File parentFile = destFile.getParentFile();
                if (!parentFile.exists()){
                        parentFile.mkdirs();
                }
                return copyFile(srcFile, destFile, deleteSrc);
        }

        /**
         * 澶嶅埗鏂囦欢锛屽彲浠ラ�鎷╂槸鍚﹀垹闄ゆ簮鏂囦欢.
         */
        public static boolean copyFile(File srcFile, File destFile,
                                       boolean deleteSrc) {
                if (!srcFile.exists() || !srcFile.isFile()) {
                        return false;
                }
                InputStream in = null;
                OutputStream out = null;
                try {
                        in = new FileInputStream(srcFile);
                        out = new FileOutputStream(destFile);
                        byte[] buffer = new byte[1024];
                        int i = -1;
                        while ((i = in.read(buffer)) > 0) {
                                out.write(buffer, 0, i);
                                out.flush();
                        }
                        if (deleteSrc) {
                                srcFile.delete();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                } finally {
                        IOUtils.close(out);
                        IOUtils.close(in);
                }
                return true;
        }

        /**
         * 鍒ゆ柇鏂囦欢鏄惁鍙啓.
         */
        public static boolean isWriteable(String path) {
                if (StringUtils.isEmpty(path)) {
                        return false;
                }
                File f = new File(path);
                return f.exists() && f.canWrite();
        }

        /**
         * 淇敼鏂囦欢鐨勬潈闄�渚嬪"777"绛�.
         */
        public static void chmod(String path, String mode) {
                try {
                        String command = "chmod " + mode + " " + path;
                        Runtime runtime = Runtime.getRuntime();
                        runtime.exec(command);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * 鎶婃暟鎹啓鍏ユ枃浠�.
         *
         * @param is       鏁版嵁娴�
         * @param path     鏂囦欢璺緞
         * @param recreate 濡傛灉鏂囦欢瀛樺湪锛屾槸鍚﹂渶瑕佸垹闄ら噸寤�
         * @return 鏄惁鍐欏叆鎴愬姛
         */
        public static boolean writeFile(InputStream is, String path,
                                        boolean recreate) {
                boolean res = false;
                File f = new File(path);
                FileOutputStream fos = null;
                try {
                        if (recreate && f.exists()) {
                                f.delete();
                        }
                        if (!f.exists() && null != is) {
                                File parentFile = new File(f.getParent());
                                parentFile.mkdirs();
                                int count = -1;
                                byte[] buffer = new byte[1024];
                                fos = new FileOutputStream(f);
                                while ((count = is.read(buffer)) != -1) {
                                        fos.write(buffer, 0, count);
                                }
                                res = true;
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(fos);
                        IOUtils.close(is);
                }
                return res;
        }

        /**
         * 鎶婂瓧绗︿覆鏁版嵁鍐欏叆鏂囦欢.
         *
         * @param content 闇�鍐欏叆鐨勫瓧绗︿覆
         * @param path    鏂囦欢璺緞鍚嶇О
         * @param append  鏄惁浠ユ坊鍔犵殑妯″紡鍐欏叆
         * @return 鏄惁鍐欏叆鎴愬姛
         */
        public static boolean writeFile(byte[] content, String path, boolean append) {
                boolean res = false;
                File f = new File(path);
                RandomAccessFile raf = null;
                try {
                        if (f.exists()) {
                                if (!append) {
                                        f.delete();
                                        f.createNewFile();
                                }
                        } else {
                                f.createNewFile();
                        }
                        if (f.canWrite()) {
                                raf = new RandomAccessFile(f, "rw");
                                raf.seek(raf.length());
                                raf.write(content);
                                res = true;
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(raf);
                }
                return res;
        }

        public static boolean writeFile(List<String> lstrcontent, String path, boolean append) {
                boolean res = false;
                File f = new File(path);
                RandomAccessFile raf = null;
                try {
                        if (f.exists()) {
                                if (!append) {
                                        f.delete();
                                        f.createNewFile();
                                }
                        } else {
                                f.createNewFile();
                        }
                        if (f.canWrite()) {
                                raf = new RandomAccessFile(f, "rw");
                                raf.seek(raf.length());
                                int size = lstrcontent.size();
                                for (int i = 0; i < size; i++) {
                                        raf.write((lstrcontent.get(i) + "\n").getBytes());
                                }
                                res = true;
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(raf);
                }
                return res;
        }

        /**
         * 鎶婂瓧绗︿覆鏁版嵁鍐欏叆鏂囦欢.
         *
         * @param content 闇�鍐欏叆鐨勫瓧绗︿覆
         * @param path    鏂囦欢璺緞鍚嶇О
         * @param append  鏄惁浠ユ坊鍔犵殑妯″紡鍐欏叆
         * @return 鏄惁鍐欏叆鎴愬姛
         */
        public static boolean writeFile(String content, String path, boolean append) {
                return writeFile(content.getBytes(), path, append);
        }

        /**
         * 鎶婇敭鍊煎鍐欏叆鏂囦欢.
         *
         * @param filePath 鏂囦欢璺緞
         * @param key      閿�
         * @param value    鍊�
         * @param comment  璇ラ敭鍊煎鐨勬敞閲�
         */
        public static void writeProperties(String filePath, String key,
                                           String value, String comment) {
                if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
                        return;
                }
                FileInputStream fis = null;
                FileOutputStream fos = null;
                File f = new File(filePath);
                try {
                        if (!f.exists() || !f.isFile()) {
                                f.createNewFile();
                        }
                        fis = new FileInputStream(f);
                        Properties p = new Properties();
                        p.load(fis);// 鍏堣鍙栨枃浠讹紝鍐嶆妸閿�瀵硅拷鍔犲埌鍚庨潰
                        p.setProperty(key, value);
                        fos = new FileOutputStream(f);
                        p.store(fos, comment);
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(fis);
                        IOUtils.close(fos);
                }
        }

        /**
         * 鏍规嵁鍊艰鍙�.
         */
        public static String readProperties(String filePath, String key,
                                            String defaultValue) {
                if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
                        return null;
                }
                String value = null;
                FileInputStream fis = null;
                File f = new File(filePath);
                try {
                        if (!f.exists() || !f.isFile()) {
                                f.createNewFile();
                        }
                        fis = new FileInputStream(f);
                        Properties p = new Properties();
                        p.load(fis);
                        value = p.getProperty(key, defaultValue);
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(fis);
                }
                return value;
        }

        /**
         * 鎶婂瓧绗︿覆閿�瀵圭殑map鍐欏叆鏂囦欢.
         */
        public static void writeMap(String filePath, Map<String, String> map,
                                    boolean append, String comment) {
                if (map == null || map.size() == 0 || StringUtils.isEmpty(filePath)) {
                        return;
                }
                FileInputStream fis = null;
                FileOutputStream fos = null;
                File f = new File(filePath);
                try {
                        if (!f.exists() || !f.isFile()) {
                                f.createNewFile();
                        }
                        Properties p = new Properties();
                        if (append) {
                                fis = new FileInputStream(f);
                                p.load(fis);// 鍏堣鍙栨枃浠讹紝鍐嶆妸閿�瀵硅拷鍔犲埌鍚庨潰
                        }
                        p.putAll(map);
                        fos = new FileOutputStream(f);
                        p.store(fos, comment);
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(fis);
                        IOUtils.close(fos);
                }
        }

        /**
         * 鎶婂瓧绗︿覆閿�瀵圭殑鏂囦欢璇诲叆map.
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        public static Map<String, String> readMap(String filePath,
                                                  String defaultValue) {
                if (StringUtils.isEmpty(filePath)) {
                        return null;
                }
                Map<String, String> map = null;
                FileInputStream fis = null;
                File f = new File(filePath);

                try {
                        if (!f.exists() || !f.isFile()) {
                                f.createNewFile();
                        }
                        fis = new FileInputStream(f);
                        Properties p = new Properties();
                        p.load(fis);
                        map = new HashMap<String, String>((Map) p);// 鍥犱负properties缁ф壙浜唌ap锛屾墍浠ョ洿鎺ラ�杩噋鏉ユ瀯閫犱竴涓猰ap
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(fis);
                }
                return map;
        }

        /**
         * 鏀瑰悕.
         */
        public static boolean copy(String src, String des, boolean delete) {
                File file = new File(src);
                if (!file.exists()) {
                        return false;
                }
                File desFile = new File(des);
                FileInputStream in = null;
                FileOutputStream out = null;
                try {
                        in = new FileInputStream(file);
                        out = new FileOutputStream(desFile);
                        byte[] buffer = new byte[1024];
                        int count = -1;
                        while ((count = in.read(buffer)) != -1) {
                                out.write(buffer, 0, count);
                                out.flush();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        IOUtils.close(in);
                        IOUtils.close(out);
                }
                if (delete) {
                        file.delete();
                }
                return true;
        }



        /**
         * 从文件读取数据，一行一行读出来,彻底解决中文乱码问题.
         *
         * @param filepath
         */
        public static String readFileCommon(String filepath) {
                StringBuffer buffer = new StringBuffer();
                File file = new File(filepath);
                if (!file.exists()) {
//            Logger.e(TAG, "file not exist,Please Check! Path=" + filepath);
                        return null;
                }
                FileInputStream fis = null;
                InputStreamReader isr = null;
                BufferedReader br = null;
                try {
                        fis = new FileInputStream(file);
                        BufferedInputStream in = new BufferedInputStream(fis);
                        br = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        in.mark(4);
                        byte[] first3bytes = new byte[3];
                        in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
                        in.reset();
                        if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                                && first3bytes[2] == (byte) 0xBF) {// utf-8
                                br = new BufferedReader(new InputStreamReader(in, "utf-8"));
//                Logger.d(TAG, filepath + " file type=utf-8");
                        } else if (first3bytes[0] == (byte) 0xFF
                                && first3bytes[1] == (byte) 0xFE) {
                                br = new BufferedReader(new InputStreamReader(in, "unicode"));
//                Logger.d(TAG, filepath + " file type=unicode");
                        } else if (first3bytes[0] == (byte) 0xFE
                                && first3bytes[1] == (byte) 0xFF) {
                                br = new BufferedReader(new InputStreamReader(in, "utf-16be"));
//                Logger.d(TAG, filepath + " file type=utf-16be");
                        } else if (first3bytes[0] == (byte) 0xFF
                                && first3bytes[1] == (byte) 0xFF) {
                                br = new BufferedReader(new InputStreamReader(in, "utf-16le"));
//                Logger.d(TAG, filepath + " file type=utf-16le");
                        } else {
                                br = new BufferedReader(new InputStreamReader(in, "GBK"));
//                Logger.d(TAG, filepath + " file type=GBK");
                        }
                        String line = "";
                        while ((line = br.readLine()) != null) {
                                buffer.append(line.trim());
                        }
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                } finally {
                        if (br != null) {
                                try {
                                        br.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                        if (isr != null) {
                                try {
                                        isr.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                        if (fis != null) {
                                try {
                                        fis.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
                return buffer.toString();
        }


        /**
         * 写入文件.
         *
         * @param path 文件名
         * @param data 文件内容
         * @return 写入结果
         */
        public static boolean writeFile(String path, String data) {
                String code = getFileEncode(path);
                boolean flag = true;
                OutputStreamWriter osw = null;
                try {
                        File file = new File(path);
                        if (!file.exists()) {
                                file = new File(file.getParent());
                                if (!file.exists()) {
                                        file.mkdirs();
                                }
                        }
                        if ("asci".equals(code)) {
                                code = "GBK";
                        }
                        osw = new OutputStreamWriter(new FileOutputStream(path), code);
                        osw.write(data);
                        osw.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                        flag = false;
                } finally {
                        try {
                                if (osw != null) {
                                        osw.close();
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
//                Logger.e(TAG, "toFile IO Exception:" + e.getMessage());
                                flag = false;
                        }
                }
                return flag;
        }


        public static String getFileEncode(String path) {
                String charset = "asci";
                byte[] first3Bytes = new byte[3];
                BufferedInputStream bis = null;
                try {
                        boolean checked = false;
                        bis = new BufferedInputStream(new FileInputStream(path));
                        bis.mark(0);
                        int read = bis.read(first3Bytes, 0, 3);
                        if (read == -1) {
                                return charset;
                        }

                        if (first3Bytes[0] == (byte) 0xFF
                                && first3Bytes[1] == (byte) 0xFE) {
                                charset = "Unicode";// UTF-16LE
                                checked = true;
                        } else if (first3Bytes[0] == (byte) 0xFE
                                && first3Bytes[1] == (byte) 0xFF) {
                                charset = "Unicode";// UTF-16BE
                                checked = true;
                        } else if (first3Bytes[0] == (byte) 0xEF
                                && first3Bytes[1] == (byte) 0xBB
                                && first3Bytes[2] == (byte) 0xBF) {
                                charset = "UTF8";
                                checked = true;
                        }
                        bis.reset();
                        if (!checked) {
                                int len = 0;
                                int loc = 0;
                                while ((read = bis.read()) != -1) {
                                        loc++;
                                        if (read >= 0xF0) {
                                                break;
                                        }

                                        if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                                        {
                                                break;
                                        }

                                        if (0xC0 <= read && read <= 0xDF) {
                                                read = bis.read();
                                                if (0x80 <= read && read <= 0xBF) {
                                                        // 双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
                                                        continue;
                                                } else {
                                                        break;
                                                }

                                        } else if (0xE0 <= read && read <= 0xEF) { // 也有可能出错，但是几率较小
                                                read = bis.read();
                                                if (0x80 <= read && read <= 0xBF) {
                                                        read = bis.read();
                                                        if (0x80 <= read && read <= 0xBF) {
                                                                charset = "UTF-8";
                                                                break;
                                                        } else {
                                                                break;
                                                        }

                                                } else {
                                                        break;
                                                }

                                        }
                                }
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        if (bis != null) {
                                try {
                                        bis.close();
                                } catch (IOException ex) {
                                        ex.printStackTrace();
                                }
                        }
                }
                return charset;
        }


        /**
         * 关闭IO.
         *
         * @param closeables closeable
         */
        public static void closeIO(Closeable... closeables) {
                if (closeables == null) {
                        return;
                }

                try {
                        for (Closeable closeable : closeables) {
                                if (closeable != null) {
                                        closeable.close();
                                }
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }


        /**
         * @param context
         * @param file
         * @param assetFile
         */
        public static void copyFileFromAssets(Context context, File file,
                                              String assetFile) {
                FileOutputStream fos = null;
                try {
                        fos = new FileOutputStream(file);
                        InputStream ins = context.getAssets().open(assetFile);
                        byte[] bytes = new byte[4096];
                        int readed = 0;
                        while ((readed = ins.read(bytes)) != -1) {
                                fos.write(bytes, 0, readed);
                        }
                        fos.flush();
                        fos.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                } finally {
                        if (fos != null) {
                                try {
                                        fos.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }


        public static String getAssetStr(Context mContext, String path) {
                BufferedReader bufferReader = null;
                InputStreamReader inputReader = null;
                InputStream inputStream = null;
                try {
                        inputStream = mContext.getAssets().open(path);
                        inputReader = new InputStreamReader(inputStream);
                        bufferReader = new BufferedReader(inputReader);
                        // 读取一行
                        String line = null;
                        StringBuffer strBuffer = new StringBuffer();
                        while ((line = bufferReader.readLine()) != null) {
                                strBuffer.append(line);
                        }
                        return strBuffer.toString();
                } catch (IOException e) {
                        e.printStackTrace();
//            Logger.e(TAG, "IOException :getAssetStr---" + path);
                } finally {
                        if (bufferReader != null) {
                                try {
                                        bufferReader.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                        if (inputReader != null) {
                                try {
                                        inputReader.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                        if (inputStream != null) {
                                try {
                                        inputStream.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
                return null;
        }

        /**
         * 读取asset目录下文件内容.
         *
         * @return content
         */
        public static String readAssetsFile(Context mContext, String file,
                                            String code) {
                int len = 0;
                byte[] buf = null;
                String result = "";
                try {
                        InputStream in = mContext.getAssets().open(file);
                        len = in.available();
                        buf = new byte[len];
                        in.read(buf, 0, len);

                        result = new String(buf, code);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return result;
        }

        /**
         * 移动文件到另外文件夹.
         * isdeleteold 是否删除源文件 ，该方法用在即使文件位移失败也要删除源文件
         */
        public static boolean renameTonewpath(String oldpath, String newpath, boolean isdeleteold) {
                File file = new File(oldpath);
                File newfile = new File(newpath);
                if (!newfile.getParentFile().exists()) {
                        newfile.getParentFile().mkdirs();
                }
                boolean flag = file.renameTo(newfile);
                //如果文件转移文件夹成功 删除源文件
                if (isdeleteold) {
                        if (file.exists()) {
                                file.delete();
                        }
                }
                return flag;
        }


}