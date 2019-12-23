package pers.wyj.smartteaching.webApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.wyj.smartteaching.common.FileType;
import pers.wyj.smartteaching.common.WebApiResult;
import pers.wyj.smartteaching.common.WebApiResultCode;
import pers.wyj.smartteaching.model.FileEntity;
import pers.wyj.smartteaching.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

/**
* @Author: wuyangjun
* @Date: 2019/12/16 10:26
* @Version 1.0
*/
@RestController
@RequestMapping("/api/file/")
@CrossOrigin
public class FileApi {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
//    private Logger logger = LoggerFactory.getLogger(FileApi.class);
    @Autowired
    FileService fileService;

    /**
     * 上传单文件
     * @param file formdata格式的文件
     * @param fileEntity 文件必要信息
     * @return
     */
    @PostMapping("upload/singleFile")
    public Object singleFileUpload(MultipartFile file, FileEntity fileEntity) {
//        FileEntity fileEntity = new FileEntity();
        System.out.println("file:"+fileEntity.toString());
//        logger.debug("传入的文件参数：{}", JSON.toJSONString(file, true));
        if (Objects.isNull(file) || file.isEmpty()) {
//            logger.error("文件为空");
            System.out.println("空文件");
            return "文件为空，请重新上传";
        }

        try {
            byte[] bytes = file.getBytes();
//            fileEntity.setAuthorId(authorId);
            Date date = new Date();
            String fileName = file.getOriginalFilename();
            fileEntity.setFileName(Long.toString(date.getTime()) + "$" + fileName); // 日期加原文件名，保证唯一性
            fileEntity.setFileExtendName(fileName.substring(fileName.indexOf('.')+1, fileName.length())); // 扩展名
            fileEntity.setFileSavePath(fileEntity.getFileName());
            Path path = Paths.get(UPLOAD_FOLDER + fileEntity.getFileSavePath());
            System.out.println(fileEntity.toString());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            //文件写入指定路径
            Files.write(path, bytes);
//            logger.debug("文件写入成功...");
            fileService.uploadFile(fileEntity);
            System.out.println("文件写入成功");
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }

    /**
     * 获取文件
     * @param authorId 上传者id
     * @param fileType 文件类型：0（资源），1（家庭作业），2（报告），3（所有文件）
     * @return
     */
    @RequestMapping(value = "getFiles")
    public WebApiResult getResource(Long authorId, FileType fileType) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        webApiResult.setWebApiResult(WebApiResultCode.SUCCESS, fileService.getFiles(authorId, fileType));
        return webApiResult;
    }

    @GetMapping(value = "deleteFile")
    public WebApiResult deleteFile(Long authorId, Long id) {
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        if (fileService.isExist(authorId, id)) {
            String fileName = fileService.getFile(id).getFileName(); // 文件名
            fileService.deleteFile(id);
            File file = new File(UPLOAD_FOLDER+fileName);
            if (file.isFile() && file.exists()) {
                file.delete();
                System.out.println("删除成功"+fileName);
            }
            webApiResult.setWebApiResult(WebApiResultCode.SUCCESS);
        }
        return webApiResult;
    }

    //fileName即要下载的文件名
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam Long fileId, HttpServletRequest request, HttpServletResponse response) {
//        if (StringUtils.isBlank(fileName)) {
//            return;
//        }
        String fileName = null;
        String parentPath = UPLOAD_FOLDER;
        if (fileService.isExistFile(fileId)) {
            System.out.println("文件存在");
            FileEntity fileEntity = fileService.getFile(fileId);
            fileEntity.setFileDownloadTimes(fileEntity.getFileDownloadTimes()+1); // 文件下载次数+1
            fileService.updateDownloadTimes(fileEntity); // 保存
            fileName = fileEntity.getFileName();
//            savePath = fileEntity.getFileSavePath();
        }
        //getParentPath是获取文件保存的目录（可参考第一个单文件上传模块）
//        String parentPath = getParentPath();
        File file = new File(parentPath, fileName);
        if (!file.exists()) {
            return;
        }
        BufferedInputStream bis = null;
        try {
            response.setContentType("application/octet-stream"); // 设置通用格式
            //response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
            byte[] buffer = new byte[1024];
            bis = new BufferedInputStream(new FileInputStream(file));
            OutputStream os = response.getOutputStream();
            int i = -1;
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (Exception e) {
            }
        }
    }
}