package com.fran.practicatema1.entidades;

import java.time.LocalDate;

public class NewPcrPositiveByGenre {
	
	private LocalDate date;
	private long menCount;
	private long womenCount;
	
	
	public NewPcrPositiveByGenre(LocalDate date, long menCount, long womenCount) {
		this.date = date;
		this.menCount = menCount;
		this.womenCount = womenCount;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public long getMenCount() {
		return menCount;
	}


	public void setMenCount(long menCount) {
		this.menCount = menCount;
	}


	public long getWomenCount() {
		return womenCount;
	}


	public void setWomenCount(long womenCount) {
		this.womenCount = womenCount;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (menCount ^ (menCount >>> 32));
		result = prime * result + (int) (womenCount ^ (womenCount >>> 32));
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
		NewPcrPositiveByGenre other = (NewPcrPositiveByGenre) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (menCount != other.menCount)
			return false;
		if (womenCount != other.womenCount)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Fecha: " + date + ", Hombres: " + menCount + ", Mujeres: " + womenCount;
	}
}
