
#include <jni.h>

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <linux/fs.h>
#include <errno.h>
#include <string.h>




#include <android/log.h>

/* Header for class com_example_inf_smarthome3_model_HomeNative */
#define  LOG_TAG    "model"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#ifndef _Included_com_example_inf_smarthome3_model_HomeNative
#define _Included_com_example_inf_smarthome3_model_HomeNative

#ifdef __cplusplus
extern "C" {
#endif
#undef com_example_inf_smarthome3_model_HomeNative_WHAT_MSG
#define com_example_inf_smarthome3_model_HomeNative_WHAT_MSG 1L

int     fd_ac = -1,
        fd_humidity = -1,
        fd_curtain = -1,
        fd_door = -1,
        fd_gas = -1,
        fd_infrared = -1,
        fd_light = -1,
        fd_lightSensor = -1 ;
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openACDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openACDev
        (JNIEnv *env, jclass cls){
      fd_ac  = open("/dev/dc_motor", O_RDWR);
      if (fd_ac<0){
         LOGI("can not open /dev/dc_motor");
      }
}

/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    operateACDev
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_operateACDev
        (JNIEnv *env, jclass cls, jint op){
         int flag = ioctl(fd_ac, op, 0);
         if (flag<0){
            LOGE("ioctl failed");
         }
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeACDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeACDev
        (JNIEnv *env, jclass cls){
         close(fd_ac);
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openCurtainDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openCurtainDev
        (JNIEnv *env, jclass cls){
       fd_curtain  = open("/dev/stepmotor", O_RDWR);
       if (fd_ac<0){
          LOGI("can not open /dev/stepmotor");
      }
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    changeCurtainDev
 * Signature: (II)I
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_changeCurtainDev
        (JNIEnv *env, jclass cls, jint state , jint angle){
        if(fd_curtain>0) {
            ioctl(fd_curtain, state, angle);
        }
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeCurtainDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeCurtainDev
        (JNIEnv *env, jclass cls){
         close(fd_curtain);
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openDoorDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openDoorDev
        (JNIEnv *env, jclass cls){
      fd_door  = open("/dev/relay", O_RDWR);
      if (fd_door<0){
         LOGI("can not open /dev/relay");
       }
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    operateDoor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_operateDoor
        (JNIEnv *env, jclass cls, jint op){
         int relay = op;
         if(relay==0||relay==1){
            ioctl(fd_door, relay, O_RDONLY);
         }else{
            LOGI("relay values error...");
          }
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeDoorDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeDoorDev
        (JNIEnv *env, jclass cls){
         close(fd_door);
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openGasDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openGasDev
        (JNIEnv *env, jclass cls){
         fd_gas  = open("/dev/gas", O_RDWR);
         if (fd_gas<0){
            LOGI("can not open /dev/gas");
         }
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    operateGasDev
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_inf_smarthome3_model_HomeNative_operateGasDev
        (JNIEnv *env, jclass cls){
         char buffer[30];
         int len = read(fd_gas, buffer, sizeof buffer - 1);
         if (len > 0) {
            buffer[len] = '\0';
            int value = -1;
            sscanf(buffer, "%d", &value);
            return value;
         }
         return 0;
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeGasDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeGasDev
        (JNIEnv *env, jclass cls){
         close(fd_gas);
        }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openInfraredDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openInfraredDev
        (JNIEnv *env, jclass cls){
       fd_infrared  = open("/dev/infrared", O_RDWR);
         if (fd_infrared<0){
            LOGI("can not open /dev/infrared");
         }
        }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    nowInfrared
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_inf_smarthome3_model_HomeNative_nowInfrared
        (JNIEnv *env, jclass cls){
         int readbye, buf;
         readbye = read(fd_infrared, &buf, sizeof(buf));
         if (buf == 0) {
            return 0;
         } else {
            return 1;
         }
        }

/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeInfraredDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeInfraredDev
        (JNIEnv *env, jclass cls){
         close(fd_infrared);
}
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openLightDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openLightDev
        (JNIEnv *env, jclass cls){
      fd_light  = open("/dev/light", O_RDWR);
         if (fd_light<0){
            LOGI("can not open /dev/light");
         }
    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    lightOp
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_lightOp
        (JNIEnv *env, jclass cls, jint op, jint which){
         ioctl(fd_light,op,which);  //客厅灯 which=0
    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeLightDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeLightDev
        (JNIEnv *env, jclass cls){
         close(fd_light);
    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openLightSenseDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openLightSenseDev
        (JNIEnv *env, jclass cls){
         fd_lightSensor  = open("/dev/lightsensor", O_RDWR);
         if (fd_lightSensor<0){
            LOGI("can not open /dev/lightsensor");
         }
    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    nowLightSense
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_example_inf_smarthome3_model_HomeNative_nowLightSense
        (JNIEnv *env, jclass cls){
         int ret;
         char buffer[30];
         int value;
         ret = read(fd_lightSensor, buffer, sizeof(buffer) - 1);
         if (ret > 0) {
            buffer[ret] = '\0';
            value = -1;
            sscanf(buffer, "%d", &value);
            return value;
         } else {
            return 0;
//         perror("read lightsensor device:");
//         exit(EXIT_FAILURE);

      }
    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeLightSenseDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeLightSenseDev
        (JNIEnv *env, jclass cls){
         close(fd_lightSensor);
    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    openDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_openDev
        (JNIEnv *env, jclass cls){
   fd_humidity = open("/dev/humidity",O_RDONLY);
   if (fd_humidity<0){
      LOGI("can not open /dev/humidity" );
   }

    }
/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    readData
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_readData
        (JNIEnv *env, jclass cls){

   unsigned short tempz = 0;
   unsigned short tempx = 0;
   unsigned short humidiyz = 0;
   unsigned short humidiyx = 0;
   unsigned long temperature = 0;
   read(fd_humidity,&temperature,sizeof( temperature ));
   humidiyz = (temperature & 0xff000000)>>24;	//湿度的整数部分
   humidiyx = (temperature & 0x00ff0000)>>16;	//湿度的小数部分
   tempz = (temperature & 0x0000ff00)>>8;		//温度的整数部分
   tempx = (temperature & 0x000000ff);		//温度的小数部分

   /*jmethodID (JNICALL *GetStaticMethodID)
   (JNIEnv *env, jclass clazz, const char *name, const char *sig);*/
   jmethodID getDataCallMethodId =  env->GetStaticMethodID(cls,"getDataCallBackListener","(II)V");
   /*void (JNICALL *CallStaticVoidMethod)
   (JNIEnv *env, jclass cls, jmethodID methodID, ...);*/
   env->CallStaticVoidMethod(cls,getDataCallMethodId,tempz,humidiyz);
    }

/*
 * Class:     com_example_inf_smarthome3_model_HomeNative
 * Method:    closeDev
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_inf_smarthome3_model_HomeNative_closeDev
        (JNIEnv *env, jclass cls){

}

#ifdef __cplusplus
}
#endif
#endif

