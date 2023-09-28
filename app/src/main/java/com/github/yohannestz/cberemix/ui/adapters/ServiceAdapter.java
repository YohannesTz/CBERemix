package com.github.yohannestz.cberemix.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yohannestz.cberemix.R;
import com.github.yohannestz.cberemix.data.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private final List<Service> services = new ArrayList<>();
    private OnClickListener onClickListener;

    public void setData(List<Service> services) {
        this.services.addAll(services);
        notifyItemInserted(services.size());
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = services.get(position);
        holder.serviceName.setText(service.getName());
        holder.serviceImage.setImageResource(service.getImageRes());
        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, service, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Service model, View view);
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        private final ImageView serviceImage;
        private final TextView serviceName;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.service_image);
            serviceName = itemView.findViewById(R.id.service_name);
        }
    }
}