//
// Created by Alienware on 2020/5/19.
//


#include "jni.h"
#include "com_example_helloworld_MyJNI.h"

JNIEXPORT jstring JNICALL Java_com_example_helloworld_MyJNI_getString(JNIEnv *env, jclass jz){
    return env->NewStringUTF("Hello JNI");
}