package com.klaxon.tris

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

class MenuActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.menu)

    findViewById(R.id.exit).setOnClickListener(new OnClickListener{
      def onClick(p1: View) = finish()
    })
  }
}
