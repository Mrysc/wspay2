package com.rltx.wspay.utils;

import com.alibaba.fastjson.JSON;
import com.rltx.common.base.BaseContext;
import com.rltx.common.vo.CommParamsVo;
import com.rltx.framework.session.context.SessionContext;
import com.rltx.framework.session.context.impl.SessionContextImpl;
import com.rltx.framework.session.domain.LoginUser;
import com.rltx.framework.support.IFrameworkParamsGetter;
import com.rltx.framework.support.impl.WebFrameworkParamsGetter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtils {
  private Logger logger = LoggerFactory.getLogger(BaseContext.class);
  private static Pattern userAgentPattern = Pattern.compile("([a-zA-Z]+)/([0-9]+)\\s+\\(([a-zA-Z]+)/([0-9.]+)/([a-zA-Z-]+);([0-9]+)\\*([0-9]+);([^;]+)\\)\\s+Location\\s+\\(([0-9.]*)/([0-9.]*)\\)\\s+\\((.*)/(.*)\\)");
  private static Pattern microProgramPattern = Pattern.compile("MicroMessenger");
  /*@Resource(name="fwWebFrameworkParamsGetter")
  private IFrameworkParamsGetter frameworkParamsGetter;
  @Resource(name="fwSessionContext")
  private SessionContext sessionContext;*/

  private static IFrameworkParamsGetter frameworkParamsGetter = SpringContextHolder.getBean(WebFrameworkParamsGetter.class);
  private static SessionContext sessionContext = SpringContextHolder.getBean(SessionContextImpl.class);
  private String module;

  public UserUtils() {}

  public UserUtils(String module) {
    this.module = module;
  }
  
  protected Locale getLocale() {
    return Locale.CHINA;
  }
  
  protected static HttpServletRequest getRequest() {
    return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
  }
  
  protected String getUserAgent() {
    return getRequest().getHeader("User-Agent");
  }
  
  protected Object getSessionAttribute(Object key) {
    return this.sessionContext.getAttribute(key);
  }
  
  protected void removeSessionAttribute(Object key) {
    this.sessionContext.removeAttribute(key);
  }
  
  protected void setSessionAttribute(Object key, Object value) {
    this.sessionContext.setAttribute(key, value);
  }
  
  protected String getPlatform() {
    String userAgent = getUserAgent();
    
    Matcher matcher = userAgentPattern.matcher(userAgent);
    if (matcher.find()) {
      return matcher.group(3);
    }
    return null;
  }
  
  protected Boolean getMicroProgramPlatform() {
    String userAgent = getUserAgent();
    Matcher matcher = microProgramPattern.matcher(userAgent);
    return Boolean.valueOf(matcher.find());
  }
  
  protected Integer getVersionCode() {
    String userAgent = getUserAgent();
    Matcher matcher = userAgentPattern.matcher(userAgent);
    if (matcher.find()) {
      String versionCode = matcher.group(2);
      return Integer.valueOf(Integer.parseInt(versionCode.trim()));
    }
    return null;
  }
  
  protected CommParamsVo getCommParamsVo() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String appCode = this.module;
      String ip = this.frameworkParamsGetter.getCurrentUserIp();
      String synchronousId = this.frameworkParamsGetter.getSynchronousId(false);
      String headerInfo = getRequest().getHeader("commParamsVo");
      try {
        headerInfo = new String(headerInfo.getBytes("ISO-8859-1"), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      CommParamsVo commParamsVo = (CommParamsVo)JSON.parseObject(headerInfo, CommParamsVo.class);
      commParamsVo.setModuleCode(appCode);
      commParamsVo.setIp(ip);
      commParamsVo.setSynchronousId(synchronousId);
      commParamsVo.setDataSources("smartz");
      return commParamsVo;
    }
    Date date = new Date();
    String appCode = this.module;
    String ip = this.frameworkParamsGetter.getCurrentUserIp();
    String synchronousId = this.frameworkParamsGetter.getSynchronousId(false);
    
    LoginUser loginUser = this.sessionContext.getLoginUser();
    String currUserCode = loginUser.getUserCode();
    String currPlatformUserCode = loginUser.getPlatformUserCode();
    String currOrgCode = loginUser.getOrgCode();
    String currentUsername = loginUser.getUserFullName();
    String currentOrgName = loginUser.getOrgFullName();
    
    CommParamsVo commParamsVo = new CommParamsVo();
    commParamsVo.setModuleCode(appCode);
    commParamsVo.setIp(ip);
    commParamsVo.setSynchronousId(synchronousId);
    
    commParamsVo.setPlatformUserCode(currPlatformUserCode);
    commParamsVo.setCreatorUserCode(currUserCode);
    commParamsVo.setCreatorUsername(currentUsername);
    commParamsVo.setCreateTime(date);
    commParamsVo.setUpdateUserCode(currUserCode);
    commParamsVo.setUpdateUsername(currentUsername);
    commParamsVo.setUpdateTime(date);
    commParamsVo.setOwnerUserCode(currUserCode);
    commParamsVo.setOwnerOrgCode(currOrgCode);
    commParamsVo.setOwnerOrgName(currentOrgName);
    return commParamsVo;
  }
  
  private Date transferDate(String source, Date date) {
    Date r = null;
    if (StringUtils.isNotEmpty(source)) {
      try {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        r = df.parse(source);
      } catch (ParseException localParseException) {}
    }
    return null != r ? r : date;
  }
  
  private Map<String, String> getCookieValues() {
    Map<String, String> map = new HashMap();
    Cookie[] cookies = getRequest().getCookies();
    if (ArrayUtils.isNotEmpty(cookies)) {
      for (Cookie cookie : cookies) {
        if ((StringUtils.isNotEmpty(cookie.getName())) && (StringUtils.isNotEmpty(cookie.getValue()))) {
          try {
            String cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
            map.put(cookie.getName(), cookieValue);
          } catch (UnsupportedEncodingException e) {
            this.logger.error("unsupported decoding for " + cookie.getName() + ":" + cookie.getValue(), e);
          }
        }
      }
    }
    return map;
  }
  
  private String getCookieValue(String cookieName) {
    Cookie[] cookies = getRequest().getCookies();
    for (Cookie cookie : cookies) {
      if (StringUtils.equals(cookie.getName(), cookieName)) {
        String cookieValue = cookie.getValue();
        try {
          return URLDecoder.decode(cookieValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
          this.logger.error("unsupported decoding for " + cookie.getName() + ":" + cookie.getValue(), e);
          return null;
        }
      }
    }
    return null;
  }
  
  public static String getCurrentUserCode() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser.getUserCode();
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser.getUserCode();
  }
  
  protected String getCurrentUsername() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser.getUserFullName();
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser.getUserFullName();
  }
  
  protected String getCurrentOrgCode() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("commParamsVo");
      CommParamsVo commParamsVo = (CommParamsVo)JSON.parseObject(headerInfo, CommParamsVo.class);
      return commParamsVo.getOwnerOrgCode();
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser.getOrgCode();
  }
  
  protected String getCurrentOrgName() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser.getOrgFullName();
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser.getOrgFullName();
  }
  
  protected String getCurrentLoginAccount() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser.getLoginAccount();
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser.getLoginAccount();
  }
  
  protected String getCurrentUserIdentityCode() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser.getUserIdentityCode();
    }
    LoginUser loginUser = this.sessionContext.getLoginUser();
    return loginUser.getUserIdentityCode();
  }
  
  public static LoginUser getCurrentLoginUser() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser;
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser;
  }
  
  public static String getCurrentPlatformUserCode() {
    String source = getRequest().getHeader("source");
    if ("smartz".equals(source)) {
      String headerInfo = getRequest().getHeader("loginUser");
      LoginUser loginUser = (LoginUser)JSON.parseObject(headerInfo, LoginUser.class);
      return loginUser.getPlatformUserCode();
    }
    LoginUser loginUser = sessionContext.getLoginUser();
    return loginUser.getPlatformUserCode();
  }


}
