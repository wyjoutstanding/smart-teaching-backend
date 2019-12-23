package pers.wyj.smartteaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import pers.wyj.smartteaching.model.ClassHomeworkEntity;

import java.util.List;

/**
 * @Author: wuyangjun
 * @Date: 2019/12/22 21:05
 * @Version 1.0
 */
public interface ClassHomeworkEntityDao extends JpaRepository<ClassHomeworkEntity, Long> {
    public List<ClassHomeworkEntity> getAllById(Long Id);
    public List<ClassHomeworkEntity> getAllByClassId(Long classId);
    public List<ClassHomeworkEntity> getAllByHomeworkId(Long homeworkId);
    public ClassHomeworkEntity getByHomeworkId(Long homeworkId);
    public boolean existsByHomeworkId(Long homeworkId);
    public boolean existsByHomeworkIdAndClassId(Long homeworkId, Long classId); // 查看是否已存在相应班级作业
    @Modifying
    public void deleteAllByHomeworkId(Long homeworkId); // 根据id全部删除
    @Modifying
    public void deleteAllByClassId(Long classId);
}
