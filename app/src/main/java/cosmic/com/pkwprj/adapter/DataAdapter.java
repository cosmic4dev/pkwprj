package cosmic.com.pkwprj.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cosmic.com.pkwprj.R;
import cosmic.com.pkwprj.model.DbHelper;
import cosmic.com.pkwprj.model.GithubOwner;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    Context context;
    List<GithubOwner>dataList;
    GithubOwner owner;
    String userName;
    boolean isSearch;

    Boolean isLike=false;
    public DataAdapter(Context context,List<GithubOwner>dataList,String name,boolean isSearch) {
        this.context = context;
        this.dataList=dataList;
        this.userName=name;
        this.isSearch=isSearch;
    }

    public DataAdapter(Context context, List<GithubOwner> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder holder, final int position) {

        owner=dataList.get(position);

        Uri imageUri = Uri.parse(owner.getAvatar_url());
        Log.d("TAG", "url->"+imageUri);
        String name=owner.getLogin();
        holder.tv_name.setText(owner.getLogin());
        Log.d("TAG", "name->"+name);
//        holder.tv_score.setText((int) githubOwner.getScore());
        Log.d("TAG", "score->"+owner.getScore());
        String html=owner.getHtml_url();
        holder.tv_html.setText(html);

        ImageView target=holder.imageView;
        Glide.with(context)
                .load(imageUri)
                .fitCenter()
                .centerCrop()
                .override(100, 100)
                .into(target);

        if(isSearch==true) {
            holder.saveBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (isLike == false) {
                        owner = dataList.get(position);
                        saveLike(owner.getLogin(), owner.getAvatar_url(), owner.getHtml_url(), owner.getScore());
                        holder.saveBtn.setBackgroundResource(R.drawable.baseline_favorite_black_18dp);
                        isLike = true;
                    } else {
                        holder.saveBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_18dp);
                        cancleLike(owner.getLogin());
                        isLike = false;
                    }
                }
            });
        }

    }

    private void cancleLike(String name) {
        DbHelper dbHelper=new DbHelper(context, "HUB.db", null, 1);
        dbHelper.delete(name);
    }

    private void saveLike(String name,String url,String html,int etc) {
        DbHelper dbHelper=new DbHelper(context, "HUB.db", null, 1);
        dbHelper.insert(name,url,html,etc);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    public class DataViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_score,tv_html;
        ImageView imageView;
        ImageButton saveBtn;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name =itemView.findViewById(R.id.repo_name);
            this.imageView = itemView.findViewById(R.id.repo_image);
            this.tv_score=itemView.findViewById(R.id.repo_score);
            this.saveBtn=itemView.findViewById(R.id.saveBtn);
            this.tv_html=itemView.findViewById(R.id.repo_html);

            checkLike(userName);

        }

        public void checkLike(String name){
            DbHelper dbHelper=new DbHelper(context, "HUB.db", null, 1);
            String data=dbHelper.getData(name);
            Log.d("TAG","넘어온 데이터::"+data);
            Log.d("TAG","이름::"+name);
            if(data==name){
                if(isLike==false) {
                    Toast.makeText(context, "같음", Toast.LENGTH_SHORT).show();
                    saveBtn.setBackgroundResource(R.drawable.baseline_favorite_black_18dp);
                    isLike = true;
                }
            }
        }
    }
}
