package com.nmq.foodninjaver2.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.Adapter.CommentAdapter;
import com.nmq.foodninjaver2.Model.Comment;
import com.nmq.foodninjaver2.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_comment.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Lấy bình luận từ danh sách
        Comment comment = commentList.get(position);

        // Gán giá trị cho các view
        holder.userName.setText(comment.getUserName());
        holder.commentDate.setText(comment.getDate());
        holder.commentContent.setText(comment.getComment());

        holder.userAvatar.setImageResource(comment.getAvatarUrl());

        // Nếu có đánh giá (nếu cần) thì hiển thị hình ảnh đánh giá
        if (comment.getRating() > 0) {
            // Đây là ví dụ với một ảnh sao
            holder.rating.setImageResource(R.drawable.five); // Bạn có thể thay đổi theo số sao
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar, rating;
        TextView userName, commentDate, commentContent;

        public CommentViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.imgUserAvatar);
            userName = itemView.findViewById(R.id.txtUserName);
            commentDate = itemView.findViewById(R.id.txtCommentDate);
            commentContent = itemView.findViewById(R.id.txtCommentContent);
            rating = itemView.findViewById(R.id.imgRating);
        }
    }
}
