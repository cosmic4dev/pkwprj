package cosmic.com.pkwprj.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cosmic.com.pkwprj.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goGitBtn.setOnClickListener {

            var intent = Intent(this,Main2Activity::class.java)
            startActivity(intent)

        }

        goUiBtn.setOnClickListener {
            var intent = Intent(this,UIActivity::class.java)
            startActivity(intent)
        }
    }
}
