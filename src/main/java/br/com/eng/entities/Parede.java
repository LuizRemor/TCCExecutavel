package br.com.eng.entities;

import java.math.BigDecimal;
import java.math.MathContext;

import br.com.eng.util.Services;
import javafx.scene.control.TextField;

public class Parede {

	BigDecimal altura;

	BigDecimal espessuraParede;

	BigDecimal areaDeInfluenciaPositiva;

	BigDecimal areaDeInfluenciaNegativa;

	BigDecimal pesoDaParedePositiva;

	BigDecimal pesoDaParedeNegativa;

	Services services = new Services();

	Materiais materiais = new Materiais();

	public Parede() {

	}

	public Parede(TextField altura, TextField espessuraParede) {

		this.altura = services.conversor(altura);
		this.espessuraParede = services.conversor(espessuraParede);

	}

	public BigDecimal calculaAreaDeInfluenciaDaParedePositiva(LajeComParede lajeComParede) {

		lajeComParede.setLadoX(services.metrosEmCentimetros(lajeComParede.getLadoX()));

		BigDecimal a1 = lajeComParede.getLadoX().divide(new BigDecimal(2.0));

		BigDecimal b = lajeComParede.getEspessura().add(this.espessuraParede);

		BigDecimal deltaVNumerador1 = new BigDecimal(2.0).multiply(a1).multiply(lajeComParede.getLadoX().subtract(a1));

		BigDecimal deltaVNumerador2 = new BigDecimal(1.0).subtract(b.divide(lajeComParede.getLadoX(), MathContext.DECIMAL128));

		BigDecimal deltaV = (deltaVNumerador1.multiply(deltaVNumerador2)).divide(lajeComParede.getLadoX());

		this.areaDeInfluenciaPositiva = b.add(deltaV);

		return this.areaDeInfluenciaPositiva;
	}

	public BigDecimal calculaAreaDeInfluenciaDaParedeNegativa(LajeComParede lajeComParede) {

		BigDecimal a1 = lajeComParede.getLadoX().divide(new BigDecimal(2.0));

		BigDecimal b = lajeComParede.getEspessura().add(this.espessuraParede);
		
		b = services.metrosEmCentimetros(b);

		BigDecimal deltaVNumerador1 = a1.multiply(new BigDecimal(2.0).multiply(lajeComParede.getLadoX()).subtract(a1));

		BigDecimal deltaVNumerador2 = new BigDecimal(1.0).subtract(b.divide(lajeComParede.getLadoX(), MathContext.DECIMAL128));

		BigDecimal deltaV = (deltaVNumerador1.multiply(deltaVNumerador2)).divide(lajeComParede.getLadoX());

		this.areaDeInfluenciaNegativa = b.add(deltaV);

		return this.areaDeInfluenciaNegativa;
	}

	public BigDecimal calculaPesoDaParedePositiva(Materiais materiais) {

		this.setEspessuraParede(services.CentimetrosEmMetros(this.espessuraParede));

		BigDecimal numerador = materiais.gamaTijolo.multiply(this.espessuraParede.multiply(this.altura));

		this.setAreaDeInfluenciaPositiva(services.CentimetrosEmMetros(this.areaDeInfluenciaPositiva));

		this.pesoDaParedePositiva = numerador.divide(this.areaDeInfluenciaPositiva, MathContext.DECIMAL128);

		return this.pesoDaParedePositiva;
	}

	public BigDecimal calculaPesoDaParedeNegativa(Materiais materiais) {

		BigDecimal numerador = materiais.gamaTijolo.multiply(this.espessuraParede.multiply(this.altura));

		this.setAreaDeInfluenciaNegativa(services.CentimetrosEmMetros(this.areaDeInfluenciaNegativa));

		this.pesoDaParedeNegativa = numerador.divide(this.areaDeInfluenciaNegativa, MathContext.DECIMAL128);

		return this.pesoDaParedeNegativa;
	}

	public BigDecimal getAreaDeInfluenciaPositiva() {
		return areaDeInfluenciaPositiva;
	}

	public BigDecimal getAreaDeInfluenciaNegativa() {
		return areaDeInfluenciaNegativa;
	}

	public void setAreaDeInfluenciaPositiva(BigDecimal areaDeInfluenciaPositiva) {
		this.areaDeInfluenciaPositiva = areaDeInfluenciaPositiva;
	}

	public void setAreaDeInfluenciaNegativa(BigDecimal areaDeInfluenciaNegativa) {
		this.areaDeInfluenciaNegativa = areaDeInfluenciaNegativa;
	}

	public BigDecimal getAltura() {

		return altura;

	}

	public void setAltura(BigDecimal altura) {

		this.altura = altura;

	}

	public BigDecimal getEspessuraParede() {

		return espessuraParede;

	}

	public void setEspessuraParede(BigDecimal espessuraParede) {

		this.espessuraParede = espessuraParede;

	}

	public BigDecimal getPesoDaParedePositiva() {
		return pesoDaParedePositiva;
	}

	public void setPesoDaParedePositiva(BigDecimal pesoDaParedePositiva) {
		this.pesoDaParedePositiva = pesoDaParedePositiva;
	}

	public BigDecimal getPesoDaParedeNegativa() {
		return pesoDaParedeNegativa;
	}

	public void setPesoDaParedeNegativa(BigDecimal pesoDaParedeNegativa) {
		this.pesoDaParedeNegativa = pesoDaParedeNegativa;
	}

}