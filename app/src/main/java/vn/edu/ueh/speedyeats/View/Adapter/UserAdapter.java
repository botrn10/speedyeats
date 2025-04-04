package vn.edu.ueh.speedyeats.View.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import vn.edu.ueh.speedyeats.Model.User;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.my_interface.IClickCTHD;


import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{


    private Context context;
    private List<User> mListUser;
    private boolean ischat;
    private IClickCTHD iClickCTHD;


    String theLastMessage;


    public UserAdapter(Context context, List<User> mListUser, boolean ischat, IClickCTHD iClickCTHD) {
        this.context = context;
        this.mListUser = mListUser;
        this.ischat = ischat;
        this.iClickCTHD = iClickCTHD;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = mListUser.get(position);
        holder.tvItemUsername.setText(user.getName());




        if (user.getAvatar().equals("default")){
            holder.imgItemUser.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(user.getAvatar()).into(holder.imgItemUser);
        }










        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCTHD.onClickCTHD(position);


            }
        });
    }


    @Override
    public int getItemCount() {
        return mListUser.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{


        private final TextView tvItemUsername;
        private final ImageView imgItemUser;




        public ViewHolder(View itemView) {
            super(itemView);


            tvItemUsername = itemView.findViewById(R.id.tv_item_username);
            imgItemUser = itemView.findViewById(R.id.img_item_user);


        }
    }




}

