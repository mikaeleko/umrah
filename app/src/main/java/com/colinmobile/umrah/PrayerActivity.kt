package com.colinmobile.umrah

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PrayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prayer)
        title = "Prayer Menu"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val elvTravelPray = findViewById<ExpandableListView>(R.id.elv_travel_pray)
        if (elvTravelPray != null) {

            val listData = getExpandableListData()
            val titleList = ArrayList(listData.keys)
            val adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            elvTravelPray!!.setAdapter(adapter)
            elvTravelPray!!.setOnGroupExpandListener { groupPosition ->

            }
            elvTravelPray!!.setOnGroupCollapseListener { groupPosition ->

            }
            elvTravelPray!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

                false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getExpandableListData():HashMap<String, List<String>>{
        val expandableListDetail = HashMap<String, List<String>>()
        val pray_title_one: MutableList<String> = ArrayList()
        pray_title_one.add(resources.getString(R.string.pray_detail_one))
        expandableListDetail[resources.getString(R.string.pray_title_one)] = pray_title_one

        val pray_title_two: MutableList<String> = ArrayList()
        pray_title_two.add(resources.getString(R.string.pray_detail_two))
        expandableListDetail[resources.getString(R.string.pray_title_two)] = pray_title_two

        val pray_title_three: MutableList<String> = ArrayList()
        pray_title_three.add(resources.getString(R.string.pray_detail_three))
        expandableListDetail[resources.getString(R.string.pray_title_three)] = pray_title_three

        val pray_title_four: MutableList<String> = ArrayList()
        pray_title_four.add(resources.getString(R.string.pray_detail_four))
        val pray_title_five: MutableList<String> = ArrayList()
        pray_title_five.add(resources.getString(R.string.pray_detail_five))
        val pray_title_six: MutableList<String> = ArrayList()
        pray_title_six.add(resources.getString(R.string.pray_detail_six))
        val pray_title_seven: MutableList<String> = ArrayList()
        pray_title_seven.add(resources.getString(R.string.pray_detail_seven))
        val pray_title_eight: MutableList<String> = ArrayList()
        pray_title_eight.add(resources.getString(R.string.pray_detail_eight))
        val pray_title_nine: MutableList<String> = ArrayList()
        pray_title_nine.add(resources.getString(R.string.pray_detail_nine))
        val pray_title_ten: MutableList<String> = ArrayList()
        pray_title_ten.add(resources.getString(R.string.pray_detail_ten))

        expandableListDetail[resources.getString(R.string.pray_title_ten)] = pray_title_ten
        expandableListDetail[resources.getString(R.string.pray_title_nine)] = pray_title_nine
        expandableListDetail[resources.getString(R.string.pray_title_eight)] = pray_title_eight
        expandableListDetail[resources.getString(R.string.pray_title_seven)] = pray_title_seven
        expandableListDetail[resources.getString(R.string.pray_title_six)] = pray_title_six
        expandableListDetail[resources.getString(R.string.pray_title_five)] = pray_title_five
        expandableListDetail[resources.getString(R.string.pray_title_four)] = pray_title_four

        return expandableListDetail
    }

    class CustomExpandableListAdapter internal constructor(
        private val context: Context,
        private val titleList: List<String>,
        private val dataList: HashMap<String, List<String>>
    ) : BaseExpandableListAdapter() {
        override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
            return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
        }
        override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
            return expandedListPosition.toLong()
        }
        override fun getChildView(
            listPosition: Int,
            expandedListPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val expandedListText = getChild(listPosition, expandedListPosition) as String
            if (convertView == null) {
                val layoutInflater =
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = layoutInflater.inflate(R.layout.list_item, null)
            }
            val expandedListTextView = convertView!!.findViewById<TextView>(R.id.listView)
            expandedListTextView.text = expandedListText
            convertView.setBackgroundColor(if (listPosition % 2 === 0) Color.parseColor("#ffffff") else Color.parseColor("#03c03c"))
            return convertView
        }
        override fun getChildrenCount(listPosition: Int): Int {
            return this.dataList[this.titleList[listPosition]]!!.size
        }
        override fun getGroup(listPosition: Int): Any {
            return this.titleList[listPosition]
        }
        override fun getGroupCount(): Int {
            return this.titleList.size
        }
        override fun getGroupId(listPosition: Int): Long {
            return listPosition.toLong()
        }
        override fun getGroupView(
            listPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup
        ): View {
            var convertView = convertView
            val listTitle = getGroup(listPosition) as String
            if (convertView == null) {
                val layoutInflater =
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = layoutInflater.inflate(R.layout.list_item, null)
            }
            val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listView)
//            listTitleTextView.setTypeface(null, Typeface.BOLD)
            listTitleTextView.text = listTitle
            convertView.setBackgroundColor(if (listPosition % 2 === 0) Color.parseColor("#ffffff") else Color.parseColor("#03c03c"))
            return convertView
        }
        override fun hasStableIds(): Boolean {
            return false
        }
        override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
            return true
        }
    }
}