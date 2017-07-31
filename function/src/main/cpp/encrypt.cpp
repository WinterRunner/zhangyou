#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_threesome_function_controller_AppManager_getRequestkey(
        JNIEnv *env,
        jobject /* this */) {

    return env->NewStringUTF("894679528FFEE029D53D70C87CA4D961");
}


extern "C"
jstring
Java_com_threesome_function_controller_AppManager_getRequestkey2(
        JNIEnv *env,
        jobject /* this */) {

    return env->NewStringUTF("57434CAECE3231EF2085A262EA1F8C4A");
}