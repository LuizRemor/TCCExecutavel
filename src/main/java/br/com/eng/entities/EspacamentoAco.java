package br.com.eng.entities;

import java.math.BigDecimal;

public class EspacamentoAco {
	
	private String eixo;
	private BigDecimal quantidade;
	private BigDecimal bitola;
	private BigDecimal espacamento;
	private BigDecimal areaDeAco;

	public EspacamentoAco(EspacamentoAco espacamentoAco){
		
		this.eixo        = espacamentoAco.getEixo();
		this.quantidade  = espacamentoAco.getQuantidade();
		this.bitola      = espacamentoAco.getBitola();
		this.espacamento = espacamentoAco.getEspacamento();
		this.areaDeAco   = espacamentoAco.getAreaDeAco();

	}

	public EspacamentoAco(BigDecimal bitola, BigDecimal espacamento, BigDecimal areaDeAco) {

		this.bitola = bitola;
		this.espacamento = espacamento;
		this.areaDeAco = areaDeAco;
	}

	public BigDecimal getBitola() {
		return bitola;
	}

	public BigDecimal getEspacamento() {
		return espacamento;
	}

	public BigDecimal getAreaDeAco() {
		return areaDeAco;
	}

	public void setBitola(BigDecimal bitola) {
		this.bitola = bitola;
	}

	public void setEspacamento(BigDecimal espacamento) {
		this.espacamento = espacamento;
	}

	public void setAreaDeAco(BigDecimal areaDeAco) {
		this.areaDeAco = areaDeAco;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public String getEixo() {
		return eixo;
	}

	public void setEixo(String eixo) {
		this.eixo = eixo;
	}

	@Override
	public String toString() {
		return eixo + " " + quantidade + " barras de Φ " + bitola + "mm com "+ espacamento + "cm";
	}

}