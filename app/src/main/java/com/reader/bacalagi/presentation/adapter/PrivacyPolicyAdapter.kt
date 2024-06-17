package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.databinding.ListPrivacyPolicyBinding

class PrivacyPolicyAdapter(private val myDataList: List<MyData>) : RecyclerView.Adapter<PrivacyPolicyAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ListPrivacyPolicyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyData) {
            binding.tvTitle.text = data.title
            binding.tvDesc.text = data.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListPrivacyPolicyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myDataList[position])
    }

    override fun getItemCount(): Int {
        return myDataList.size
    }
    data class MyData(val title: String, val description: String)
}
