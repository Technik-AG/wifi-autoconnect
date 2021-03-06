package de.goetheschule_essen.technik.wifi_autoconnect

import de.goetheschule_essen.technik.wifi_autoconnect.utils.*
import org.freedesktop.DBus
import org.freedesktop.dbus.UInt32

/**
 * Observes the network connectivity state as reported by NetworkManager and notifies when a portal is detected
 */
class ConnectivityObserver(private val networkManager: NetworkManagerHelper, private val portalDetected: () -> Unit) {
    private val handler = ::handlePropertyChange

    /**
     * Gets or sets the observing state
     */
    var observing: Boolean = false
        set(value) {
            if(value == field) return
            if(value) {
                networkManager.addPropertyChangedHandler(handler)
            } else {
                networkManager.removePropertyChangedHandler(handler)
            }

            field = value
        }

    private fun handlePropertyChange(signal: DBus.Properties.PropertiesChanged) {
        for ((prop, valueVariant) in signal.changedProperties) { // for each changed property
            val value = valueVariant.value
            when (prop) {
                ConnectivityProperty -> if (value is UInt32) handleConnectivityChange(NMConnectivityState.fromUInt32(value))
                ConnectivityCheckEnabledProperty -> if (value is Boolean && !value) System.err.print("Connectivity check disabled")
                ConnectivityCheckAvailableProperty -> if (value is Boolean && !value) System.err.print("Connectivity check unavailable")
            }
        }
    }

    private fun handleConnectivityChange(connectivity: NMConnectivityState) {
        println("Connectivity changed: $connectivity")
        if(connectivity == NMConnectivityState.PORTAL) portalDetected()
    }
}