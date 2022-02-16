package com.fran.practicatema1.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class CumulativeIncidence implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private double cases;
	private double by100ThousandPeopleEuskadi;
	private double by100ThousandPeopleAlaba;
	private double by100ThousandPeopleBixkaia;
	private double by100ThousandPeopleGipuzkua;
	
	
	public CumulativeIncidence(LocalDate date, double cases, double by100ThousandPeopleEuskadi,
			double by100ThousandPeopleAlaba, double by100ThousandPeopleBixkaia, double by100ThousandPeopleGipuzkua) {
		super();
		this.date = date;
		this.cases = cases;
		this.by100ThousandPeopleEuskadi = by100ThousandPeopleEuskadi;
		this.by100ThousandPeopleAlaba = by100ThousandPeopleAlaba;
		this.by100ThousandPeopleBixkaia = by100ThousandPeopleBixkaia;
		this.by100ThousandPeopleGipuzkua = by100ThousandPeopleGipuzkua;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public double getCases() {
		return cases;
	}


	public void setCases(double cases) {
		this.cases = cases;
	}


	public double getBy100ThousandPeopleEuskadi() {
		return by100ThousandPeopleEuskadi;
	}


	public void setBy100ThousandPeopleEuskadi(double by100ThousandPeopleEuskadi) {
		this.by100ThousandPeopleEuskadi = by100ThousandPeopleEuskadi;
	}


	public double getBy100ThousandPeopleAlaba() {
		return by100ThousandPeopleAlaba;
	}


	public void setBy100ThousandPeopleAlaba(double by100ThousandPeopleAlaba) {
		this.by100ThousandPeopleAlaba = by100ThousandPeopleAlaba;
	}


	public double getBy100ThousandPeopleBixkaia() {
		return by100ThousandPeopleBixkaia;
	}


	public void setBy100ThousandPeopleBixkaia(double by100ThousandPeopleBixkaia) {
		this.by100ThousandPeopleBixkaia = by100ThousandPeopleBixkaia;
	}


	public double getBy100ThousandPeopleGipuzkua() {
		return by100ThousandPeopleGipuzkua;
	}


	public void setBy100ThousandPeopleGipuzkua(double by100ThousandPeopleGipuzkua) {
		this.by100ThousandPeopleGipuzkua = by100ThousandPeopleGipuzkua;
	}


	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(by100ThousandPeopleAlaba);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(by100ThousandPeopleBixkaia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(by100ThousandPeopleEuskadi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(by100ThousandPeopleGipuzkua);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(cases);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		CumulativeIncidence other = (CumulativeIncidence) obj;
		if (Double.doubleToLongBits(by100ThousandPeopleAlaba) != Double
				.doubleToLongBits(other.by100ThousandPeopleAlaba))
			return false;
		if (Double.doubleToLongBits(by100ThousandPeopleBixkaia) != Double
				.doubleToLongBits(other.by100ThousandPeopleBixkaia))
			return false;
		if (Double.doubleToLongBits(by100ThousandPeopleEuskadi) != Double
				.doubleToLongBits(other.by100ThousandPeopleEuskadi))
			return false;
		if (Double.doubleToLongBits(by100ThousandPeopleGipuzkua) != Double
				.doubleToLongBits(other.by100ThousandPeopleGipuzkua))
			return false;
		if (Double.doubleToLongBits(cases) != Double.doubleToLongBits(other.cases))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return  "Fecha: " + date
				+", Nº Casos: " + (int)cases
				+ ", Euskadi: "+ by100ThousandPeopleEuskadi 
				+ ", Álava: " + by100ThousandPeopleAlaba
				+ ", Bizkaia: " + by100ThousandPeopleBixkaia 
				+ ", Gipuzkua: "+ by100ThousandPeopleGipuzkua;
	}
	
}
