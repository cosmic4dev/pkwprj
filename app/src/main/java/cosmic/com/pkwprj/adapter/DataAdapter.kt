package cosmic.com.pkwprj.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.model.DbHelper
import cosmic.com.pkwprj.model.GitHubResult
import cosmic.com.pkwprj.model.User

class DataAdapter : RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    internal var context: Context
    internal lateinit var gitHubResult: GitHubResult
    internal lateinit var user: User
    internal lateinit var userName: String
    internal var isLike: Boolean? = false

    constructor(context: Context?, gitHubResult: GitHubResult, name: String) {
        this.context = context!!
        this.gitHubResult=gitHubResult;
        this.userName = name
    }

    constructor(context: Context, gitHubResult: GitHubResult) {
        this.context = context
        this.gitHubResult = gitHubResult
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        user=gitHubResult.items[position]
        val imageUri = Uri.parse(user.avatar_url)
        Log.d("TAG", "url->$imageUri")
        val name = user.login
        holder.tv_name.text = user.login
        Log.d("TAG", "name->$name")
        val score=user.score
        holder.tv_score.text= user.score.toString()
        Log.d("TAG", "score->$score")
        val html = user.html_url
        holder.tv_html.text = html

        val target = holder.imageView
        Glide.with(context)
            .load(imageUri)
            .fitCenter()
            .centerCrop()
            .override(100, 100)
            .into(target)

        holder.saveBtn.setOnClickListener {
            if (isLike == false) {
                user=gitHubResult.items[position]
                saveLike(user.login, user.avatar_url, user.html_url, user.score)
                holder.saveBtn.setBackgroundResource(R.drawable.baseline_favorite_black_18dp)
                isLike = true
            } else {
                holder.saveBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_18dp)
                cancleLike(user.login)
                isLike = false
            }
        }

        fun checkLike(userName: String) {
            val dbHelper = DbHelper(context, "HUB.db", null, 1)
            val getdata = dbHelper.getData(userName)

            if (getdata == userName) {
                holder.saveBtn.setBackgroundResource(R.drawable.baseline_favorite_black_18dp)
                isLike = true
            }
        }
        checkLike(name)
    }



    private fun cancleLike(name: String) {
        val dbHelper = DbHelper(context, "HUB.db", null, 1)
        dbHelper.delete(name)
    }

    private fun saveLike(name: String, url: String, html: String, etc: Float) {
        val dbHelper = DbHelper(context, "HUB.db", null, 1)
        dbHelper.insert(name, url, html, etc)
    }

    override fun getItemCount(): Int {
        return gitHubResult.items.size
    }


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv_name: TextView
        internal var tv_score: TextView
        internal var tv_html: TextView
        internal var imageView: ImageView
        internal var saveBtn: ImageButton

        init {
            this.tv_name = itemView.findViewById(R.id.repo_name)
            this.imageView = itemView.findViewById(R.id.repo_image)
            this.tv_score = itemView.findViewById(R.id.repo_score)
            this.saveBtn = itemView.findViewById(R.id.saveBtn)
            this.tv_html = itemView.findViewById(R.id.repo_html)
            

        }

    }
}
