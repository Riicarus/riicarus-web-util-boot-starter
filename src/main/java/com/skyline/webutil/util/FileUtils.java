package com.skyline.webutil.util;

import com.skyline.webutil.config.WebUtilProperties;
import com.skyline.webutil.exception.Asserts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * [FEATURE INFO]<br/>
 * 文件操作工具
 *
 * @author Skyline
 * @create 2022-11-1 15:54
 * @since 1.0.0
 */
@Component
@Setter
@Getter
public class FileUtils {

    private WebUtilProperties webUtilProperties;

    @Autowired
    public void setWebUtilProperties(WebUtilProperties webUtilProperties) {
        this.webUtilProperties = webUtilProperties;
    }

    /**
     * 检查文件
     *
     * @param file 文件
     */
    public void checkFile(MultipartFile file) {
        if (file.isEmpty()) {
            Asserts.fail("文件不存在.");
        }

        if (!StringUtils.hasLength(file.getContentType())) {
            Asserts.fail("文件类型不存在.");
        }

        if (file.getSize() > webUtilProperties.getFileProperties().getMaxSize()) {
            Asserts.fail("文件大小溢出.");
        }
    }

    /**
     * 将文本写入本地文件
     *
     * @param text 待写入的文本
     * @param filePath 文件路径
     * @param append 是否覆盖写入
     */
    public void writeText(String text, String filePath, Boolean append) {
        if (!StringUtils.hasLength(filePath.trim())) {
            Asserts.fail("文件路径不能为空");
        }

        File file = getFile(filePath, true);
        if (file == null) {
            Asserts.fail("创建/获取文件失败");
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);

            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Asserts.fail("读取文件失败");
        } finally {
            closeFile(fileWriter);
        }
    }


    /**
     * 将文本写入本地文件, 默认覆盖写入
     *
     * @param text 待写入的文本
     * @param filePath 文件路径
     */
    public void writeText(String text, String filePath) {
        writeText(text, filePath, false);
    }

    /**
     * 读取文本内容
     *
     * @param filePath 文件地址
     * @return 文本内容
     */
    public String readText(String filePath) {
        String text = null;

        File file = getFile(filePath, false);
        if (file == null) {
            Asserts.fail("无法获取对应文件");
        }

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);

            StringBuilder builder = new StringBuilder();
            char[] chars = new char[1024];
            int num;
            while (-1 != (num = fileReader.read(chars))) {
                builder.append(chars, 0, num);
            }

            text = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Asserts.fail("读取文件失败");
        } finally {
            closeFile(fileReader);
        }

        return text;
    }

    /**
     * 返回项目的绝对路径
     *
     * @return 项目绝对路径
     * */
    public String getPath() {
        File file=new File("");
        return file.getAbsolutePath();
    }

    /**
     * 根据文件路径生成 file 对象
     *
     * @param filePath 文件路径
     * @param create 没有找到文件是否自动生成文件
     * @return 生成的 file 对象
     */
    public File getFile(String filePath, Boolean create) {
        File file = new File(filePath);

        // 文件存在,直接返回
        if (file.exists()) {
            return file;
        }

        // 文件不存在但不创建文件,返回 null
        if (!create) {
            return null;
        }

        // 文件不存在,创建文件,返回文件
        try {
            if (!file.createNewFile()) {
                Asserts.fail("创建文件失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Asserts.fail("创建文件失败");
        }

        return file;
    }

    /**
     * 根据文件夹路径生成 file 对象
     *
     * @param filePath 文件夹路径
     * @return 生成的 file 对象
     */
    public File getDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Asserts.fail("创建文件夹失败");
            }
        }

        return file;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        // 有可能没有文件
        boolean success = true;
        if (file.exists() && file.isFile()) {
            success = file.delete();
        }
        if (!success) {
            Asserts.fail("删除文件失败!");
        }
    }

    /**
     * 将 byte[] 转换为 base64
     *
     * @param bytes byte[]
     * @return base64
     */
    public String byteArrayToBase64(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 关闭流
     *
     * @param stream 流
     */
    private void closeFile(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                Asserts.fail("流关闭失败");
            }
        }
    }
}
