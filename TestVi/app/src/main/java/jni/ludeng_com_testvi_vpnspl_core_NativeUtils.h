/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ludeng_com_testvi_vpnspl_core_NativeUtils */

#ifndef _Included_ludeng_com_testvi_vpnspl_core_NativeUtils
#define _Included_ludeng_com_testvi_vpnspl_core_NativeUtils
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     ludeng_com_testvi_vpnspl_core_NativeUtils
 * Method:    rsasign
 * Signature: ([BI)[B
 */
JNIEXPORT jbyteArray JNICALL Java_ludeng_com_testvi_vpnspl_core_NativeUtils_rsasign
  (JNIEnv *, jclass, jbyteArray, jint);

/*
 * Class:     ludeng_com_testvi_vpnspl_core_NativeUtils
 * Method:    getIfconfig
 * Signature: ()[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_ludeng_com_testvi_vpnspl_core_NativeUtils_getIfconfig
  (JNIEnv *, jclass);

/*
 * Class:     ludeng_com_testvi_vpnspl_core_NativeUtils
 * Method:    jniclose
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_ludeng_com_testvi_vpnspl_core_NativeUtils_jniclose
  (JNIEnv *, jclass, jint);

/*
 * Class:     ludeng_com_testvi_vpnspl_core_NativeUtils
 * Method:    getNativeAPI
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ludeng_com_testvi_vpnspl_core_NativeUtils_getNativeAPI
  (JNIEnv *, jclass);

/*
 * Class:     ludeng_com_testvi_vpnspl_core_NativeUtils
 * Method:    getICon
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_ludeng_com_testvi_vpnspl_core_NativeUtils_getICon
  (JNIEnv *, jclass, jint);

#ifdef __cplusplus
}
#endif
#endif