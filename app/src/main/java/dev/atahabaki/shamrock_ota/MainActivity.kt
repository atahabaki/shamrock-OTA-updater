package dev.atahabaki.shamrock_ota

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.atahabaki.shamrock_ota.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _logMainCategory: String
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _logMainCategory = "${getString(R.string.app_name_package)}.mainActivity"

        binding.gplayLaunchBtn.setOnClickListener {
            val isInstalledADM = checkADMInstalled()
            if (!isInstalledADM) {
                Log.d(_logMainCategory, "ADM is not installed.")
                openADMinPlayStore()
            }
            else {
                Log.d(_logMainCategory, "ADM is installed.")
                launchADMBrowserWithUrl(Uri.parse("https://github.com/atahabaki/"))
            }
        }
    }

    private fun launchADMBrowserWithUrl(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = uri
            setPackage(getString(R.string.adm_package_name))
        }
        startActivity(intent)
    }

    private fun checkADMInstalled(): Boolean {
        return isAppInstalled(getString(R.string.adm_package_name), packageManager)
    }

    private fun isAppInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openADMinPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("${getString(R.string.play_store_package_details_uri)}${getString(R.string.adm_package_name)}")
            setPackage(getString(R.string.open_play_store_package_name))
        }
        startActivity(intent)
    }
}