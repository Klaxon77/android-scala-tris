package com.klaxon.tris.storage

import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import android.content.Context

/**
 * <p>date 1/9/14 </p>
 * @author klaxon
 */
class LeaderSQLiteHelper(c: Context) extends SQLiteOpenHelper(c, "leaders.db", null, 1) {

  def onCreate(db: SQLiteDatabase): Unit = db.execSQL(LeaderSQLiteHelper.CREATE_TABLE_QUERY)

  def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = {
    db.execSQL(LeaderSQLiteHelper.DROP_TABLE_QUERY)
    onCreate(db)
  }

}

object LeaderSQLiteHelper {
  val TABLE_NAME = "Leaders"
  val SCORE_KEY = "score"

  private val CREATE_TABLE_QUERY = String.format("CREATE TABLE %s (%s INTEGER UNIQUE)", TABLE_NAME, SCORE_KEY)
  private val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME
}



