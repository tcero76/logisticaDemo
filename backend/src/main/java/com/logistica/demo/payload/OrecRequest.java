package com.logistica.demo.payload;

import javax.validation.constraints.NotNull;

import com.logistica.demo.model.Oritem;

import java.util.List;

public class OrecRequest {
	
	public OrecRequest( String guiadespacho, List<Oritem> oritems) {
		this.guiadespacho = guiadespacho;
		this.oritems = oritems;
	}

	@NotNull
	private String guiadespacho;

	@NotNull
	private List<Oritem> oritems;

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
