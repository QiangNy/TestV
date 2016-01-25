#include <jni.h>
#include <android/log.h>
#include <stdlib.h>
#include <unistd.h>


#include "jniglue.h"

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
#ifndef NDEBUG
    __android_log_write(ANDROID_LOG_DEBUG,"openvpn", "Loading openvpn native library $id$ compiled on "   __DATE__ " " __TIME__ );
#endif
    return JNI_VERSION_1_2;
}

#define a1 "-----BEGIN CERTIFICATE-----\n\
MIIDQDCCAqmgAwIBAgIJAMSYRMniNVlwMA0GCSqGSIb3DQEBBQUAMHQxCzAJBgNV\n\
BAYTAkNOMQswCQYDVQQIEwJHRDELMAkGA1UEBxMCU1oxDjAMBgNVBAoTBWN5bGFu\n\
MQswCQYDVQQLEwJ5ZjENMAsGA1UEAxMEdGVzdDEfMB0GCSqGSIb3DQEJARYQNDIw\n\
MjIzMjExQHFxLmNvbTAeFw0xNTAzMTIwNzAzMTNaFw0yNTAzMDkwNzAzMTNaMHQx\n\
CzAJBgNVBAYTAkNOMQswCQYDVQQIEwJHRDELMAkGA1UEBxMCU1oxDjAMBgNVBAoT\n\
BWN5bGFuMQswCQYDVQQLEwJ5ZjENMAsGA1UEAxMEdGVzdDEfMB0GCSqGSIb3DQEJ\n\
ARYQNDIwMjIzMjExQHFxLmNvbTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA\n\
mluZxbTsBni2QtJVFFAmkXAJZgtEiHPgoTk6iUtFkC5zdNP2IEJ9t9IQRGHX5Ceh\n\
HmmT63R+FMX3Z1VKVZ75eV+Kkd1lKt1SHWOR4/FR03PAxOzKrcrQIOT9AZ0HHQba\n\
JvcGVG9aJf8/UfO537wtkzAcv3P3Ip5mTqribemDY/8CAwEAAaOB2TCB1jAdBgNV\n\
HQ4EFgQUum5FT5S/iNITw1q2JnVcip4vNbMwgaYGA1UdIwSBnjCBm4AUum5FT5S/\n\
iNITw1q2JnVcip4vNbOheKR2MHQxCzAJBgNVBAYTAkNOMQswCQYDVQQIEwJHRDEL\n\
MAkGA1UEBxMCU1oxDjAMBgNVBAoTBWN5bGFuMQswCQYDVQQLEwJ5ZjENMAsGA1UE\n\
AxMEdGVzdDEfMB0GCSqGSIb3DQEJARYQNDIwMjIzMjExQHFxLmNvbYIJAMSYRMni\n\
NVlwMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAQdjckp6wMAM2gmFC\n\
ogyGgsEx8gVY8UZPEDgQgkrGMfbLfe5Ta+ZpUnGHUJJOdXRT9QBq1vbzRZcCYDgF\n\
B9o7++VfLQEGX02IGZH+tViPNZYdgCs2It/LNCba5wWa2xZrX+RmMmlZnDv7GDZU\n\
Xy/uZ9MS0huefU3uOP97Cr9IgAY=\n\
-----END CERTIFICATE-----\n"


#define b2 "Certificate:\n\
    Data:\n\
        Version: 3 (0x2)\n\
        Serial Number: 2 (0x2)\n\
    Signature Algorithm: sha1WithRSAEncryption\n\
        Issuer: C=CN, ST=GD, L=SZ, O=cylan, OU=yf, CN=test/emailAddress=420223211@qq.com\n\
        Validity\n\
            Not Before: Mar 13 09:48:46 2015 GMT\n\
            Not After : Mar 10 09:48:46 2025 GMT\n\
        Subject: C=CN, ST=GD, L=SZ, O=cylan, OU=yf, CN=client1/emailAddress=420223211@qq.com\n\
        Subject Public Key Info:\n\
            Public Key Algorithm: rsaEncryption\n\
                Public-Key: (1024 bit)\n\
                Modulus:\n\
                    00:c2:78:0a:a6:53:21:6a:01:44:91:9c:e3:ca:8f:\n\
                    8c:a4:4d:f5:ef:26:25:3d:84:9a:dd:3f:14:36:e6:\n\
                    d8:d2:5b:b2:05:b5:00:d5:b0:3c:ce:a3:f4:af:6c:\n\
                    58:cd:ff:04:29:82:25:e2:de:a4:fd:e1:55:d2:5a:\n\
                    da:69:46:43:37:60:cb:87:7e:4a:f7:05:12:fc:1d:\n\
                    cb:47:0d:08:23:85:0d:53:6f:f6:80:61:b0:0d:1d:\n\
                    3a:27:40:28:66:a2:98:9b:06:c7:7d:29:ea:7a:b4:\n\
                    b4:08:4c:a7:62:b6:db:f1:9d:26:ed:37:e2:0b:2f:\n\
                    08:3b:78:d1:bb:89:2e:9d:23\n\
                Exponent: 65537 (0x10001)\n\
        X509v3 extensions:\n\
            X509v3 Basic Constraints: \n\
                CA:FALSE\n\
            Netscape Comment: \n\
                Easy-RSA Generated Certificate\n\
            X509v3 Subject Key Identifier: \n\
                46:67:30:CE:B4:96:3F:A1:59:79:B2:88:CE:F9:EF:85:09:6C:64:52\n\
            X509v3 Authority Key Identifier: \n\
                keyid:BA:6E:45:4F:94:BF:88:D2:13:C3:5A:B6:26:75:5C:8A:9E:2F:35:B3\n\
                DirName:/C=CN/ST=GD/L=SZ/O=cylan/OU=yf/CN=test/emailAddress=420223211@qq.com\n\
                serial:C4:98:44:C9:E2:35:59:70\n\
\n\
            X509v3 Extended Key Usage: \n\
                TLS Web Client Authentication\n\
            X509v3 Key Usage: \n\
                Digital Signature\n\
    Signature Algorithm: sha1WithRSAEncryption\n\
         35:d2:44:70:3d:2e:ba:e0:18:20:10:7d:c1:7f:de:7c:e3:73:\n\
         c4:54:ed:cf:9a:ff:94:44:65:00:50:e9:1a:df:a2:df:37:4a:\n\
         32:f1:53:88:dd:21:c0:05:79:6e:ad:29:36:8b:60:29:d9:8c:\n\
         a0:43:b8:ff:27:8f:8f:44:7b:59:e3:25:c7:0c:42:e0:b0:bc:\n\
         64:51:1d:7c:8f:bf:8f:24:34:4f:8c:97:26:3a:37:1d:08:8b:\n\
         d2:c8:39:93:1f:94:e9:01:0c:e5:51:64:72:c4:3b:ab:e1:a5:\n\
         a1:a9:19:a8:2a:c9:84:dd:7d:46:29:fa:09:ff:f7:24:45:da:\n\
         8f:01\n\
-----BEGIN CERTIFICATE-----\n\
MIIDizCCAvSgAwIBAgIBAjANBgkqhkiG9w0BAQUFADB0MQswCQYDVQQGEwJDTjEL\n\
MAkGA1UECBMCR0QxCzAJBgNVBAcTAlNaMQ4wDAYDVQQKEwVjeWxhbjELMAkGA1UE\n\
CxMCeWYxDTALBgNVBAMTBHRlc3QxHzAdBgkqhkiG9w0BCQEWEDQyMDIyMzIxMUBx\n\
cS5jb20wHhcNMTUwMzEzMDk0ODQ2WhcNMjUwMzEwMDk0ODQ2WjB3MQswCQYDVQQG\n\
EwJDTjELMAkGA1UECBMCR0QxCzAJBgNVBAcTAlNaMQ4wDAYDVQQKEwVjeWxhbjEL\n\
MAkGA1UECxMCeWYxEDAOBgNVBAMTB2NsaWVudDExHzAdBgkqhkiG9w0BCQEWEDQy\n\
MDIyMzIxMUBxcS5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMJ4CqZT\n\
IWoBRJGc48qPjKRN9e8mJT2Emt0/FDbm2NJbsgW1ANWwPM6j9K9sWM3/BCmCJeLe\n\
pP3hVdJa2mlGQzdgy4d+SvcFEvwdy0cNCCOFDVNv9oBhsA0dOidAKGaimJsGx30p\n\
6nq0tAhMp2K22/GdJu034gsvCDt40buJLp0jAgMBAAGjggEoMIIBJDAJBgNVHRME\n\
AjAAMC0GCWCGSAGG+EIBDQQgFh5FYXN5LVJTQSBHZW5lcmF0ZWQgQ2VydGlmaWNh\n\
dGUwHQYDVR0OBBYEFEZnMM60lj+hWXmyiM7574UJbGRSMIGmBgNVHSMEgZ4wgZuA\n\
FLpuRU+Uv4jSE8NatiZ1XIqeLzWzoXikdjB0MQswCQYDVQQGEwJDTjELMAkGA1UE\n\
CBMCR0QxCzAJBgNVBAcTAlNaMQ4wDAYDVQQKEwVjeWxhbjELMAkGA1UECxMCeWYx\n\
DTALBgNVBAMTBHRlc3QxHzAdBgkqhkiG9w0BCQEWEDQyMDIyMzIxMUBxcS5jb22C\n\
CQDEmETJ4jVZcDATBgNVHSUEDDAKBggrBgEFBQcDAjALBgNVHQ8EBAMCB4AwDQYJ\n\
KoZIhvcNAQEFBQADgYEANdJEcD0uuuAYIBB9wX/efONzxFTtz5r/lERlAFDpGt+i\n\
3zdKMvFTiN0hwAV5bq0pNotgKdmMoEO4/yePj0R7WeMlxwxC4LC8ZFEdfI+/jyQ0\n\
T4yXJjo3HQiL0sg5kx+U6QEM5VFkcsQ7q+GloakZqCrJhN19Rin6Cf/3JEXajwE=\n\
-----END CERTIFICATE-----"

#define c3 "-----BEGIN PRIVATE KEY-----\n\
MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMJ4CqZTIWoBRJGc\n\
48qPjKRN9e8mJT2Emt0/FDbm2NJbsgW1ANWwPM6j9K9sWM3/BCmCJeLepP3hVdJa\n\
2mlGQzdgy4d+SvcFEvwdy0cNCCOFDVNv9oBhsA0dOidAKGaimJsGx30p6nq0tAhM\n\
p2K22/GdJu034gsvCDt40buJLp0jAgMBAAECgYEAluEJ6jevQg6Pw6YE+iDbzpKK\n\
ECz5UbxzZtrM9Tev26229kSTzDc7RlvguMYINHL6KLZIbLevLSbKLjW9JVaVSKQb\n\
PWjF2joeyWmJZaSwEf7Xt4cMMXipO6YhMOFywRLB7TY8weTuVIKKu8XO9VemmOJr\n\
MvGdABvJA9pi39YA4ekCQQDtJMVyq4anVVxlxEpDSCJarEDUbHJ1aMRjBLge+Ds1\n\
hIbovKcicdx3ERi2j3w6O9Q2Lg94CGS0E+6Vb6xBH7GHAkEA0e6Yf7eHr+Wm95tJ\n\
/Ww2o8Cf5yWejQNb9pNjBeG4nfICQ3C5DQsgQ0oKmtj0ouZOXV7aQFDxtcTpEWge\n\
0G4OhQJAeH66pO1q2hORg1Qdt9orOo9T0ZvY/LdjMjzvoSzYSPUx54gUFHhuYUGa\n\
oQh/QserqGv3CDKbmLSYAe5gRCbqeQJAWlduQxANEVIn/0hRmAB4pxGe/o7+4zo9\n\
2iflJkPT+gyKktEwoX9XO/3iAG87H9sygBXwoFr1Gb8KeZYwJIwoLQJBAIp/LbMq\n\
XBE+6lWAZiPskyJViz0ZZglvfgd+XGcCk5HG6+/sXTL+eweGKBFYiPyW9FR5GAjD\n\
1gT+S/iKuylawKg=\n\
-----END PRIVATE KEY-----"

void android_openvpn_log(int level,const char* prefix,const char* prefix_sep,const char* m1)
{
    __android_log_print(ANDROID_LOG_DEBUG,"openvpn","%s%s%s",prefix,prefix_sep,m1);
}

void Java_ludeng_com_testvi_vpnspl_core_NativeUtils_jniclose(JNIEnv *env,jclass jo, jint fd)
{
	int ret = close(fd);
}


//! Hack to get the current installed ABI of the libraries. See also https://github.com/schwabe/ics-openvpn/issues/391
jstring Java_ludeng_com_testvi_vpnspl_core_NativeUtils_getNativeAPI(JNIEnv *env, jclass jo)
{

    return (*env)->NewStringUTF(env, TARGET_ARCH_ABI);
}

jstring Java_ludeng_com_testvi_vpnspl_core_NativeUtils_getICon(JNIEnv *env, jclass jo, jint num)
{
    char * str = NULL;
    if (num == 100) {
            str = a1;
        }else if (num == 101) {
            str = b2;
        }else if (num == 102) {
            str = c3;
    }

    return (*env)->NewStringUTF(env, str);
}