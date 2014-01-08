package com.klaxon.tris.classical

import android.content.{DialogInterface, Context}
import android.app.AlertDialog
import com.klaxon.tris.R
import android.content.DialogInterface.OnCancelListener

/**
 * <p>date 12/30/13 </p>
 * @author klaxon
 */
final class PauseDialog(c: Context, listener: PauseDialogListener) {

  lazy val dialog = createDialog()

  private def createDialog() = {
    val dialogBuilder = new AlertDialog.Builder(c)
    dialogBuilder.setTitle(R.string.pause)

    dialogBuilder.setItems(R.array.pause_menu_items, new DialogInterface.OnClickListener {
      def onClick(dialog: DialogInterface, which: Int): Unit = which match {
        case 0 => listener.onResumePressed()
        case 1 => listener.onExitPressed()
        case _ => throw new UnsupportedOperationException
      }
    })

    dialogBuilder.setOnCancelListener(new OnCancelListener {
      def onCancel(dialog: DialogInterface): Unit = listener.onResumePressed()
    })

    dialogBuilder.create()
  }

  def show() = dialog.show()
  def isShowing = dialog.isShowing

}

trait PauseDialogListener {
  def onResumePressed()

  def onExitPressed()
}
