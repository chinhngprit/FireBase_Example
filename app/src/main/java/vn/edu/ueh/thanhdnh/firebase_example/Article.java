package vn.edu.ueh.thanhdnh.firebase_example;

import java.io.Serializable;

// Cần implements Serializable để truyền Object sang màn hình Detail
public class Article implements Serializable {
  private String id; // Lưu lại ID của document trên Firestore
  private String title;
  private String imageUrl;
  private String description;
  private int views; // Thêm trường số lượt xem

  public Article() {} // Constructor rỗng cho Firebase

  // Constructor để thêm bài mới (chưa có views, mặc định = 0)
  public Article(String title, String imageUrl, String description) {
    this.title = title;
    this.imageUrl = imageUrl;
    this.description = description;
    this.views = 0;
  }

  // Các hàm Getter & Setter
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getImageUrl() { return imageUrl; }
  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public int getViews() { return views; }
  public void setViews(int views) { this.views = views; }
}