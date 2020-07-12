package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MemberPersonInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("memberPersonInfoDao")
public interface MemberPersonInfoDao {
  MemberPersonInfoEntity select(@Param("code") String paramString);

  MemberPersonInfoEntity selectResourceImgByIdNumber(@Param("idNumber")String idNumber);
}
