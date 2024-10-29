package com.example.navigation_smd_7a.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.navigation_smd_7a.Model.Product;
import com.example.navigation_smd_7a.Dao.ProductDao; // Update to ProductDao
import com.example.navigation_smd_7a.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final int resource;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tvProductTitle);
            holder.ivEdit = convertView.findViewById(R.id.ivEdit);
            holder.ivDelete = convertView.findViewById(R.id.ivDelete);
            convertView.setTag(holder); // Store the holder with the view
        } else {
            holder = (ViewHolder) convertView.getTag(); // Retrieve the holder
        }

        Product product = getItem(position);
        if (product != null) {
            holder.tvTitle.setText(product.getPrice() + " : " + product.getTitle());

            // Set click listener for the edit button
            holder.ivEdit.setOnClickListener(view -> {
                // Implement the edit functionality
                // You could show a dialog or navigate to an edit activity
                // Example: showEditDialog(product);
            });

            // Set click listener for the delete button
            holder.ivDelete.setOnClickListener(view -> {
                ProductDao productDao = new ProductDao(context);
                productDao.open();
                productDao.remove(product.getId());
                productDao.close();
                remove(product);
                notifyDataSetChanged();
            });
        }

        return convertView;
    }

    // ViewHolder pattern to optimize view lookup
    static class ViewHolder {
        TextView tvTitle;
        ImageView ivEdit;
        ImageView ivDelete;
    }
}
