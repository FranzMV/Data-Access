package com.fran.practicatema1.entidades;

import java.time.LocalDate;

public class TotalPcrsByDate {
	
		private LocalDate date;
		private long numberOfPcrsPerformed;
		
	
		public TotalPcrsByDate(LocalDate date, long numberOfPcrsPerformed) {
			super();
			
			this.date = date;
			this.numberOfPcrsPerformed = numberOfPcrsPerformed;
		}
		
		public LocalDate getDate() {
			return date;
		}


		public void setDate(LocalDate date) {
			this.date = date;
		}


		public long getNumberOfPcrsPerformed() {
			return numberOfPcrsPerformed;
		}


		public void setNumberOfPcrsPerformed(long numberOfPcrsPerformed) {
			this.numberOfPcrsPerformed = numberOfPcrsPerformed;
		}


		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result + (int) (numberOfPcrsPerformed ^ (numberOfPcrsPerformed >>> 32));
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
			TotalPcrsByDate other = (TotalPcrsByDate) obj;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (numberOfPcrsPerformed != other.numberOfPcrsPerformed)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Fecha: " + date + ", Nº de Pcrs: "+ numberOfPcrsPerformed;
		}
}
