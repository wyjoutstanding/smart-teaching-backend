package pers.wyj.smartteaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wyj.smartteaching.common.FileType;
import pers.wyj.smartteaching.dao.FileEntityDao;
import pers.wyj.smartteaching.model.FileEntity;

import java.util.Date;
import java.util.List;

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
}
