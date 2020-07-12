package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MemberPersonInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("memberThumbnailResourceFileDao")
public interface MemberThumbnailResourceFileDao {
  String select(@Param("resourceCode") String resourceCode);

}
