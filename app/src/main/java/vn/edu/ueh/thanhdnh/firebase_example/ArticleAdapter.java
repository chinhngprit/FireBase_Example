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
    Article currentArticle = articles.get(position);

    holder.getTxtTitle().setText(currentArticle.getTitle());
    holder.getTxtDescription().setText(currentArticle.getDescription());
    holder.getTxtViews().setText("Views: " + currentArticle.getViews());

    String imageURL = currentArticle.getImageUrl();
    if (imageURL != null && !imageURL.isEmpty()) {
      Picasso.get().load(imageURL).placeholder(android.R.drawable.ic_menu_gallery).into(holder.getImgArticle());
    }

    // THÊM SỰ KIỆN CLICK: Mở màn hình Detail khi nhấn vào bài viết
    holder.itemView.setOnClickListener(v -> {
      // Chuyển sang màn hình ArticleDetailActivity và gửi theo đối tượng currentArticle
      android.content.Intent intent = new android.content.Intent(holder.itemView.getContext(), ArticleDetailActivity.class);
      intent.putExtra("ARTICLE_DATA", currentArticle);
      holder.itemView.getContext().startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }
}
