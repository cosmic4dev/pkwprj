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
import cosmic.com.pkwprj.model.GithubOwner

class DataAdapter2 : RecyclerView.Adapter<DataAdapter2.DataViewHolder> {

    internal var context: Context
    internal var dataList: List<GithubOwner>
    internal lateinit var owner: GithubOwner
    internal lateinit var userName: String

    internal var isLike: Boolean? = false

    constructor(context: Context, dataList: List<GithubOwner>, name: String, isSearch: Boolean) {
        this.context = context
        this.dataList = dataList
        this.userName = name
    }

    constructor(context: Context, dataList: List<GithubOwner>) {
        this.context = context
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        owner = dataList[position]

        val imageUri = Uri.parse(owner.avatar_url)
        Log.d("TAG", "url->$imageUri")
        val name = owner.login
        holder.tv_name.text = owner.login
        Log.d("TAG", "name->$name")
        //        holder.tv_score.setText((int) githubOwner.getScore());
        Log.d("TAG", "score->" + owner.score)
        val html = owner.html_url
        holder.tv_html.text = html

        val target = holder.imageView
        Glide.with(context)
            .load(imageUri)
            .fitCenter()
            .centerCrop()
            .override(100, 100)
            .into(target)


    }


    override fun getItemCount(): Int {
        return dataList.size
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

            saveBtn.setBackgroundResource(R.drawable.baseline_favorite_black_18dp)
        }


    }
}
