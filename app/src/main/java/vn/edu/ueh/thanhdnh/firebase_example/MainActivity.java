package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  FirebaseFirestore db;
  Button btAdd, btShow;
  EditText etTitle, etImageUrl, etDescription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
//    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//      return insets;
//    });
    // khoi tao Firebase
    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    etTitle = findViewById(R.id.etTitle);
    etDescription = findViewById(R.id.etDescription);
    etImageUrl = findViewById(R.id.etImageUrl);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btAdd) {
      // lay du lieu nguoi dung nhap
      String title = etTitle.getText().toString().trim();
      String imageUrl = etImageUrl.getText().toString().trim();
      String description = etDescription.getText().toString().trim();

      if (title.isEmpty() || description.isEmpty()){
        Toast.makeText(this, "Vui lòng nhập Tiêu đề và Nội dung!", Toast.LENGTH_SHORT).show();
        return;
      }
      // dong goi doi tuong article va luu vao collection "article" tren firestore
      Article newArticle = new Article(title, imageUrl, description);

      db.collection("articles")
              .add(newArticle)
              .addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Đăng bài thành công!", Toast.LENGTH_SHORT).show();
                etTitle.setText("");
                etImageUrl.setText("");
                etDescription.setText("");
              })
              .addOnFailureListener(e -> {
                Toast.makeText(this, "Lỗi đăng bài!", Toast.LENGTH_SHORT).show();
              });
    } else if (view.getId() == R.id.btShow) {
      Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
      startActivity(intent);
    }
  }
}
