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
        if (!running) return

        val startTime = System.currentTimeMillis()
        action

        val delayTime = delayInMillis - (System.currentTimeMillis() - startTime)
        handler.postDelayed(this, delayTime)
      }
    }

    )
  }

  def stop() = {
    running = false
  }

}
