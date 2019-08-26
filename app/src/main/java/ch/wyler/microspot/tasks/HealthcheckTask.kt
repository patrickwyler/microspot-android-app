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


class HealthcheckTask constructor(private val rootView: View) : AsyncTask<String, Void, Boolean>() {

    override fun onPreExecute() {
        val textView = rootView.findViewById<TextView>(R.id.healthcheck)
        textView.setText(R.string.checking)
    }

    override fun doInBackground(vararg params: String): Boolean {
        // wait five seconds..
        Thread.sleep(5000)

        return checkStatus()
    }

    override fun onPostExecute(result: Boolean) {
        val textView = rootView.findViewById<TextView>(R.id.healthcheck)

        if (result) {
            textView?.setText(R.string.online)
            textView.setBackgroundResource(R.color.colorOnline)
        } else {
            textView?.setText(R.string.offline)
            textView.setBackgroundResource(R.color.colorOffline)
        }

    }

    private fun checkStatus(): Boolean {
        val httpclient = DefaultHttpClient()

        httpclient.getConnectionManager().getSchemeRegistry().register(
            Scheme("https", SSLSocketFactory.getSocketFactory(), 443)
        )

        val response = httpclient.execute(HttpGet("https://www.microspot.ch"))
        val statusLine = response.statusLine

        return if (HttpStatus.SC_OK.equals(statusLine.statusCode)) {
            val out = ByteArrayOutputStream()
            out.close()
            true
        } else {
            response.entity.content.close()
            false
        }
    }
}