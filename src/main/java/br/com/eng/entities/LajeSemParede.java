package br.com.eng.entities;

import java.math.BigDecimal;
import java.math.MathContext;

import br.com.eng.util.Services;
import javafx.scene.control.TextField;

public class LajeSemParede {
	
	BigDecimal lambda;
	BigDecimal lambdaInvertido;
	BigDecimal ladoX;
	BigDecimal ladoY;
	BigDecimal caso;
	BigDecimal area;
	BigDecimal espessura;
	BigDecimal alturaPiso = new BigDecimal(0.02);
	BigDecimal alturaContraPiso = new BigDecimal(0.03);
	BigDecimal cargaTotal;
	BigDecimal cargaPermanente;
	BigDecimal cargaDeServico;
	BigDecimal cargaAcidental;
	BigDecimal psi;
	BigDecimal momentoDeFissuracao;
	BigDecimal momentoDeServico;
	BigDecimal inercia;
	BigDecimal coeficienteK;
	BigDecimal flechaDeCurtaDuracao;
	BigDecimal flechaDeLongaDuracao;
	BigDecimal flechaAdmissivel;
	BigDecimal momentoDeProjetoX;
	BigDecimal momentoDeProjetoY;
	BigDecimal momentoDeProjetoXLinha;
	BigDecimal momentoDeProjetoYLinha;
	BigDecimal d;
	BigDecimal x;
	BigDecimal y;
	BigDecimal xLinha;
	BigDecimal yLinha;
	BigDecimal areaDeAcoX;
	BigDecimal areaDeAcoXLinha;
	BigDecimal areaDeAcoY;
	BigDecimal areaDeAcoYLinha;
	BigDecimal areaDeAcoMinima;
	BigDecimal areaDeAcoMinimaPositiva;
	BigDecimal reacaoX;
	BigDecimal reacaoXLinha;
	BigDecimal reacaoY;
	BigDecimal reacaoYLinha;
	
	
	Services services = new Services();

	public LajeSemParede() {

	}
	
	public LajeSemParede(TextField ladoX, TextField ladoY, TextField espessura) {

		this.ladoX = services.conversor(ladoX);
		this.ladoY = services.conversor(ladoY);
		this.espessura = services.conversor(espessura);

	}
	
	public void calculaArea() {

		this.area = this.ladoX.multiply(this.ladoY);

	}
	
	public BigDecimal calculaCargaPermanente(Materiais materiais) {
		
		setEspessura(services.CentimetrosEmMetros(this.espessura));
		
		BigDecimal pesoProprio = materiais.getGamaConcreto().multiply(this.espessura);
		BigDecimal pesoPiso = materiais.gamaPiso.multiply(this.alturaPiso);
		BigDecimal pesoContraPiso = materiais.gamaContraPiso.multiply(this.alturaContraPiso);
		BigDecimal pesoForro = materiais.pesoForro;
		
		this.cargaPermanente = pesoProprio.add(pesoPiso).add(pesoContraPiso).add(pesoForro);
		
		return this.cargaPermanente;
		
	}
	
	public BigDecimal calculaCargaDeServico(Materiais materiais) {
		
		this.cargaDeServico = cargaPermanente.add(this.psi.multiply(this.cargaAcidental));
		
		return cargaDeServico;
	}
	
	public BigDecimal calculaInercia() {
		
		BigDecimal espessura =  services.metrosEmCentimetros(this.espessura);
		
		this.inercia = new BigDecimal(100.0).multiply(espessura.pow(3)).divide(new BigDecimal(12.0), MathContext.DECIMAL128);
		
		return inercia;
	}
	
	public BigDecimal calculaD() {
		
		BigDecimal cobrimento = new BigDecimal(2.5);
		BigDecimal barraPadrao = new BigDecimal(1.0);
		BigDecimal espessura = services.metrosEmCentimetros(this.espessura);
		
		this.d = espessura.subtract(cobrimento).subtract(barraPadrao.divide(new BigDecimal(2.0)));		
		
		return d;
		
	}
	
	public BigDecimal calculaAcoMinimo() {
		
		this.areaDeAcoMinima = new BigDecimal(0.0015).multiply(new BigDecimal(100.0)).multiply(this.espessura);
		
		return areaDeAcoMinima;
		
	}
	
	public BigDecimal calculaAcoMinimoPositivo() {
		
		this.areaDeAcoMinimaPositiva = new BigDecimal(0.67).
							  multiply(new BigDecimal(0.0015)).
							  multiply(new BigDecimal(100.0)).
							  multiply(this.espessura);
		
		return areaDeAcoMinimaPositiva;
		
	}

	public BigDecimal getLambda() {
		return lambda;
	}

	public BigDecimal getLadoX() {
		return ladoX;
	}

	public BigDecimal getLadoY() {
		return ladoY;
	}

	public BigDecimal getCaso() {
		return caso;
	}

	public BigDecimal getArea() {
		return area;
	}

	public BigDecimal getEspessura() {
		return espessura;
	}

	public BigDecimal getAlturaPiso() {
		return alturaPiso;
	}

	public BigDecimal getAlturaContraPiso() {
		return alturaContraPiso;
	}

	public BigDecimal getCargaTotal() {
		return cargaTotal;
	}

	public BigDecimal getCargaPermanente() {
		return cargaPermanente;
	}

	public BigDecimal getCargaDeServico() {
		return cargaDeServico;
	}

	public BigDecimal getCargaAcidental() {
		return cargaAcidental;
	}

	public BigDecimal getPsi() {
		return psi;
	}

	public BigDecimal getMomentoDeFissuracao() {
		return momentoDeFissuracao;
	}

	public BigDecimal getMomentoDeServico() {
		return momentoDeServico;
	}

	public BigDecimal getInercia() {
		return inercia;
	}

	public BigDecimal getCoeficienteK() {
		return coeficienteK;
	}

	public BigDecimal getFlechaDeCurtaDuracao() {
		return flechaDeCurtaDuracao;
	}

	public BigDecimal getFlechaDeLongaDuracao() {
		return flechaDeLongaDuracao;
	}

	public BigDecimal getFlechaAdmissivel() {
		return flechaAdmissivel;
	}

	public BigDecimal getMomentoDeProjetoX() {
		return momentoDeProjetoX;
	}

	public BigDecimal getMomentoDeProjetoY() {
		return momentoDeProjetoY;
	}

	public BigDecimal getMomentoDeProjetoXLinha() {
		return momentoDeProjetoXLinha;
	}

	public BigDecimal getMomentoDeProjetoYLinha() {
		return momentoDeProjetoYLinha;
	}

	public BigDecimal getD() {
		return d;
	}

	public BigDecimal getX() {
		return x;
	}

	public BigDecimal getY() {
		return y;
	}

	public BigDecimal getxLinha() {
		return xLinha;
	}

	public BigDecimal getyLinha() {
		return yLinha;
	}

	public BigDecimal getAreaDeAcoX() {
		return areaDeAcoX;
	}

	public BigDecimal getAreaDeAcoXLinha() {
		return areaDeAcoXLinha;
	}

	public BigDecimal getAreaDeAcoY() {
		return areaDeAcoY;
	}

	public BigDecimal getAreaDeAcoYLinha() {
		return areaDeAcoYLinha;
	}

	public BigDecimal getAreaDeAcoMinima() {
		return areaDeAcoMinima;
	}

	public void setLambda(BigDecimal lambda) {
		this.lambda = lambda;
	}

	public void setLadoX(BigDecimal ladoX) {
		this.ladoX = ladoX;
	}

	public void setLadoY(BigDecimal ladoY) {
		this.ladoY = ladoY;
	}

	public void setCaso(BigDecimal caso) {
		this.caso = caso;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public void setEspessura(BigDecimal espessura) {
		this.espessura = espessura;
	}

	public void setAlturaPiso(BigDecimal alturaPiso) {
		this.alturaPiso = alturaPiso;
	}

	public void setAlturaContraPiso(BigDecimal alturaContraPiso) {
		this.alturaContraPiso = alturaContraPiso;
	}

	public void setCargaTotal(BigDecimal cargaTotal) {
		this.cargaTotal = cargaTotal;
	}

	public void setCargaPermanente(BigDecimal cargaPermanente) {
		this.cargaPermanente = cargaPermanente;
	}

	public void setCargaDeServico(BigDecimal cargaDeServico) {
		this.cargaDeServico = cargaDeServico;
	}

	public void setCargaAcidental(BigDecimal cargaAcidental) {
		this.cargaAcidental = cargaAcidental;
	}

	public void setPsi(BigDecimal psi) {
		this.psi = psi;
	}

	public void setMomentoDeFissuracao(BigDecimal momentoDeFissuracao) {
		this.momentoDeFissuracao = momentoDeFissuracao;
	}

	public void setMomentoDeServico(BigDecimal momentoDeServico) {
		this.momentoDeServico = momentoDeServico;
	}

	public void setInercia(BigDecimal inercia) {
		this.inercia = inercia;
	}

	public void setCoeficienteK(BigDecimal coeficienteK) {
		this.coeficienteK = coeficienteK;
	}

	public void setFlechaDeCurtaDuracao(BigDecimal flechaDeCurtaDuracao) {
		this.flechaDeCurtaDuracao = flechaDeCurtaDuracao;
	}

	public void setFlechaDeLongaDuracao(BigDecimal flechaDeLongaDuracao) {
		this.flechaDeLongaDuracao = flechaDeLongaDuracao;
	}

	public void setFlechaAdmissivel(BigDecimal flechaAdmissivel) {
		this.flechaAdmissivel = flechaAdmissivel;
	}

	public void setMomentoDeProjetoX(BigDecimal momentoDeProjetoX) {
		this.momentoDeProjetoX = momentoDeProjetoX;
	}

	public void setMomentoDeProjetoY(BigDecimal momentoDeProjetoY) {
		this.momentoDeProjetoY = momentoDeProjetoY;
	}

	public void setMomentoDeProjetoXLinha(BigDecimal momentoDeProjetoXLinha) {
		this.momentoDeProjetoXLinha = momentoDeProjetoXLinha;
	}

	public void setMomentoDeProjetoYLinha(BigDecimal momentoDeProjetoYLinha) {
		this.momentoDeProjetoYLinha = momentoDeProjetoYLinha;
	}

	public void setD(BigDecimal d) {
		this.d = d;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public void setxLinha(BigDecimal xLinha) {
		this.xLinha = xLinha;
	}

	public void setyLinha(BigDecimal yLinha) {
		this.yLinha = yLinha;
	}

	public void setAreaDeAcoX(BigDecimal areaDeAcoX) {
		this.areaDeAcoX = areaDeAcoX;
	}

	public void setAreaDeAcoXLinha(BigDecimal areaDeAcoXLinha) {
		this.areaDeAcoXLinha = areaDeAcoXLinha;
	}

	public void setAreaDeAcoY(BigDecimal areaDeAcoY) {
		this.areaDeAcoY = areaDeAcoY;
	}

	public void setAreaDeAcoYLinha(BigDecimal areaDeAcoYLinha) {
		this.areaDeAcoYLinha = areaDeAcoYLinha;
	}

	public void setAreaDeAcoMinima(BigDecimal areaDeAcoMinima) {
		this.areaDeAcoMinima = areaDeAcoMinima;
	}

	public BigDecimal getLambdaInvertido() {
		return lambdaInvertido;
	}

	public void setLambdaInvertido(BigDecimal lambdaInvertido) {
		this.lambdaInvertido = lambdaInvertido;
	}

	public BigDecimal getReacaoX() {
		return reacaoX;
	}

	public BigDecimal getReacaoXLinha() {
		return reacaoXLinha;
	}

	public BigDecimal getReacaoY() {
		return reacaoY;
	}

	public BigDecimal getReacaoYLinha() {
		return reacaoYLinha;
	}

	public void setReacaoX(BigDecimal reacaoX) {
		this.reacaoX = reacaoX;
	}

	public void setReacaoXLinha(BigDecimal reacaoXLinha) {
		this.reacaoXLinha = reacaoXLinha;
	}

	public void setReacaoY(BigDecimal reacaoY) {
		this.reacaoY = reacaoY;
	}

	public void setReacaoYLinha(BigDecimal reacaoYLinha) {
		this.reacaoYLinha = reacaoYLinha;
	}

	public BigDecimal getAreaDeAcoMinimaPositiva() {
		return areaDeAcoMinimaPositiva;
	}

	public void setAreaDeAcoMinimaPositiva(BigDecimal areaDeAcoMinimaPositiva) {
		this.areaDeAcoMinimaPositiva = areaDeAcoMinimaPositiva;
	}
	
}