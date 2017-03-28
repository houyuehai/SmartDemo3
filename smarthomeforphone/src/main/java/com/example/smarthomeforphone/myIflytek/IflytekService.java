package com.example.smarthomeforphone.myIflytek;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * 建议全局只使用一个对象。
 * 创建时传入的Activity是那一个，那么UI就在那一个界面弹出。
 * openListen(CallBack call)；call回调的结果是 命令识别可信度最高的一个
 * say（）；语音合成
 */
public class IflytekService {
    static final String TAG = "IflytekService";

    Context context;
    SpeechRecognizer mSpeechRecognizer;//语音识别对象
    RecognizerDialog iatDialog;        //语音识别对象（已封装了界面）
    SpeechSynthesizer mTts;            //语音合成对象
    String mGrammarId;                 //云端语法文件ID
    boolean isSaying;                  //说的时候不能读
    String mCloudGrammar = "#ABNF 1.0 UTF-8;\n" +    // ABNF语法示例，可以说”打开摄像头”
            "language zh-CN;\n" +
            "mode voice;\n" +
            "root $main;\n" +
            "$main = $place1  $place2;\n" +
            "$place1 = 关闭|打开;\n" +
            "$place2 = 空调|窗帘|红外|摄像头|大门|客厅灯|房间灯|语音服务|光感|烟雾|暖空调|冷空调|空调;";
    CallBack openListenCall;
    public IflytekService(Context context) {
        this.context = context;
        init();
    }
    private void init() {
        iatDialog = new RecognizerDialog(context,null);
        mSpeechRecognizer  = SpeechRecognizer.createRecognizer(context, null);
        mTts= SpeechSynthesizer.createSynthesizer(context, null);
        mGrammarId = getGrammarIdFromSP();
        //设置语音命令识别基础参数（不包括语法文件）
        setBaseListenerParameters();
        //设置语音合成基础参数
        setBaseSayParameters();
    }

    private void setBaseListenerParameters() {
        if(iatDialog==null)
            return;
        //设置引擎类型为云端
        iatDialog.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //指定编码
        iatDialog.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        //设置返回结果为json格式
        iatDialog.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        iatDialog.setParameter(SpeechConstant.ASR_PTT,"0");
        //语音无输入时等待时间
        iatDialog.setParameter(SpeechConstant.VAD_BOS, "8000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        iatDialog.setParameter(SpeechConstant.VAD_EOS,"2000");
        //iatDialog.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");//语音文件保存格式和路径
        //iatDialog.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/asr.wav");
    }
    private void setBaseSayParameters(){
        //合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "vinn");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置为云端引擎
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        }

    //开启 命令云端语音识别  同步：避免回调时 openListenCall已经被修改
    //这里有可能造成anr
    public synchronized void  openListen(CallBack call){
        if(mGrammarId==null){
            Log.i(TAG, "openListen: 上传语法中");
            upLoadGrammarText(); //上传语法
            return;
        }
        if (isSaying||iatDialog.isShowing()){
            Log.i(TAG, "openListen: 小妹正在说话或者已经在等你开口说话，不要重复调用本方法");
            return;
        }
        openListenCall = call ;
        iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR,mGrammarId);//设置语法来自云端，以及语法ID
        iatDialog.setListener(recognizerDialogListener);
        iatDialog.show(); //开启界面，准备接受语音命令
    }

    //语音合成 传入要说的话
    public void say(String content){
        isSaying=true; //开始说话
        //开始合成
        mTts.startSpeaking(content, mSynListener);
    }

    //上传语法文件
    private void upLoadGrammarText() {
        //开始识别,设置引擎类型为云端
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //指定编码
        mSpeechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        int ret = mSpeechRecognizer.buildGrammar("abnf", mCloudGrammar, mCloudGrammarListener);
        if (ret != ErrorCode.SUCCESS) {
            Log.i(TAG, "语法构建失败,错误码：" + ret);
        } else {
            Log.i(TAG, "语法构建成功，正在上传");
        }
    }

    //上传语法文件监听
    private GrammarListener mCloudGrammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if(error == null){
                if(!TextUtils.isEmpty(grammarId)){
                    mGrammarId=grammarId;
                    saveGrammarId(mGrammarId);
                    Log.i(TAG, "onBuildFinish: 语法上传成功，语法ID："+grammarId);
                }
            }else{
                Log.i(TAG, "onBuildFinish: 语法上传出错，错误码："+error.getErrorCode());
            }
        }
    };
    //保存语段语法的ID到本地
    private void saveGrammarId(String grammarId) {
        SharedPreferences sp = context.getSharedPreferences("iflytek",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("GrammarId",grammarId);
        e.commit();
    }
    //读取本地保存的语法ID
    private String getGrammarIdFromSP() {
        SharedPreferences sp = context.getSharedPreferences("iflytek",Context.MODE_PRIVATE);
        String result = sp.getString("GrammarId",null);
        return result;
    }
    //命令识别回调
    private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            if (null != recognizerResult) {
                //返回的是json数据，这里解析
                Log.i(TAG, "onResult: "+recognizerResult.getResultString());
                String text = JsonParser.parseGrammarResult(recognizerResult.getResultString());
                openListenCall.onSuccess(text);
            } else {
                Log.i(TAG, "recognizer result : null");
                openListenCall.onError(new Exception("异常，请查看logcat"));
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            openListenCall.onError(new Exception(speechError.getMessage()));
            Log.i(TAG, "onError: 语法命令回调产生错误");
        }
    };
    //语音合成监听
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {}
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {}
        @Override
        public void onSpeakPaused() {}
        @Override
        public void onSpeakResumed() {}
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {}
        @Override
        public void onCompleted(SpeechError speechError) {isSaying=false;}
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) { }
    };
        //回调接口
    public interface CallBack{
        void onError(Exception e);
        void onSuccess(String result);
    }
}
