
package com.nostra13.universalimageloader.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtil {

    public static String SDCARD = android.os.Environment
            .getExternalStorageDirectory() + "/";

    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void saveBitmap(Bitmap bitmap, String savePath) {
        saveBitmap(bitmap, savePath, Bitmap.CompressFormat.PNG);
    }

    public static void saveBitmap(Bitmap bitmap, String savePath,
            Bitmap.CompressFormat compressFormat) {
        File file = new File(savePath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(compressFormat, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
    }

    public static int copySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static String getJsonStr(InputStream inputStream) {

        StringBuilder total = new StringBuilder();

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    inputStream));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total.toString();
    }

    public static void checkAndCreatFolder(String folderPath) {
        File homeFolder = new File(folderPath);
        if (!homeFolder.exists() || !homeFolder.isDirectory()) {
            L.i("!exists");
            homeFolder.mkdirs();
        }
    }

    public static void checkAndCreatFile(String filePath) {
        File homeFile = new File(filePath);
        if (!homeFile.exists()) {
            try {
                homeFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkFolderExists(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        return false;
    }

    public static boolean checkFileExists(String path) {
        File folder = new File(path);
        if (folder.exists() && folder.isFile()) {
            return true;
        }
        return false;
    }

    public static File copyFileFromAssets(InputStream inputStream, String path) {
        File file = new File(path);

        if (!file.exists()) {
            try {

                OutputStream os = new FileOutputStream(file);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                inputStream.close();
            } catch (Exception e) {
                return null;
            }
        }
        return file;
    }

    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromAssets(InputStream inputStream) {

        StringBuffer strBuf = new StringBuffer();
        try {
            byte[] buffer = new byte[8192];
            while (inputStream.read(buffer, 0, 8192) != -1) {
                strBuf.append(new String(buffer));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBuf.toString();
    }

    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        long freeSize = (freeBlocks * blockSize) / 1024 / 1024;
        return freeSize; // 单位MB
    }

    public static String getStringFromFile(String fileName) {
        File myFile = new File(fileName);
        if (!myFile.exists()) {
            return null;
        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(myFile));
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while ((str = in.readLine()) != null) {
                stringBuffer.append(str);
            }
            in.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
        return getBytes(new File(filePath));
    }

    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
