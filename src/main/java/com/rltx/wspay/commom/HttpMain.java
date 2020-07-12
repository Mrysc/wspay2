package com.rltx.wspay.commom;

import com.rltx.wspay.utils.constant.ParamUtil;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpMain {



    public static final String IsvOrgId="202210000000000001891";

    public static final String Appid="2020062900000555";

    //商户号 同时也是 余额支付付款方
    public static final String  merchantId ="226801000000156876727";

    //商户号 同时也是  余额支付 收款方
    public static final String  PayeeId ="226801000000156333724";

    public static final String Currency ="CNY";

    public static final String SmsCode ="888888";
//ParamUtil.getParamInfoByKey("IsvOrgId")
    public static final String reqUrl="https://fcsupergwlitedev.dl.szulodev.com/open/api/common/request2.htm";

    public static final String upLoadReqUrl="https://fcsupergwlitedev.dl.alipaydev.com/ant/mybank/merchantprod/merchant/uploadphoto.htm";

    public static final String kybApply="http://11.166.150.53:80/bkmerchantprod/kyb/apply.htm";

        public static final String kybMatch ="http://11.166.150.53:80/bkmerchantprod/kyb/match.htm";

    public static final String CHARSET          = "UTF-8";

    public static final String SIGN_ALGORITHM   = "SHA256withRSA";





    //除 kyb 打款验证 测试环境是 http 请求 其余都是 https 请求 适用与此 请求方法
    public static String httpsReq(String reqUrl, String param) throws NoSuchAlgorithmException,
            NoSuchProviderException, IOException,
            KeyManagementException {
        URL url = new URL(reqUrl);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String paramString, SSLSession paramSSLSession) {
                return true;
            }
        });

        //创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, new TrustManager[] { tm }, new java.security.SecureRandom());

        //从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        //创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
        httpsConn.setSSLSocketFactory(ssf);

        httpsConn.setDoOutput(true);
        httpsConn.setRequestMethod("POST");
        httpsConn.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
        httpsConn.setRequestProperty("Content-Length", String.valueOf(param.length()));

        OutputStreamWriter out = new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
        out.write(param);
        out.flush();
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpsConn.getInputStream(), "UTF-8"));
        String tempLine = "";
        StringBuffer resultBuffer = new StringBuffer();
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine).append(System.getProperty("line.separator"));
        }
        return resultBuffer.toString();
    }





    public static String httpReq( String urlPath,String param) throws NoSuchAlgorithmException,
            NoSuchProviderException, IOException,
            KeyManagementException {
        URL url = new URL(urlPath);
        HttpURLConnection httpsConn = (HttpURLConnection) url.openConnection();

        httpsConn.setDoOutput(true);
        httpsConn.setRequestMethod("POST");
        httpsConn.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
        httpsConn.setRequestProperty("Content-Length", String.valueOf(param.length()));

        OutputStreamWriter out = new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
        out.write(param);
        out.flush();
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpsConn.getInputStream(), "UTF-8"));
        String tempLine = "";
        StringBuffer resultBuffer = new StringBuffer();
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine).append(System.getProperty("line.separator"));
        }
        return resultBuffer.toString();
    }






}
