package pers.wyj.smartteaching.common;

import lombok.Getter;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/28 12:56
 * @Version 1.0
 */
@Getter
public enum FileType {
    RESOURCE("资源"),
    HOMEWORK("家庭作业"),
    REPORT("报告");

    FileType(String fileType) {
        this.fileType = fileType;
    }
    private String fileType;
}
