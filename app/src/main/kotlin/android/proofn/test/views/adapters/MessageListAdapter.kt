package android.proofn.test.views.adapters

import android.content.Context
import android.proofn.test.R
import android.proofn.test.entities.SenderModel
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import android.proofn.test.entities.MessageModel
import android.util.SparseBooleanArray

import java.util.ArrayList

class MessageListAdapter(private val ctx: Context, items: List<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = ArrayList<MessageModel>()
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: MessageModel, position: Int)

        fun onItemLongClick(view: View, obj: MessageModel, pos: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mItemClickListener
    }

    init {
        this.items = items as ArrayList<MessageModel>
    }

    inner class OriginalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView = v.findViewById(R.id.image) as ImageView
        var subject: TextView = v.findViewById(R.id.txtSubject) as TextView
        var time: TextView = v.findViewById(R.id.txtTime) as TextView
        var preview: TextView = v.findViewById(R.id.txtPreview) as TextView
        var lytParent: View = v.findViewById(R.id.lyt_parent) as View

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.include_drawer_content, parent, false)
        vh = OriginalViewHolder(v)
        return vh
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OriginalViewHolder) {

            val p = items[position]
            holder.subject.text = p.subject
            holder.preview.text = p.contentPreview
            holder.lytParent.setOnClickListener { view ->
                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(view, items[position], position)
                }
            }
            holder.lytParent.setOnLongClickListener(View.OnLongClickListener { v ->
                if (mOnItemClickListener == null) return@OnLongClickListener false
                mOnItemClickListener!!.onItemLongClick(v, p, position)
                true
            })

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}