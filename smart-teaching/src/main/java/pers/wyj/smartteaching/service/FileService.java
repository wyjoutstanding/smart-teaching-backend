package pers.wyj.smartteaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pers.wyj.smartteaching.common.FileType;
import pers.wyj.smartteaching.dao.FileEntityDao;
import pers.wyj.smartteaching.model.FileEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/19 14:50
 * @Version 1.0
 */
@Service
@Transactional
public class FileService {
    @Autowired
    FileEntityDao fileEntityDao;
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    public void uploadFile(FileEntity fileEntity) {
        fileEntity.setFileDownloadTimes(0L);
        fileEntity.setFileUploadTime(new Date()); // 上传时间
        fileEntityDao.save(fileEntity);
    }

    public List<FileEntity> getFiles(Long authorId, FileType fileType) {
        return fileEntityDao.getAllByAuthorIdAndFileType(authorId, fileType);
    }

    public FileEntity getFile(Long fileId) {
        return fileEntityDao.getById(fileId);
    }
    public void deleteFile(Long fileId) {
        fileEntityDao.deleteById(fileId);
    }

    public boolean isExist(Long authorId, Long id) {
        return fileEntityDao.existsByAuthorIdAndId(authorId, id);
    }

    public boolean isExistFile(Long id) {
        return fileEntityDao.existsById(id);
    }

    public void updateDownloadTimes(FileEntity fileEntity) {
        fileEntityDao.save(fileEntity);
    }

    /**
     * 上传文件
     * @param file 文件
     * @param fileEntity 文件实体
     * @return
     */
    public Object fileUpload(MultipartFile file, FileEntity fileEntity) {
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
            uploadFile(fileEntity);
            System.out.println("文件写入成功");
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }
}
