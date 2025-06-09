import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.krackshackbanking.R

class CategoryExpenseAdapter(private val expenseList: List<CategoryExpense>) :
    RecyclerView.Adapter<CategoryExpenseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        val amountText: TextView = itemView.findViewById(R.id.amountText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]
        holder.categoryText.text = item.category
        holder.amountText.text = "R ${item.totalAmount}"
    }

    override fun getItemCount() = expenseList.size
}
