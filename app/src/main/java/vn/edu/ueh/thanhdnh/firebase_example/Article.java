package vn.edu.ueh.thanhdnh.firebase_example;

public class Article {
  private String title;
  private String imageUrl; // Thêm thuộc tính lưu link ảnh (URL)
  private String description;

  public Article() {}

  // Cập nhật Constructor để nhận đủ 3 tham số
  public Article(String title, String imageUrl, String description) {
    this.title = title;
    this.imageUrl = imageUrl;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}