package ch.wyler.microspot.tasks

import android.os.AsyncTask
import android.view.View
import android.widget.TextView
import ch.wyler.microspot.R
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.impl.client.DefaultHttpClient
import java.io.ByteArrayOutputStream


/**
 * Task for checking if microspot.ch is online or not
 */
class HealthcheckTask constructor(private val rootView: View) : AsyncTask<String, Void, Boolean>() {

    private var uri = "https://www.microspot.ch"

    override fun onPreExecute() {
        val textView = rootView.findViewById<TextView>(R.id.healthcheck)
        displayCheckingStatus(textView)
    }

    override fun doInBackground(vararg params: String): Boolean {
        // wait five seconds..
        Thread.sleep(5000)

        return checkStatus()
    }

    override fun onPostExecute(result: Boolean) {
        val textView = rootView.findViewById<TextView>(R.id.healthcheck)

        // display online/offline status
        if (result) {
            displayOnlineStatus(textView)
            return
        }

        displayOfflineStatus(textView)
    }

    private fun checkStatus(): Boolean {
        val httpclient = DefaultHttpClient()

        // had to configure this otherwise it's not working..
        httpclient.getConnectionManager().getSchemeRegistry().register(
            Scheme("https", SSLSocketFactory.getSocketFactory(), 443)
        )

        try {
            // check website status
            val response = httpclient.execute(HttpGet(uri))
            val statusLine = response.statusLine

            if (HttpStatus.SC_OK.equals(statusLine.statusCode)) {
                val out = ByteArrayOutputStream()
                out.close()
                // return online
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // return offline
        return false
    }

    private fun displayCheckingStatus(textView: TextView) {
        textView.setText(R.string.checking)
    }

    private fun displayOfflineStatus(textView: TextView) {
        textView?.setText(R.string.offline)
        textView.setBackgroundResource(R.color.colorOffline)
    }

    private fun displayOnlineStatus(textView: TextView) {
        textView?.setText(R.string.online)
        textView.setBackgroundResource(R.color.colorOnline)
    }
}