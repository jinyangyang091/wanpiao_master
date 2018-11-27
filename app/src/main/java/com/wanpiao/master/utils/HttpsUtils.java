package com.wanpiao.master.utils;

import android.content.Context;


import com.wanpiao.master.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 描述: https工具類
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-01-04 11:56
 */

public class HttpsUtils {

    private HttpsUtils(){};
    private Context context;
    private String keyStoreType;
    private int keystoreResId;
    public HttpsUtils(Context context, String keyStoreType, int keystoreResId) {
        this.context = context.getApplicationContext();
        this.keyStoreType = keyStoreType;
        this.keystoreResId = keystoreResId;
    }

    public TrustManager[] getWrappedTrustManagers() {
        final X509TrustManager originalTrustManager = (X509TrustManager) getTrustManagerFactory().getTrustManagers()[0];
        return new TrustManager[]{

                new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {

                        return originalTrustManager.getAcceptedIssuers();

                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {

                        try {

                            originalTrustManager.checkClientTrusted(certs, authType);

                        } catch (CertificateException e) {

                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        //参数有效性校验
                        if (chain == null || chain.length == 0 || authType == null || authType.length() == 0) {
                            throw new CertificateException();
                        }
                        //证书有效性校验
                        for(X509Certificate cert : chain){
                            cert.checkValidity();
                        }
                        //证书完整性校验
                        check(chain);

                    }

                }

        };

    }


    private static final int MIN_MODULUS = 1024;
    private static final String[] OID_BLACKLIST = {"1.2.840.113549.1.1.4"}; // MD5withRSA
    private static TrustManagerFactory trustManagerFactory;

    public static final void check(X509Certificate[] chain) throws CertificateException {
        for (X509Certificate cert : chain) {
            checkCert(cert);
        }
    }

    private static final void checkCert(X509Certificate cert) throws CertificateException {
        checkModulusLength(cert);
        checkNotMD5(cert);
    }

    private static final void checkModulusLength(X509Certificate cert) throws CertificateException {
        Object pubkey = cert.getPublicKey();
        if (pubkey instanceof RSAPublicKey) {
            int modulusLength = ((RSAPublicKey) pubkey).getModulus().bitLength();
            if (!(modulusLength >= MIN_MODULUS)) {
                throw new CertificateException("Modulus is < 1024 bits");
            }
        }
    }

    private static final void checkNotMD5(X509Certificate cert) throws CertificateException {
        String oid = cert.getSigAlgOID();
        for (String blacklisted : OID_BLACKLIST) {
            if (oid.equals(blacklisted)) {
                throw new CertificateException("Signature uses an insecure hash function");
            }
        }
    }

    public SSLSocketFactory getSSLSocketFactory_Certificate() {
        try {
            getTrustManagerFactory();
            TrustManager[] wrappedTrustManagers = null;
            if (trustManagerFactory != null){
                wrappedTrustManagers =  getWrappedTrustManagers();
            }
            //SSLContext sslContext = SSLContext.getInstance("TLS");
            SSLContext sslContext = SSLContext.getInstance("TLSv1","AndroidOpenSSL");
            sslContext.init(null, wrappedTrustManagers, null);
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * HostnameVerifier可以借用源码HttpsUrlConnection中NoPreloadHolder中默认设置的OKHostnameVerifier进行域名合法性校验。
     */
    public static boolean verifyHost(String host, SSLSession session){
        L.i("Host",host);
        if(Constants.HOST_NAME_ZXGS.equals(host)){
            return true;
        } else{
            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
            return hv.verify(host, session);
        }
    }

    private TrustManagerFactory getTrustManagerFactory(){
        if (trustManagerFactory != null){
            return trustManagerFactory;
        }
        try {
        CertificateFactory cf =  CertificateFactory.getInstance("X.509");

        InputStream caInput = context.getResources().openRawResource(keystoreResId);

        Certificate ca = cf.generateCertificate(caInput);

        caInput.close();

        if (keyStoreType == null || keyStoreType.length() == 0) {

            keyStoreType = KeyStore.getDefaultType();

        }

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);

        keyStore.load(null, null);

        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);

        tmf.init(keyStore);
        trustManagerFactory = tmf;
        return trustManagerFactory;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
