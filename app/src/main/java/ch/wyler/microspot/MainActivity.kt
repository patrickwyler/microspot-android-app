package ch.wyler.microspot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val plaintext = "plain/text"
    private val mailaddress = "support@microspot.ch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // on support bubble click open mail client with prefilled support email address
        fab.setOnClickListener { sendSupportMail() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        return when (item.itemId) {
            R.id.action_home -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                true
            }
            R.id.action_search -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.searchFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendSupportMail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = plaintext
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mailaddress))
        startActivity(Intent.createChooser(intent, ""))
    }
}
