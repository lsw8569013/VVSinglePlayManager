package movie.example.ls.vvmoviemanager.utils;

import android.media.MediaRecorder;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 录音管理器
 * Created By: lsw
 * Author : lsw
 * Date :  2016/11/10
 * Email : lsw
 */
public class AudioManager {

    private MediaRecorder mRecorder;
    private String mDirString;
    public static final int MSG_ERROR_AUDIO_RECORD = -4;//录音出错
    private static AudioManager mInstance;
    private boolean isPrepared;// 是否准备好了
    public AudioStageListener mListener;
    private Handler handler;
    private String mCurrentFilePathString;


    private AudioManager(String dir) {
        mDirString = dir;
    }

    /**
     * 传入文件路径
     * @param dir
     * @return
     */
    public static AudioManager getInstance(String dir) {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioManager(dir);

                }
            }
        }
        return mInstance;
    }

    public void setHandle(Handler handler) {
        this.handler = handler;
    }

    /**
     * 回调函数，准备完毕，准备好后，button才会开始显示录音框
     *
     */
    public interface AudioStageListener {
        void wellPrepared();
    }

    public void setOnAudioStageListener(AudioStageListener listener) {
        mListener = listener;
    }
    public void setVocDir(String dir) {
        mDirString = dir;
    }

    @SuppressWarnings("deprecation")
    public void prepareAudio() {
        try {

            isPrepared = false;

            File dir = new File(mDirString);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileNameString = generalFileName();
            File file = new File(dir, fileNameString);

            mCurrentFilePathString = file.getAbsolutePath();

            mRecorder = new MediaRecorder();
            // 设置输出文件
            mRecorder.setOutputFile(file.getAbsolutePath());
            // 设置meidaRecorder的音频源是麦克风
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置文件音频的输出格式为amr
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            // 设置音频的编码格式为amr
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            mRecorder.prepare();

            mRecorder.start();

            if (mListener != null) {
                mListener.wellPrepared();
            }

            isPrepared = true;

        } catch (IllegalStateException e) {
            e.printStackTrace();
            if (handler != null) {
                handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (handler != null) {
                handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (handler != null) {
                handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
            }
        }

    }

    /**
     * 随机生成文件的名称
     *
     * @return
     */
    private String generalFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    private int vocAuthority[] = new int[10];
    private int vocNum = 0;
    private boolean check = true;

    /**
     * 获取声音的 水平
     * @param maxLevel
     * @return
     */
    public int getVoiceLevel(int maxLevel) {

        if (isPrepared) {
            try {
                //音频的振幅范围，值域是0-32767
                int vocLevel = mRecorder.getMaxAmplitude();
                if (check) {
                    if (vocNum >= 10) {
                        Set<Integer> set = new HashSet<Integer>();
                        for (int i = 0; i < vocNum; i++) {
                            set.add(vocAuthority[i]);
                        }
                        if (set.size() == 1) {
                            if (handler != null)
                                handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
                            vocNum = 0;
                            vocAuthority = null;
                            vocAuthority = new int[10];
                        } else {
                            check = false;
                        }
                    } else {
                        vocAuthority[vocNum] = vocLevel;
                        vocNum++;
                    }
                }
                return maxLevel * vocLevel / 32768 + 1;
            } catch (Exception e) {
                e.printStackTrace();
                if (handler != null)
                    handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
            }
        }

        return 1;
    }

    /**
     * 释放资源
     */
    public void release() {

        if (null != mRecorder) {
            isPrepared = false;
            try {
                mRecorder.stop();
                mRecorder.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecorder = null;
        }

    }

    /**
     * 录音取消 删除文件
     */
    public void cancel() {
        release();
        if (mCurrentFilePathString != null) {
            File file = new File(mCurrentFilePathString);
            if(file.exists()){
                file.delete();
            }
            mCurrentFilePathString = null;
        }

    }

    /**
     * 返回文件的路径
     * @return
     */
    public String getCurrentFilePath() {
        return mCurrentFilePathString;
    }


}
