package jp.bizen.android.vrchat.oscmessenger.extension

val String.isValidIPAddress: Boolean
    get() {
        val octets = split(".")
        if (octets.size != 4) return false
        for (octet in octets) {
            if (octet.toIntOrNull() == null) return false
            if (octet.toInt() !in 0..255) return false
        }
        return true
    }
