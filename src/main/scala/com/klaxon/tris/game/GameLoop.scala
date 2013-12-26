package com.klaxon.tris.game

import android.os.Handler
import android.util.Log

/**
 * Responsible for running main loop of a game. You should provide fps for loop.
 * @param fps frames per second
 */
class GameLoop(fps: Int) {

  private val MILLIS_IN_SECOND = 1000

  @volatile
  var running = false
  private val handler = new Handler()
  private val delayInMillis = MILLIS_IN_SECOND / fps

  def loop(action: => Unit) = {
    require(!running, "Loop is already running")
    running = true

    handler.post(new Runnable {
      def run(): Unit = if (running) {
        action
        handler.postDelayed(this, delayInMillis)
      }
    })
  }

  def stop() = running = false

}
