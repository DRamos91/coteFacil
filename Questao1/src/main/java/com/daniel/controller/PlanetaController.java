package com.daniel.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.daniel.model.Planeta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@ViewScoped
public class PlanetaController {
    private List<Planeta> planets = new ArrayList<>();

    public List<Planeta> getPlanets() {
        if (planets.isEmpty()) {
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
                        this.planets.add(p);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.close();
        }
        return planets;
    }
}