package com.daniel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.daniel.model.Planeta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@ApplicationScoped
public class PlanetDao {

    private List<Planeta> planets = new ArrayList<>();
    private Long nextId = 1L;

    public void add(Planeta planet) {
        planet.setId(nextId++);
        planets.add(planet);
    }

    public List<Planeta> list() {
        return planets;
    }

    public Planeta findById(Long id) {
        for (Planeta planet : planets) {
            if (planet.getId().equals(id)) {
                return planet;
            }
        }
        return null;
    }

    public List<Planeta> findByName(String name) {
        List<Planeta> result = new ArrayList<>();
        for (Planeta planet : planets) {
            if (planet.getName().contains(name)) {
                result.add(planet);
            }
        }
        return result;
    }

    public void remove(Planeta planet) {
        planets.remove(planet);
    }
    
    public int findNumberOfFilms(Planeta planet) throws Exception {
        HttpResponse<String> response = Unirest.get("https://swapi.dev/api/planets/1/")
                .queryString("search", planet.getName())
                .asString();
        JSONObject json = new JSONObject(response.getBody());
        JSONArray results = json.getJSONArray("results");
        if (results.length() > 0) {
            JSONObject planetJson = results.getJSONObject(0);
            return planetJson.getJSONArray("films").length();
        }
        return 0;
    }
    
    public void init() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://swapi.dev/api/planets/");
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            try {
                String json = response.readEntity(String.class);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readValue(json, JsonNode.class);
                JsonNode results = node.get("results");
                List<Planeta> planetas = objectMapper.readValue(results.traverse(),
                        new TypeReference<List<Planeta>>() {});

                for (Planeta p : planetas) {
                    Planeta novoPlaneta = new Planeta();
                    novoPlaneta.setName(p.getName());
                    novoPlaneta.setClimate(p.getClimate());
                    novoPlaneta.setTerrain(p.getTerrain());
                    novoPlaneta.setNumberOfFilms(p.getNumberOfFilms());
                    this.planets.add(novoPlaneta);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.close();
    }
}
