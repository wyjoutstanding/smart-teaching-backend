package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.common.FileType;
import pers.wyj.smartteaching.model.FileEntity;

import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/27 23:36
 * @Version 1.0
 */
public interface FileEntityDao extends JpaRepository<FileEntity, Long> {

    /**
     * 根据id和文件类型查找所有文件
     * @param authorId 上传者id
     * @param fileType 文件类型
     * @return
     */
    public List<FileEntity> getAllByAuthorIdAndFileType(Long authorId, FileType fileType);

    public FileEntity getById(Long id);

    public List<FileEntity> getAllByAuthorId(Long authorId); // 根据用户id获取其上传的所有文件

    public void deleteById(Long id); // 根据文件id删除文件

    public boolean existsByAuthorIdAndId(Long authorId, Long id); // 根据上传作者id判断是否存在相应文件id

//    public void
}
