/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.opendata;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 *
 * @author USUARIO
 */
public class ConectorAemet {

    private static OkHttpClient client;

    public OkHttpClient getCliente() {
        return client;
    }
    
    public ConectorAemet() {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();
    }

    
    private InputStream trustedCertificatesInputStream() {
        // root certificates of AEMET ... (PEM file)
        String aemetCertificate = ""
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIHzTCCBbWgAwIBAgIIftSoNgFVwkMwDQYJKoZIhvcNAQELBQAwQzETMBEGA1UE\n"
                + "AwwKQUNDVkNBLTEyMDEQMA4GA1UECwwHUEtJQUNDVjENMAsGA1UECgwEQUNDVjEL\n"
                + "MAkGA1UEBhMCRVMwHhcNMTcwMjE1MTIyOTQ5WhcNMjAwMjE1MTIyOTQ5WjCBlzET\n"
                + "MBEGA1UEAwwKKi5hZW1ldC5lczESMBAGA1UEBRMJUTI4MDE2NjhBMRMwEQYDVQQL\n"
                + "DApTZXJ2aWRvcmVzMSgwJgYDVQQKDB9BR0VOQ0lBIEVTVEFUQUwgREUgTUVURU9S\n"
                + "T0xPR0lBMQ8wDQYDVQQHDAZNQURSSUQxDzANBgNVBAgMBk1BRFJJRDELMAkGA1UE\n"
                + "BhMCRVMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCkLR6FgiigbyAZ\n"
                + "94KXLUOZ2Rld1R7t46ekwW2iZaDUHzcCzcWZyN496Q8K7LBk+OA0XkPl9aNzLf8d\n"
                + "MLSOs0UtFJXd4TqxLDt4cgg+SiHM9nh/BYXWX6djPdaXxg7W1bWaifDEhc1mEqq/\n"
                + "eVHZG0/GyX5VrJFyKhHLvXqX8N9Spaoz242vVGy6/2FokLLIJ6hoYMhRcxokzJqY\n"
                + "yDDcSB/RHIPqo6W5fsEYSGv9Uw5URiwGAI/pO/mgGi6aGyPwbfnK3nZ1jzwjx73x\n"
                + "IG7VK9IaKJ/YBVe+F/SN/L4GP36tc5gGifjvYHqMT5Rj6YtAAneBLqKF8nD7oy/e\n"
                + "1ZuKdtyzAgMBAAGjggNuMIIDajBmBggrBgEFBQcBAQRaMFgwNQYIKwYBBQUHMAKG\n"
                + "KWh0dHA6Ly93d3cuYWNjdi5lcy9nZXN0Y2VydC9BQ0NWQ0ExMjAuY3J0MB8GCCsG\n"
                + "AQUFBzABhhNodHRwOi8vb2NzcC5hY2N2LmVzMB0GA1UdDgQWBBSWbfdmEg8mTiO5\n"
                + "pOJM1OunQVDEFjAfBgNVHSMEGDAWgBToQJuO+2Y/wUTYod/USoFCCBfL5TCCAd4G\n"
                + "A1UdIASCAdUwggHRMIIBzQYLKwYBBAG/VQMDAwAwggG8MIIBhgYIKwYBBQUHAgIw\n"
                + "ggF4HoIBdABDAGUAcgB0AGkAZgBpAGMAYQBkAG8AIABwAGEAcgBhACAAcwBlAHIA\n"
                + "dgBpAGQAbwByAGUAcwAgAGMAbwBuACAAcwBvAHAAbwByAHQAZQAgAFMAUwBMACAA\n"
                + "ZQB4AHAAZQBkAGkAZABvACAAcABvAHIAIABsAGEAIABBAGcAZQBuAGMAaQBhACAA\n"
                + "ZABlACAAVABlAGMAbgBvAGwAbwBnAO0AYQAgAHkAIABDAGUAcgB0AGkAZgBpAGMA\n"
                + "YQBjAGkA8wBuACAARQBsAGUAYwB0AHIA8wBuAGkAYwBhACAAKABQAGwALgAgAEMA\n"
                + "4QBuAG8AdgBhAHMAIABkAGUAbAAgAEMAYQBzAHQAaQBsAGwAbwAsACAAMQAuACAA\n"
                + "QwBJAEYAIABRADQANgAwADEAMQA1ADYARQApAC4AIABDAFAAUwAgAHkAIABDAFAA\n"
                + "IABlAG4AIABoAHQAdABwADoALwAvAHcAdwB3AC4AYQBjAGMAdgAuAGUAczAwBggr\n"
                + "BgEFBQcCARYkaHR0cDovL3d3dy5hY2N2LmVzL2xlZ2lzbGFjaW9uX2MuaHRtMIGh\n"
                + "BgNVHR8EgZkwgZYwgZOgSKBGhkRodHRwOi8vd3d3LmFjY3YuZXMvZmlsZWFkbWlu\n"
                + "L0FyY2hpdm9zL2NlcnRpZmljYWRvcy9hY2N2Y2ExMjBfZGVyLmNybKJHpEUwQzET\n"
                + "MBEGA1UEAwwKQUNDVkNBLTEyMDEQMA4GA1UECwwHUEtJQUNDVjENMAsGA1UECgwE\n"
                + "QUNDVjELMAkGA1UEBhMCRVMwDgYDVR0PAQH/BAQDAgXgMBMGA1UdJQQMMAoGCCsG\n"
                + "AQUFBwMBMBUGA1UdEQQOMAyCCiouYWVtZXQuZXMwDQYJKoZIhvcNAQELBQADggIB\n"
                + "AEHEQud7HWahqQ57fkrQEFbiOw8Dmhga4sj/XBW8MdlHQZjORiQR664++EC2DPVU\n"
                + "DnCNFPF9RzXGjDA5lrSPWb5mQVK/gCnRo/JVQ4OFXfcyYRsEYxXkSJcN72tF93N5\n"
                + "XT23G6JLA/IL9uJ3Gh0DtCmdYMzhFFnSKxJGS3OdTkKI9N8xEPn8DlhkiY8UZ/2I\n"
                + "Fz10+oDDrY8BiGwjj2tFU7tDcx7aHFHkle1r6IlARuuh44y0IZ5dGb5qbN/4Z44i\n"
                + "AijS4c3vQwrDpr3InLv+JYSr23jyt+tOXUz4CAioKq4jc8Jbh1aozaOx+CoQPCzf\n"
                + "HTBJ30EteOezacveaE2akFVjYs8obzwUGZcuwFMn5A4MKTsP3jJ7HLk9wl3Zk+ep\n"
                + "LuzmGBXKggdlxtBO3+tchcDHiyxHvYoKKqusHPSgdRQdhRhiL3HU/0hvCe98TdTF\n"
                + "2ch+daq2B+xdlI68ixJTsQM8qf8pu6zIGoGP5JYIzUucbEU28XC+HI+EwOt/clTx\n"
                + "jJanWVE9K5qQ9tvRCwak4baqPhdvuJQO0ssVrm6GruMICcqkWa4iYTFCyXUxWVyb\n"
                + "D38TCSyYKvU8lG1rNHw3iTdPKH3LkWq8CPU3SOhtN1BaZZXaT5HW1eQ5DFzChkqd\n"
                + "cOX5rHQVx5feyXKgaSY3NOcnRrW4gKnhq66CCM2lChgy\n"
                + "-----END CERTIFICATE-----\n";

        return new Buffer()
                .writeUtf8(aemetCertificate)
                .inputStream();
    }

    /**
     * Retorna un trust manager que acepta el certificado proporcionado en el 
     * código
     * TODO: leer el certificado desde fichero
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Poner el certificado en el keystore.
        char[] password = "password".toCharArray(); // No importa el password.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Construir X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // keystore vacía.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}

