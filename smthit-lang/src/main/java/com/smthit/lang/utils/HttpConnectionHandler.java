package com.smthit.lang.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(HttpConnectionHandler.class);

    static HttpConnectionManager connManager = new HttpConnectionManager();
    
    public static String post(String url, String content) {
        CloseableHttpClient httpClient=connManager.getHttpClient();
        
        
//        HttpGet httpget = new HttpGet(path);
//        HttpPost httpPost = new HttpPost(path);
//        List<BasicNameValuePair> parameters = new ArrayList<>();
//        parameters.add(new BasicNameValuePair("xml", content));  
//        httpPost.setEntity(new UrlEncodedFormEntity(parameters,"UTF-8")); 
        
        HttpPost httppost = new HttpPost(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        httppost.setConfig(requestConfig);
        
        StringEntity myEntity = new StringEntity(content, "UTF-8");
        httppost.addHeader("Content-Type", "text/xml");
        httppost.setEntity(myEntity); 
        
        String json=null;        
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httppost);
            InputStream in=response.getEntity().getContent();
            json=IOUtils.toString(in, "UTF-8");
            in.close();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {            
            if(response!=null){
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }            
        }
        return json;
    }
    
    public static String postSsl(String url, String certPath, String certPwd, String content) {

    	KeyStore keyStore = null;
    	FileInputStream instream = null;
    	CloseableHttpClient httpclient = null;
    	CloseableHttpResponse response = null;
    	
        StringBuilder result = new StringBuilder("");

		try {
	    	keyStore = KeyStore.getInstance("PKCS12");
	    	instream = new FileInputStream(new File(certPath));
			keyStore.load(instream, certPwd.toCharArray());
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPwd.toCharArray()).build();
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
	        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        HttpPost httppost = new HttpPost(url);
	        
	        //设置请求和传输超时时间
	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
	        httppost.setConfig(requestConfig);
	        
	        StringEntity myEntity = new StringEntity(content, "UTF-8");
	        httppost.addHeader("Content-Type", "text/xml");
	        httppost.setEntity(myEntity); 
	        
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
                
            if (entity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                	result.append(line);
                }
                EntityUtils.consume(entity);
            }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Request Url:" + url + ",Cert Path:" + certPath + ",Cert Password:" + certPwd, e);
		}finally{
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	try {
        		if(httpclient != null){
        			httpclient.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return result.toString();
    }
    
    public static String postSsl(String url) {

    	CloseableHttpClient httpclient = null;
    	CloseableHttpResponse response = null;
    	
        StringBuilder result = new StringBuilder("");

		try {

			SSLContext sslcontext = SSLContexts.createDefault();
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
	        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        HttpPost httppost = new HttpPost(url);
//	        HttpGet get = new HttpGet(url);
	        //设置请求和传输超时时间
	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
	        httppost.setConfig(requestConfig);
	        
//	        StringEntity myEntity = new StringEntity(content, "UTF-8");
//	        httppost.addHeader("Content-Type", "text/xml");
//	        httppost.setEntity(myEntity); 
	        
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
                
            if (entity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                	result.append(line);
                }
                EntityUtils.consume(entity);
            }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Request Url:" + url , e);
		}finally{
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	try {
        		if(httpclient != null){
        			httpclient.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return result.toString();
    }
    
    public static String postSsl(String url, String jsonContext) {

    	CloseableHttpClient httpclient = null;
    	CloseableHttpResponse response = null;
    	
        StringBuilder result = new StringBuilder("");

		try {

			SSLContext sslcontext = SSLContexts.createDefault();
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
	        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        HttpPost httppost = new HttpPost(url);

	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
	        httppost.setConfig(requestConfig);
	        
	        StringEntity myEntity = new StringEntity(jsonContext, "UTF-8");
	        httppost.setEntity(myEntity); 
	        
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
                
            if (entity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                	result.append(line);
                }
                EntityUtils.consume(entity);
            }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Request Url:" + url , e);
		}finally{
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	try {
        		if(httpclient != null){
        			httpclient.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return result.toString();
    }
    
    public static void main(String[] args){
    	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9fa8a80e686185a7&secret=1a8b5246e6d8e744348d31635db85dd0&code=0216yIFh0NUcXA1mpeJh0hfIFh06yIFn&grant_type=authorization_code";
    	System.out.println(HttpConnectionHandler.postSsl(url));
//    	StringBuilder sBuilder = new StringBuilder();
//    	sBuilder.append("<xml>");
//    	sBuilder.append("<appid>wxed12a80e31e0915a</appid>");
//    	sBuilder.append("<attach>支付测试</attach>");
//    	sBuilder.append("<body>树屋树递快递到寝配送服务</body>");
//    	sBuilder.append("<detail>树屋树递快递到寝配送服务</detail>");
//    	sBuilder.append("<device_info>WEB</device_info>");
//    	sBuilder.append("<fee_type>CNY</fee_type>");
//    	sBuilder.append("<mch_id>1367059002</mch_id>");
//    	sBuilder.append("<nonce_str><![CDATA[7&-1ib26HO5lWFoavTwe^]]></nonce_str>");
//    	sBuilder.append("<notify_url><![CDATA[http://www.treehouses.cn/front-4ae1dacda1f197bbfbc57ec9fbb4911f/api/wxpayresponse]]></notify_url>");
//    	sBuilder.append("<openid><![CDATA[o8nXct499ctQzioUQt93-vyq9hgM]]></openid>");
//    	sBuilder.append("<out_trade_no><![CDATA[60065]]></out_trade_no>");
//    	sBuilder.append("<product_id>0001</product_id>");
//    	sBuilder.append("<spbill_create_ip><![CDATA[49.140.188.1]]></spbill_create_ip>");
//    	sBuilder.append("<total_fee>1</total_fee>");
//    	sBuilder.append("<trade_type>JSAPI</trade_type>");
//    	sBuilder.append("<sign><![CDATA[F88FABC7BFD25B726864DC6D6FC8313B]]></sign>");
//    	sBuilder.append("</xml>");
//		try {
//			System.out.println(HttpConnectionHandler.post("https://api.mch.weixin.qq.com/pay/unifiedorder", sBuilder.toString()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }
}