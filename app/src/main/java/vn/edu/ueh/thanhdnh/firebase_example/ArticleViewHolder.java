package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private TextView txtTitle, txtDescription;
  private ImageView imgArticle;
  private ArticleAdapter adapter;


  public ArticleViewHolder(@NonNull View itemView, ArticleAdapter adapter) {
    super(itemView);
    txtTitle = itemView.findViewById(R.id.txt_title);
    txtDescription = itemView.findViewById(R.id.txt_description);
    imgArticle = itemView.findViewById(R.id.img_article);
    this.adapter = adapter;
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public TextView getTxtDescription() {
    return txtDescription;
  }

  public ImageView getImgArticle() {
    return imgArticle;
  }
}
