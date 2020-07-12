package com.rltx.wspay.other.dao;

import com.rltx.wspay.account.entity.MemberOrgInfoEntity;
import com.rltx.wspay.notice.entity.ChargeNotifyRecordEntity;
import com.rltx.wspay.other.entity.PhotoEntity;
import com.rltx.wspay.other.entity.UploadPhotoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("uploadPhotoDao")
public interface UploadPhotoDao {

    void insert(@Param("entity") PhotoEntity PhotoEntity);

    List<PhotoEntity> select(@Param("merchUserCode") String merchUserCode, @Param("type") String type);


}
