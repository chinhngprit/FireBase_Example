package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private TextView txtTitle, txtDescription, txtViews;
  private ImageView imgArticle;
  private ArticleAdapter adapter;

  public ArticleViewHolder(@NonNull View itemView, ArticleAdapter adapter) {
    super(itemView);

    txtTitle = itemView.findViewById(R.id.tv_title);
    txtDescription = itemView.findViewById(R.id.tv_content);
    txtViews = itemView.findViewById(R.id.tv_views);
    imgArticle = itemView.findViewById(R.id.img_cover);
    this.adapter = adapter;
  }

  public TextView getTxtTitle() { return txtTitle; }
  public TextView getTxtDescription() { return txtDescription; }
  public TextView getTxtViews() { return txtViews; }
  public ImageView getImgArticle() { return imgArticle; }
}