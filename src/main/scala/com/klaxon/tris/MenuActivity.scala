package com.klaxon.tris

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.content.Intent
import com.klaxon.tris.classical.ClassicalActivity

class MenuActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.menu)

    findViewById(R.id.exit).setOnClickListener((v: View) => finish())
    findViewById(R.id.play).setOnClickListener((v: View) => startClassicalGame)
  }

  private def startClassicalGame = {
    val intent = new Intent(this, classOf[ClassicalActivity])
    startActivity(intent)
  }

  implicit def function2onClickListener(f: View => Unit):OnClickListener = new OnClickListener {
    def onClick(v: View) = f(v)
  }

}
