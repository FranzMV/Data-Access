package com.fran.swapi.entidades;

import com.google.gson.annotations.Expose;

/**  @author Francisco David Manzanedo */

public class Films {
	
	private int codigo;
	@Expose
	private String title;
	@Expose
	private String episode_id;
	@Expose
	private String opening_crawl;
	@Expose
	private String director;
	@Expose
	private String producer;
	@Expose
	private String release_date;
	@Expose
	private String created;
	@Expose
	private String edited;
	
	
	public Films () { }
	
	
	public Films(String title, String episode_id, String opening_crawl, String director, String producer,
			String release_date, String created, String edited) {
		this.title = title;
		this.episode_id = episode_id;
		this.opening_crawl = opening_crawl;
		this.director = director;
		this.producer = producer;
		this.release_date = release_date;
		this.created = created;
		this.edited = edited;
	}
	
	
	public Films(int codigo, String title, String episode_id, String opening_crawl, String director, String producer,
			String release_date, String created, String edited) {
		this.codigo = codigo;
		this.title = title;
		this.episode_id = episode_id;
		this.opening_crawl = opening_crawl;
		this.director = director;
		this.producer = producer;
		this.release_date = release_date;
		this.created = created;
		this.edited = edited;
	}
	
	
	public int getCodigo() { return codigo; }
	
	public void setCodigo(int code) { this.codigo = code; }
	
	public String getTitle() { return title; }
	
	public void setTitle(String title) { this.title = title; }
	
	public String getEpisode_id() { return episode_id; }
	
	public void setEpisode_id(String episode_id) { this.episode_id = episode_id; }
	
	public String getOpening_crawl() { return opening_crawl; }
	
	public void setOpening_crawl(String opening_crawl) { this.opening_crawl = opening_crawl; }
	
	public String getDirector() { return director; }
	
	public void setDirector(String director) { this.director = director; }
	
	public String getProducer() { return producer; }
	
	public void setProducer(String producer) { this.producer = producer; }
	
	public String getRelease_date() { return release_date; }
	
	public void setRelease_date(String release_date) { this.release_date = release_date; }
	
	public String getCreated() { return created; }
	
	public void setCreated(String created) { this.created = created; }
	
	public String getEdited() { return edited; }
	
	public void setEdited(String edited) { this.edited = edited; }


	@Override
	public String toString() {
		return "Films [codigo: " + codigo + ", title: " + title + ", episode_id: " + episode_id + 
				", opening_crawl: "+ opening_crawl+"\n" + ", director: " + director + ", producer: " + producer +
				", release_date: " + release_date + ", created: " + created + ", edited: " + edited +"]\n";
	}
}
