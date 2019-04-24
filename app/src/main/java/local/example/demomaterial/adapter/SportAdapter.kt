/**
 *
 * Copyright 2019 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.sportnews.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import local.example.sportnews.DetailActivity
import local.example.sportnews.R
import local.example.sportnews.model.Sport

import java.util.ArrayList

internal class SportAdapter internal constructor(
    private val context: Context,
    private val sportData: ArrayList<Sport>
) :
    RecyclerView.Adapter<SportAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val currentSport = sportData[position]

        holder.bindTo(currentSport)
    }

    override fun getItemCount(): Int {
        return sportData.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val titleText: TextView = itemView.findViewById(R.id.title)
        private val infoText: TextView = itemView.findViewById(R.id.sub_title)
        private val sportImage: ImageView = itemView.findViewById(R.id.sport_image)

        init {

            itemView.setOnClickListener(this)
        }

        fun bindTo(currentSport: Sport) {
            titleText.text = currentSport.title
            infoText.text = currentSport.info

            Glide.with(context).load(
                currentSport.imageResource
            ).into(sportImage)
        }

        override fun onClick(view: View) {
            val currentSport = sportData[adapterPosition]
            val detailIntent = Intent(context, DetailActivity::class.java)
            detailIntent.putExtra("title", currentSport.title)
            detailIntent.putExtra(
                "image_resource",
                currentSport.imageResource
            )
            context.startActivity(detailIntent)
        }
    }
}
