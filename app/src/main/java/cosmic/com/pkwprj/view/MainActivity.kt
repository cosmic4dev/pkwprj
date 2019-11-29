package cosmic.com.pkwprj.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import cosmic.com.pkwprj.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        goGitBtn.setOnClickListener {

            var intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)

        }

        goUiBtn.setOnClickListener {
            var intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
