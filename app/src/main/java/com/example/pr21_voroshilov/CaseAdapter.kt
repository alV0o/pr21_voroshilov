package com.example.pr21_voroshilov

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pr21_voroshilov.databinding.ItemCaseBinding

class CaseAdapter(val caseList: ArrayList<CaseClass>) : RecyclerView.Adapter<CaseAdapter.CaseViewHolder>() {

    class CaseViewHolder(val item: View):RecyclerView.ViewHolder(item){
        val binding = ItemCaseBinding.bind(item)
        fun bind(case: CaseClass) = with(binding){
            nameOfTask.text = case.name
            time.text = case.time
            date.text = case.date

            val items: List<String> = item.resources.getStringArray(R.array.categories).toList()

            when(case.category){
                items[0] -> category.setImageResource(R.drawable.home_outline)
                items[1] -> category.setImageResource(R.drawable.briefcase_outline)
                items[2] -> category.setImageResource(R.drawable.party_popper)
            }

            if (case.isImportant) importantTask.isVisible = true
            else importantTask.isVisible = false
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_case, parent, false)
        return CaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return caseList.size
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.bind(caseList[position])
    }

}
