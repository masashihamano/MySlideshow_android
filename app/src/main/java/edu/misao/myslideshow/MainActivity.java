package edu.misao.myslideshow;

import android.annotation.SuppressLint;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;//変数を用意
    //画像のを10枚格納
    int[] mImageResources = {R.drawable.slide00,R.drawable.slide01,R.drawable.slide02,R.drawable.slide03,R.drawable.slide04,R.drawable.slide05,R.drawable.slide06,R.drawable.slide07,R.drawable.slide08,R.drawable.slide09};
    int mPosition = 0;//どの画像を表示するか記憶するための変数


    boolean mIsSlideshow = false;
    MediaPlayer mMediaplayer; //変数を用意

    public class MainTimerTask extends TimerTask{

        @Override
        public void run() {
//            if (mIsSlideshow){
//                mHandler.post( new Runnable() {
//                    @Override
//                    public void run() {
//                        movePosition( 1 );
//                    }
//                } );
//            }
        }
    }

    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler() {
        @Override
        public void publish(LogRecord logRecord) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mTimer.schedule( mTimerTask,0,5000 );
        mMediaplayer = MediaPlayer.create( this,R.raw.getdown );//インスタンス化
        mMediaplayer.setLooping( true );//繰り返し再生

        mImageSwitcher = (ImageSwitcher) findViewById( R.id.imageSwitcher );//インスタンス化
        mImageSwitcher.setFactory( new ViewSwitcher.ViewFactory(){//ビューを生成する方法を指定

            @Override
            public View makeView() {
                ImageView imageView = new ImageView( getApplicationContext() );
                return imageView;
            }
        });
        mImageSwitcher.setImageResource( mImageResources[0] );
    }


    public void onAnimationButtonTapped(final View view){
//        ViewPropertyAnimator animater = view.animate();//ViewPropertyAnimatorのインスタンス化
//        animater.setDuration( 3000 );//アニメーション時間をミリ秒で指定(3秒)
//        animater.rotation(360.0f * 5.0f);//回転する角度(360度✕5回転)

        view.animate().setDuration( 3000 ).rotation( 360.0f * 5.0f );//5回転
        view.animate().setDuration( 3000 ).scaleX( 2.5f ).scaleY( 2.5f );//x,yそれぞれ2.5倍
        view.animate().setDuration( 3000 ).x( 200.0f ).y(200.0f);//x,yそれぞれ200px移動


        float y = view.getY() + 100;//y座標を取得し100px追加
        view.animate().setDuration( 1000 ).setInterpolator( new BounceInterpolator(  ) ).y( y );//移動後のy座標を指定

    }

    //画像の変更用メソッドを作成する
    private void movePosition(int move){
        mPosition = mPosition + move;
        if (mPosition >= mImageResources.length) {//引数moveの値だけ変化させる
            mPosition = 0;
        }else if (mPosition < 0){//0よりも小さくなった場合は最後の画像を示す
            mPosition = mImageResources.length -1;
        }
        mImageSwitcher.setImageResource( mImageResources[mPosition] );//リソースIDを渡して画像を表示
    }

    //<ボタンを押した時のメソッド
    @SuppressLint("WrongViewCast")
    public void onPrevButtonTapped(View view){
        mImageSwitcher.setInAnimation( this,android.R.anim.fade_in );//画像を表示
        mImageSwitcher.setOutAnimation( this, android.R.anim.fade_out );//画像を非表示
        movePosition( -1 );
        findViewById( R.id.imageView ).animate().setDuration(1000 ).alpha(0.0f);//アンドロイド君を非表示
    }

    //>ボタンを押した時のメソッド
    @SuppressLint("WrongViewCast")
    public void onNextButtonTapped(View view){
        mImageSwitcher.setInAnimation( this,android.R.anim.slide_in_left );
        mImageSwitcher.setOutAnimation( this,android.R.anim.slide_out_right );
        movePosition( 1 );
        findViewById( R.id.imageView ).animate().setDuration( 1000 ).alpha( 0.0f );
    }

    public void onSlideshowButtonTapped(View view){
        mIsSlideshow = !mIsSlideshow;

        if(mIsSlideshow){
            mMediaplayer.start();
        }else{
            mMediaplayer.pause();
            mMediaplayer.seekTo( 0 );
        }
    }

}
