package cosmic.com.pkwprj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cosmic.com.pkwprj.R;
import cosmic.com.pkwprj.model.Office;


public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    final String TAG = "아답터";
    Context context;
//    List<Drawable> drawables; //3개담아와야한다.
//    Drawable drawable;
    Office office;
    ArrayList<Office> lists;


    public ProgressAdapter(Context context, ArrayList<Office> list) {
        this.context = context;
        this.lists = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_office, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        office = lists.get(position);

        holder.tv_officeName.setText(office.getName());
        holder.tv_location.setText(office.getLocation());
        holder.progressBar3.setProgressDrawable(office.getDrawable());
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar3;
        TextView tv_officeName, tv_location,tv_current;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar3 = itemView.findViewById(R.id.progressBar3);
            tv_officeName = itemView.findViewById(R.id.tv_officeName);
            tv_location = itemView.findViewById(R.id.tv_officeLocation);
        }

    }
}
