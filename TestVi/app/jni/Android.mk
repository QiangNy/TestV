# Path of the sources

LOCAL_PATH := $(call my-dir)





# The only real JNI libraries
include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog  -lz
LOCAL_CFLAGS = --std=c99 -DTARGET_ARCH_ABI=\"${TARGET_ARCH_ABI}\"
LOCAL_SRC_FILES:= jniglue.c  scan_ifs.c
LOCAL_MODULE = opvpnutil
include $(BUILD_SHARED_LIBRARY)


