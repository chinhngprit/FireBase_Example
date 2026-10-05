package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowDataActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Article> articles  = new ArrayList();
    ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.main).setBackgroundColor(android.graphics.Color.parseColor("#0A0A0A"));
        // 1. CÀI ĐẶT TOOLBAR VÀ NÚT BACK
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_show_data);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FirebaseApp.initializeApp(this);

        // cai dat recyclerView va Adapter
        recyclerView = findViewById(R.id.reclyclerview);
        adapter = new ArticleAdapter(ShowDataActivity.this, articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowDataActivity.this));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // lang nghe du lieu realtime tu collection articles
      db.collection("articles").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
          if (error != null) {
            Log.e("Firebase", "Lỗi đọc dữ liệu: ", error);
              Toast.makeText(ShowDataActivity.this, "Lỗi kết nối Firebase", Toast.LENGTH_SHORT).show();
              return;
            }
          if (snapshots != null) {
              // xoa danh sach cu de tranh bij trung lap khi load lai
              articles.clear();

              for (QueryDocumentSnapshot document : snapshots){
                  Map<String, Object> data = document.getData();

                  String title = data.get("title") != null ? (String) data.get("title") : "";
                  String imageUrl = data.get("imageUrl") != null ? (String) data.get("imageUrl") : "";
                  String description = data.get("description") != null ? (String) data.get("description") : "";

                  // Lấy số view từ Firebase (nếu chưa có thì mặc định là 0)
                  long views = data.get("views") != null ? (long) data.get("views") : 0;

                  // Dong goi thanh doi tuowng Article va them vao mang
                  Article article = new Article(title, imageUrl, description);
                  // THÊM 2 DÒNG NÀY: Lưu lại ID của Firebase và gán số View
                  article.setId(document.getId());
                  article.setViews((int) views);
                  articles.add(article);
              }
              // cap nhat mang vao adapter va ve lai man hinh
            adapter.update(articles);
            adapter.notifyDataSetChanged();
          }
        }
      });

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Đóng màn hình danh sách, quay về màn hình nhập liệu
        return true;
    }

}
