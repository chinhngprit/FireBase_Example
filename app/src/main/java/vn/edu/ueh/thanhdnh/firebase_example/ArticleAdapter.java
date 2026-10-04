package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
  private LayoutInflater mInflater;
  private List<Article> articles;

  public ArticleAdapter(Context context, List<Article> articles) {
    this.mInflater = LayoutInflater.from(context);
    this.articles = articles;
  }

  public void update(List<Article> articles){
    this.articles = articles;
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View customView = mInflater.inflate(R.layout.item_article, parent, false);
    return new ArticleViewHolder(customView, this);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
    // lay bai viet hien tai theo vi tri
    Article currentArticle = articles.get(position);
    // cap nhat do van ban vao text view
    holder.getTxtTitle().setText(currentArticle.getTitle());
    holder.getTxtDescription().setText(currentArticle.getDescription());
    // dung picasso tai anh tu URL
    String imageURL = currentArticle.getImageUrl();
    // ktra bai viet co link anh khong
    if (imageURL != null && !imageURL.isEmpty()){
      Picasso.get()
              .load(imageURL)
              .placeholder(android.R.drawable.ic_menu_gallery) // anh xam hien thi tam luc tai manhg
              .into(holder.getImgArticle());
    } else {
      holder.getImgArticle().setImageResource(android.R.drawable.ic_menu_gallery);
    }
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }
}
