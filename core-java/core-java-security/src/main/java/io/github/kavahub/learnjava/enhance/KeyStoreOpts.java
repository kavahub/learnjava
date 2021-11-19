package io.github.kavahub.learnjava.enhance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/**
 * 操作{@link KeyStore}
 */
public class KeyStoreOpts {
    private KeyStore keyStore;

    private final String keyStoreName;
    private final String keyStoreType;
    private final String keyStorePassword;

    /**
     * 
     * @param keyStoreType 类型
     * @param keyStorePassword 密码
     * @param keyStoreName 名称
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     */
    KeyStoreOpts(String keyStoreType, String keyStorePassword, String keyStoreName) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        this.keyStoreName = keyStoreName;
        if(keyStoreType ==null || keyStoreType.isEmpty()){
            this.keyStoreType = KeyStore.getDefaultType();
        } else {
            this.keyStoreType = keyStoreType;
        }
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * 创建一个空的{@link KeyStore}，并创建文件，文件名称是<code>keyStoreName</code>属性值
     * 
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public void createEmptyKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore = KeyStore.getInstance(keyStoreType);
        //load
        char[] pwdArray = keyStorePassword.toCharArray();
        keyStore.load(null, pwdArray);

        // Save the keyStore
        try(FileOutputStream fos = new FileOutputStream(keyStoreName)) {
            keyStore.store(fos, pwdArray);
        }
    }

    /**
     * 从文件加载{@link KeyStore}，文件名称是<code>keyStoreName</code>属性值
     * 
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    public void loadKeyStore() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        char[] pwdArray = keyStorePassword.toCharArray();
        keyStore.load(new FileInputStream(keyStoreName), pwdArray);
    }

    /**
     * 设置项
     * @param alias
     * @param secretKeyEntry
     * @param protectionParameter
     * @throws KeyStoreException
     */
    public void setEntry(String alias, KeyStore.SecretKeyEntry secretKeyEntry, KeyStore.ProtectionParameter protectionParameter) throws KeyStoreException {
        keyStore.setEntry(alias, secretKeyEntry, protectionParameter);
    }

    /**
     * 获取项
     * @param alias
     * @return
     * @throws UnrecoverableEntryException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     */
    public KeyStore.Entry getEntry(String alias) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keyStorePassword.toCharArray());
        return keyStore.getEntry(alias, protParam);
    }

    /**
     * 
     * @param alias
     * @param privateKey
     * @param keyPassword
     * @param certificateChain
     * @throws KeyStoreException
     */
    public void setKeyEntry(String alias, PrivateKey privateKey, String keyPassword, Certificate[] certificateChain) throws KeyStoreException {
        keyStore.setKeyEntry(alias, privateKey, keyPassword.toCharArray(), certificateChain);
    }

    /**
     * 
     * @param alias
     * @param certificate
     * @throws KeyStoreException
     */
    public void setCertificateEntry(String alias, Certificate certificate) throws KeyStoreException {
        keyStore.setCertificateEntry(alias, certificate);
    }

    /**
     * 
     * @param alias
     * @return
     * @throws KeyStoreException
     */
    public Certificate getCertificate(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias);
    }

    /**
     * 
     * @param alias
     * @throws KeyStoreException
     */
    public void deleteEntry(String alias) throws KeyStoreException {
        keyStore.deleteEntry(alias);
    }

    /**
     * 
     * @throws KeyStoreException
     * @throws IOException
     */
    public void deleteKeyStore() throws KeyStoreException, IOException {
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            keyStore.deleteEntry(alias);
        }
        keyStore = null;
        Files.delete(Paths.get(keyStoreName));
    }

    /**
     * 
     * @return
     */
    public KeyStore getKeyStore() {
        return this.keyStore;
    }    
}
