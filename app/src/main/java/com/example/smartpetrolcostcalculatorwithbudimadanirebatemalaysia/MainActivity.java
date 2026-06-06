package com.example.smartpetrolcostcalculatorwithbudimadanirebatemalaysia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengaktifkan mod EdgeToEdge (skrin penuh menembusi status bar)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Hubungkan Toolbar XML dengan Java & set sebagai ActionBar aplikasi
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Laraskan Window Insets pada parent view supaya Toolbar turun di bawah status bar/kamera bulat
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            if (toolbar != null) {
                // Mengubah margin atas Toolbar secara dinamik mengikut ketinggian status bar telefon
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) toolbar.getLayoutParams();
                params.topMargin = systemBars.top;
                toolbar.setLayoutParams(params);
            }
            return insets;
        });

        // 3. Paparkan skrin Home secara automatik semasa aplikasi mula dibuka (Default)
        if (savedInstanceState == null) {
            loadFragment(new Home());
        }
    }

    // 4. Masukkan fail menu dropdown tiga titik (navigation_menu.xml) ke dalam Toolbar atas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    // 5. Logik penukaran halaman apabila menu dropdown (Home / About) diklik
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            loadFragment(new Home());
            return true;
        } else if (id == R.id.menu_about) {
            loadFragment(new About()); // Memanggil fail About.java anda
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Fungsi bantuan untuk menukar skrin Fragment di dalam fragment_container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}