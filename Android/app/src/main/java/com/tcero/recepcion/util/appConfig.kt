package com.tcero.recepcion.util

import java.net.URI

class appConfig {

    companion object {
        fun uri() = URI("http",null, "192.168.1.82", 80,null,null, null)
    }
}