package com.liu.other;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

public class RequestHelper {
	private static final String TAG = RequestHelper.class.getSimpleName();
	private static final HttpClientVM clientVM = HttpClientVM.getClientVM();
	
	boolean sendData(String jsonStr) {
		return sendData(jsonStr, Config.server);
	}
	/*
    Should add follow line to AndroidManifest.xml
        <uses-permission android:name="android.permission.INTERNET"/>
    */
    private boolean sendData(String jsonStr, String url) {
        Log.d(TAG, "Begin to send data to " + url);
        if (url == null) {
            Log.d(TAG, "No server url is specified, abort");
            return false;
        }
        if (jsonStr.length() == 0) {
            Log.d(TAG, "Data is about to upload is empty, abort");
            return false;
        }

        byte[] deflatedData = gzipDeflatedData(jsonStr);
        if (deflatedData == null) {
            Log.d(TAG, "Failed to compress data, abort");
            return false;
        }
        byte[] dataEncrypted = encryptData(deflatedData, Config.AES128_ECB_KEY);
        if (dataEncrypted == null) {
            Log.d(TAG, "Failed to encrypt data, abort");
            return false;
        }

        final HttpClient httpClient = new DefaultHttpClient();
        final HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("x-upload-time", Long.toString(System.currentTimeMillis()/1000));
        httpPost.addHeader("Content-Type", "application/x-gzip");
        httpPost.addHeader("Content-Encoding", "gzip");
        httpPost.addHeader("SDK-Ver", Config.SDK_VERSION);
        httpPost.setEntity(new ByteArrayEntity(dataEncrypted));
        try {
            final HttpResponse response = httpClient.execute(httpPost);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                Log.d(TAG, "Finish uploading");
                return true;
            } else {
                Log.d(TAG, "Failed to upload, resonse code: " +
                        Integer.toString(statusCode));
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error occured during data sending, abort reason: " +
                    e.getMessage());
            return false;
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
}
