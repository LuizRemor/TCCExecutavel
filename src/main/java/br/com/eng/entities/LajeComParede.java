package br.com.eng.entities;

import java.math.BigDecimal;
import java.math.MathContext;

import br.com.eng.util.Services;
import javafx.scene.control.TextField;

public class LajeComParede {

	BigDecimal lambda;
	BigDecimal lambdaInvertido;
	BigDecimal ladoX;
	BigDecimal ladoY;
	BigDecimal caso;
	BigDecimal area;
	BigDecimal espessura;
	BigDecimal alturaPiso = new BigDecimal(0.02);
	BigDecimal alturaContraPiso = new BigDecimal(0.03);
	BigDecimal cargaTotalPositiva;
	BigDecimal cargaTotalNegativa;
	BigDecimal cargaPermanentePositiva;
	BigDecimal cargaPermanenteNegativa;
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

	public LajeComParede() {

	}
	
	

	public LajeComParede(BigDecimal lambda, BigDecimal lambdaInvertido, BigDecimal ladoX, BigDecimal ladoY,
			BigDecimal caso, BigDecimal area, BigDecimal espessura, BigDecimal alturaPiso, BigDecimal alturaContraPiso,
			BigDecimal cargaTotalPositiva, BigDecimal cargaTotalNegativa, BigDecimal cargaPermanentePositiva,
			BigDecimal cargaPermanenteNegativa, BigDecimal cargaDeServico, BigDecimal cargaAcidental, BigDecimal psi,
			BigDecimal momentoDeFissuracao, BigDecimal momentoDeServico, BigDecimal inercia, BigDecimal coeficienteK,
			BigDecimal flechaDeCurtaDuracao, BigDecimal flechaDeLongaDuracao, BigDecimal flechaAdmissivel,
			BigDecimal momentoDeProjetoX, BigDecimal momentoDeProjetoY, BigDecimal momentoDeProjetoXLinha,
			BigDecimal momentoDeProjetoYLinha, BigDecimal d, BigDecimal x, BigDecimal y, BigDecimal xLinha,
			BigDecimal yLinha, BigDecimal areaDeAcoX, BigDecimal areaDeAcoXLinha, BigDecimal areaDeAcoY,
			BigDecimal areaDeAcoYLinha, BigDecimal areaDeAcoMinima, BigDecimal areaDeAcoMinimaPositiva,
			BigDecimal reacaoX, BigDecimal reacaoXLinha, BigDecimal reacaoY, BigDecimal reacaoYLinha) {

		this.lambda = lambda;
		this.lambdaInvertido = lambdaInvertido;
		this.ladoX = ladoX;
		this.ladoY = ladoY;
		this.caso = caso;
		this.area = area;
		this.espessura = espessura;
		this.alturaPiso = alturaPiso;
		this.alturaContraPiso = alturaContraPiso;
		this.cargaTotalPositiva = cargaTotalPositiva;
		this.cargaTotalNegativa = cargaTotalNegativa;
		this.cargaPermanentePositiva = cargaPermanentePositiva;
		this.cargaPermanenteNegativa = cargaPermanenteNegativa;
		this.cargaDeServico = cargaDeServico;
		this.cargaAcidental = cargaAcidental;
		this.psi = psi;
		this.momentoDeFissuracao = momentoDeFissuracao;
		this.momentoDeServico = momentoDeServico;
		this.inercia = inercia;
		this.coeficienteK = coeficienteK;
		this.flechaDeCurtaDuracao = flechaDeCurtaDuracao;
		this.flechaDeLongaDuracao = flechaDeLongaDuracao;
		this.flechaAdmissivel = flechaAdmissivel;
		this.momentoDeProjetoX = momentoDeProjetoX;
		this.momentoDeProjetoY = momentoDeProjetoY;
		this.momentoDeProjetoXLinha = momentoDeProjetoXLinha;
		this.momentoDeProjetoYLinha = momentoDeProjetoYLinha;
		this.d = d;
		this.x = x;
		this.y = y;
		this.xLinha = xLinha;
		this.yLinha = yLinha;
		this.areaDeAcoX = areaDeAcoX;
		this.areaDeAcoXLinha = areaDeAcoXLinha;
		this.areaDeAcoY = areaDeAcoY;
		this.areaDeAcoYLinha = areaDeAcoYLinha;
		this.areaDeAcoMinima = areaDeAcoMinima;
		this.areaDeAcoMinimaPositiva = areaDeAcoMinimaPositiva;
		this.reacaoX = reacaoX;
		this.reacaoXLinha = reacaoXLinha;
		this.reacaoY = reacaoY;
		this.reacaoYLinha = reacaoYLinha;
	}



	public LajeComParede(TextField ladoX, TextField ladoY) {

		this.ladoX = services.conversor(ladoX);
		this.ladoY = services.conversor(ladoY);

	}

	public LajeComParede(TextField ladoX, TextField ladoY, TextField espessura) {

		this.ladoX = services.conversor(ladoX);
		this.ladoY = services.conversor(ladoY);
		this.espessura = services.conversor(espessura);

	}

	public void calculaArea() {

		this.area = this.ladoX.multiply(this.ladoY);

	}
	
	public BigDecimal calculaCargaPermanentePositiva(Materiais materiais, Parede parede) {
		
		setEspessura(services.CentimetrosEmMetros(this.espessura));
		
		BigDecimal pesoProprio = materiais.getGamaConcreto().multiply(this.espessura);
		BigDecimal pesoPiso = materiais.gamaPiso.multiply(this.alturaPiso);
		BigDecimal pesoContraPiso = materiais.gamaContraPiso.multiply(this.alturaContraPiso);
		BigDecimal pesoForro = materiais.pesoForro;
		BigDecimal pesoParede = parede.calculaPesoDaParedePositiva(materiais);
		
		this.cargaPermanentePositiva = pesoProprio.add(pesoPiso).add(pesoContraPiso).add(pesoForro).add(pesoParede);
		
		return this.cargaPermanentePositiva;
		
	}
	
	public BigDecimal calculaCargaPermanenteNegativa(Materiais materiais, Parede parede) {
		
		BigDecimal pesoProprio = materiais.getGamaConcreto().multiply(this.espessura);
		BigDecimal pesoPiso = materiais.gamaPiso.multiply(this.alturaPiso);
		BigDecimal pesoContraPiso = materiais.gamaContraPiso.multiply(this.alturaContraPiso);
		BigDecimal pesoForro = materiais.pesoForro;
		BigDecimal pesoParede = parede.calculaPesoDaParedeNegativa(materiais);
		
		this.cargaPermanenteNegativa = pesoProprio.add(pesoPiso).add(pesoContraPiso).add(pesoForro).add(pesoParede);
		
		return this.cargaPermanenteNegativa;
		
	}
	
	public BigDecimal calculaCargaDeServico(Materiais materiais, Parede parede) {
		
		this.cargaDeServico = cargaPermanentePositiva.add(this.psi.multiply(this.cargaAcidental));
		
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

	public BigDecimal getPsi() {
		return psi;
	}

	public void setPsi(BigDecimal psi) {
		this.psi = psi;
	}

	public BigDecimal getLadoX() {
		return ladoX;
	}

	public void setLadoX(BigDecimal ladoX) {
		this.ladoX = ladoX;
	}

	public BigDecimal getLadoY() {
		return ladoY;
	}

	public void setLadoY(BigDecimal ladoY) {
		this.ladoY = ladoY;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getEspessura() {
		return espessura;
	}

	public void setEspessura(BigDecimal espessura) {
		this.espessura = espessura;
	}

	public BigDecimal getAlturaPiso() {
		return alturaPiso;
	}

	public void setAlturaPiso(BigDecimal alturaPiso) {
		this.alturaPiso = alturaPiso;
	}

	public BigDecimal getAlturaContraPiso() {
		return alturaContraPiso;
	}

	public void setAlturaContraPiso(BigDecimal alturaContraPiso) {
		this.alturaContraPiso = alturaContraPiso;
	}

	public BigDecimal getCargaPermanentePositiva() {
		return cargaPermanentePositiva;
	}

	public void setCargaPermanentePositiva(BigDecimal cargaPermanentePositiva) {
		this.cargaPermanentePositiva = cargaPermanentePositiva;
	}

	public BigDecimal getCargaPermanenteNegativa() {
		return cargaPermanenteNegativa;
	}

	public void setCargaPermanenteNegativa(BigDecimal cargaPermanenteNegativa) {
		this.cargaPermanenteNegativa = cargaPermanenteNegativa;
	}

	public BigDecimal getCargaDeServico() {
		return cargaDeServico;
	}

	public void setCargaDeServico(BigDecimal cargaDeServico) {
		this.cargaDeServico = cargaDeServico;
	}

	public BigDecimal getCargaAcidental() {
		return cargaAcidental;
	}

	public void setCargaAcidental(BigDecimal cargaAcidental) {
		this.cargaAcidental = cargaAcidental;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public BigDecimal getMomentoDeFissuracao() {
		return momentoDeFissuracao;
	}

	public void setMomentoDeFissuracao(BigDecimal momentoDeFissuracao) {
		this.momentoDeFissuracao = momentoDeFissuracao;
	}
	
	public BigDecimal getMomentoDeServico() {
		return momentoDeServico;
	}

	public void setMomentoDeServico(BigDecimal momentoDeServico) {
		this.momentoDeServico = momentoDeServico;
	}

	public BigDecimal getInercia() {
		return inercia;
	}

	public void setInercia(BigDecimal inercia) {
		this.inercia = inercia;
	}

	public BigDecimal getCoeficienteK() {
		return coeficienteK;
	}

	public void setCoeficienteK(BigDecimal coeficienteK) {
		this.coeficienteK = coeficienteK;
	}

	public BigDecimal getFlechaDeCurtaDuracao() {
		return flechaDeCurtaDuracao;
	}

	public void setFlechaDeCurtaDuracao(BigDecimal flechaDeCurtaDuracao) {
		this.flechaDeCurtaDuracao = flechaDeCurtaDuracao;
	}

	public BigDecimal getFlechaDeLongaDuracao() {
		return flechaDeLongaDuracao;
	}

	public void setFlechaDeLongaDuracao(BigDecimal flechaDeLongaDuracao) {
		this.flechaDeLongaDuracao = flechaDeLongaDuracao;
	}

	public BigDecimal getFlechaAdmissivel() {
		return flechaAdmissivel;
	}

	public void setFlechaAdmissivel(BigDecimal flechaAdmissivel) {
		this.flechaAdmissivel = flechaAdmissivel;
	}

	public BigDecimal getCargaTotalPositiva() {
		return cargaTotalPositiva;
	}

	public void setCargaTotalPositiva(BigDecimal cargaTotalPositiva) {
		this.cargaTotalPositiva = cargaTotalPositiva;
	}

	public BigDecimal getCargaTotalNegativa() {
		return cargaTotalNegativa;
	}

	public void setCargaTotalNegativa(BigDecimal cargaTotalNegativa) {
		this.cargaTotalNegativa = cargaTotalNegativa;
	}

	public BigDecimal getCaso() {
		return caso;
	}

	public void setCaso(BigDecimal caso) {
		this.caso = caso;
	}

	public BigDecimal getLambda() {
		return lambda;
	}

	public void setLambda(BigDecimal lambda) {
		this.lambda = lambda;
	}

	public BigDecimal getMomentoDeProjetoX() {
		return momentoDeProjetoX;
	}

	public void setMomentoDeProjetoX(BigDecimal momentoDeProjetoX) {
		this.momentoDeProjetoX = momentoDeProjetoX;
	}

	public BigDecimal getMomentoDeProjetoY() {
		return momentoDeProjetoY;
	}

	public void setMomentoDeProjetoY(BigDecimal momentoDeProjetoY) {
		this.momentoDeProjetoY = momentoDeProjetoY;
	}

	public BigDecimal getMomentoDeProjetoXLinha() {
		return momentoDeProjetoXLinha;
	}

	public void setMomentoDeProjetoXLinha(BigDecimal momentoDeProjetoXLinha) {
		this.momentoDeProjetoXLinha = momentoDeProjetoXLinha;
	}

	public BigDecimal getMomentoDeProjetoYLinha() {
		return momentoDeProjetoYLinha;
	}

	public void setMomentoDeProjetoYLinha(BigDecimal momentoDeProjetoYLinha) {
		this.momentoDeProjetoYLinha = momentoDeProjetoYLinha;
	}

	public BigDecimal getD() {
		return d;
	}

	public void setD(BigDecimal d) {
		this.d = d;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public BigDecimal getxLinha() {
		return xLinha;
	}

	public void setxLinha(BigDecimal xLinha) {
		this.xLinha = xLinha;
	}

	public BigDecimal getyLinha() {
		return yLinha;
	}

	public void setyLinha(BigDecimal yLinha) {
		this.yLinha = yLinha;
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

	public BigDecimal getAreaDeAcoMinima() {
		return areaDeAcoMinima;
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