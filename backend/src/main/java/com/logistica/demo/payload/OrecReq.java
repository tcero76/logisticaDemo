package com.logistica.demo.payload;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrecReq {

	public OrecReq(String guiadespacho, List<Object> oritems) {
		this.guiadespacho = guiadespacho;
		this.oritems = oritems.stream()
				.map(Oritem::new)
				.collect(Collectors.toList());
	}

	private String guiadespacho;

	private List<Oritem> oritems;

	public class Oritem {
		public Oritem(Object object) {
			this.pos = ((LinkedHashMap<String,Integer>)object).get("pos");
			this.idmaterial = ((LinkedHashMap<String,Integer>)object).get("idmaterial");
			Object cant = ((LinkedHashMap<String,Object>)object).get("cantidad");
			this.cantidad = cant instanceof Integer?Double.valueOf((Integer)cant):((Double)cant);
		}

		private Integer pos;

		private Integer idmaterial;

		private Double cantidad;

		public Integer getIdmaterial() {
			return idmaterial;
		}

		public void setIdmaterial(Integer idmaterial) {
			this.idmaterial = idmaterial;
		}

		public Double getCantidad() {
			return cantidad;
		}

		public void setCantidad(Double cantidad) {
			this.cantidad = cantidad;
		}

		public Integer getPos() {
			return pos;
		}

		public void setPos(Integer pos) {
			this.pos = pos;
		}
	}

	public String getGuiadespacho() {
		return guiadespacho;
	}

	public void setGuiadespacho(String guiadespacho) {
		this.guiadespacho = guiadespacho;
	}

	public List<Oritem> getOritems() {
		return oritems;
	}

	public void setOritems(List<Oritem> oritems) {
		this.oritems = oritems;
	}
}
