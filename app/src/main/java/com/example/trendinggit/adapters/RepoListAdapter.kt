package com.example.trendinggit.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trendinggit.R
import com.example.trendinggit.RepoDetail
import com.example.trendinggit.databinding.LayoutRepoItemBinding
import com.example.trendinggit.models.GitResponse
import com.example.trendinggit.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_repo_item.view.*

class RepoListAdapter(var context: Context, var listRepo : List<Item>) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>(){
    class ViewHolder(itemView : LayoutRepoItemBinding): RecyclerView.ViewHolder(itemView.root){
        var layoutRepoItemBinding : LayoutRepoItemBinding = itemView

        private val avatarImage = itemView.itemAvatar
        fun setupAvatar(item : Item){
            Picasso.get().load(item.avatar).into(avatarImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRepoItemBinding : LayoutRepoItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_repo_item,parent,false)
        val viewHolder : ViewHolder = ViewHolder(layoutRepoItemBinding)
        return viewHolder

    }

    override fun getItemCount(): Int {
        return listRepo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : Item = listRepo[position]
        holder.layoutRepoItemBinding.item = item;
        holder.setupAvatar(item);

        holder.itemView.setOnClickListener {
            val intent = Intent(context,RepoDetail::class.java)
            intent.putExtra("HTML_URL",item.url);
            context.startActivity(intent);
        }

    }

}

