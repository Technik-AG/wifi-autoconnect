# WiFi Autoconnect
Ein Service, um sich automatisch im Schul-WLAN anzumelden

## Funktionsweise
Der Service nutzt NetworkManager, um zu erkennen, ob das Gerät bereits angemeldet ist oder nicht.
Er sendet dann die Anmeldedaten automatisch ab, sobald eine Anmeldung erforderlich ist.

## Vorraussetzungen
Der Service benötigt NetworkManager und DBus und funktioniert somit nur auf Linux-basierten Betriebssystemen.