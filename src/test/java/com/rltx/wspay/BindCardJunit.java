package com.rltx.wspay;

import com.rltx.WsPayApplication;
import com.rltx.wspay.bindbankcard.entity.OtherMemberPayeeInfoEntity;
import com.rltx.wspay.bindbankcard.service.IOtherMemberPayeeInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


;

/**
 * Created by  lenovo on 2019/8/5 16:54.
 * Description: No Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WsPayApplication.class)
public class BindCardJunit {

    @Autowired
    private IOtherMemberPayeeInfoService otherMemberPayeeInfoService;

    @Test
    public void testInsertSelectMemberPayeeInfo(){
        OtherMemberPayeeInfoEntity entity = new OtherMemberPayeeInfoEntity();
        entity.setFullName("张三0");
        entity.setIdNumber("371521199212143120");
        int n  =  otherMemberPayeeInfoService.insertSelect(entity);
        System.out.println(n +"---------------------------");
    }



}

