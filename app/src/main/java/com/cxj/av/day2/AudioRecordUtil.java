package com.cxj.av.day2;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;

import com.cxj.jetpackmvvm.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioRecordUtil {
    private static AudioRecordUtil instance = null;
    private Context mContext;
    private int sampleRateInHz;
    private int channelConfig;
    private int audioFormat;
    private File pcmFile;
    private boolean isRecording;
    private int bufferSize;
    private AudioRecord audioRecord;

    public static AudioRecordUtil getInstance(Context context) {
        if (instance == null) {
            instance = new AudioRecordUtil(context);
        }
        return instance;
    }

    private AudioRecordUtil(Context context) {
        this.mContext = context;
    }
    private void createAudioRecord() {
        try {
            int audioSource = MediaRecorder.AudioSource.MIC;//音频源：指的是从哪里采集音频。这里我们当然是从麦克风采集音频，所以此参数的值为MIC
            //音频采样率，常见的采样率为44100即44.1KHZ
            sampleRateInHz = 44100;
            // AudioFormat.CHANNEL_IN_MONO 单通道   AudioFormat.CHANNEL_IN_STEREO双通道
            //音频录制时的声道，分为单声道和立体声道，在AudioFormat中定义。
            channelConfig = AudioFormat.CHANNEL_IN_MONO;
            //常用的是 ENCODING_PCM_16BIT（16bit），ENCODING_PCM_8BIT（8bit），注意，前者是可以保证兼容所有Android手机的。
            //音频格式
            audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            //音频缓冲区大小，不同手机厂商有不同的实现（比如 我的一加手机该值为3584字节），可以通过下面的方法获取。
            bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
            audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSize);

            int state = audioRecord.getState();
            if (AudioRecord.STATE_INITIALIZED != state) {
                throw new Exception("AudioRecord无法初始化，请检查录制权限或者是否其他app没有释放录音器");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPCMFile() {
        pcmFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"raw.pcm");
        LogUtils.d( "initPCMFile: pcmFile=" + pcmFile);
    }

    public void startRecord() {
        if (audioRecord == null) {
            createAudioRecord();
        }
        initPCMFile();
        if (pcmFile.exists()) {
            pcmFile.delete();
        }
        isRecording = true;
        byte[] buffer = new byte[bufferSize];
        audioRecord.startRecording();
        new Thread(() -> {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(pcmFile);
                if (fileOutputStream != null) {
                    while (isRecording) {
                        int readStatus = audioRecord.read(buffer,0,bufferSize);
                        LogUtils.d( "run: readStatus=" + readStatus);
                        fileOutputStream.write(buffer);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
                LogUtils.e(e.getMessage());
            }finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopRecord() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
        }
    }

    public void release() {
        if (audioRecord != null) {
            audioRecord.release();
        }
        audioRecord = null;
    }

    public void convertPcmToWav() {
        File wavFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"convert.wav");
        if (wavFile.exists()) {
            wavFile.delete();
        }
        PcmToWavUtil.convertPcmToWav(pcmFile,wavFile,bufferSize,sampleRateInHz,channelConfig,audioFormat);
    }
}
