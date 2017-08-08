package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.PostModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    public interface OnItemClickListener {
        void onPostItemClicked(PostModel postModel);
    }

    private List<PostModel> postsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    PostsAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.postsCollection != null) ? this.postsCollection.size() : 0;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_user, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int position) {
        final PostModel postModel = this.postsCollection.get(position);
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

    public void setPostsCollection(Collection<PostModel> postsCollection) {
        this.validatePostsCollection(postsCollection);
        this.postsCollection = (List<PostModel>) postsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validatePostsCollection(Collection<PostModel> postsCollection) {
        if (postsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;

        PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

