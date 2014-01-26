package com.klaxon.tris.topscore

import android.app.ListActivity
import android.os.Bundle
import com.klaxon.tris.R
import android.widget.ArrayAdapter
import com.klaxon.tris.storage.ScoreDao

/**
 * <p>date 1/12/14 </p>
 * @author klaxon
 */
class TopScoreActivity extends ListActivity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.top_score_layout)

    val adapter = new ArrayAdapter[String](this, R.layout.top_score_item_layout, R.id.text, scoreArray)
    setListAdapter(adapter)
  }

  def scoreArray = {
    val scoreDao = new ScoreDao(this)
    scoreDao.scores.map(_.toString).toArray
  }

}
