package com.colinmobile.umrah

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.Collections.sort
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PrayerActivity : AppCompatActivity() {

    var expandableListDetail = HashMap<String, List<String>>()
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

    fun getExpandableListData(): SortedMap<String, List<String>> {
//        val expandableListDetail = HashMap<String, List<String>>()

        val pray_title_one: MutableList<String> = ArrayList()
        pray_title_one.add(resources.getString(R.string.pray_arab_one))
        pray_title_one.add(resources.getString(R.string.pray_detail_one))
        expandableListDetail[resources.getString(R.string.pray_title_one)] = pray_title_one

        val pray_title_two: MutableList<String> = ArrayList()
        pray_title_two.add(resources.getString(R.string.pray_arab_two))
        pray_title_two.add(resources.getString(R.string.pray_detail_two))
        expandableListDetail[resources.getString(R.string.pray_title_two)] = pray_title_two

        val pray_title_three: MutableList<String> = ArrayList()
        pray_title_three.add(resources.getString(R.string.pray_arab_three))
        pray_title_three.add(resources.getString(R.string.pray_detail_three))
        expandableListDetail[resources.getString(R.string.pray_title_three)] = pray_title_three

        val pray_title_five: MutableList<String> = ArrayList()
        pray_title_five.add(resources.getString(R.string.pray_arab_five))
        pray_title_five.add(resources.getString(R.string.pray_detail_five))

        val pray_title_six: MutableList<String> = ArrayList()
        pray_title_six.add(resources.getString(R.string.pray_arab_six))
        pray_title_six.add(resources.getString(R.string.pray_detail_six))

        val pray_title_seven: MutableList<String> = ArrayList()
        pray_title_seven.add(resources.getString(R.string.pray_arab_seven))
        pray_title_seven.add(resources.getString(R.string.pray_detail_seven))

        val pray_title_eight: MutableList<String> = ArrayList()
        pray_title_eight.add(resources.getString(R.string.pray_arab_eight))
        pray_title_eight.add(resources.getString(R.string.pray_detail_eight))

        val pray_title_nine: MutableList<String> = ArrayList()
        pray_title_nine.add(resources.getString(R.string.pray_arab_nine))
        pray_title_nine.add(resources.getString(R.string.pray_detail_nine))

        val pray_title_ten: MutableList<String> = ArrayList()
        pray_title_ten.add(resources.getString(R.string.pray_detail_ten))
        pray_title_ten.add(resources.getString(R.string.pray_arab_ten))

        val pray_title_eleven: MutableList<String> = ArrayList()
        pray_title_eleven.add(resources.getString(R.string.pray_arab_eleven))
        pray_title_eleven.add(resources.getString(R.string.pray_detail_eleven))

        expandableListDetail[resources.getString(R.string.pray_title_eleven)] = pray_title_eleven
        expandableListDetail[resources.getString(R.string.pray_title_ten)] = pray_title_ten
        expandableListDetail[resources.getString(R.string.pray_title_nine)] = pray_title_nine
        expandableListDetail[resources.getString(R.string.pray_title_eight)] = pray_title_eight
        expandableListDetail[resources.getString(R.string.pray_title_seven)] = pray_title_seven
        expandableListDetail[resources.getString(R.string.pray_title_six)] = pray_title_six
        expandableListDetail[resources.getString(R.string.pray_title_five)] = pray_title_five

        setPrayer(resources.getString(R.string.pray_title_twelve),
            resources.getString(R.string.pray_arab_twelve),
            resources.getString(R.string.pray_detail_twelve)
        )
        setPrayer(resources.getString(R.string.pray_title_thirteen),
            resources.getString(R.string.pray_arab_thirteen),
            resources.getString(R.string.pray_detail_thirteen)
        )
        setPrayer(resources.getString(R.string.pray_title_fourteen),
            resources.getString(R.string.pray_arab_fourteen),
            resources.getString(R.string.pray_detail_fourteen))
        setPrayer(resources.getString(R.string.pray_title_fifteen),
            resources.getString(R.string.pray_arab_fifteen),
            resources.getString(R.string.pray_detail_fifteen))
        setPrayer(resources.getString(R.string.pray_title_sixteen),
            resources.getString(R.string.pray_arab_sixteen),
            resources.getString(R.string.pray_detail_sixteen))
        setPrayer(resources.getString(R.string.pray_title_seventeen),
            resources.getString(R.string.pray_arab_seventeen),
            resources.getString(R.string.pray_detail_seventeen))
        setPrayer(resources.getString(R.string.pray_title_eightteen),
            resources.getString(R.string.pray_arab_eightteen),
            resources.getString(R.string.pray_detail_eightteen))

        setPrayer(resources.getString(R.string.pray_title_nineteen),
            resources.getString(R.string.pray_arab_nineteen),
            resources.getString(R.string.pray_detail_nineteen))
        setPrayer(resources.getString(R.string.pray_title_twenty),
            resources.getString(R.string.pray_arab_twenty),
            resources.getString(R.string.pray_detail_twenty))

        setPrayer(resources.getString(R.string.pray_title_twenty_one),
            resources.getString(R.string.pray_arab_twenty_one),
            resources.getString(R.string.pray_detail_twenty_one))
        setPrayer(resources.getString(R.string.pray_title_twenty_two),
            resources.getString(R.string.pray_arab_twenty_two),
            resources.getString(R.string.pray_detail_twenty_two))
        setPrayer(resources.getString(R.string.pray_title_twenty_three),
            resources.getString(R.string.pray_arab_twenty_three),
            resources.getString(R.string.pray_detail_twenty_three))
        setPrayer(resources.getString(R.string.pray_title_twenty_four),
            resources.getString(R.string.pray_arab_twenty_four),
            resources.getString(R.string.pray_detail_twenty_four))
        setPrayer(resources.getString(R.string.pray_title_twenty_five),
            resources.getString(R.string.pray_arab_twenty_five),
            resources.getString(R.string.pray_detail_twenty_five))
        setPrayer(resources.getString(R.string.pray_title_twenty_six),
            resources.getString(R.string.pray_arab_twenty_six),
            resources.getString(R.string.pray_detail_twenty_six))
        setPrayer(resources.getString(R.string.pray_title_twenty_seven),
            resources.getString(R.string.pray_arab_twenty_seven),
            resources.getString(R.string.pray_detail_twenty_seven))
        setPrayer(resources.getString(R.string.pray_title_twenty_eight),
            resources.getString(R.string.pray_arab_twenty_eight),
            resources.getString(R.string.pray_detail_twenty_eight))
        setPrayer(resources.getString(R.string.pray_title_twenty_nine),
            resources.getString(R.string.pray_arab_twenty_nine),
            resources.getString(R.string.pray_detail_twenty_nine))
        setPrayer(resources.getString(R.string.pray_title_thirty),
            resources.getString(R.string.pray_arab_thirty),
            resources.getString(R.string.pray_detail_thirty)
        )
        setPrayer(resources.getString(R.string.pray_title_thirty_one),
            resources.getString(R.string.pray_arab_thirty_one),
            resources.getString(R.string.pray_detail_thirty_one)
        )
        setPrayer(resources.getString(R.string.pray_title_thirty_two),
            resources.getString(R.string.pray_arab_thirty_two),
            resources.getString(R.string.pray_detail_thirty_two))
        setPrayer(resources.getString(R.string.pray_title_thirty_three),
            resources.getString(R.string.pray_arab_thirty_three),
            resources.getString(R.string.pray_detail_thirty_three))


        val sortedAppsList = expandableListDetail.toSortedMap()

        return sortedAppsList
    }

    fun setPrayer(title:String, arabic:String, detail:String){

        val pray_title_eleven: MutableList<String> = ArrayList()
        pray_title_eleven.add(arabic)
        pray_title_eleven.add(detail)
        expandableListDetail[title] = pray_title_eleven
    }

    class CustomExpandableListAdapter internal constructor(
        private val context: Context,
        private val titleList: List<String>,
        private val dataList: SortedMap<String, List<String>>
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