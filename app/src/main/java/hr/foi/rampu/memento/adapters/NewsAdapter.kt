package hr.foi.rampu.memento.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.foi.rampu.memento.NewsActivity
import hr.foi.rampu.memento.R
import hr.foi.rampu.memento.ws.WsNews
import hr.foi.rampu.memento.ws.WsNewsResult

class NewsAdapter(private val newsList: List<WsNewsResult>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private var title: TextView
        private var contentText: TextView
        private var dateTime: TextView
        private var imageView: ImageView

        init {
            title = view.findViewById(R.id.tv_news_list_item_title)
            contentText = view.findViewById(R.id.tv_news_list_item_text)
            dateTime = view.findViewById(R.id.tv_news_list_item_date)
            imageView = view.findViewById(R.id.iv_news_list_item_image)
        }

        fun bind(newsItem: WsNewsResult) {
            title.text = newsItem.title
            contentText.text = newsItem.text
            dateTime.text = newsItem.date
            Picasso.get().load(WsNews.BASE_URL + newsItem.imagePath).into(imageView)

            view.setOnLongClickListener {
                val intent = Intent(view.context, NewsActivity::class.java).apply {
                    putExtra("news_name", title.text)
                }
                ContextCompat.startActivity(view.context, intent, null)

                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val newsView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(newsView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}
