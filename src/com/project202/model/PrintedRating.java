package com.project202.model;

public class PrintedRating {
	private float globalGrade;
	private float cultureGrade;
	private float leisureGrade;
	private float shopsGrade;
	private float institutionsGrade;
	private float transitGrade;
	private float natureGrade;
	
	public PrintedRating(){};
	
	public PrintedRating(float globalGrade, float cultureGrade, float leisureGrade, float shopsGrade, float institutionsGrade, float transitGrade, float natureGrade) {
		this.globalGrade = globalGrade;
		this.cultureGrade = cultureGrade;
		this.leisureGrade = leisureGrade;
		this.shopsGrade = shopsGrade;
		this.institutionsGrade = institutionsGrade;
		this.transitGrade = transitGrade;
		this.natureGrade = natureGrade;
	}

	public float getGlobalGrade() {
		return globalGrade;
	}
	public void setGlobalGrade(float globalGrade) {
		this.globalGrade = globalGrade;
	}
	public float getCultureGrade() {
		return cultureGrade;
	}
	public void setCultureGrade(float cultureGrade) {
		this.cultureGrade = cultureGrade;
	}
	public float getLeisureGrade() {
		return leisureGrade;
	}
	public void setLeisureGrade(float leisureGrade) {
		this.leisureGrade = leisureGrade;
	}
	public float getShopsGrade() {
		return shopsGrade;
	}
	public void setShopsGrade(float shopsGrade) {
		this.shopsGrade = shopsGrade;
	}
	public float getInstitutionsGrade() {
		return institutionsGrade;
	}
	public void setInstitutionsGrade(float institutionsGrade) {
		this.institutionsGrade = institutionsGrade;
	}
	public float getTransitGrade() {
		return transitGrade;
	}
	public void setTransitGrade(float transitGrade) {
		this.transitGrade = transitGrade;
	}
	public float getNatureGrade() {
		return natureGrade;
	}
	public void setNatureGrade(float natureGrade) {
		this.natureGrade = natureGrade;
	}
	
	
}
