package org.jypj.zgcsx.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.jypj.zgcsx.common.dto.DtoFile;
import org.jypj.zgcsx.common.dto.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author jian_wu
 * @date 2017/11-20
 * 新三小文件工具类，配合FTP和Apache服务器使用
 * 配置文件需要配ftp的地址，用户名和密码，以及Apache的地址
 */
@Component
@Slf4j
public class FileUtil {

    @Value("${FTP.SERVER.IP}")
    public  String hostname;
    @Value("${FTP.SERVER.PORT}")
    public  Integer port;
    @Value("${FTP.LOGINNAME}")
    public  String username;
    @Value("${FTP.PASSWORD}")
    public  String password;
    @Value("${APACHE.SERVER}")
    public  String APACHE_IP;

    public final  String FIREFOX = "firefox";

    private final  List<String> IMGS = Arrays.asList(".jpg",".png",".jpeg",".bmp");

    /**
     * 图片显示
     * @param filePath 文件路径
     * @param response
     * @return
     */
    public  String showImage(String filePath,HttpServletResponse response){
        try {
            //1.从Apache服务器上取数据流
            String ftpPath = "http://" + APACHE_IP + "/" + filePath;
            URL url = new URL(ftpPath);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            // 设置相应信息的类型
            response.reset();
            response.setContentType("image/jpeg");
            OutputStream os = null;
            os = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int bytesRead;
            byte[] buffer = new byte[5 * 1024];
            while ((bytesRead = bis.read(buffer)) > 0) {
                // 将文件发送到客户端
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            bis.close();
            os.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "图片未找到";
    }


    /**
     * 上传，图片生成缩略图
     * @param files
     * @return
     * @throws IOException
     */
    public  Result uploadFileWithSImage(List<MultipartFile> files) throws IOException{
        List<DtoFile> dtoFiles = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        //获取年
        int year = calendar.get(Calendar.YEAR);
        //获取月份，0表示1月份
        int month = calendar.get(Calendar.MONTH) + 1;
        String path = year + "/" + month;
        //循环遍历，取出单个文件
        for (MultipartFile file : files) {
            //这个判断必须要加
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                String uuid = StringUtil.getLowUUID();
                String fileFtpName =uuid+ fileType;
                String fileImageName = uuid+"_s"+fileType;
                InputStream is = null;
                DtoFile dtoFile = new DtoFile();
                //图片生成缩略图
                try {
                    if(IMGS.contains(fileType)){
                        is = file.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        Thumbnails.of(is).size(640,640).toOutputStream(outputStream);
                        byte[] bytes = outputStream.toByteArray();
                        InputStream isImage = new ByteArrayInputStream(outputStream.toByteArray());
                        if(uploadFile(path,fileImageName,isImage)){
                            if(isImage!=null){
                                isImage.close();
                            }if(outputStream!=null){
                                outputStream.close();
                            }if(is!=null){
                                is.close();
                            }
                        }else{
                            return new Result(Result.FAIL,"上传失败");
                        }
                        dtoFile.setSmallImagePath(path+"/"+fileImageName);
                        dtoFile.setSmallImage(true);
                        log.info("文件上传完成,fileName:{" + fileName + "},filePath:{" + path+"/"+fileImageName + "}");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return new Result(Result.FAIL,"上传失败");
                }

                is = null;
                try {
                    is = file.getInputStream();
                    //FTP上传

                    //成功
                    if (uploadFile(path, fileFtpName, is)) {
                        if (is != null) {
                            is.close();
                        }
                    }else{
                        return new Result(Result.FAIL,"上传失败");
                    }
                    dtoFile.setFileName(fileName);
                    dtoFile.setFileType(fileType);
                    dtoFile.setFilePath(path+"/"+fileFtpName);
                    dtoFile.setFileSize(file.getSize()+"");
                    dtoFiles.add(dtoFile);
                    log.info("文件上传完成,fileName:{" + fileName + "},filePath:{" + path+"/"+fileFtpName + "}");
                }catch (Exception e){
                    e.printStackTrace();
                    if (is != null) {
                        is.close();
                    }
                    return new Result(Result.FAIL,"上传失败");
                }
            }
        }
        return new Result(dtoFiles);
    }

    /**
     * 上传，传Spring的Files即可
     * @param files
     * @return
     * @throws IOException
     */
    public  Result uploadFile(List<MultipartFile> files) throws IOException {
        List<DtoFile> dtoFiles = new ArrayList<>();
        //循环遍历，取出单个文件
        for (MultipartFile file : files) {
            //这个判断必须要加
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                String fileFtpName = StringUtil.getLowUUID() + fileType;
                InputStream is = null;
                try {
                    is = file.getInputStream();
                    //FTP上传
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    //获取年
                    int year = calendar.get(Calendar.YEAR);
                    //获取月份，0表示1月份
                    int month = calendar.get(Calendar.MONTH) + 1;
                    String path = year + "/" + month;
                    //成功
                    if (uploadFile(path, fileFtpName, is)) {
                        if (is != null) {
                            is.close();
                        }
                    }else{
                        return new Result(Result.FAIL,"上传失败");
                    }
                    DtoFile dtoFile = new DtoFile();
                    dtoFile.setFileName(fileName);
                    dtoFile.setFileType(fileType);
                    dtoFile.setFilePath(path+"/"+fileFtpName);
                    dtoFile.setFileSize(file.getSize()+"");
                    dtoFiles.add(dtoFile);
                    log.info("文件上传完成,fileName:{" + fileName + "},filePath:{" + path+"/"+fileFtpName + "}");
                }catch (Exception e){
                    e.printStackTrace();
                    if (is != null) {
                        is.close();
                    }
                    return new Result(Result.FAIL,"上传失败");
                }
            }
        }
        return new Result(dtoFiles);
    }

    /**
     * 上传文件
     *
     * @param pathname    保存路径名称（不要带文件名）
     * @param fileName    保存文件名称
     * @param inputStream 输入流
     * @return
     */
    public  boolean uploadFile(String pathname, String fileName, InputStream inputStream) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            //连接ftp服务器
            if (!loginFtpService(ftpClient)) {
                //连接失败
                return false;
            }
            //切换到上传目录
            if (!ftpClient.changeWorkingDirectory(pathname)) {
                //如果目录不存在创建目录
                String[] dirs = pathname.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) {
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        if (!ftpClient.makeDirectory(tempPath)) {
                            log.debug("ftp创建目录失败");
                        } else {
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //log.info("文件上传完成,fileName:{" + fileName + "},filePath:{" + pathname + "}");
        }
        return flag;
    }


    /**
     * 下载文件
     *
     * @param filePath 文件地址（带文件名）
     * @param fileName 下载文件名称
     * @param request
     * @param response
     * @return
     */
    public  boolean downLoadFile(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) {
        Boolean flag = false;
        try {
            //1.从Apache服务器上取数据流
            String ftpPath = "http://" + APACHE_IP + "/" + filePath;
            URL url = new URL(ftpPath);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            //1.1 如果是ajax验证文件是否存在，则直接返回不传流
            if (request.getHeader("x-requested-with") != null
                    && "XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
                if (is != null) {
                    is.close();
                }
                return true;
            }
            OutputStream os = null;
            //2.设置response为下载
            response.reset();
            response.setContentType("application/x-download;charset=UTF-8");
            //2.1解决中文乱码
            //2.1.1火狐乱码问题
            String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (UserAgent.contains(FIREFOX)) {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                response.setHeader("content-disposition", "attachment; filename=\"" + fileName + "\"");
            }else{
                char[] fileNameChars = fileName.toCharArray();
                StringBuffer sb = new StringBuffer();
                for(char c:fileNameChars){
                    if(StringUtil.isChinese(c)){
                        //只转义中文字符
                        sb.append(URLEncoder.encode(c+"", "UTF-8"));
                    }else{
                        //其他字符不转义，不然特殊字符会出现乱码
                        sb.append(c);
                    }
                }
                fileName = sb.toString();
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            }
            BufferedInputStream bis = new BufferedInputStream(is);
            os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int bytesRead;
            byte[] buffer = new byte[5 * 1024];
            while ((bytesRead = bis.read(buffer)) > 0) {
                // 将文件发送到客户端
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            bis.close();
            os.close();
            is.close();
        } catch (Exception e) {
            //e.printStackTrace();
            //设置409为文件下载错误
            response.setStatus(409);
        }
        return flag;
    }

    /**
     * 删除文件
     * @return
     */
    public Boolean delFile(){
        Boolean flag = false;
        //todo 删除文件
        return flag;
    }

    /**
     * 登录
     */
    public  boolean loginFtpService(FTPClient ftpClient) {
        try {
            //连接FTP服务器
            ftpClient.connect(hostname, port);
            //登录FTP服务器
            ftpClient.login(username, password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return false;
            }
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ftp服务器连接失败,error:{" + e.getMessage() + "}");
            return false;
        }
        return true;
    }

}
