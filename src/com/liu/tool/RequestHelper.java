package com.liu.tool;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.liu.bean.DataType;
import com.liu.bean.Event;
import com.liu.bean.Message;
import com.liu.bean.Response;

public class RequestHelper {
	private static final String TAG = RequestHelper.class.getSimpleName();
//	private static final HttpClientVM clientVM = HttpClientVM.getClientVM();
	
	private static Response sendData(DataType dataType, String jsonStr) {
		String requestRet = sendData(new Request(dataType, jsonStr).toJson(), Config.server);
		Log.d(TAG, "$ret: " + requestRet);
		try{
		return Response.fromRequestReturn(requestRet);
		} catch (Exception e) {
			Log.e(TAG, "make response failed.", e);
			return null;
		}
//		return Response.DEMO_SUCCESS;
	}
	
	public static Response sendMessage(Message msg) {
		return sendData(msg.getDataType(), msg.toJson());
	}
	
	public static Response sendEvent(Event event) {
		return sendData(event.getDataType(), event.toJson());
	}
	
	private static class Request {
		private DataType dataType;
		private String jsonStr;
		public Request(DataType dataType, String jsonStr) {
			this.dataType = dataType;
			this.jsonStr = jsonStr;
		}
		
		public DataType getDataType() {
			return dataType;
		}

		public void setDataType(DataType dataType) {
			this.dataType = dataType;
		}

		public String getJsonStr() {
			return jsonStr;
		}

		public void setJsonStr(String jsonStr) {
			this.jsonStr = jsonStr;
		}

		public String toJson() {
			return JSON.toJSONString(this);
		}
	}
	
	/*
    Should add follow line to AndroidManifest.xml
        <uses-permission android:name="android.permission.INTERNET"/>
    */
    private static String sendData(String jsonStr, String url) {
        Log.d(TAG, "Begin to send data to " + url);
        if (url == null) {
            Log.d(TAG, "No server url is specified, abort");
            return null;
        }
        if (jsonStr.length() == 0) {
            Log.d(TAG, "Data is about to upload is empty, abort");
            return null;
        }

/*        byte[] deflatedData = gzipDeflatedData(jsonStr);
        if (deflatedData == null) {
            Log.d(TAG, "Failed to compress data, abort");
            return null;
        }
        byte[] dataEncrypted = encryptData(deflatedData, Config.AES128_ECB_KEY);
        if (dataEncrypted == null) {
            Log.d(TAG, "Failed to encrypt data, abort");
            return null;
        }*/

        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("x-upload-time", Long.toString(System.currentTimeMillis()/1000));
        httpPost.addHeader("Content-Type", "application/x-gzip");
        httpPost.addHeader("Content-Encoding", "gzip");
        httpPost.addHeader("SDK-Ver", Config.SDK_VERSION);
//        httpPost.setEntity(new ByteArrayEntity(dataEncrypted));
        httpPost.setEntity(new ByteArrayEntity(jsonStr.getBytes()));
        try {
            final HttpResponse response = httpClient.execute(httpPost);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                Log.d(TAG, "Finish uploading");
                InputStream is = response.getEntity().getContent();
                return inputStream2String(is);
            } else {
                Log.d(TAG, "Failed to upload, resonse code: " +
                        Integer.toString(statusCode));
                return null;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error occured during data sending, abort reason: " +
                    e);
            return null;
        }
    }

    static byte[] gzipDeflatedData(String jsonStr) {
        GZIPOutputStream gzipOutputStream = null;
        try {
            byte[] data = jsonStr.getBytes("UTF-8");
            final ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream(data.length);
            gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(data);
            gzipOutputStream.finish();
            gzipOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (final UnsupportedEncodingException e) {
            return null;
        } catch (final IOException e) {
            return null;
        } finally {
            if (null != gzipOutputStream) {
                try {
                    gzipOutputStream.close();
                } catch (final Exception e) {
                }
            }
        }
    }

    static byte[] encryptData(byte[] data, String key) {
        try {
            SecretKeySpec secretKey =
                    new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static String inputStream2String(InputStream is) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
