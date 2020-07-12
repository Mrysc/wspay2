package com.rltx.wspay.utils.entity;


import com.rltx.wspay.utils.UserUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by  zl on 2020/6/9 15:59.
 * Description:
 */
@Data
public abstract class BaseEntity {
    private int id;

    private String code;

    private String createBy;

    private String updateBy;

    private Date createTime;

    private Date updateTime;

    private String deleted;


    private void preInsertTime(){
        this.createTime = new Date();
        this.updateTime = createTime;
    }

    private void preUpdateTime(){
        this.updateTime = new Date();
    }


    public void preInsert(){
        String currentUserCode = UserUtils.getCurrentPlatformUserCode();
        this.createBy = currentUserCode;
        this.updateBy = this.createBy;
        preInsertTime();
    }

    public void preUpdate(){
        String currentUserCode = UserUtils.getCurrentPlatformUserCode();
        this.updateBy = currentUserCode;
        preUpdateTime();
    }

    public void preUpdateKafka(){
        preUpdateTime();
        this.updateBy = "kafka消息消费";
    }
}
