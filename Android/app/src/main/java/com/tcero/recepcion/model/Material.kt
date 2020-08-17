package com.tcero.recepcion.model

class Material {

    constructor(idmaterial: Int?, nombre: String?, precio: Double?) {
        this.idmaterial = idmaterial
        this.nombre = nombre
        this.precio = precio
    }

    var idmaterial:Int?=null

    var nombre:String?=null

    var precio:Double?=null

    override fun toString(): String {
        return this.nombre!!
    }
}