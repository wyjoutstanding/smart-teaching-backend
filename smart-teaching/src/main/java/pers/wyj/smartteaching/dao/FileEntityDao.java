package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.wyj.smartteaching.model.FileEntity;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/27 23:36
 * @Version 1.0
 */
public interface FileEntityDao extends JpaRepository<FileEntity, Integer> {
}
