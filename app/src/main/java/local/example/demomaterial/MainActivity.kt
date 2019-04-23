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

package local.example.demomaterial

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var sportData: ArrayList<Sport>? = null
    private var sportAdapter: SportAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)

        recyclerView!!.layoutManager = LinearLayoutManager(this)

        sportData = ArrayList()

        sportAdapter = SportAdapter(this, sportData!!)
        recyclerView!!.adapter = sportAdapter

        initializeData()

        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or
                    ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                Collections.swap(sportData, from, to)
                sportAdapter!!.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                sportData!!.removeAt(viewHolder.adapterPosition)
                sportAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })

        helper.attachToRecyclerView(recyclerView)
    }

    private fun initializeData() {
        val sportsList = resources
            .getStringArray(R.array.sports_titles)
        val sportsInfo = resources
            .getStringArray(R.array.sports_info)
        val sportsImageResources = resources
            .obtainTypedArray(R.array.sports_images)

        sportData!!.clear()

        for (i in sportsList.indices) {
            sportData!!.add(
                Sport(
                    sportsList[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)
                )
            )
        }

        sportsImageResources.recycle()

        sportAdapter!!.notifyDataSetChanged()
    }

    fun resetSports(view: View) {
        initializeData()
    }
}
