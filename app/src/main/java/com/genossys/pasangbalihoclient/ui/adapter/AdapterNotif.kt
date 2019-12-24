package com.genossys.pasangbalihoclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.genossys.pasangbalihoclient.data.db.entity.Notifikasi
import com.genossys.pasangbalihoclient.utils.tglSystemToView
import com.genossys.pasangbalihoclient.R
import kotlinx.android.synthetic.main.temp_notif.view.*

class AdapterNotif : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Notifikasi> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.temp_notif, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is NewsViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun sumitList(notifList: List<Notifikasi>) {
        items = notifList
    }

    class NewsViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val newsImage: ImageView = itemView.image_notif
        private val newsTittle: TextView = itemView.tittle_notif
        private val newsDeskripsi: TextView = itemView.body_notif
        private val newsCreatedAt: TextView = itemView.tgl_notif

        fun bind(notif: Notifikasi) {

            newsTittle.text = notif.judul
            newsDeskripsi.text = notif.isi
            newsCreatedAt.text = tglSystemToView(notif.createdAt!!)

        }
    }

}
