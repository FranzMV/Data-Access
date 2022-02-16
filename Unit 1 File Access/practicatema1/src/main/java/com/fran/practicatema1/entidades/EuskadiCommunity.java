package com.fran.practicatema1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EuskadiCommunity {
	
	private LocalDate date;
	private String name;
	private long todayDeads;
	private long todayConfirmedCases;
	private List<Region> regions;
	
	
	public EuskadiCommunity(LocalDate date, String name, long todayDeads, long todayConfirmedCases) {
		super();
		this.date = date;
		this.name = name;
		this.todayDeads = todayDeads;
		this.todayConfirmedCases = todayConfirmedCases;
		this.regions = new ArrayList<Region>();
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getTodayDeads() {
		return todayDeads;
	}


	public void setTodayDeads(long todayDeads) {
		this.todayDeads = todayDeads;
	}


	public long getTodayConfirmedCases() {
		return todayConfirmedCases;
	}


	public void setTodayConfirmedCases(long todayConfirmedCases) {
		this.todayConfirmedCases = todayConfirmedCases;
	}


	public List<Region> getRegions() {
		return regions;
	}


	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public void addRegion(Region r) {
		regions.add(r);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (todayConfirmedCases ^ (todayConfirmedCases >>> 32));
		result = prime * result + (int) (todayDeads ^ (todayDeads >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EuskadiCommunity other = (EuskadiCommunity) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (todayConfirmedCases != other.todayConfirmedCases)
			return false;
		if (todayDeads != other.todayDeads)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Nombre: " + name + ", Fecha: " + date +", Nº de muertes: " + todayDeads
				+ ", Nº de casos confirmados: " + todayConfirmedCases;
	}
}




//######################################################################################################
class Region {
	
	private LocalDate regionDate;
	private String regionName;
	private long regionTodayDeads;
	private long RegionodayConfirmedCases;
	
	
	public Region(LocalDate regionDate, String regionName, long regionTodayDeads, long regionodayConfirmedCases) {
		this.regionDate = regionDate;
		this.regionName = regionName;
		this.regionTodayDeads = regionTodayDeads;
		RegionodayConfirmedCases = regionodayConfirmedCases;
	}


	public LocalDate getRegionDate() {
		return regionDate;
	}


	public void setRegionDate(LocalDate regionDate) {
		this.regionDate = regionDate;
	}


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}


	public long getRegionTodayDeads() {
		return regionTodayDeads;
	}


	public void setRegionTodayDeads(long regionTodayDeads) {
		this.regionTodayDeads = regionTodayDeads;
	}


	public long getRegionodayConfirmedCases() {
		return RegionodayConfirmedCases;
	}


	public void setRegionodayConfirmedCases(long regionodayConfirmedCases) {
		RegionodayConfirmedCases = regionodayConfirmedCases;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regionDate == null) ? 0 : regionDate.hashCode());
		result = prime * result + (int) (RegionodayConfirmedCases ^ (RegionodayConfirmedCases >>> 32));
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
		result = prime * result + (int) (regionTodayDeads ^ (regionTodayDeads >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		if (regionDate == null) {
			if (other.regionDate != null)
				return false;
		} else if (!regionDate.equals(other.regionDate))
			return false;
		if (RegionodayConfirmedCases != other.RegionodayConfirmedCases)
			return false;
		if (regionName == null) {
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		if (regionTodayDeads != other.regionTodayDeads)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return   "Name=" + regionName + ", Fecha:" + regionDate+", Nº de muertes: "
				+ regionTodayDeads + ", Nº de casos confirmados: " + RegionodayConfirmedCases;
	}
}