package jp.bizen.android.vrchat.oscmessenger.ui.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jp.bizen.android.vrchat.oscmessenger.R

class OscMessageAdapter(private val listener: Listener) : RecyclerView.Adapter<ViewHolder>() {

    interface Listener {
        fun onClick(operation: String, value: Any? = null)
    }

    private val data = listOf(
        OscMessage("/input/Vertical", listOf(1, 0, -1)),
        OscMessage("/input/Horizontal", listOf(1, 0, -1)),
        OscMessage("/input/LookHorizontal", listOf()),
        OscMessage("/input/UseAxisRight", listOf()),
        OscMessage("/input/GrabAxisRight", listOf()),
        OscMessage("/input/MoveHoldFB", listOf()),
        OscMessage("/input/SpinHoldCwCcw", listOf()),
        OscMessage("/input/SpinHoldUD", listOf()),
        OscMessage("/input/SpinHoldLR", listOf()),
        OscMessage("/input/MoveForward", listOf(0, 1)),
        OscMessage("/input/MoveBackward", listOf(0, 1)),
        OscMessage("/input/MoveLeft", listOf(0, 1)),
        OscMessage("/input/MoveRight", listOf(0, 1)),
        OscMessage("/input/LookLeft", listOf(0, 1)),
        OscMessage("/input/LookRight", listOf(0, 1)),
        OscMessage("/input/Jump", listOf()),
        OscMessage("/input/Run", listOf()),
        OscMessage("/input/ComfortLeft", listOf()),
        OscMessage("/input/ComfortRight", listOf()),
        OscMessage("/input/DropRight", listOf()),
        OscMessage("/input/UseRight", listOf()),
        OscMessage("/input/GrabRight", listOf()),
        OscMessage("/input/DropLeft", listOf()),
        OscMessage("/input/UseLeft", listOf()),
        OscMessage("/input/GrabLeft", listOf()),
        OscMessage("/input/PanicButton", listOf()),
        OscMessage("/input/QuickMenuToggleLeft", listOf(0, 1)),
        OscMessage("/input/QuickMenuToggleRight", listOf(0, 1)),
        OscMessage("/input/Voice", listOf(0, 1)),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ContentViewHolder(view = view, listener = object : ContentViewHolder.Listener {
            override fun onTap(message: String, value: Any?) {
                listener.onClick(message, value)
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ContentViewHolder -> {
                val message = data[position]
                holder.itemView.setOnClickListener {
                    listener.onClick(message.name)
                }
                holder.bind(message)
            }
        }
    }

    override fun getItemCount() = data.size

    class ContentViewHolder(view: View, private val listener: Listener) : ViewHolder(view) {
        interface Listener {
            fun onTap(message: String, value: Any?)
        }

        private val name: TextView by lazy {
            view.findViewById(R.id.name)
        }
        private val views: LinearLayout by lazy {
            view.findViewById(R.id.views)
        }

        fun bind(message: OscMessage) {
            name.text = message.name
            views.removeAllViews()
            itemView.setOnClickListener(null)
            if (message.inputs.isEmpty()) {
                itemView.setOnClickListener {
                    listener.onTap(message.name, null)
                }
            } else {
                message.inputs.forEach { value ->
                    val button = Button(itemView.context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        text = "$value"
                        setOnClickListener {
                            listener.onTap(message.name, value)
                        }
                    }
                    views.addView(button)
                }
            }
        }
    }
}