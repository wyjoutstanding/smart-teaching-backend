package pers.wyj.smartteaching.webApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.wyj.smartteaching.common.WebApiResult;
import pers.wyj.smartteaching.common.WebApiResultCode;
import pers.wyj.smartteaching.dao.ReportEntityDao;
import pers.wyj.smartteaching.model.FileEntity;
import pers.wyj.smartteaching.model.ReportEntity;
import pers.wyj.smartteaching.service.FileService;

import java.util.Objects;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/24 11:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/report/")
@CrossOrigin
public class ReportApi {
    @Autowired
    FileService fileService;
    @Autowired
    ReportEntityDao reportEntityDao;
    /**
     * 提交报告？？前端接受到的file为空？？先弃用
     * @param multipartFile 文件内容
     * @param fileEntity 文件附加信息：上传者
     * @return
     */
    @PostMapping(value = "submitReport")
//    public WebApiResult submitReport(MultipartFile multipartFile, FileEntity fileEntity, Long homeworkId) {
    public WebApiResult submitReport(MultipartFile multipartFile, FileEntity fileEntity) {
        Long homeworkId = 20L;
        WebApiResult webApiResult = new WebApiResult(WebApiResultCode.NO_AUTH);
        System.out.println("homeworkid:"+homeworkId);
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
//            logger.error("文件为空");
            System.out.println("===空文件,重新上传");
//            return "文件为空，请重新上传";
        }
        fileService.fileUpload(multipartFile, fileEntity); // 保存文件
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setHomeworkId(homeworkId);
        reportEntity.setStudentId(fileEntity.getAuthorId());
        reportEntity.setReportFormat("任意");
        reportEntityDao.save(reportEntity);
        webApiResult.setWebApiResult(WebApiResultCode.SUCCESS);
        return webApiResult;
    }
}
