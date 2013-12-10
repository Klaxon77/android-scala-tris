package com.klaxon.tris.game

import android.os.Handler

/**
 * <p>User: v.pronyshyn<br/>
 * Date: 12/7/13</p>
 */
class GameLoop(fps: Int) {

  private val MILLIS_IN_SECOND = 1000

  @volatile
  private var running = true
  private val handler = new Handler()
  private val delayInMillis = MILLIS_IN_SECOND / fps

  def loop(action: => Unit) = {
    require(running)
    handler.post(new Runnable {
      def run(): Unit = {
        handler.postDelayed(this, delayInMillis)
        if (running) action
      }
    })
  }

  def stop() = {
    running = false
  }

}
