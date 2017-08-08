package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.OnishiModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OnishisAdapter extends RecyclerView.Adapter<OnishisAdapter.OnishiViewHolder> {

    public interface OnItemClickListener {
        void onOnishiItemClicked(OnishiModel onishiModel);
    }

    private List<OnishiModel> onishisCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    OnishisAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onishisCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.onishisCollection != null) ? this.onishisCollection.size() : 0;
    }

    @Override
    public OnishiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_user, parent, false);
        return new OnishiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OnishiViewHolder holder, final int position) {
        final OnishiModel onishiModel = this.onishisCollection.get(position);
        holder.textViewTitle.setText("aaaa");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (PostsAdapter.this.onItemClickListener != null) {
//                    PostsAdapter.this.onItemClickListener.onPostItemClicked(postModel);
//                }
//            }
//        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnishisCollection(Collection<OnishiModel> onishisCollection) {
        this.validateOnishisCollection(onishisCollection);
        this.onishisCollection = (List<OnishiModel>) onishisCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateOnishisCollection(Collection<OnishiModel> onishisCollection) {
        if (onishisCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class OnishiViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;

        OnishiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

