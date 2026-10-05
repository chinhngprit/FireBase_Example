package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import androidx.appcompat.widget.Toolbar;

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

        // Bật nút mũi tên quay lại (Back button) trên thanh Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ánh xạ giao diện
        imgCover = findViewById(R.id.img_detail_cover);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvViews = findViewById(R.id.tv_detail_views);
        tvContent = findViewById(R.id.tv_detail_content);

        db = FirebaseFirestore.getInstance();

        // Nhận đối tượng Article từ màn hình danh sách gửi qua
        Article article = (Article) getIntent().getSerializableExtra("ARTICLE_DATA");

        if (article != null) {
            // Đổ dữ liệu lên màn hình
            tvTitle.setText(article.getTitle());
            tvContent.setText(article.getDescription());

            if (article.getImageUrl() != null && !article.getImageUrl().isEmpty()) {
                Picasso.get().load(article.getImageUrl()).into(imgCover);
            }

            // Tự động tăng số View (+1) khi mở bài viết
            int newViews = article.getViews() + 1;
            tvViews.setText("Views: " + newViews);

            // Cập nhật số View mới lên Firebase (nếu bài viết có lưu ID)
            if (article.getId() != null) {
                db.collection("articles").document(article.getId())
                        .update("views", newViews);
            }
        }
    }

    // Xử lý nút Back (Mũi tên quay lại) trên Action Bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}