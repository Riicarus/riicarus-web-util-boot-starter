package com.riicarus.webutil.util;

import com.riicarus.webutil.config.WebUtilProperties;
import com.riicarus.webutil.exception.ApiException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

/**
 * [FEATURE INFO]<br/>
 * 文件操作工具
 *
 * @author Riicarus
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
            throw new ApiException("文件不存在.");
        }

        if (!StringUtils.hasLength(file.getContentType())) {
            throw new ApiException("文件类型不存在.");
        }

        if (file.getSize() > webUtilProperties.getFileProperties().getMaxSize()) {
            throw new ApiException("文件大小溢出.");
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
            throw new ApiException("文件路径不能为空.");
        }

        File file = getFile(filePath, true);
        if (file == null) {
            throw new ApiException("创建/获取文件失败.");
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);

            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("读取文件失败.");
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
        String text;

        File file = getFile(filePath, false);
        if (file == null) {
            throw new ApiException("无法获取对应文件.");
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
            throw new ApiException("读取文件失败.");
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
                throw new ApiException("创建文件失败.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("创建文件失败.");
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
                throw new ApiException("创建文件夹失败.");
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
            throw new ApiException("删除文件失败.");
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
                throw new ApiException("流关闭失败.");
            }
        }
    }

    public static class Base64DecodedMultipartFile implements MultipartFile {

        private final byte[] fileContent;
        private final String header;

        public Base64DecodedMultipartFile(byte[] fileContent, String header) {
            this.fileContent = fileContent;
            this.header = header.split(";")[0];
        }

        @Override
        public String getName() {
            return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
        }

        @Override
        public String getOriginalFilename() {
            return System.currentTimeMillis() + (int) (Math.random() * 10000) + "." + header.split("/")[1];
        }

        @Override
        public String getContentType() {
            return header.split(":")[1];
        }

        @Override
        public boolean isEmpty() {
            return fileContent == null || fileContent.length == 0;
        }

        @Override
        public long getSize() {
            return fileContent.length;
        }

        @Override
        public byte[] getBytes() {
            return fileContent;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(fileContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            FileOutputStream fileOutputStream = new FileOutputStream(dest);
            fileOutputStream.write(fileContent);
            fileOutputStream.close();
        }

        public static MultipartFile base64Convert(String base64) {
            //base64编码后的图片有头信息所以要分离出来   [0]data:image/png;base64, 图片内容为索引[1]
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            try {
                //取索引为1的元素进行处理
                b = decoder.decodeBuffer(baseStrs[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            //处理过后的数据通过Base64DecodedMultipartFile转换为MultipartFile对象
            return new Base64DecodedMultipartFile(b, baseStrs[0]);
        }
    }
}
