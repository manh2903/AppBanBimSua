package com.example.appbanbimsua.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.appbanbimsua.R;
import com.example.appbanbimsua.adapter.ImagePagerAdapter;
import com.example.appbanbimsua.enitities.respone.ProductDetailResponse;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductDetailResponse product;
    private ViewPager viewPager;
    private TextView productNameTextView;
    private TextView productThuonghieu;
    private TextView productPriceTextView;
    private TextView productQuantityTextView;
    private Button addToCartButton;
    private Button buyNowButton;
    private WebView productDescriptionWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        product = (ProductDetailResponse) getIntent().getSerializableExtra("productResponse");
        initUI();
        populateData();
        setUpListeners();
    }

    private void initUI() {
        viewPager = findViewById(R.id.viewpager);
        productNameTextView = findViewById(R.id.product_name);
        productThuonghieu = findViewById(R.id.product_thuonghieu);
        productPriceTextView = findViewById(R.id.product_price);
        productQuantityTextView = findViewById(R.id.tv_quantity);
        addToCartButton = findViewById(R.id.add_to_cart_button);
        buyNowButton = findViewById(R.id.btn_buy);
        productDescriptionWebView = findViewById(R.id.product_description_webview);
    }

    private void populateData() {
        if (product != null) {
            productNameTextView.setText(product.getProduct().getName());
            productThuonghieu.setText(product.getProduct().getBrand().getName());
            productPriceTextView.setText(String.format("%d VND", product.getProduct().getPrice()));
            productQuantityTextView.setText(String.valueOf(product.getProduct().getQuantity()));
            productDescriptionWebView.loadData(product.getProduct().getDescription(), "text/html", "UTF-8");

            List<String> imageUrls = product.getProduct().getProductImages();
            if (imageUrls != null && !imageUrls.isEmpty()) {
                ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageUrls);
                viewPager.setAdapter(adapter);
            }
        }
    }

    private void setUpListeners() {
        addToCartButton.setOnClickListener(v -> {
            // Logic thêm vào giỏ hàng
            Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        buyNowButton.setOnClickListener(v -> {
            // Logic mua ngay
            Toast.makeText(this, "Mua ngay", Toast.LENGTH_SHORT).show();
        });
    }
}
