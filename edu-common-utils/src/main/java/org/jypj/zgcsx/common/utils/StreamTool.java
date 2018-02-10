package org.jypj.zgcsx.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ChenYu
 */
public class StreamTool {
    /**
     * 从输入流中读取二进制数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readByteFromStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        //网页的二进制数据
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    /**
     * @param is 输入流
     * @return String 返回字符串
     * @throws IOException
     */
    public static String readStringFromStream(InputStream is) throws IOException {
        byte[] bytes = readByteFromStream(is);
        String result = new String(bytes, "UTF-8");
        return result;
    }
}