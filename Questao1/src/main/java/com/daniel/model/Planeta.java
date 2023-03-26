package com.daniel.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "planeta")
public class Planeta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private Long id;
    private String name;
    private String climate;
    private String terrain;
    private int numberOfFilms;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getTerrain() {
		return terrain;
	}
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	public int getNumberOfFilms() {
		return numberOfFilms;
	}
	public void setNumberOfFilms(int numberOfFilms) {
		this.numberOfFilms = numberOfFilms;
	}

	
}