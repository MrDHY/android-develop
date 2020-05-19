LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)


# 要生成的.so库的名称。
LOCAL_MODULE := hello

#c++文件
LOCAL_SRC_FILES := hello.cpp

include $(BUILD_SHARED_LIBRARY)
