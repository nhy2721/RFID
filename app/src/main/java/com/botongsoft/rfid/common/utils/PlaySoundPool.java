package com.botongsoft.rfid.common.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by pc on 2017/8/9.
 */

public class PlaySoundPool {
    private Context context;
    //音效的音量
    int streamVolume;

    //定义SoundPool 对象
    private SoundPool soundPool;
    //定义HASH表
    private HashMap<Integer, Integer> soundPoolMap;

    public PlaySoundPool(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        initSounds();
    }

    private void initSounds() {
        //初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);

        //初始化HASH表
        soundPoolMap = new HashMap<Integer, Integer>();

        //获得声音设备和设备音量
        AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void loadSfx(int raw, int ID) {
        //把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)
        soundPoolMap.put(ID, soundPool.load(context, raw, ID));
    }

    /**
     * //播放音频，第二个参数为左声道音量;第三个参数为右声道音量;
     * 第四个参数为优先级；第五个参数为循环次数，0不循环，-1循环;
     * 第六个参数为速率，速率    最低0.5最高为2，1代表正常速度
     *
     * @param sound 声音Id
     * @param uLoop 是否循环
     */
    public void play(int sound, int uLoop) {
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, uLoop, 1);
            }
        });

    }
}
