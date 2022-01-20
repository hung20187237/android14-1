package com.hust.httpconnection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hust.httpconnection.User.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    List<User> userList;
    Context mContext;
    String baseURL = "https://lebavui.github.io";
    private OnUserClickListener listener;

    public UserAdapter(List<User> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        String username = user.getUsername();
        String email = user.getEmail();


        holder.textViewUsername.setText(username);
        holder.textViewEmail.setText(email);

        Picasso.get()
                .load(baseURL + user.getAvatar().getPhoto())
                .into(holder.imageAvatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView imageAvatar;
        TextView textViewUsername, textViewEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imageAvatar = itemView.findViewById(R.id.avatar_list);
            textViewUsername = itemView.findViewById(R.id.username_list);
            textViewEmail = itemView.findViewById(R.id.email_list);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onUserClick(userList.get(position));
                }
            });
        }
    }

}
