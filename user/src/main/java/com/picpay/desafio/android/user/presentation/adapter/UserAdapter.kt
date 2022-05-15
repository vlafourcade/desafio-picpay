package com.picpay.desafio.android.user.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.user.databinding.LayoutUserItemBinding
import com.picpay.desafio.android.user.domain.model.User
import javax.inject.Inject

internal abstract class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    abstract fun setDataSource(dataSource: List<User>)

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(user: User)
    }
}

internal class UserAdapterImpl @Inject constructor() : UserAdapter() {
    private var dataSource: List<User> = listOf()

    override fun setDataSource(dataSource: List<User>) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = LayoutUserItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount(): Int = dataSource.size

    class ViewHolder (private val binding: LayoutUserItemBinding)
        : UserAdapter.ViewHolder(binding.root) {
        override fun bind(user: User) {
            binding.user = user
        }
    }

}

