package com.vsca.vsnapvoicecollege.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Interfaces.RecipientCheckListener
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.SenderModel.RecipientSelected

class SelectedRecipientAdapter constructor(
    data: List<RecipientSelected>, context: Context,
    val checkListener: RecipientCheckListener
) :
    RecyclerView.Adapter<SelectedRecipientAdapter.MyViewHolder>() {
    var selectedList: List<RecipientSelected> = ArrayList()
    var context: Context
    var Position: Int = 0

    companion object {
        var checkClick: RecipientCheckListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.recipientlist_ui, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: RecipientSelected = selectedList.get(position)
        Position = holder.getAbsoluteAdapterPosition()

        checkClick = checkListener
        holder.lblDocumentName!!.setText(data.SelectedName)

        holder.chbox!!.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkClick?.add(data)
            } else {
                checkClick?.remove(data)
            }
        })
    }

    override fun getItemCount(): Int {
        return selectedList.size
    }

    inner class MyViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(
        (itemView)!!
    ) {
        @JvmField
        @BindView(R.id.lblRecipientData)
        var lblDocumentName: TextView? = null

        @JvmField
        @BindView(R.id.chbox)
        var chbox: CheckBox? = null

        init {
            ButterKnife.bind(this, (itemView)!!)
        }
    }

    init {
        selectedList = data
        this.context = context
    }
}