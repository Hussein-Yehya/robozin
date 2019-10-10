package com.robozinho.robozinho.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("statistics")
	private Statistic statistics;

	@JsonProperty("location")
	private Location location;

	@JsonProperty("address")
	private Address address;

	@JsonProperty("urlApontador")
	private String urlApontador;

	@JsonProperty("phones")
	private List<String> phones = new ArrayList<String>();;

	@JsonProperty("categories")
	private List<Category> categories = new ArrayList<Category>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Statistic getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistic statistics) {
		this.statistics = statistics;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUrlApontador() {
		return urlApontador;
	}

	public void setUrlApontador(String urlApontador) {
		this.urlApontador = urlApontador;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Map<String, String> placeMap() {
		Map<String, String> placesMaps = new LinkedHashMap<String, String>();

		placesMaps.put("ID", this.id);
		placesMaps.put("Nome", this.name);
		placesMaps.put("Nota", this.statistics.getRating());
		placesMaps.put("Positivo", this.statistics.getThumbsUp());
		placesMaps.put("Negativo", this.statistics.getThumbsDown());
		placesMaps.put("Telefone", this.phones.isEmpty() ? "Sem Telefone" : this.phones.get(0));
		placesMaps.put("Estado", this.address.getState());
		placesMaps.put("Cidade", this.address.getCity());
		placesMaps.put("Bairro", this.address.getDistrict());
		placesMaps.put("Rua", this.address.getStreet());
		placesMaps.put("Numero", this.address.getNumber());
		placesMaps.put("Latitude", this.location.getLat());
		placesMaps.put("Longitude", this.location.getLng());

		for (Category category : this.categories) {
			if (Objects.nonNull(category.getSubCategory())) {
				placesMaps.put("Categoria", category.getSubCategory().getName());
			}else {
				placesMaps.put("Categoria", category.getName());
			}
		}

		return placesMaps;

	}

}
