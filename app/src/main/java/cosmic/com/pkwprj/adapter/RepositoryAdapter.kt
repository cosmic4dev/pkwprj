package cosmic.com.pkwprj.view

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.model.GitHubService

/**
 * RecyclerView에서 리포지토리 목록을 표시하기 위한 Adapter 클래스
 * 이 클래스에 의해 RecyclerView 아이템의 View를 생성하고, View에 데이터를 넣는다
 */
class RepositoryAdapter(
    private val context: Context,
    private val onRepositoryItemClickListener: OnRepositoryItemClickListener
) : RecyclerView.Adapter<RepositoryAdapter.RepoViewHolder>() {
    private var items: List<GitHubService.RepositoryItem>? = null

    /**
     * 리포지토리의 데이터를 설정해서 갱신한다
     * @param items
     */
    fun setItemsAndRefresh(items: List<GitHubService.RepositoryItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): GitHubService.RepositoryItem {
        return items!![position]
    }

    /**
     * RecyclerView 아이템의 View 생성과 View를 유지할 ViewHolder를 생성
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
        return RepoViewHolder(view)
    }

    /**
     * onCreateViewHolder로 만든ViewHolder의 View에
     * setItemsAndRefresh(items)로 설정된 데이터를 넣는다
     */
    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItemAt(position)

        // View가 클릭되면, 클릭된 아이템을 Listener에 알린다
        holder.itemView.setOnClickListener {
            onRepositoryItemClickListener.onRepositoryItemClick(
                item
            )
        }

  /*      holder.repoName.text = item.name
        holder.repoDetail.text = item.description
        holder.starCount.text = item.stargazers_count*/
        // 이미지는 Glide라는 라이브러리를 써서 데이터를 설정한다
//        Glide.with(context)
//            .load(item.owner.avatar_url)
//            .asBitmap().centerCrop().into<>(object : BitmapImageViewTarget(holder.repoImage) {
//
//            })

    }

    override fun getItemCount(): Int {
        return if (items == null) {
            0
        } else items!!.size
    }

    interface OnRepositoryItemClickListener {
        /**
         * 리포지토리의 아이템이 탭되면 호출된다
         */
        fun onRepositoryItemClick(item: GitHubService.RepositoryItem)
    }

    /**
     * View를 저장해 두는 클래스
     */
     class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val repoName: TextView
//        private val repoDetail: TextView
//        private val repoImage: ImageView
//        private val starCount: TextView

        init {
//            repoName = itemView.findViewById(R.id.repo_name) as TextView
//            repoDetail = itemView.findViewById(R.id.repo_detail) as TextView
//            repoImage = itemView.findViewById(R.id.repo_image) as ImageView
//            starCount = itemView.findViewById(R.id.repo_star) as TextView
        }
    }
}
