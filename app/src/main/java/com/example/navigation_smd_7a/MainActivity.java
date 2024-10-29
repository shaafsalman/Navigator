package com.example.navigation_smd_7a;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.navigation_smd_7a.Adapter.ViewPagerAdapter;
import com.example.navigation_smd_7a.Dao.ProductDao;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final int badgeCount = 0;
    private boolean isBadgeVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupViewAndInsets();

        viewPager.setAdapter(new ViewPagerAdapter(this));
        setupFabClickListener();
        setupTabLayoutWithViewPager();
    }

    private void setupViewAndInsets() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupFabClickListener() {
        FloatingActionButton fabAddProduct = findViewById(R.id.fab_add);
        fabAddProduct.setOnClickListener(view -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.add_new_product_dialog_design, null);
            EditText etTitle = dialogView.findViewById(R.id.etTitle);
            EditText etDate = dialogView.findViewById(R.id.etDate);
            EditText etPrice = dialogView.findViewById(R.id.etPrice);

            new AlertDialog.Builder(this)
                    .setTitle("Add Product")
                    .setView(dialogView)
                    .setPositiveButton("Save", (dialog, which) -> {
                        String title = etTitle.getText().toString().trim();
                        String date = etDate.getText().toString().trim();
                        String priceStr = etPrice.getText().toString().trim();

                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(priceStr)) {
                            int price = Integer.parseInt(priceStr);
                            try (ProductDao productDao = new ProductDao(this)) {
                                productDao.insert(title, date, price);
                            }
                            Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void setupTabLayoutWithViewPager() {
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Scheduled");
                    tab.setIcon(R.drawable.schedule_icon);
                    BadgeDrawable badge = tab.getOrCreateBadge();
                    badge.setNumber(badgeCount);
                    badge.setMaxCharacterCount(2);
                    badge.setVisible(isBadgeVisible);
                    break;
                case 1:
                    tab.setText("Delivered");
                    tab.setIcon(R.drawable.delivered_icon);
                    break;
                default:
                    tab.setText("New Orders");
                    tab.setIcon(R.drawable.new_orders_icon);
            }
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                TabLayout.Tab selectedTab = tabLayout.getTabAt(position);
                if (selectedTab != null) {
                    BadgeDrawable badge = selectedTab.getBadge();
                    if (badge != null) {
                        badge.setVisible(isBadgeVisible);
                        isBadgeVisible = !isBadgeVisible;
                    }
                }
            }
        });
    }
}
