package com.daniel.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.daniel.model.Planeta;

@Named
@ViewScoped
public class PlanetasBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Planeta planeta;
	
	public void salvar() {
	
	}	
	
	public Planeta getPlaneta() {		
		return planeta;
	}
	
}
