package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {

    private ImageView imgCover;
    private TextView tvTitle, tvViews, tvContent;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. KẾT NỐI VÀ THIẾT LẬP THANH TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 2. ÁNH XẠ GIAO DIỆN
        imgCover = findViewById(R.id.img_detail_cover);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvViews = findViewById(R.id.tv_detail_views);
        tvContent = findViewById(R.id.tv_detail_content);

        db = FirebaseFirestore.getInstance();

        // 3. LẤY ID BÀI VIẾT TỪ INTENT
        Article article = (Article) getIntent().getSerializableExtra("ARTICLE_DATA");

        if (article != null && article.getId() != null) {
            String documentId = article.getId();

            // BƯỚC A: TĂNG VIEW (Thực hiện 1 lần duy nhất khi vừa mở màn hình)
            int newViews = article.getViews() + 1;
            db.collection("articles").document(documentId).update("views", newViews);

            // BƯỚC B: LẮNG NGHE REAL-TIME TỪ FIREBASE
            db.collection("articles").document(documentId)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firebase", "Lỗi lắng nghe chi tiết", error);
                                Toast.makeText(ArticleDetailActivity.this, "Mất kết nối dữ liệu", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Kiểm tra xem bài viết có còn tồn tại trên server không
                            if (snapshot != null && snapshot.exists()) {
                                // Trích xuất dữ liệu mới nhất trực tiếp từ máy chủ
                                String title = snapshot.getString("title");
                                String description = snapshot.getString("description");
                                String imageUrl = snapshot.getString("imageUrl");
                                Long views = snapshot.getLong("views"); // Trên Firebase số nguyên thường trả về kiểu Long

                                // Đổ dữ liệu real-time lên giao diện
                                tvTitle.setText(title != null ? title : "");
                                tvContent.setText(description != null ? description : "");
                                tvViews.setText("Views: " + (views != null ? views : 0));

                                // Tải ảnh (kiểm tra link rỗng)
                                if (imageUrl != null && !imageUrl.isEmpty()) {
                                    Picasso.get()
                                            .load(imageUrl)
                                            .placeholder(android.R.drawable.ic_menu_gallery)
                                            .into(imgCover);
                                } else {
                                    imgCover.setImageResource(android.R.drawable.ic_menu_gallery);
                                }
                            } else {
                                // Nếu bài viết bị ai đó xóa khỏi Firebase trong lúc đang xem
                                Toast.makeText(ArticleDetailActivity.this, "Bài viết không còn tồn tại", Toast.LENGTH_SHORT).show();
                                finish(); // Tự động đóng màn hình
                            }
                        }
                    });
        }
    }

    // Xử lý nút Back (Mũi tên quay lại) trên Action Bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}