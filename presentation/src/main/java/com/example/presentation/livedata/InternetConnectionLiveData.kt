package com.example.presentation.livedata

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.domain.qualifiers.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.util.HashSet
import javax.inject.Inject
import javax.net.SocketFactory

class InternetConnectionLiveData @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest
) : LiveData<Boolean>() {

    private val availableNetworks: MutableSet<Network> = HashSet()

    init {
        postValue(availableNetworks.size > 0)
    }

    private companion object {
        fun execute(socketFactory: SocketFactory) = try {
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null")
            // 8.8.8.8 - Google server
            // 53 - TCP
            socket.connect(InetSocketAddress("8.8.8.8", 53), 3000)
            socket.close()
            true
        } catch (e: IOException) {
            Log.d("DEBUG_TAG", "No internet connection. ${e.message}")
            false
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)

            if(hasInternetCapability == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = execute(network.socketFactory)
                    if(hasInternet) {
                        withContext(Dispatchers.Main) {
                            availableNetworks.add(network)
                            postValue(availableNetworks.size > 0)
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            availableNetworks.remove(network)
            postValue(availableNetworks.size > 0)
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}