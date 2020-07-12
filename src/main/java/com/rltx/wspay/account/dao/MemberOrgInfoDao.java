package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MemberOrgInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("memberOrgInfoDao")
public  interface MemberOrgInfoDao {
  MemberOrgInfoEntity select(@Param("code") String paramString);
}
