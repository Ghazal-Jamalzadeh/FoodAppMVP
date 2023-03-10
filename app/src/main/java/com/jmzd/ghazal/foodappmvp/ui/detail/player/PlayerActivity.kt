package com.jmzd.ghazal.foodappmvp.ui.detail.player

import android.os.Bundle
import android.view.WindowManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.jmzd.ghazal.foodappmvp.databinding.ActivityPlayerBinding
import com.jmzd.ghazal.foodappmvp.utils.VIDEO_ID
import com.jmzd.ghazal.foodappmvp.utils.YOUTUBE_API_KEY
import com.jmzd.ghazal.foodappmvp.utils.showSnackBar


@Suppress("DEPRECATION")
class PlayerActivity : YouTubeBaseActivity() {
    //Binding
    private lateinit var binding: ActivityPlayerBinding

    //Other
    private var videoId = ""
    private lateinit var player: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        //Full screen
        /* حذف استاتوس بار روش های مختلفی داره. مثلا از طریق مانیفست. روش زیر یکی از بهترین روش هاست که حتی توی دستگاه های شیاپومی و هواوی با کرش رو به رو نمیشه  */
        /* این کد حتما حتما باید قبل از setContentView قرار بگیرد که قبل از ساختن لایه اعمال شود  */
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        //Get data
        videoId = intent.getStringExtra(VIDEO_ID).toString()
        //InitViews
        binding.apply {
            val listener = object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer, p2: Boolean) {
                    player = p1
                    player.loadVideo(videoId)
                    player.play() // pause .... متدهای متنوعی داره
                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                    root.showSnackBar("Error")
                }

            }
            /* برای initialize کردن پلیر یوتیوب نیاز به api key و listener داریم */
            videoPlayer.initialize(YOUTUBE_API_KEY, listener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /* نکته مهم :
        * هر چیزی که توی یک صفحه داره اجرا میشه که به واسطه دکمه بک باید متوقف شه رو توی onDestroy مدیریت کنید همیشه
        * تا با ارور out of memory رو به رو نشید
        * مثل پرزنتر یا کروتین ها یا این پلیر که از سرویس های اندروید داره استفاده میکنه
        * */
        player.release()
    }
}