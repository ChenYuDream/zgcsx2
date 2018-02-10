package org.jypj.zgcsx.course.controller;


import org.jypj.zgcsx.common.dto.DtoFile;
import org.jypj.zgcsx.common.utils.FileUtil;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.code.Result;
import org.jypj.zgcsx.course.entity.Attachment;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author qi_ma
 * @since 2017-11-21
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private FileUtil fileUtil;

    /**
     * 显示 图片 大图
     *
     * @return
     */
    @RequestMapping("page/img")
    public String toShowImg(Model model, String id) {
        model.addAttribute("id", id);
        return "upload/dlg/img";
    }

    /**
     * 文件上传到ftp（不写库）
     *
     * @param files 文件数组
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public Result uploads(List<MultipartFile> files, UserInfo userInfo) throws Exception {
        if (files == null || files.size() == 0) {
            return new Result(false, "上传文件为空");
        }
        org.jypj.zgcsx.common.dto.Result fileResult = fileUtil.uploadFileWithSImage(files);

        List<Attachment> attachments = new ArrayList<Attachment>();
        Attachment attachment = null;
        //上传成功
        if (fileResult.getCode().equals(org.jypj.zgcsx.common.dto.Result.SUCCESS)) {
            List<DtoFile> dtoFiles = (List<DtoFile>) fileResult.getResult();
            for (DtoFile dtoFile : dtoFiles) {
                attachment = new Attachment();
                attachment.setFileName(dtoFile.getFileName());
                attachment.setFileType(dtoFile.getFileType());
                attachment.setFileSize(Double.valueOf(dtoFile.getFileSize()));
                attachment.setUserId(userInfo.getId());
                attachment.setFilePath(dtoFile.getFilePath());
                attachment.setFilePathS(StringUtil.isNotEmpty(dtoFile.getSmallImagePath()) ? dtoFile.getSmallImagePath() : dtoFile.getFilePath());   //缩略图地址
                attachment.setUploadTime(LocalDateTime.now());

                attachments.add(attachment);
            }
        }
        return new Result(attachments);
    }

    /**
     * 显示图片
     *
     * @param filePath 文件路径
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("showImg")
    @ResponseBody
    public void showImg(String filePath, HttpServletResponse response) throws Exception {
        fileUtil.showImage(filePath, response);
    }

    /**
     * 文件下载
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("downLoad/{id}")
    public void downLoadFile(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.从Apache服务器上取数据流
            Attachment attachment = attachmentService.selectById(id);
            fileUtil.downLoadFile(attachment.getFilePath(), attachment.getFileName(), request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传到ftp（并保存至数据库）
     *
     * @param file 文件
     * @return
     */
    @PostMapping("file_upload")
    @ResponseBody
    public Result upload(MultipartFile file, UserInfo userInfo) {
        if (file == null || file.isEmpty()) {
            return new Result(false, "上传文件为空");
        }
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        org.jypj.zgcsx.common.dto.Result fileResult = null;
        try {
            fileResult = fileUtil.uploadFileWithSImage(files);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Attachment attachment = null;
        //上传成功
        if (fileResult.getCode().equals(org.jypj.zgcsx.common.dto.Result.SUCCESS)) {
            List<DtoFile> dtoFiles = (List<DtoFile>) fileResult.getResult();
            for (DtoFile dtoFile : dtoFiles) {
                attachment = new Attachment();
                attachment.setFileName(dtoFile.getFileName());
                attachment.setFileType(dtoFile.getFileType());
                attachment.setFileSize(Double.valueOf(dtoFile.getFileSize()));
                attachment.setUserId(userInfo.getId());
                attachment.setFilePath(dtoFile.getFilePath());
                attachment.setFilePathS(StringUtil.isNotEmpty(dtoFile.getSmallImagePath()) ? dtoFile.getSmallImagePath() : dtoFile.getFilePath());   //缩略图地址
                attachment.setUploadTime(LocalDateTime.now());
            }
        }
        attachmentService.insert(attachment);
        return new Result(attachment);
    }

    @RequestMapping("path/{id}")
    public String path(@PathVariable("id") String id) {
        Attachment attachment = attachmentService.selectById(id);
        return "redirect:/file/showImg?filePath=" + attachment.getFilePath();
    }

}


