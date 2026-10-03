package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {
  private LayoutInflater mInflater;
  private List<Article> users;

  public UserViewAdapter(Context context, List<Article> users) {
    this.mInflater = LayoutInflater.from(context);
    this.users = users;
  }

  public void update(List<Article> users){
    this.users = users;
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View customView = mInflater.inflate(R.layout.item_article, parent, false);
    return new UserViewHolder(customView, this);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    Article currentuser = users.get(position);
    holder.getTxtName().setText(currentuser.getName());
    holder.getTxtPhone().setText(currentuser.getPhone());
  }

  @Override
  public int getItemCount() {
    return users.size();
  }
}
