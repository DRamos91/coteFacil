package com.daniel.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.daniel.model.Planeta;

public class Planetas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Planetas() {

	}

	public Planetas(EntityManager manager) {
		this.manager = manager;
	}

	public Planeta porId(Long id) {
		return manager.find(Planeta.class, id);
	}

	public List<Planeta> pesquisar(String nome) {
		String jpql = "from Planeta where nomePlaneta like :nomePlaneta";
		
		TypedQuery<Planeta> query = manager.createQuery(jpql, Planeta.class);
		
		query.setParameter("nomePlaneta", nome + "%");
		
		return query.getResultList();
	}

	public Planeta guardar(Planeta planeta) {
		return manager.merge(planeta);
	}

	public void remover(Planeta planeta) {
		planeta = porId(planeta.getId());
		manager.remove(planeta);
	}
}
