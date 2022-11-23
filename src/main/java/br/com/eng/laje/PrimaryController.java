package br.com.eng.laje;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.eng.entities.Coeficientes;
import br.com.eng.entities.EspacamentoAco;
import br.com.eng.entities.LajeComParede;
import br.com.eng.entities.LajeSemParede;
import br.com.eng.entities.Materiais;
import br.com.eng.entities.Parede;
import br.com.eng.entities.Resultado;
import br.com.eng.util.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("deprecation")
public class PrimaryController {

	private LajeComParede lajeComParede;

	private LajeSemParede lajeSemParede;

	private Parede parede;

	private Materiais materiais;

	private Coeficientes coeficientes;
	
	private Coeficientes coeficientesMxKx;

	List<Coeficientes> coeficientesList = new ArrayList<>();
	
	List<Coeficientes> coeficientesMxKxList = new ArrayList<>();

	List<EspacamentoAco> espacamentoAcoList = new ArrayList<>();

	private Services services = new Services();
	
	@FXML
	private Button btCalcular = new Button();

	@FXML
	private Label tijoloFurado = new Label();

	@FXML
	private Label tijoloFuradoS = new Label();

	@FXML
	private Label tijoloFuradoN = new Label();

	@FXML
	private Label textEspessuraDaParede = new Label();

	@FXML
	private Label textAlturaDaParede = new Label();

	@FXML
	private Text engasteCheckXEsquerda = new Text();

	@FXML
	private Text engasteCheckYCima = new Text();

	@FXML
	private Text engasteCheckXDireita = new Text();

	@FXML
	private Text engastecheckYBaixo = new Text();
	
	@FXML
	private Label acoPositivoEmXComParede = new Label();
	
	@FXML
	private Label acoNegativoEmXComParede = new Label();
	
	@FXML
	private Label acoPositivoEmYComParede = new Label();
	
	@FXML
	private Label acoNegativoEmYComParede = new Label();

	@FXML
	private TextField ladoX = new TextField();

	@FXML
	private TextField ladoY = new TextField();

	@FXML
	private TextField espessuraLaje = new TextField();

	@FXML
	private TextField espessuraParede = new TextField();

	@FXML
	private TextField alturaParede = new TextField();

	@FXML
	private TextField cargaAcidental = new TextField();

	@FXML
	private RadioButton psi0_3 = new RadioButton();

	@FXML
	private RadioButton psi0_4 = new RadioButton();

	@FXML
	private RadioButton psi0_5 = new RadioButton();

	@FXML
	private RadioButton psi0_6 = new RadioButton();

	@FXML
	private RadioButton paredeSim = new RadioButton();

	@FXML
	private RadioButton paredeNao = new RadioButton();

	@FXML
	private RadioButton tijoloFuradoSim = new RadioButton();

	@FXML
	private RadioButton tijoloFuradoNao = new RadioButton();

	@FXML
	private RadioButton agregadoBasaltoDiabasio = new RadioButton();

	@FXML
	private RadioButton agregadoGranitoGnaisse = new RadioButton();

	@FXML
	private RadioButton agregadoCalcario = new RadioButton();

	@FXML
	private RadioButton agregadoArenito = new RadioButton();

	@FXML
	private CheckBox checkYCima = new CheckBox();

	@FXML
	private CheckBox checkYBaixo = new CheckBox();

	@FXML
	private CheckBox checkXEsquerda = new CheckBox();

	@FXML
	private CheckBox checkXDireita = new CheckBox();

	@FXML
	private TextArea imprimeResultados = new TextArea();

	@FXML
	private ComboBox<EspacamentoAco> acoXPositivo = new ComboBox<EspacamentoAco>();

	@FXML
	private ComboBox<EspacamentoAco> acoXNegativo = new ComboBox<EspacamentoAco>();

	@FXML
	private ComboBox<EspacamentoAco> acoYPositivo = new ComboBox<EspacamentoAco>();

	@FXML
	private ComboBox<EspacamentoAco> acoYNegativo = new ComboBox<EspacamentoAco>();
	
	@FXML
	private ComboBox<EspacamentoAco> acoXPositivoComParede = new ComboBox<EspacamentoAco>();

	@FXML
	private ComboBox<EspacamentoAco> acoXNegativoComParede = new ComboBox<EspacamentoAco>();

	@FXML
	private ComboBox<EspacamentoAco> acoYPositivoComParede = new ComboBox<EspacamentoAco>();

	@FXML
	private ComboBox<EspacamentoAco> acoYNegativoComParede = new ComboBox<EspacamentoAco>();
	
	@FXML
	private Rectangle retanguloComParede = new Rectangle();
	
	@FXML
	private Rectangle retanguloParedeLaje = new Rectangle();
	
	@FXML
	private Label avisoEspessuraLaje = new Label();
	
	@FXML
	private Label labelEscolhaBitolaEspacamento = new Label();
	
	@FXML
	private Label labelEscolhaBitolaEspacamentoComParede = new Label();
	
	@FXML
	private Label armadurasForaDaZonaDeInflucencia = new Label();
	
	@FXML
	private Label armadurasNaZonaDeInflucencia = new Label();

	private BigDecimal lajeDirecao = new BigDecimal(0.0);

	private BigDecimal caso = new BigDecimal(0.0);
	
	private List<Resultado> resultados;
	
	@FXML
	protected void initialize() {
		App.addOnChangeScreenListener(new App.OnChangeScreen() {
			
			@Override
			public void onScreenChanged(String novaTela, Object userData) {
				
			}
		});
	}
	
	@FXML
	private void btDetalhamentoArmaduras() throws IOException {
				
		App.escolheTela("secondary");
		
	}
	
	@SuppressWarnings("unchecked")
	public void btImprimirResultados() {
		
		try {
		
	    String caminhoJasper = "";
		
		if (this.paredeSim.selectedProperty().getValue() == false) {

			if (this.caso.doubleValue() == 1.0) {

				caminhoJasper = "semengaste.jasper";

			} else {
				caminhoJasper = "semparede.jasper";
			}
			
		}
		else {
			if (this.caso.doubleValue() == 1.0) {
				caminhoJasper = "semengastecomparede.jasper";
			} else {
				caminhoJasper = "comparede.jasper";
			}

		}
		
		@SuppressWarnings("rawtypes")
		Map parametros = new HashMap<String, Object>();
		parametros.put("acoXPositivo", this.acoXPositivo.getValue().toString());
		parametros.put("acoYPositivo", this.acoYPositivo.getValue().toString());
		parametros.put("acoXNegativo", this.acoXNegativo.getValue().toString());
		parametros.put("acoYNegativo", this.acoYNegativo.getValue().toString());
		
		parametros.put("checkXEsquerda", this.checkXEsquerda.selectedProperty().getValue());
		parametros.put("checkXDireita",  this.checkXDireita.selectedProperty().getValue());
		parametros.put("checkYCima",     this.checkYCima.selectedProperty().getValue());
		parametros.put("checkYBaixo",    this.checkYBaixo.selectedProperty().getValue());
		
		//Variáveis novas - Preencher quando for com parede em cima
		
		if (this.paredeSim.selectedProperty().getValue() == true) {

			parametros.put("acoXPositivoComParede", this.acoXPositivoComParede.getValue().toString());
			parametros.put("acoYPositivoComParede", this.acoYPositivoComParede.getValue().toString());
			parametros.put("acoXNegativoComParede", this.acoXNegativoComParede.getValue().toString());
			parametros.put("acoYNegativoComParede", this.acoYNegativoComParede.getValue().toString());
			
		}
		
		InputStream relJasper = getClass().getResourceAsStream(caminhoJasper);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(this.resultados);
		
		JasperPrint impressao = null;
		
		impressao = JasperFillManager.fillReport(relJasper, parametros, ds);
		
		JasperViewer viewer = new JasperViewer(impressao, false);
		
		viewer.setVisible(true);
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public void btNovaLaje() {

		this.ladoX.clear();
		this.ladoY.clear();
		this.espessuraLaje.clear();
		this.cargaAcidental.clear();
		this.espessuraParede.clear();
		this.alturaParede.clear();
		this.imprimeResultados.clear();
		this.acoXPositivo.getItems().clear();
		this.acoXNegativo.getItems().clear();
		this.acoYPositivo.getItems().clear();
		this.acoYNegativo.getItems().clear();
		this.acoXPositivoComParede.getItems().clear();
		this.acoXNegativoComParede.getItems().clear();
		this.acoYPositivoComParede.getItems().clear();
		this.acoYNegativoComParede.getItems().clear();
		
		this.avisoEspessuraLaje.setVisible(false);
		this.checkYCima.setSelected(false);
		this.engasteCheckYCima.setVisible(false);
		this.checkXDireita.setSelected(false);
		this.engasteCheckXDireita.setVisible(false);
		this.checkYBaixo.setSelected(false);
		this.engastecheckYBaixo.setVisible(false);
		this.checkXEsquerda.setSelected(false);
		this.engasteCheckXEsquerda.setVisible(false);
		this.paredeSim.setSelected(false);
		this.paredeNao.setSelected(false);
		this.tijoloFuradoSim.setSelected(false);
		this.tijoloFuradoNao.setSelected(false);
		this.psi0_3.setSelected(false);
		this.psi0_4.setSelected(false);
		this.psi0_5.setSelected(false);
		this.psi0_6.setSelected(false);
		this.agregadoBasaltoDiabasio.setSelected(false);
		this.agregadoGranitoGnaisse.setSelected(false);
		this.agregadoCalcario.setSelected(false);
		this.agregadoArenito.setSelected(false);
		this.tijoloFuradoSim.setVisible(true);
		this.tijoloFuradoNao.setVisible(true);
		this.espessuraParede.setVisible(true);
		this.alturaParede.setVisible(true);
		this.tijoloFurado.setVisible(true);
		this.tijoloFuradoS.setVisible(true);
		this.tijoloFuradoN.setVisible(true);
		this.textEspessuraDaParede.setVisible(true);
		this.textAlturaDaParede.setVisible(true);
		
		this.retanguloComParede.setVisible(false);
		this.retanguloParedeLaje.setVisible(false);
		this.acoPositivoEmXComParede.setVisible(false);
		this.acoNegativoEmXComParede.setVisible(false);
		this.acoPositivoEmYComParede.setVisible(false);
		this.acoNegativoEmYComParede.setVisible(false);
		this.acoXPositivoComParede.setVisible(false);
		this.acoXNegativoComParede.setVisible(false);
		this.acoYPositivoComParede.setVisible(false);
		this.acoYNegativoComParede.setVisible(false);
		this.labelEscolhaBitolaEspacamentoComParede.setVisible(false);
		this.armadurasForaDaZonaDeInflucencia.setVisible(false);
		this.armadurasNaZonaDeInflucencia.setVisible(false);
		this.labelEscolhaBitolaEspacamento.setText("Escolha a bitola e os espaçamentos");

	}
	
	public void btMontaDuasDirecoesSemParede() {

		this.ladoX.setText("3.4");
		this.ladoY.setText("4.8");
		this.espessuraLaje.setText("9");
		this.cargaAcidental.setText("5");
		this.paredeNao.setSelected(true);
		this.psi0_4.setSelected(true);
		this.agregadoBasaltoDiabasio.setSelected(true);
		this.checkYBaixo.setSelected(true);
		this.engastecheckYBaixo.setVisible(true);
		this.checkXEsquerda.setSelected(true);
		this.engasteCheckXEsquerda.setVisible(true);

		lajeSemParede = new LajeSemParede(this.ladoX, this.ladoY, this.espessuraLaje);

		materiais = new Materiais();

		lajeSemParede.setCargaAcidental(services.conversor(this.cargaAcidental));

	}

	public void btMontaDuasDirecoesComParede() {

		this.ladoX.setText("3");
		this.ladoY.setText("5.9");
		this.espessuraLaje.setText("10");
		this.espessuraParede.setText("12");
		this.alturaParede.setText("3");
		this.cargaAcidental.setText("2");
		this.paredeSim.setSelected(true);
		this.tijoloFuradoSim.setSelected(true);
		this.psi0_4.setSelected(true);
		this.agregadoBasaltoDiabasio.setSelected(true);
		this.checkYCima.setSelected(true);
		this.engasteCheckYCima.setVisible(true);
		this.checkYBaixo.setSelected(true);
		this.engastecheckYBaixo.setVisible(true);

		lajeComParede = new LajeComParede(this.ladoX, this.ladoY, this.espessuraLaje);
		lajeSemParede = new LajeSemParede(this.ladoX, this.ladoY, this.espessuraLaje);

		parede = new Parede(this.alturaParede, this.espessuraParede);

		materiais = new Materiais();

		lajeComParede.setCargaAcidental(services.conversor(this.cargaAcidental));
		lajeSemParede.setCargaAcidental(services.conversor(this.cargaAcidental));

	}
	
	public void btMontaUmaDirecaoComParede() {

		this.ladoX.setText("3");
		this.ladoY.setText("8");
		this.espessuraLaje.setText("10");
		this.espessuraParede.setText("14");
		this.alturaParede.setText("2.9");
		this.cargaAcidental.setText("1.5");
		this.paredeSim.setSelected(true);
		this.tijoloFuradoSim.setSelected(true);
		this.psi0_4.setSelected(true);
		this.agregadoGranitoGnaisse.setSelected(true);
		this.checkYCima.setSelected(true);
		this.engasteCheckYCima.setVisible(true);
		this.checkYBaixo.setSelected(true);
		this.engastecheckYBaixo.setVisible(true);

		lajeComParede = new LajeComParede(this.ladoX, this.ladoY, this.espessuraLaje);

		parede = new Parede(this.alturaParede, this.espessuraParede);

		materiais = new Materiais();

		lajeComParede.setCargaAcidental(services.conversor(this.cargaAcidental));

	}

	public void btMontaUmaDirecaoSemParede() {

		this.ladoX.setText("2.5");
		this.ladoY.setText("5.2");
		this.espessuraLaje.setText("11");
		this.cargaAcidental.setText("3");
		this.paredeNao.setSelected(true);
		this.psi0_4.setSelected(true);
		this.agregadoGranitoGnaisse.setSelected(true);
		this.checkYCima.setSelected(true);
		this.engasteCheckYCima.setVisible(true);
		this.checkXDireita.setSelected(true);
		this.engasteCheckXDireita.setVisible(true);
		this.checkYBaixo.setSelected(true);
		this.engastecheckYBaixo.setVisible(true);
		this.checkXEsquerda.setSelected(true);
		this.engasteCheckXEsquerda.setVisible(true);

		lajeSemParede = new LajeSemParede(this.ladoX, this.ladoY, this.espessuraLaje);

		materiais = new Materiais();

		lajeSemParede.setCargaAcidental(services.conversor(this.cargaAcidental));

	}

	public void validaCamposVisiveis() {

		if (this.paredeNao.selectedProperty().getValue()) {

			this.tijoloFuradoSim.setVisible(false);
			this.tijoloFuradoNao.setVisible(false);
			this.espessuraParede.setVisible(false);
			this.alturaParede.setVisible(false);
			this.tijoloFurado.setVisible(false);
			this.tijoloFuradoS.setVisible(false);
			this.tijoloFuradoN.setVisible(false);
			this.textEspessuraDaParede.setVisible(false);
			this.textAlturaDaParede.setVisible(false);
			this.retanguloComParede.setVisible(false);
			this.retanguloParedeLaje.setVisible(false);
			this.acoPositivoEmXComParede.setVisible(false);
			this.acoNegativoEmXComParede.setVisible(false);
			this.acoPositivoEmYComParede.setVisible(false);
			this.acoNegativoEmYComParede.setVisible(false);
			this.acoXPositivoComParede.setVisible(false);
			this.acoXNegativoComParede.setVisible(false);
			this.acoYPositivoComParede.setVisible(false);
			this.acoYNegativoComParede.setVisible(false);
			this.labelEscolhaBitolaEspacamentoComParede.setVisible(false);
			this.armadurasForaDaZonaDeInflucencia.setVisible(false);
			this.armadurasNaZonaDeInflucencia.setVisible(false);
			this.labelEscolhaBitolaEspacamento.setText("Escolha a bitola e os espaçamentos");

		} else {
			this.tijoloFuradoSim.setVisible(true);
			this.tijoloFuradoNao.setVisible(true);
			this.espessuraParede.setVisible(true);
			this.alturaParede.setVisible(true);
			this.tijoloFurado.setVisible(true);
			this.tijoloFuradoS.setVisible(true);
			this.tijoloFuradoN.setVisible(true);
			this.textEspessuraDaParede.setVisible(true);
			this.textAlturaDaParede.setVisible(true);
			this.tijoloFuradoSim.setVisible(true);
			this.tijoloFuradoNao.setVisible(true);
			this.retanguloComParede.setVisible(true);
			this.acoPositivoEmXComParede.setVisible(true);
			this.acoNegativoEmXComParede.setVisible(true);
			this.acoPositivoEmYComParede.setVisible(true);
			this.acoNegativoEmYComParede.setVisible(true);
			this.acoXPositivoComParede.setVisible(true);
			this.acoXNegativoComParede.setVisible(true);
			this.acoYPositivoComParede.setVisible(true);
			this.acoYNegativoComParede.setVisible(true);
			this.armadurasForaDaZonaDeInflucencia.setVisible(true);
			this.retanguloParedeLaje.setVisible(true);
			this.armadurasNaZonaDeInflucencia.setVisible(true);
			this.labelEscolhaBitolaEspacamentoComParede.setVisible(true);
			this.labelEscolhaBitolaEspacamentoComParede.setText("Escolha a bitola e os espaçamentos");
			this.armadurasNaZonaDeInflucencia.setText("Dentro da Zona de influência da parede");
			this.armadurasForaDaZonaDeInflucencia.setText("Fora da Zona de influência da parede");
			
		}
	}

	public void limpaResultados() {

		this.imprimeResultados.clear();

	}

	public void btCalcular() {
		
		this.avisoEspessuraLaje.setVisible(false);
		
		this.imprimeResultados.clear();
		
		resultados = new ArrayList<>();
		
		lajeComParede = new LajeComParede(this.ladoX, this.ladoY, this.espessuraLaje);
		
		lajeSemParede = new LajeSemParede(this.ladoX, this.ladoY, this.espessuraLaje);

		materiais = new Materiais();

		lajeSemParede.setCargaAcidental(services.conversor(this.cargaAcidental));
		
		lajeComParede.setCargaAcidental(services.conversor(this.cargaAcidental));
		
		if(lajeComParede.getEspessura().doubleValue() <= 5.0) {
			
			this.avisoEspessuraLaje.setVisible(true);
			
		}
		
		if(paredeSim.selectedProperty().getValue() == true) {
			
			parede = new Parede(this.alturaParede, this.espessuraParede);
			
		}
		
		calculaLambda();
		calculaLambdaInvertido();
		verificaDirecoes();

		if (this.lajeDirecao.doubleValue() == 2.0) {
			
			ajustaLambda();
			ajustaLambdaInvertido();
			
			if (paredeSim.selectedProperty().getValue() == true) {
				
				calculosLajeComParedeDuasDirecoes();

			} else {

				calculosLajeSemParedeDuasDirecoes();

			}
			

		} else {

			if (paredeSim.selectedProperty().getValue() == true) {
				
				lajeComParede.setLambda(new BigDecimal(99999.0));

				calculosLajeComParedeUmaDirecao();

			} else {

				lajeSemParede.setLambda(new BigDecimal(99999.0));
				
				calculosLajeSemParedeUmaDirecao();

			}
		}

	}
	
	public void verificaEspessuraMinimaDaLaje() {
		
		BigDecimal espessura = services.conversor(this.espessuraLaje);
		
		if(espessura.doubleValue() <= 5.0) {
			this.btCalcular.setVisible(false);
			this.avisoEspessuraLaje.setVisible(true);
		}
		else {
			this.btCalcular.setVisible(true);
			this.avisoEspessuraLaje.setVisible(false);
		}
		
	}

	public void calculosLajeSemParedeUmaDirecao() {
		
		String string = "-----------------------------\n"
					  + "LAJE SEM PAREDE - UMA DIRECAO\n"
					  + "-----------------------------\n";
		
		this.imprimeResultados.appendText(string);

		acoXPositivo.getItems().clear();
		acoXNegativo.getItems().clear();
		acoYPositivo.getItems().clear();
		acoYNegativo.getItems().clear();

		populaCoeficientes();
		populaEspacamentoAco();
		defineCaso();
		decideGamaTijolo();
		calculaAreaDeAcoMinima();
		cargaPermanenteSemParede();
		cargaTotalSemParede();
		definePsi();
		cargaDeServico();
		defineAgregado();
		calculaEci();
		calculaAlphaI();
		calculaEcs();
		calculaFctm();
		calculaFctkInf();
		calculaFctd();
		calculaMomentoDeFissuracao();
		defineECalculaEquacaoMomentoDeServicoSemParede();
		verificaMomentoDeFissuracao();
		
		if(this.avisoEspessuraLaje.isVisible() == true){
			
		}
		else {
			
			inercia();
			defineCoeficienteKParaUmaDirecao();
			calculaFlechaDeCurtaDuracao();
			calculaFlechaDeLongaDuracao();
			calculaFlechaAdmissivel();
			comparaFlecha();
			defineCoeficientes();
			calculaMomentoDeProjetoSemParede();
			calculaXSemParede();
			calculaAcoSemParede();
			calculaArmaduraDeDistribuicao();
			defineAreaDeAcoSemParede();
			calculaReacoes();
			decideReacoes();
			montaOpcoesDeEspacamentoX();
			montaOpcoesDeEspacamentoXNegativo();
			montaOpcoesDeEspacamentoY();
			montaOpcoesDeEspacamentoYNegativo();
			validaCamposVisiveis();
		}
	}

	public void calculosLajeComParedeUmaDirecao() {
		
		String string = "-----------------------------\n"
				      + "LAJE COM PAREDE - UMA DIRECAO\n"
				      + "-----------------------------\n";
	
		this.imprimeResultados.appendText(string);

		acoXPositivo.getItems().clear();
		acoXNegativo.getItems().clear();
		acoYPositivo.getItems().clear();
		acoYNegativo.getItems().clear();

		populaCoeficientes();
		populaEspacamentoAco();
		decideGamaTijolo();
		defineCaso();
		calculaAreaDeAcoMinima();
		areaDeInfluenciaDaParedePositiva();
		cargaPermanentePositivaComParede();
		cargaTotalPositivaComParede();
		definePsi();
		cargaDeServico();
		defineAgregado();
		calculaEci();
		calculaAlphaI();
		calculaEcs();
		calculaFctm();
		calculaFctkInf();
		calculaFctd();
		calculaMomentoDeFissuracao();
		defineECalculaEquacaoMomentoDeServicoUmaDirecaoComParede();
		verificaMomentoDeFissuracao();
		
		if(avisoEspessuraLaje.isVisible() == true){
			
		}
		else {
		
			inercia();
			defineCoeficienteKParaUmaDirecao();
			calculaFlechaDeCurtaDuracao();
			calculaFlechaDeLongaDuracao();
			calculaFlechaAdmissivel();
			comparaFlecha();
			areaDeInfluenciaDaParedeNegativa();
			cargaPermanenteNegativaComParede();
			cargaTotalNegativa();
			defineCoeficientes();
			calculaMomentoDeProjetoComParede();
			calculaXComParede();
			calculaAcoComParede();
			calculaArmaduraDeDistribuicao();
			defineAreaDeAcoComParede();
			calculaReacoes();
			decideReacoes();
			montaOpcoesDeEspacamentoX();
			montaOpcoesDeEspacamentoXNegativo();
			montaOpcoesDeEspacamentoY();
			montaOpcoesDeEspacamentoYNegativo();
			
			this.paredeSim.setSelected(false);
			calculosLajeSemParedeUmaDirecao();
			validaCamposVisiveis();
			this.paredeSim.setSelected(true);
		}
	}
	
	public void calculosLajeSemParedeDuasDirecoes() {
		
		String string = "-------------------------------\n"
				  	  + "LAJE SEM PAREDE - DUAS DIRECOES\n"
				  	  + "-------------------------------\n";
	
		this.imprimeResultados.appendText(string);

		acoXPositivo.getItems().clear();
		acoXNegativo.getItems().clear();
		acoYPositivo.getItems().clear();
		acoYNegativo.getItems().clear();
		
		populaCoeficientes();
		populaEspacamentoAco();
		populaCoeficientesMxKx();
		defineCaso();
		calculaAreaDeAcoMinima();
		cargaPermanenteSemParede();
		cargaTotalSemParede();
		definePsi();
		cargaDeServico();
		defineAgregado();
		calculaEci();
		calculaAlphaI();
		calculaEcs();
		calculaFctm();
		calculaFctkInf();
		calculaFctd();
		calculaMomentoDeFissuracao();
		calculaMomentoDeServicoDuasDirecoes();
		verificaMomentoDeFissuracao();
		
		if(avisoEspessuraLaje.isVisible() == true){
			
		}
		else {
		
			inercia();
			defineCoeficienteKParaDuasDirecoes();
			calculaFlechaDeCurtaDuracao();
			calculaFlechaDeLongaDuracao();
			calculaFlechaAdmissivel();
			comparaFlecha();
			defineCoeficientes();
			calculaMomentoDeProjetoSemParede();
			calculaXSemParede();
			calculaAcoSemParede();
			defineAreaDeAcoSemParede();
			calculaReacoes();
			decideReacoes();
			montaOpcoesDeEspacamentoX();
			montaOpcoesDeEspacamentoXNegativo();
			montaOpcoesDeEspacamentoY();
			montaOpcoesDeEspacamentoYNegativo();
			validaCamposVisiveis();
		}

	}
	
	public void calculosLajeComParedeDuasDirecoes() {
		
		String string = "-------------------------------\n"
			  	      + "LAJE COM PAREDE - DUAS DIRECOES\n"
			  	      + "-------------------------------\n";

		this.imprimeResultados.appendText(string);

		acoXPositivo.getItems().clear();
		acoXNegativo.getItems().clear();
		acoYPositivo.getItems().clear();
		acoYNegativo.getItems().clear();

		populaCoeficientes();
		populaEspacamentoAco();
		populaCoeficientesMxKx();
		defineCaso();
		decideGamaTijolo();
		calculaAreaDeAcoMinima();
		areaDeInfluenciaDaParedePositiva();
		cargaPermanentePositivaComParede();
		cargaTotalPositivaComParede();
		definePsi();
		cargaDeServico();
		defineAgregado();
		calculaEci();
		calculaAlphaI();
		calculaEcs();
		calculaFctm();
		calculaFctkInf();
		calculaFctd();
		calculaMomentoDeFissuracao();
		calculaMomentoDeServicoDuasDirecoes();
		verificaMomentoDeFissuracao();
		
		if(avisoEspessuraLaje.isVisible() == true){
			
		}
		else {
		
			inercia();
			defineCoeficienteKParaDuasDirecoes();
			calculaFlechaDeCurtaDuracao();
			calculaFlechaDeLongaDuracao();
			calculaFlechaAdmissivel();
			comparaFlecha();
			areaDeInfluenciaDaParedeNegativa();
			cargaPermanenteNegativaComParede();
			cargaTotalNegativa();
			defineCoeficientes();
			calculaMomentoDeProjetoComParede();
			calculaXComParede();
			calculaAcoComParede();
			defineAreaDeAcoComParede();
			calculaReacoes();
			decideReacoes();
			montaOpcoesDeEspacamentoX();
			montaOpcoesDeEspacamentoXNegativo();
			montaOpcoesDeEspacamentoY();
			montaOpcoesDeEspacamentoYNegativo();
			
			this.paredeSim.setSelected(false);
			calculosLajeSemParedeDuasDirecoes();
			validaCamposVisiveis();
			this.paredeSim.setSelected(true);
		}
	}

	public void decideGamaTijolo() {
		
		if(this.tijoloFuradoSim.selectedProperty().getValue() == true) {
			
			materiais.setGamaTijolo(new BigDecimal(13.0));
			
		} else {
			
			materiais.setGamaTijolo(new BigDecimal(18.0));
			
		}
		
	}
	
	public void calculaAreaDeAcoMinima() {

		if (paredeSim.selectedProperty().getValue() == true) {

			lajeComParede.calculaAcoMinimo();
			lajeSemParede.calculaAcoMinimo();
			lajeComParede.calculaAcoMinimoPositivo();
			lajeSemParede.calculaAcoMinimoPositivo();
			

		} else {

			lajeSemParede.calculaAcoMinimo();
			lajeSemParede.calculaAcoMinimoPositivo();
		}

	}
	
	public void calculaLambda() {
		
		lajeComParede.setLambda(lajeComParede.getLadoY().divide(lajeComParede.getLadoX(), MathContext.DECIMAL128));
		lajeSemParede.setLambda(lajeSemParede.getLadoY().divide(lajeSemParede.getLadoX(), MathContext.DECIMAL128));
		
		if(lajeComParede.getLambda().doubleValue() > 2.0) {
			
			lajeComParede.setLambda(new BigDecimal(99999.0));
			
		}
		if(lajeSemParede.getLambda().doubleValue() > 2.0) {
			
			lajeSemParede.setLambda(new BigDecimal(99999.0));
			
		}
		
	}
	
	public void calculaLambdaInvertido() {
		
		lajeComParede.setLambdaInvertido(lajeComParede.getLadoX().divide(lajeComParede.getLadoY(), MathContext.DECIMAL128));
		lajeSemParede.setLambdaInvertido(lajeSemParede.getLadoX().divide(lajeSemParede.getLadoY(), MathContext.DECIMAL128));
		
	}

	public void verificaDirecoes() {

		if (paredeSim.selectedProperty().getValue() == true) {

			if (lajeComParede.getLambda().doubleValue() > 2.0) {

				this.lajeDirecao = new BigDecimal(1.0);

			} else {

				this.lajeDirecao = new BigDecimal(2.0);
				
			}

		} else {

			if (lajeSemParede.getLambda().doubleValue() > 2.0) {

				this.lajeDirecao = new BigDecimal(1.0);

			} else {

				this.lajeDirecao = new BigDecimal(2.0);

			}

		}

	}

	public void areaDeInfluenciaDaParedePositiva() {

		parede.calculaAreaDeInfluenciaDaParedePositiva(lajeComParede);

		services.imprimeResultados("Area de influencia: "
				+ parede.getAreaDeInfluenciaPositiva().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void areaDeInfluenciaDaParedeNegativa() {

		parede.calculaAreaDeInfluenciaDaParedeNegativa(lajeComParede);

		services.imprimeResultados(
				"Area de influencia Negativa: "
						+ parede.getAreaDeInfluenciaNegativa().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void cargaPermanenteSemParede() {

		lajeSemParede.setCargaPermanente(lajeSemParede.calculaCargaPermanente(materiais));

		services.imprimeResultados("Carga permamente: "
				+ lajeSemParede.getCargaPermanente().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void cargaPermanentePositivaComParede() {

		lajeComParede.setCargaPermanentePositiva(lajeComParede.calculaCargaPermanentePositiva(materiais, parede));

		services.imprimeResultados(
				"Carga permamente: "
				+ lajeComParede.getCargaPermanentePositiva().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void cargaPermanenteNegativaComParede() {

		lajeComParede.setCargaPermanenteNegativa(lajeComParede.calculaCargaPermanenteNegativa(materiais, parede));

		services.imprimeResultados(
				"Carga permamente Negativa: "
						+ lajeComParede.getCargaPermanenteNegativa().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
	}

	public void cargaTotalSemParede() {

		BigDecimal cargaAcidental = services.conversor(this.cargaAcidental);

		lajeSemParede.setCargaTotal(lajeSemParede.getCargaPermanente().add(cargaAcidental));

		services.imprimeResultados(
				"Carga Total: " + lajeSemParede.getCargaTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void cargaTotalPositivaComParede() {

		BigDecimal cargaAcidental = services.conversor(this.cargaAcidental);

		lajeComParede.setCargaTotalPositiva(lajeComParede.getCargaPermanentePositiva().add(cargaAcidental));

		services.imprimeResultados(
				"Carga Total: " + lajeComParede.getCargaTotalPositiva().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void cargaTotalNegativa() {

		BigDecimal cargaAcidental = services.conversor(this.cargaAcidental);

		lajeComParede.setCargaTotalNegativa(lajeComParede.getCargaPermanenteNegativa().add(cargaAcidental));

		services.imprimeResultados("Carga Total Negativa: "
				+ lajeComParede.getCargaTotalNegativa().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void cargaDeServico() {

		if (paredeSim.selectedProperty().getValue() == true) {

			lajeComParede.setCargaDeServico(lajeComParede.calculaCargaDeServico(materiais, parede));

			services.imprimeResultados(
					"Carga de Servico: "
							+ lajeComParede.getCargaDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		} else {

			lajeSemParede.setCargaDeServico(lajeSemParede.calculaCargaDeServico(materiais));

			services.imprimeResultados("Carga de Servico: "
					+ lajeSemParede.getCargaDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

	}

	public void calculaEci() {

		Double fckConcreto = materiais.getFckConcreto().doubleValue();

		Double agregado = materiais.getAgregado().doubleValue();

		Double eci = agregado * 5600.0 * Math.sqrt(fckConcreto);

		materiais.setEci(services.doubleEmBigDecimal(eci));

		services.imprimeResultados("ECI: " + materiais.getEci().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void calculaAlphaI() {

		materiais.setAlphaI(new BigDecimal(0.8)
				.add(new BigDecimal(0.2).multiply(materiais.getFckConcreto().divide(new BigDecimal(80)))));

		services.imprimeResultados("Alpha I: " + materiais.getAlphaI().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
	}

	public void calculaEcs() {

		materiais.setEcs(materiais.getAlphaI().multiply(materiais.getEci()));

		materiais.setEcs(services.mpaParakNPorCmQuadrado(materiais.getEcs()));

		services.imprimeResultados("ECS: " + materiais.getEcs().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void calculaFctm() {

		Double fckConcreto = materiais.getFckConcreto().doubleValue();

		Double fctm = 0.3 * Math.pow(fckConcreto, (2.0 / 3.0));

		materiais.setFctm(services.doubleEmBigDecimal(fctm));

		services.imprimeResultados("Fctm: " + materiais.getFctm().setScale(3, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void calculaFctkInf() {

		materiais.setFctkInf(new BigDecimal(0.7).multiply(materiais.getFctm()));

		services.imprimeResultados("FctkInf: " + materiais.getFctkInf().setScale(3, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void calculaFctd() {

		materiais.setFctd(materiais.getFctkInf().divide(new BigDecimal(1.4)));

		materiais.setFctd(services.mpaParakNPorCmQuadrado(materiais.getFctd()));

		services.imprimeResultados("Fctd: " + materiais.getFctd().setScale(3, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void calculaMomentoDeFissuracao() {

		if (paredeSim.selectedProperty().getValue() == true) {

			BigDecimal espessuraLaje = services.metrosEmCentimetros(lajeComParede.getEspessura());

			BigDecimal fctm = services.mpaParakNPorCmQuadrado(materiais.getFctm());

			espessuraLaje = espessuraLaje.pow(2);

			lajeComParede.setMomentoDeFissuracao(
					new BigDecimal(0.25).multiply(fctm).multiply(new BigDecimal(100.0)).multiply(espessuraLaje));

			services.imprimeResultados(
					"Momento de fissuracao: "
							+ lajeComParede.getMomentoDeFissuracao().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		} else {

			BigDecimal espessuraLaje = services.metrosEmCentimetros(lajeSemParede.getEspessura());

			BigDecimal fctm = services.mpaParakNPorCmQuadrado(materiais.getFctm());

			espessuraLaje = espessuraLaje.pow(2);

			lajeSemParede.setMomentoDeFissuracao(
					new BigDecimal(0.25).multiply(fctm).multiply(new BigDecimal(100.0)).multiply(espessuraLaje));

			services.imprimeResultados(
					"Momento de fissuracao: "
							+ lajeSemParede.getMomentoDeFissuracao().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

	}

	public void verificaMomentoDeFissuracao() {

		if (paredeSim.selectedProperty().getValue() == true) {

			Double momentoDeServico = lajeComParede.getMomentoDeServico().doubleValue();

			Double momentoDeFissuracao = lajeComParede.getMomentoDeFissuracao().doubleValue();

			if (momentoDeServico < momentoDeFissuracao) {

				

			} else {

				this.avisoEspessuraLaje.setVisible(true);
				
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("Aumentar a espessura da laje" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				
			}
		} else {

			Double momentoDeServico = lajeSemParede.getMomentoDeServico().doubleValue();

			Double momentoDeFissuracao = lajeSemParede.getMomentoDeFissuracao().doubleValue();

			if (momentoDeServico < momentoDeFissuracao) {

				services.imprimeResultados("OK, Segue o baile!" + "\n", this.imprimeResultados, resultados);

			} else {

				this.avisoEspessuraLaje.setVisible(true);
				
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("Aumentar a espessura da laje" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);

			}

		}

	}

	public void inercia() {

		if (paredeSim.selectedProperty().getValue() == true) {

			lajeComParede.calculaInercia();

			services.imprimeResultados(
					"Inercia: " + lajeComParede.getInercia().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		} else {

			lajeSemParede.calculaInercia();

			services.imprimeResultados(
					"Inercia: " + lajeSemParede.getInercia().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}
	}

	public void calculaFlechaDeCurtaDuracao() {

		if (paredeSim.selectedProperty().getValue() == true) {

			BigDecimal cargaDeServico;
			BigDecimal numerador;
			BigDecimal denominador;
			BigDecimal ladoX = services.metrosEmCentimetros(lajeSemParede.getLadoX());

			cargaDeServico = lajeComParede.getCargaDeServico()
					.multiply(new BigDecimal(1.0).divide(new BigDecimal(10000.0)), MathContext.DECIMAL128);

			numerador = cargaDeServico.multiply(ladoX.pow(4), MathContext.DECIMAL128);

			denominador = materiais.getEcs().multiply(lajeComParede.getInercia(), MathContext.DECIMAL128);

			lajeComParede.setFlechaDeCurtaDuracao(lajeComParede.getCoeficienteK()
					.multiply(numerador, MathContext.DECIMAL128).divide(denominador, MathContext.DECIMAL128));

			services.imprimeResultados(
					"Flecha de curta duracao: "
							+ lajeComParede.getFlechaDeCurtaDuracao().setScale(4, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		} else {

			BigDecimal cargaDeServico;
			BigDecimal numerador;
			BigDecimal denominador;
			BigDecimal ladoX = services.metrosEmCentimetros(lajeSemParede.getLadoX());

			cargaDeServico = lajeSemParede.getCargaDeServico()
					.multiply(new BigDecimal(1.0).divide(new BigDecimal(10000.0)), MathContext.DECIMAL128);

			numerador = cargaDeServico.multiply(ladoX.pow(4), MathContext.DECIMAL128);

			denominador = materiais.getEcs().multiply(lajeSemParede.getInercia(), MathContext.DECIMAL128);

			lajeSemParede.setFlechaDeCurtaDuracao(lajeSemParede.getCoeficienteK()
					.multiply(numerador, MathContext.DECIMAL128).divide(denominador, MathContext.DECIMAL128));

			services.imprimeResultados(
					"Flecha de curta duracao: "
							+ lajeSemParede.getFlechaDeCurtaDuracao().setScale(4, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}
	}

	public void calculaFlechaDeLongaDuracao() {

		if (paredeSim.selectedProperty().getValue() == true) {

			lajeComParede.setFlechaDeLongaDuracao(
					new BigDecimal(2.32).multiply(lajeComParede.getFlechaDeCurtaDuracao(), MathContext.DECIMAL128));

			services.imprimeResultados(
					"Flecha de longa duracao: "
							+ lajeComParede.getFlechaDeLongaDuracao().setScale(4, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		} else {

			lajeSemParede.setFlechaDeLongaDuracao(
					new BigDecimal(2.32).multiply(lajeSemParede.getFlechaDeCurtaDuracao(), MathContext.DECIMAL128));

			services.imprimeResultados(
					"Flecha de longa duracao: "
							+ lajeSemParede.getFlechaDeLongaDuracao().setScale(4, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		}
	}

	public void calculaFlechaAdmissivel() {

		if (paredeSim.selectedProperty().getValue() == true) {

			lajeComParede.setFlechaAdmissivel(lajeComParede.getLadoX().divide(new BigDecimal(250.0)));

			services.imprimeResultados("Flecha Admissivel: "
					+ lajeComParede.getFlechaAdmissivel().setScale(4, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		} else {
			
			BigDecimal ladoX = services.metrosEmCentimetros(lajeSemParede.getLadoX());

			lajeSemParede.setFlechaAdmissivel(ladoX.divide(new BigDecimal(250.0)));

			services.imprimeResultados("Flecha Admissivel: "
					+ lajeSemParede.getFlechaAdmissivel().setScale(4, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		}
	}

	public void comparaFlecha() {

		if (paredeSim.selectedProperty().getValue() == true) {

			Double flechaAdmissivel = lajeComParede.getFlechaAdmissivel().doubleValue();

			Double flechaDeLongaDuracao = lajeComParede.getFlechaDeLongaDuracao().doubleValue();

			if (flechaAdmissivel > flechaDeLongaDuracao) {

				services.imprimeResultados("OK, Segue o baile! \n", this.imprimeResultados, resultados);

			} else {

				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("Aumentar a espessura da laje" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);

			}
		} else {

			Double flechaAdmissivel = lajeSemParede.getFlechaAdmissivel().doubleValue();

			Double flechaDeLongaDuracao = lajeSemParede.getFlechaDeLongaDuracao().doubleValue();

			if (flechaAdmissivel > flechaDeLongaDuracao) {

				services.imprimeResultados("OK, Segue o baile! \n", this.imprimeResultados, resultados);

			} else {

				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("Aumentar a espessura da laje" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("" + "\n", this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);
				services.imprimeResultados("-------------------------------------------------" + "\n",this.imprimeResultados, resultados);

			}

		}
	}

	public void definePsi() {

		if (paredeSim.selectedProperty().getValue() == true) {

			if (psi0_3.isSelected()) {
				lajeComParede.setPsi(new BigDecimal(0.3));
			}

			else if (psi0_4.isSelected()) {
				lajeComParede.setPsi(new BigDecimal(0.4));
			}

			else if (psi0_5.isSelected()) {
				lajeComParede.setPsi(new BigDecimal(0.5));
			}

			else {
				lajeComParede.setPsi(new BigDecimal(0.6));
			}
		} else {

			if (psi0_3.isSelected()) {
				lajeSemParede.setPsi(new BigDecimal(0.3));
			}

			else if (psi0_4.isSelected()) {
				lajeSemParede.setPsi(new BigDecimal(0.4));
			}

			else if (psi0_5.isSelected()) {
				lajeSemParede.setPsi(new BigDecimal(0.5));
			}

			else {
				lajeSemParede.setPsi(new BigDecimal(0.6));
			}
		}

	}

	public void defineECalculaEquacaoMomentoDeServicoUmaDirecaoComParede() {

		if (!checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = services.CentimetrosEmMetros(lajeComParede.getLadoX());

			lajeComParede.setMomentoDeServico(lajeComParede.getCargaDeServico().multiply(ladoX.pow(2))
					.divide(new BigDecimal(8.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (Pserv*Lx^2)/8 \n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeComParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

		else if (checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = services.CentimetrosEmMetros(lajeComParede.getLadoX());

			lajeComParede.setMomentoDeServico(new BigDecimal(9.0).multiply(lajeComParede.getCargaDeServico())
					.multiply(ladoX.pow(2)).divide(new BigDecimal(128.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (9*Pserv*Lx^2)/128\n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeComParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

		else if (!checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = services.CentimetrosEmMetros(lajeComParede.getLadoX());

			lajeComParede.setMomentoDeServico(new BigDecimal(9.0).multiply(lajeComParede.getCargaDeServico())
					.multiply(ladoX.pow(2)).divide(new BigDecimal(128.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (9*Pserv*Lx^2)/128\n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeComParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

		else if (checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = services.CentimetrosEmMetros(lajeComParede.getLadoX());

			lajeComParede.setMomentoDeServico(lajeComParede.getCargaDeServico().multiply(ladoX.pow(2))
					.divide(new BigDecimal(24.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (Pserv*Lx^2)/24\n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeComParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

	}
	
	public void calculaMomentoDeServicoDuasDirecoes() {
		
		//Tem que verificar onde tem engaste para usar a carga de serviço negativa.
		
		defineCoeficientesMxKx();
		
		if(paredeSim.selectedProperty().getValue() == true) {
			
			BigDecimal ladoX = lajeComParede.getLadoX();
			
			lajeComParede.setMomentoDeServico(this.coeficientesMxKx.getmX().
					                   		multiply(lajeComParede.getCargaDeServico().divide(new BigDecimal(10000.0))).
					                   		multiply(ladoX.pow(2)).
					                   		multiply(new BigDecimal(100.0)));
			services.imprimeResultados("Momento de Servico: "
					+ lajeComParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			
			
		} else {
			
			BigDecimal ladoX = services.metrosEmCentimetros(lajeSemParede.getLadoX());
			
			lajeSemParede.setMomentoDeServico(this.coeficientesMxKx.getmX().
					                   		multiply(lajeSemParede.getCargaDeServico().divide(new BigDecimal(10000.0))).
					                   		multiply(ladoX.pow(2)).
					                   		multiply(new BigDecimal(100.0)));
			services.imprimeResultados("Momento de Servico: "
					+ lajeSemParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		}
		
		
	}
	
	public void ajustaLambda() {

		if (paredeSim.selectedProperty().getValue() == true) {

			Double lambda = lajeComParede.getLambda().doubleValue();

			Double resto = lambda % 1;

			resto = resto * 10;

			resto = resto % 1;

			resto = resto * 10;

			if (resto < 5 && resto != 0.0) {

				resto = 5.0 / 100.0;

				lambda = lambda * 10;

				lambda = Math.floor(lambda);

				lambda = lambda / 10;

				lambda = lambda + resto;

			}

			else if (resto > 5 && resto != 0.0) {

				lambda = lambda * 10;

				lambda = Math.ceil(lambda);

				lambda = lambda / 10;

			}

			lajeComParede.setLambda(services.doubleEmBigDecimal(lambda));

			System.out.printf("Lambda = %.2f%n", lajeComParede.getLambda().doubleValue());

		} else {

			Double lambda = lajeSemParede.getLambda().doubleValue();

			Double resto = lambda % 1;

			resto = resto * 10;

			resto = resto % 1;

			resto = resto * 10;

			if (resto < 5 && resto != 0.0) {

				resto = 5.0 / 100.0;

				lambda = lambda * 10;

				lambda = Math.floor(lambda);

				lambda = lambda / 10;

				lambda = lambda + resto;

			}

			else if (resto > 5 && resto != 0.0) {

				lambda = lambda * 10;

				lambda = Math.ceil(lambda);

				lambda = lambda / 10;

			}

			lajeSemParede.setLambda(services.doubleEmBigDecimal(lambda));

			System.out.printf("Lambda = %.2f%n", lajeSemParede.getLambda().doubleValue());

		}

	}
	
	public void ajustaLambdaInvertido() {

		if (paredeSim.selectedProperty().getValue() == true) {

			Double lambda = lajeComParede.getLambdaInvertido().doubleValue();
			
			lambda = lambda * 10;
			
			lambda = Math.floor(lambda);
			
			lambda = lambda / 10;
			
			lajeComParede.setLambdaInvertido(services.doubleEmBigDecimal(lambda));
			
			System.out.printf("Lambda Invertido = %.2f%n", lajeComParede.getLambdaInvertido().doubleValue());
			
		} else {
			
			Double lambda = lajeSemParede.getLambdaInvertido().doubleValue();
			
			lambda = lambda * 10;
			
			lambda = Math.floor(lambda);
			
			lambda = lambda / 10;
			
			lajeSemParede.setLambdaInvertido(services.doubleEmBigDecimal(lambda));
			
			System.out.printf("Lambda Invertido = %.2f%n", lajeSemParede.getLambdaInvertido().doubleValue());
			
		}

	}
	
	public void defineCoeficientesMxKx() {
		
		if(paredeSim.selectedProperty().getValue() == true) {
		
		for (Coeficientes coef : coeficientesMxKxList) {
			if (coef.getCaso().doubleValue() == this.caso.doubleValue()
					&& coef.getLambda().doubleValue() == lajeComParede.getLambdaInvertido().doubleValue()) {

				this.coeficientesMxKx = coef;

				}
			}
		}
		else {
			for (Coeficientes coef : coeficientesMxKxList) {
				if (coef.getCaso().doubleValue() == this.caso.doubleValue()
						&& coef.getLambda().doubleValue() == lajeSemParede.getLambdaInvertido().doubleValue()) {

					this.coeficientesMxKx = coef;

					}
				}
		}

	}

	public void defineECalculaEquacaoMomentoDeServicoSemParede() {

		if (!checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = lajeSemParede.getLadoX();

			lajeSemParede.setMomentoDeServico(lajeSemParede.getCargaDeServico().multiply(ladoX.pow(2))
					.divide(new BigDecimal(8.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (Pserv*Lx^2)/8 \n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeSemParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

		else if (checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = lajeSemParede.getLadoX();

			lajeSemParede.setMomentoDeServico(new BigDecimal(9.0).multiply(lajeSemParede.getCargaDeServico())
					.multiply(ladoX.pow(2)).divide(new BigDecimal(128.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (9*Pserv*Lx^2)/128\n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeSemParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

		else if (!checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = lajeSemParede.getLadoX();

			lajeSemParede.setMomentoDeServico(new BigDecimal(9.0).multiply(lajeSemParede.getCargaDeServico())
					.multiply(ladoX.pow(2)).divide(new BigDecimal(128.0)).multiply(new BigDecimal(100.0)));

			services.imprimeResultados("Equacao M. Serv. = (9*Pserv*Lx^2)/128\n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeSemParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

		else if (checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			BigDecimal ladoX = lajeSemParede.getLadoX();

			lajeSemParede.setMomentoDeServico(lajeSemParede.getCargaDeServico()
					.multiply(ladoX.pow(2), MathContext.DECIMAL128).divide(new BigDecimal(24.0), MathContext.DECIMAL128)
					.multiply(new BigDecimal(100.0), MathContext.DECIMAL128));

			services.imprimeResultados("Equacao M. Serv. = (Pserv*Lx^2)/24\n", this.imprimeResultados, resultados);
			services.imprimeResultados("Momento de Servico: "
					+ lajeSemParede.getMomentoDeServico().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}

	}

	public void defineCaso() {

		if (!checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(1.0);

			services.imprimeResultados("CASO 1\n", this.imprimeResultados, resultados);

		} else if (!checkXEsquerda.selectedProperty().getValue() && checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(2.0);

			services.imprimeResultados("CASO 2\n", this.imprimeResultados, resultados);

		} else if (checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(2.0);

			services.imprimeResultados("CASO 2\n", this.imprimeResultados, resultados);

		} else if (!checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(3.0);

			services.imprimeResultados("CASO 3\n", this.imprimeResultados, resultados);

		} else if (!checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(3.0);

			services.imprimeResultados("CASO 3\n", this.imprimeResultados, resultados);

		} else if (checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(4.0);

			services.imprimeResultados("CASO 4\n", this.imprimeResultados, resultados);

		} else if (!checkXEsquerda.selectedProperty().getValue() && checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(4.0);

			services.imprimeResultados("CASO 4\n", this.imprimeResultados, resultados);

		}else if (checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(4.0);

			services.imprimeResultados("CASO 4\n", this.imprimeResultados, resultados);

		} else if (checkXEsquerda.selectedProperty().getValue() && checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(5.0);

			services.imprimeResultados("CASO 5\n", this.imprimeResultados, resultados);

		} else if (!checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(6.0);

			services.imprimeResultados("CASO 6\n", this.imprimeResultados, resultados);

		} else if (checkXEsquerda.selectedProperty().getValue() && checkXDireita.selectedProperty().getValue()
				&& checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(7.0);

			services.imprimeResultados("CASO 7\n", this.imprimeResultados, resultados);

		} else if (checkXEsquerda.selectedProperty().getValue() && checkXDireita.selectedProperty().getValue()
				&& !checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(7.0);

			services.imprimeResultados("CASO 7\n", this.imprimeResultados, resultados);

		} else if (!checkXEsquerda.selectedProperty().getValue() && checkXDireita.selectedProperty().getValue()
				&& checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(8.0);

			services.imprimeResultados("CASO 8\n", this.imprimeResultados, resultados);

		} else if (checkXEsquerda.selectedProperty().getValue() && !checkXDireita.selectedProperty().getValue()
				&& checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

			this.caso = new BigDecimal(8.0);

			services.imprimeResultados("CASO 8\n", this.imprimeResultados, resultados);

		} else {

			this.caso = new BigDecimal(9.0);

			services.imprimeResultados("CASO 9\n", this.imprimeResultados, resultados);
		}

	}

	public void defineAgregado() {

		if (agregadoBasaltoDiabasio.isSelected()) {
			materiais.setAgregado(materiais.getAgregadoBasaltoDiabasio());
		}

		else if (agregadoGranitoGnaisse.isSelected()) {
			materiais.setAgregado(materiais.getAgregadoGranitoGnaisse());
		}

		else if (agregadoCalcario.isSelected()) {
			materiais.setAgregado(materiais.getAgregadoCalcario());
		}

		else {
			materiais.setAgregado(materiais.getAgregadoArenito());
		}

	}
	
	public void defineCoeficienteKParaDuasDirecoes() {
		
		if(paredeSim.selectedProperty().getValue() == true) {
			
			lajeComParede.setCoeficienteK(this.coeficientesMxKx.getkX());
			
			services.imprimeResultados("Coeficiente K: "
					+ lajeComParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			
		} else {
			
			lajeSemParede.setCoeficienteK(this.coeficientesMxKx.getkX());
			
			services.imprimeResultados("Coeficiente K: "
					+ lajeSemParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			
		}
	
		
		
	}

	public void defineCoeficienteKParaUmaDirecao() {

		if (paredeSim.selectedProperty().getValue() == true) {

			if (!checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

				lajeComParede.setCoeficienteK(new BigDecimal(1.3));

				services.imprimeResultados("Coeficiente K: "
						+ lajeComParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

			else if (checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

				lajeComParede.setCoeficienteK(new BigDecimal(0.53));

				services.imprimeResultados("Coeficiente K: "
						+ lajeComParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

			else if (!checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

				lajeComParede.setCoeficienteK(new BigDecimal(0.53));

				services.imprimeResultados("Coeficiente K: "
						+ lajeComParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

			else if (checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

				lajeComParede.setCoeficienteK(new BigDecimal(0.26));

				services.imprimeResultados("Coeficiente K: "
						+ lajeComParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

		} else {

			if (!checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

				lajeSemParede.setCoeficienteK(new BigDecimal(1.3));

				services.imprimeResultados("Coeficiente K: "
						+ lajeSemParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

			else if (checkYCima.selectedProperty().getValue() && !checkYBaixo.selectedProperty().getValue()) {

				lajeSemParede.setCoeficienteK(new BigDecimal(0.53));

				services.imprimeResultados("Coeficiente K: "
						+ lajeSemParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

			else if (!checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

				lajeSemParede.setCoeficienteK(new BigDecimal(0.53));

				services.imprimeResultados("Coeficiente K: "
						+ lajeSemParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

			else if (checkYCima.selectedProperty().getValue() && checkYBaixo.selectedProperty().getValue()) {

				lajeSemParede.setCoeficienteK(new BigDecimal(0.26));

				services.imprimeResultados("Coeficiente K: "
						+ lajeSemParede.getCoeficienteK().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
						this.imprimeResultados, resultados);

			}

		}

	}

	public void defineCoeficientes() {

		if(paredeSim.selectedProperty().getValue() == true) {
		
		for (Coeficientes coef : coeficientesList) {
			if (coef.getCaso().doubleValue() == this.caso.doubleValue()
					&& coef.getLambda().doubleValue() == lajeComParede.getLambda().doubleValue()) {

				this.coeficientes = coef;

				}
			}
		}
		else {
			for (Coeficientes coef : coeficientesList) {
				if (coef.getCaso().doubleValue() == this.caso.doubleValue()
						&& coef.getLambda().doubleValue() == lajeSemParede.getLambda().doubleValue()) {

					this.coeficientes = coef;

					}
				}
		}

	}

	public BigDecimal defineCargaDoMomentoDeProjetoComParedeXEsquerda(BigDecimal carga) {

		if (checkXEsquerda.selectedProperty().getValue()) {

			carga = lajeComParede.getCargaTotalNegativa();

		} else {
			carga = lajeComParede.getCargaTotalPositiva();
		}

		return carga;

	}

	public BigDecimal defineCargaDoMomentoDeProjetoComParedeXDireita(BigDecimal carga) {

		if (checkXDireita.selectedProperty().getValue()) {

			carga = lajeComParede.getCargaTotalNegativa();

		} else {
			carga = lajeComParede.getCargaTotalPositiva();
		}

		return carga;

	}

	public BigDecimal defineCargaDoMomentoDeProjetoComParedeYCima(BigDecimal carga) {

		if (checkYCima.selectedProperty().getValue()) {

			carga = lajeComParede.getCargaTotalNegativa();

		} else {
			carga = lajeComParede.getCargaTotalPositiva();
		}

		return carga;

	}

	public BigDecimal defineCargaDoMomentoDeProjetoComParedeYBaixo(BigDecimal carga) {

		if (checkYBaixo.selectedProperty().getValue()) {

			carga = lajeComParede.getCargaTotalNegativa();

		} else {
			carga = lajeComParede.getCargaTotalPositiva();
		}

		return carga;

	}

	public void calculaMomentoDeProjetoComParede() {

		BigDecimal ladoX = services.CentimetrosEmMetros(lajeComParede.getLadoX());
		BigDecimal coeficienteDeSeguranca = new BigDecimal(1.4);

		lajeComParede.setMomentoDeProjetoX(coeficientes.getMiX().multiply(lajeComParede.getCargaTotalPositiva())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		lajeComParede.setMomentoDeProjetoXLinha(coeficientes.getMiX1().multiply(lajeComParede.getCargaTotalNegativa())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		lajeComParede.setMomentoDeProjetoY(coeficientes.getMiY().multiply(lajeComParede.getCargaTotalPositiva())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		lajeComParede.setMomentoDeProjetoYLinha(coeficientes.getMiY1().multiply(lajeComParede.getCargaTotalNegativa())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		services.imprimeResultados(
				"Mx = " + lajeComParede.getMomentoDeProjetoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"My = " + lajeComParede.getMomentoDeProjetoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"Mx' = " + lajeComParede.getMomentoDeProjetoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"My' = " + lajeComParede.getMomentoDeProjetoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}
	
	public void calculaMomentoDeProjetoSemParede() {

		BigDecimal ladoX = lajeSemParede.getLadoX();
		BigDecimal coeficienteDeSeguranca = new BigDecimal(1.4);

		lajeSemParede.setMomentoDeProjetoX(coeficientes.getMiX().multiply(lajeSemParede.getCargaTotal())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		lajeSemParede.setMomentoDeProjetoXLinha(coeficientes.getMiX1().multiply(lajeSemParede.getCargaTotal())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		lajeSemParede.setMomentoDeProjetoY(coeficientes.getMiY().multiply(lajeSemParede.getCargaTotal())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		lajeSemParede.setMomentoDeProjetoYLinha(coeficientes.getMiY1().multiply(lajeSemParede.getCargaTotal())
				.multiply(ladoX.pow(2)).multiply(coeficienteDeSeguranca));

		services.imprimeResultados(
				"Mx = " + lajeSemParede.getMomentoDeProjetoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"My = " + lajeSemParede.getMomentoDeProjetoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"Mx' = " + lajeSemParede.getMomentoDeProjetoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"My' = " + lajeSemParede.getMomentoDeProjetoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}
	
	@SuppressWarnings("removal")
	public void calculaXSemParede() {

		lajeSemParede.calculaD();
		materiais.calculaFcd();

		Double numerador = new Double(0.0);
		Double denominador = new Double(0.0);
		Double raiz = new Double(0.0);
		Double x = new Double(0.0);

		numerador = 2 * lajeSemParede.getMomentoDeProjetoX().doubleValue();
		denominador = 0.85 * materiais.getFcd().doubleValue() * 100 * lajeSemParede.getD().pow(2).doubleValue();

		raiz = Math.sqrt(1 - (numerador / denominador));

		if(raiz == 1.0) {
			x = 0.0;
		}
		else {
			x = lajeSemParede.getD().doubleValue() / 0.8 * (1 - raiz);
		}
		
		lajeSemParede.setX(services.doubleEmBigDecimal(x));
		
		numerador = new Double(0.0);
		numerador = 2 * lajeSemParede.getMomentoDeProjetoXLinha().doubleValue();

		raiz = new Double(0.0);
		raiz = Math.sqrt(1 - (numerador / denominador));

		if(raiz == 1.0) {
			x = 0.0;
		}
		else {
			x = lajeSemParede.getD().doubleValue() / 0.8 * (1 - raiz);
		}
		
		lajeSemParede.setxLinha(services.doubleEmBigDecimal(x));

		numerador = new Double(0.0);
		numerador = 2 * lajeSemParede.getMomentoDeProjetoY().doubleValue();

		raiz = new Double(0.0);
		raiz = Math.sqrt(1 - (numerador / denominador));
		
		if(raiz == 1.0) {
			x = 0.0;
		}
		else {
			x = lajeSemParede.getD().doubleValue() / 0.8 * (1 - raiz);
		}
		
		lajeSemParede.setY(services.doubleEmBigDecimal(x));

		numerador = new Double(0.0);
		numerador = 2 * lajeSemParede.getMomentoDeProjetoYLinha().doubleValue();

		raiz = new Double(0.0);
		raiz = Math.sqrt(1 - (numerador / denominador));
		
		if(raiz == 1.0) {
			x = 0.0;
		}
		else {
			x = lajeSemParede.getD().doubleValue() / 0.8 * (1 - raiz);
		}
		
		lajeSemParede.setyLinha(services.doubleEmBigDecimal(x));

		services.imprimeResultados("X = " + lajeSemParede.getX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados("Y = " + lajeSemParede.getY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados("X' = " + lajeSemParede.getxLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados("Y' = " + lajeSemParede.getyLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void calculaXComParede() {

		lajeComParede.calculaD();
		materiais.calculaFcd();

		Double numerador;
		Double denominador;
		Double raiz;
		Double x;

		numerador = 2 * lajeComParede.getMomentoDeProjetoX().doubleValue();
		denominador = 0.85 * materiais.getFcd().doubleValue() * 100 * lajeComParede.getD().pow(2).doubleValue();

		raiz = Math.sqrt(1 - (numerador / denominador));

		x = lajeComParede.getD().doubleValue() / 0.8 * (1 - raiz);

		lajeComParede.setX(services.doubleEmBigDecimal(x));

		numerador = 2 * lajeComParede.getMomentoDeProjetoXLinha().doubleValue();

		raiz = Math.sqrt(1 - (numerador / denominador));

		x = lajeComParede.getD().doubleValue() / 0.8 * (1 - raiz);

		lajeComParede.setxLinha(services.doubleEmBigDecimal(x));

		numerador = 2 * lajeComParede.getMomentoDeProjetoY().doubleValue();

		raiz = Math.sqrt(1 - (numerador / denominador));

		x = lajeComParede.getD().doubleValue() / 0.8 * (1 - raiz);

		lajeComParede.setY(services.doubleEmBigDecimal(x));

		numerador = 2 * lajeComParede.getMomentoDeProjetoYLinha().doubleValue();

		raiz = Math.sqrt(1 - (numerador / denominador));

		x = lajeComParede.getD().doubleValue() / 0.8 * (1 - raiz);

		lajeComParede.setyLinha(services.doubleEmBigDecimal(x));

		services.imprimeResultados("X = " + lajeComParede.getX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados("Y = " + lajeComParede.getY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados("X' = " + lajeComParede.getxLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados("Y' = " + lajeComParede.getyLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}
	
	public void calculaAcoSemParede() {

		lajeSemParede.setAreaDeAcoX(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeSemParede.getX(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));

		lajeSemParede.setAreaDeAcoXLinha(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeSemParede.getxLinha(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));

		lajeSemParede.setAreaDeAcoY(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeSemParede.getY(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));

		lajeSemParede.setAreaDeAcoYLinha(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeSemParede.getyLinha(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));

		services.imprimeResultados(
				"Area de aco X = " + lajeSemParede.getAreaDeAcoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		services.imprimeResultados(
				"Area de aco Y = " + lajeSemParede.getAreaDeAcoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		services.imprimeResultados(
				"Area de aco X' = " + lajeSemParede.getAreaDeAcoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		services.imprimeResultados(
				"Area de aco Y' = " + lajeSemParede.getAreaDeAcoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}
	
	
	public void calculaAcoComParede() {

		lajeComParede.setAreaDeAcoX(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeComParede.getX(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));
		
		lajeComParede.setAreaDeAcoY(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeComParede.getY(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));

		lajeComParede.setAreaDeAcoXLinha(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeComParede.getxLinha(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));

		lajeComParede.setAreaDeAcoYLinha(
				new BigDecimal(0.85).multiply(new BigDecimal(0.8)).multiply(materiais.getFcd(), MathContext.DECIMAL128)
						.multiply(new BigDecimal(100.0)).multiply(lajeComParede.getyLinha(), MathContext.DECIMAL128)
						.divide(materiais.getFydAco(), MathContext.DECIMAL128));
		
		services.imprimeResultados(
				"Area de aco X = " + lajeComParede.getAreaDeAcoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		services.imprimeResultados(
				"Area de aco Y = " + lajeComParede.getAreaDeAcoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		services.imprimeResultados(
				"Area de aco X' = " + lajeComParede.getAreaDeAcoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		services.imprimeResultados(
				"Area de aco Y' = " + lajeComParede.getAreaDeAcoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);

	}

	public void defineAreaDeAcoSemParede() {

		if (this.lajeDirecao.doubleValue() == 1.0) {

			if (lajeSemParede.getAreaDeAcoX().doubleValue() < lajeSemParede.getAreaDeAcoMinima().doubleValue()) {

				lajeSemParede.setAreaDeAcoX(lajeSemParede.getAreaDeAcoMinima());

			}

			if (lajeSemParede.getAreaDeAcoXLinha().doubleValue() < lajeSemParede.getAreaDeAcoMinima().doubleValue()) {

				lajeSemParede.setAreaDeAcoXLinha(lajeSemParede.getAreaDeAcoMinima());

			}

			if (lajeSemParede.getAreaDeAcoYLinha().doubleValue() < lajeSemParede.getAreaDeAcoMinima().doubleValue()) {

				lajeSemParede.setAreaDeAcoYLinha(lajeSemParede.getAreaDeAcoMinima());

			}

			services.imprimeResultados("Área de aço mínima = "
					+ lajeSemParede.getAreaDeAcoMinima().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final X = "
					+ lajeSemParede.getAreaDeAcoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final Y (distribuicao) = "
					+ lajeSemParede.getAreaDeAcoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final X' = "
					+ lajeSemParede.getAreaDeAcoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final Y' = "
					+ lajeSemParede.getAreaDeAcoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		} else {

			if (lajeSemParede.getAreaDeAcoX().doubleValue() < lajeSemParede.getAreaDeAcoMinimaPositiva().doubleValue()) {

				lajeSemParede.setAreaDeAcoX(lajeSemParede.getAreaDeAcoMinimaPositiva());

			}

			if (lajeSemParede.getAreaDeAcoXLinha().doubleValue() < lajeSemParede.getAreaDeAcoMinima()
					.doubleValue()) {

				lajeSemParede.setAreaDeAcoXLinha(lajeSemParede.getAreaDeAcoMinima());

			}

			if (lajeSemParede.getAreaDeAcoY().doubleValue() < lajeSemParede.getAreaDeAcoMinimaPositiva().doubleValue()) {

				lajeSemParede.setAreaDeAcoY(lajeSemParede.getAreaDeAcoMinimaPositiva());

			}

			if (lajeSemParede.getAreaDeAcoYLinha().doubleValue() < lajeSemParede.getAreaDeAcoMinima()
					.doubleValue()) {

				lajeSemParede.setAreaDeAcoYLinha(lajeSemParede.getAreaDeAcoMinima());

			}

			services.imprimeResultados("Área de aço mínima = "
					+ lajeSemParede.getAreaDeAcoMinima().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Área de aço mínima positiva = "
					+ lajeSemParede.getAreaDeAcoMinimaPositiva().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final X = "
					+ lajeSemParede.getAreaDeAcoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final Y = "
					+ lajeSemParede.getAreaDeAcoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final X' = "
					+ lajeSemParede.getAreaDeAcoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final Y' = "
					+ lajeSemParede.getAreaDeAcoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}
	}
	
	public void defineAreaDeAcoComParede() {

		if (this.lajeDirecao.doubleValue() == 1.0) {

			if (lajeComParede.getAreaDeAcoX().doubleValue() < lajeComParede.getAreaDeAcoMinima().doubleValue()) {

				lajeComParede.setAreaDeAcoX(lajeComParede.getAreaDeAcoMinima());

			}
			
			if (lajeComParede.getAreaDeAcoXLinha().doubleValue() < lajeComParede.getAreaDeAcoMinima().doubleValue()) {

				lajeComParede.setAreaDeAcoXLinha(lajeComParede.getAreaDeAcoMinima());

			}

			if (lajeComParede.getAreaDeAcoYLinha().doubleValue() < lajeComParede.getAreaDeAcoMinima().doubleValue()) {

				lajeComParede.setAreaDeAcoYLinha(lajeComParede.getAreaDeAcoMinima());

			}

			services.imprimeResultados("Área de aço mínima = "
					+ lajeComParede.getAreaDeAcoMinima().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final X = "
					+ lajeComParede.getAreaDeAcoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados(
					"Area de aco final Y (distribuicao) = "
							+ lajeComParede.getAreaDeAcoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados(
					"Area de aco final X' = "
							+ lajeComParede.getAreaDeAcoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados(
					"Area de aco final Y' = "
							+ lajeComParede.getAreaDeAcoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

		}
		else {
			
			if (lajeComParede.getAreaDeAcoX().doubleValue() < lajeComParede.getAreaDeAcoMinimaPositiva().doubleValue()) {

				lajeComParede.setAreaDeAcoX(lajeComParede.getAreaDeAcoMinimaPositiva());

			}
			
			if (lajeComParede.getAreaDeAcoXLinha().doubleValue() < lajeComParede.getAreaDeAcoMinima().doubleValue()) {

				lajeComParede.setAreaDeAcoXLinha(lajeComParede.getAreaDeAcoMinima());

			}
			
			if (lajeComParede.getAreaDeAcoY().doubleValue() < lajeComParede.getAreaDeAcoMinimaPositiva().doubleValue()) {

				lajeComParede.setAreaDeAcoY(lajeComParede.getAreaDeAcoMinimaPositiva());

			}

			if (lajeComParede.getAreaDeAcoYLinha().doubleValue() < lajeComParede.getAreaDeAcoMinima().doubleValue()) {

				lajeComParede.setAreaDeAcoYLinha(lajeComParede.getAreaDeAcoMinima());

			}

			services.imprimeResultados("Área de aço mínima = "
					+ lajeComParede.getAreaDeAcoMinima().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Área de aço mínima positiva = "
					+ lajeComParede.getAreaDeAcoMinimaPositiva().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados("Area de aco final X = "
					+ lajeComParede.getAreaDeAcoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados(
					"Area de aco final Y = "
					+ lajeComParede.getAreaDeAcoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);			
			services.imprimeResultados(
					"Area de aco final X' = "
					+ lajeComParede.getAreaDeAcoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			services.imprimeResultados(
					"Area de aco final Y' = "
					+ lajeComParede.getAreaDeAcoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			
			
		}
	}

	public void calculaArmaduraDeDistribuicao() {

		if(paredeSim.selectedProperty().getValue() == true) {
		
		BigDecimal calculoAreaDeAcoPrincipal;
		BigDecimal calculoAreaDeAcoMinima;
		BigDecimal definicaoNBR = new BigDecimal(0.9);

		calculoAreaDeAcoPrincipal = lajeComParede.getAreaDeAcoX().divide(new BigDecimal(5.0));
		calculoAreaDeAcoMinima = lajeComParede.getAreaDeAcoMinima().divide(new BigDecimal(2.0));

		if (calculoAreaDeAcoPrincipal.doubleValue() > calculoAreaDeAcoMinima.doubleValue()
				&& calculoAreaDeAcoPrincipal.doubleValue() > definicaoNBR.doubleValue()) {

			lajeComParede.setAreaDeAcoY(calculoAreaDeAcoPrincipal);

		} else if (calculoAreaDeAcoMinima.doubleValue() > calculoAreaDeAcoPrincipal.doubleValue()
				&& calculoAreaDeAcoMinima.doubleValue() > definicaoNBR.doubleValue()) {

			lajeComParede.setAreaDeAcoY(calculoAreaDeAcoMinima);

		} else {
			
			lajeComParede.setAreaDeAcoY(definicaoNBR);
			
			}
		}
		else {
			
			BigDecimal calculoAreaDeAcoPrincipal;
			BigDecimal calculoAreaDeAcoMinima;
			BigDecimal definicaoNBR = new BigDecimal(0.9);

			calculoAreaDeAcoPrincipal = lajeSemParede.getAreaDeAcoX().divide(new BigDecimal(5.0));
			calculoAreaDeAcoMinima = lajeSemParede.getAreaDeAcoMinima().divide(new BigDecimal(2.0));

			if (calculoAreaDeAcoPrincipal.doubleValue() > calculoAreaDeAcoMinima.doubleValue()
					&& calculoAreaDeAcoPrincipal.doubleValue() > definicaoNBR.doubleValue()) {

				lajeSemParede.setAreaDeAcoY(calculoAreaDeAcoPrincipal);

			} else if (calculoAreaDeAcoMinima.doubleValue() > calculoAreaDeAcoPrincipal.doubleValue()
					&& calculoAreaDeAcoMinima.doubleValue() > definicaoNBR.doubleValue()) {

				lajeSemParede.setAreaDeAcoY(calculoAreaDeAcoMinima);

			} else {
				
				lajeSemParede.setAreaDeAcoY(definicaoNBR);
				
				}
			
		}

	}
	
	public void calculaReacoes() {
		
		if(this.paredeSim.selectedProperty().getValue() == true) {
			
			BigDecimal ladoX = services.CentimetrosEmMetros(lajeComParede.getLadoX());
			
			lajeComParede.setReacaoX(this.coeficientes.getKx().
									multiply(lajeComParede.getCargaTotalPositiva()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
			lajeComParede.setReacaoXLinha(this.coeficientes.getKx1().
									multiply(lajeComParede.getCargaTotalPositiva()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
			lajeComParede.setReacaoY(this.coeficientes.getKy().
									multiply(lajeComParede.getCargaTotalPositiva()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
			lajeComParede.setReacaoYLinha(this.coeficientes.getKy1().
									multiply(lajeComParede.getCargaTotalPositiva()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));

		}
		else {
			
			BigDecimal ladoX = lajeSemParede.getLadoX();
			
			lajeSemParede.setReacaoX(this.coeficientes.getKx().
									multiply(lajeSemParede.getCargaTotal()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
			lajeSemParede.setReacaoXLinha(this.coeficientes.getKx1().
									multiply(lajeSemParede.getCargaTotal()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
			lajeSemParede.setReacaoY(this.coeficientes.getKy().
									multiply(lajeSemParede.getCargaTotal()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
			lajeSemParede.setReacaoYLinha(this.coeficientes.getKy1().
									multiply(lajeSemParede.getCargaTotal()).
									multiply(ladoX).
									divide(new BigDecimal(10.0)));
			
		}
		
	}
	
	public void decideReacoes() {
		
		if(this.paredeSim.selectedProperty().getValue() == true) {
		
		if(lajeComParede.getReacaoX().doubleValue() == 0.0) {
			
			lajeComParede.setReacaoX(lajeComParede.getReacaoXLinha());
			
		}
		
		if(lajeComParede.getReacaoXLinha().doubleValue() == 0.0) {
			
			lajeComParede.setReacaoXLinha(lajeComParede.getReacaoX());
			
		}
		
		if(lajeComParede.getReacaoY().doubleValue() == 0.0) {
			
			lajeComParede.setReacaoY(lajeComParede.getReacaoYLinha());
			
		}
		
		if(lajeComParede.getReacaoYLinha().doubleValue() == 0.0) {
			
			lajeComParede.setReacaoYLinha(lajeComParede.getReacaoY());
			
		}
		
		services.imprimeResultados(
				"Reacao X = " + lajeComParede.getReacaoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"Reacao Y = " + lajeComParede.getReacaoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"Reacao X' = " + lajeComParede.getReacaoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		
		services.imprimeResultados(
				"Reacao Y' = " + lajeComParede.getReacaoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
				this.imprimeResultados, resultados);
		}
		else {
			
			if(lajeSemParede.getReacaoX().doubleValue() == 0.0) {
				
				lajeSemParede.setReacaoX(lajeSemParede.getReacaoXLinha());
				
			}
			
			if(lajeSemParede.getReacaoXLinha().doubleValue() == 0.0) {
				
				lajeSemParede.setReacaoXLinha(lajeSemParede.getReacaoX());
				
			}
			
			if(lajeSemParede.getReacaoY().doubleValue() == 0.0) {
				
				lajeSemParede.setReacaoY(lajeSemParede.getReacaoYLinha());
				
			}
			
			if(lajeSemParede.getReacaoYLinha().doubleValue() == 0.0) {
				
				lajeSemParede.setReacaoYLinha(lajeSemParede.getReacaoY());
				
			}
			
			services.imprimeResultados(
					"Reacao X = " + lajeSemParede.getReacaoX().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			
			services.imprimeResultados(
					"Reacao Y = " + lajeSemParede.getReacaoY().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
			
			services.imprimeResultados(
					"Reacao X' = " + lajeSemParede.getReacaoXLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);

			services.imprimeResultados(
					"Reacao Y' = " + lajeSemParede.getReacaoYLinha().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "\n",
					this.imprimeResultados, resultados);
		}
	}
	
	private List<EspacamentoAco>  clonarListaEspacamentoAco() {
		
		
		ArrayList<EspacamentoAco> lista = new ArrayList<>();
		
		for (EspacamentoAco espacamentoAco : this.espacamentoAcoList) {
			
			EspacamentoAco temp = new EspacamentoAco(espacamentoAco);	
			
			lista.add(temp);
			
		}

		return lista;
		
	}

	public void montaOpcoesDeEspacamentoX() {

		if(paredeSim.selectedProperty().getValue() == true) {
		
			List<EspacamentoAco> espacamentoX = new ArrayList<>();
			ObservableList<EspacamentoAco> observableList;
			

		for (EspacamentoAco espacamentoAco : this.clonarListaEspacamentoAco()) {
			
			if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeComParede.getAreaDeAcoX().doubleValue()
					&& espacamentoAco.getAreaDeAco().doubleValue() <= lajeComParede.getAreaDeAcoX().doubleValue()
							+ 0.3) {
				
				Double quantidade = calculaQuantidadeDeBarras(parede.getAreaDeInfluenciaPositiva().doubleValue(), 
						                                      espacamentoAco.getEspacamento().doubleValue());
				
				espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
				espacamentoAco.setEixo("XComparede");
						
				espacamentoX.add(espacamentoAco);
				

				}
			}
		   
		       
		
		       observableList = FXCollections.observableArrayList(espacamentoX);
		
		       acoXPositivoComParede.setItems(observableList);
		
		}
		
		else {
			
			for (EspacamentoAco espacamentoAco : this.clonarListaEspacamentoAco()) {
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeSemParede.getAreaDeAcoX().doubleValue()
						&& espacamentoAco.getAreaDeAco().doubleValue() <= lajeSemParede.getAreaDeAcoX().doubleValue()
								+ 0.3) {
					
					Double quantidade = calculaQuantidadeDeBarras(lajeSemParede.getLadoY().doubleValue(),
                            espacamentoAco.getEspacamento().doubleValue());
					
					espacamentoAco.setEixo("XSemparede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
									
					acoXPositivo.getItems().add(espacamentoAco);

					}
				
				}
		}
		

	}

	public void montaOpcoesDeEspacamentoXNegativo() {
		


		if (paredeSim.selectedProperty().getValue() == true) {

			for (EspacamentoAco espacamentoAco : this.clonarListaEspacamentoAco()) {
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeComParede.getAreaDeAcoXLinha().doubleValue()	&&
						espacamentoAco.getAreaDeAco().doubleValue() <= lajeComParede.getAreaDeAcoXLinha().doubleValue() + 0.3) {
					
					Double quantidade = calculaQuantidadeDeBarras(parede.getAreaDeInfluenciaNegativa().doubleValue(),
                            espacamentoAco.getEspacamento().doubleValue());
					
					espacamentoAco.setEixo("XNegativoComParede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
					
					acoXNegativoComParede.getItems().add(espacamentoAco);

				}
			}
		}
		else {
			

			for (EspacamentoAco espacamentoAco : this.clonarListaEspacamentoAco()) {
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeSemParede.getAreaDeAcoXLinha().doubleValue()	&&
						espacamentoAco.getAreaDeAco().doubleValue() <= lajeSemParede.getAreaDeAcoXLinha().doubleValue() + 0.3) {
					
					Double quantidade = calculaQuantidadeDeBarras(lajeSemParede.getLadoY().doubleValue(),
                            espacamentoAco.getEspacamento().doubleValue());
					
					espacamentoAco.setEixo("XNegativoSemParede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
					
					acoXNegativo.getItems().add(espacamentoAco);

				}
			}
		}
		


	}

	public void montaOpcoesDeEspacamentoY() {


		if (paredeSim.selectedProperty().getValue() == true) {

			for (EspacamentoAco espacamentoAco : this.clonarListaEspacamentoAco()) {
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeComParede.getAreaDeAcoY().doubleValue()
						&& espacamentoAco.getAreaDeAco().doubleValue() <= lajeComParede.getAreaDeAcoY().doubleValue() + 0.3) {
					
					Double quantidade = calculaQuantidadeDeBarras(lajeComParede.getLadoX().divide(new BigDecimal(100.0)).doubleValue(),
                            	                                  espacamentoAco.getEspacamento().doubleValue());
					
					espacamentoAco.setEixo("YComParede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
					
					acoYPositivoComParede.getItems().add(espacamentoAco);

				}
			}
		}
		else {
			

			for (EspacamentoAco espacamentoAco : this.clonarListaEspacamentoAco()) {
				
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeSemParede.getAreaDeAcoY().doubleValue()
						&& espacamentoAco.getAreaDeAco().doubleValue() <= lajeSemParede.getAreaDeAcoY().doubleValue() + 0.3) {

					Double quantidade = calculaQuantidadeDeBarras(lajeComParede.getLadoX().divide(new BigDecimal(100.0)).doubleValue(),
                                                                  espacamentoAco.getEspacamento().doubleValue());
					
					espacamentoAco.setEixo("YSemParede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
					
					acoYPositivo.getItems().add(espacamentoAco);

				}
			}
			
		}
		

	}

	public void montaOpcoesDeEspacamentoYNegativo() {
		


		if (paredeSim.selectedProperty().getValue() == true) {
			
		    ArrayList<EspacamentoAco> espacamentoAcoListTemp = new ArrayList<>();
		    espacamentoAcoListTemp.addAll(espacamentoAcoList);

			for (EspacamentoAco espacamentoAco : espacamentoAcoListTemp) {
				
		
				
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeComParede.getAreaDeAcoYLinha().doubleValue() &&
						espacamentoAco.getAreaDeAco().doubleValue() <= lajeComParede.getAreaDeAcoYLinha().doubleValue() + 0.3) {
					
					
                   
					Double quantidade = calculaQuantidadeDeBarras(lajeComParede.getLadoX().divide(new BigDecimal(100.0)).doubleValue(),
                            espacamentoAco.getEspacamento().doubleValue());

					espacamentoAco.setEixo("YNegativoComParede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));

					acoYNegativoComParede.getItems().add(espacamentoAco);
					 
				}
		
				
			}
		}
		
		
		else {
			
			
		    ArrayList<EspacamentoAco> espacamentoAcoListTemp = new ArrayList<>();
		    espacamentoAcoListTemp.addAll(espacamentoAcoList);
			
			
			for (EspacamentoAco espacamentoAco : espacamentoAcoListTemp) {
				
				if (espacamentoAco.getAreaDeAco().doubleValue() >= lajeSemParede.getAreaDeAcoYLinha().doubleValue() &&
						espacamentoAco.getAreaDeAco().doubleValue() <= lajeSemParede.getAreaDeAcoYLinha().doubleValue() + 0.3) {

					Double quantidade = calculaQuantidadeDeBarras(lajeSemParede.getLadoX().doubleValue(),
                            espacamentoAco.getEspacamento().doubleValue());
					
					espacamentoAco.setEixo("YNegativoSemParede");
					espacamentoAco.setQuantidade(services.doubleEmBigDecimal(quantidade));
					
					
					acoYNegativo.getItems().add(espacamentoAco);

				}
			}
			
		}


	}
	
	public void capturaSelecaoComboBox() {
		
		acoXPositivo.toString();
		
	}
	
	public Double calculaQuantidadeDeBarras(Double comprimento, Double espacamento) {

		comprimento = comprimento * 100;
		
		Double quantidade = comprimento / espacamento;
		
		quantidade = Math.ceil(quantidade);
		
		return quantidade;
	}

	public void mostraEngastes() {

		if (checkXEsquerda.selectedProperty().getValue()) {
			engasteCheckXEsquerda.setVisible(true);
		}

		else {
			engasteCheckXEsquerda.setVisible(false);
		}

		if (checkYCima.selectedProperty().getValue()) {
			engasteCheckYCima.setVisible(true);
		}

		else {
			engasteCheckYCima.setVisible(false);
		}

		if (checkXDireita.selectedProperty().getValue()) {
			engasteCheckXDireita.setVisible(true);
		}

		else {
			engasteCheckXDireita.setVisible(false);
		}

		if (checkYBaixo.selectedProperty().getValue()) {
			engastecheckYBaixo.setVisible(true);
		}

		else {
			engastecheckYBaixo.setVisible(false);
		}

	}

	public void populaCoeficientes() {

		coeficientesList = new ArrayList<>();

		File file = new File("C:\\Programa Laje\\CoeficientesCasos.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String line = br.readLine();

			while (line != null) {

				String[] vect = line.split(";");

				BigDecimal caso = new BigDecimal(vect[0]);
				BigDecimal lambda = new BigDecimal(vect[1]);
				BigDecimal miX = new BigDecimal(vect[2]);
				BigDecimal miY = new BigDecimal(vect[3]);
				BigDecimal miX1 = new BigDecimal(vect[4]);
				BigDecimal miY1 = new BigDecimal(vect[5]);
				BigDecimal kx = new BigDecimal(vect[6]);
				BigDecimal ky = new BigDecimal(vect[7]);
				BigDecimal kx1 = new BigDecimal(vect[8]);
				BigDecimal ky1 = new BigDecimal(vect[9]);

				Coeficientes coeficientes = new Coeficientes(caso, lambda, miX, miY, miX1, miY1, kx, ky, kx1, ky1);

				coeficientesList.add(coeficientes);

				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public void populaCoeficientesMxKx() {

		coeficientesMxKxList = new ArrayList<>();

		File file = new File("C:\\Programa Laje\\CoeficientesMxKx.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String line = br.readLine();

			while (line != null) {

				String[] vect = line.split(";");

				BigDecimal caso = new BigDecimal(vect[0]);
				BigDecimal lambda = new BigDecimal(vect[1]);
				BigDecimal mX = new BigDecimal(vect[2]);
				BigDecimal kX = new BigDecimal(vect[3]);

				Coeficientes coeficientes = new Coeficientes(caso, lambda, mX, kX);

				coeficientesMxKxList.add(coeficientes);

				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void populaEspacamentoAco() {

		espacamentoAcoList = new ArrayList<>();

		File file = new File("C:\\Programa Laje\\EspacamentoAco.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String line = br.readLine();

			while (line != null) {

				String[] vect = line.split(";");

				BigDecimal bitola = new BigDecimal(vect[0]);
				BigDecimal espacamento = new BigDecimal(vect[1]);
				BigDecimal areaDeAco = new BigDecimal(vect[2]);

				EspacamentoAco aco = new EspacamentoAco(bitola, espacamento, areaDeAco);

				espacamentoAcoList.add(aco);

				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public LajeComParede getLajeComParede() {
		return lajeComParede;
	}

	public Parede getParede() {
		return parede;
	}

	public Materiais getMateriais() {
		return materiais;
	}

	public Coeficientes getCoeficientes() {
		return coeficientes;
	}

	public List<Coeficientes> getCoeficientesList() {
		return coeficientesList;
	}

	public List<EspacamentoAco> getEspacamentoAcoList() {
		return espacamentoAcoList;
	}

	public Services getServices() {
		return services;
	}

	public Text getEngasteCheckXEsquerda() {
		return engasteCheckXEsquerda;
	}

	public Text getEngasteCheckYCima() {
		return engasteCheckYCima;
	}

	public Text getEngasteCheckXDireita() {
		return engasteCheckXDireita;
	}

	public Text getEngastecheckYBaixo() {
		return engastecheckYBaixo;
	}

	public TextField getLadoX() {
		return ladoX;
	}

	public TextField getLadoY() {
		return ladoY;
	}

	public TextField getEspessuraLaje() {
		return espessuraLaje;
	}

	public TextField getEspessuraParede() {
		return espessuraParede;
	}

	public TextField getAlturaParede() {
		return alturaParede;
	}

	public TextField getCargaAcidental() {
		return cargaAcidental;
	}

	public RadioButton getPsi0_3() {
		return psi0_3;
	}

	public RadioButton getPsi0_4() {
		return psi0_4;
	}

	public RadioButton getPsi0_5() {
		return psi0_5;
	}

	public RadioButton getPsi0_6() {
		return psi0_6;
	}

	public RadioButton getParedeSim() {
		return paredeSim;
	}

	public RadioButton getParedeNao() {
		return paredeNao;
	}

	public RadioButton getTijoloFuradoSim() {
		return tijoloFuradoSim;
	}

	public RadioButton getTijoloFuradoNao() {
		return tijoloFuradoNao;
	}

	public RadioButton getAgregadoBasaltoDiabasio() {
		return agregadoBasaltoDiabasio;
	}

	public RadioButton getAgregadoGranitoGnaisse() {
		return agregadoGranitoGnaisse;
	}

	public RadioButton getAgregadoCalcario() {
		return agregadoCalcario;
	}

	public RadioButton getAgregadoArenito() {
		return agregadoArenito;
	}

	public CheckBox getCheckYCima() {
		return checkYCima;
	}

	public CheckBox getCheckYBaixo() {
		return checkYBaixo;
	}

	public CheckBox getCheckXEsquerda() {
		return checkXEsquerda;
	}

	public CheckBox getCheckXDireita() {
		return checkXDireita;
	}

	public TextArea getImprimeResultados() {
		return imprimeResultados;
	}

	public void setLajeComParede(LajeComParede lajeComParede) {
		this.lajeComParede = lajeComParede;
	}

	public void setParede(Parede parede) {
		this.parede = parede;
	}

	public void setMateriais(Materiais materiais) {
		this.materiais = materiais;
	}

	public void setCoeficientes(Coeficientes coeficientes) {
		this.coeficientes = coeficientes;
	}

	public void setCoeficientesList(List<Coeficientes> coeficientesList) {
		this.coeficientesList = coeficientesList;
	}

	public void setEspacamentoAcoList(List<EspacamentoAco> espacamentoAcoList) {
		this.espacamentoAcoList = espacamentoAcoList;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public void setEngasteCheckXEsquerda(Text engasteCheckXEsquerda) {
		this.engasteCheckXEsquerda = engasteCheckXEsquerda;
	}

	public void setEngasteCheckYCima(Text engasteCheckYCima) {
		this.engasteCheckYCima = engasteCheckYCima;
	}

	public void setEngasteCheckXDireita(Text engasteCheckXDireita) {
		this.engasteCheckXDireita = engasteCheckXDireita;
	}

	public void setEngastecheckYBaixo(Text engastecheckYBaixo) {
		this.engastecheckYBaixo = engastecheckYBaixo;
	}

	public void setLadoX(TextField ladoX) {
		this.ladoX = ladoX;
	}

	public void setLadoY(TextField ladoY) {
		this.ladoY = ladoY;
	}

	public void setEspessuraLaje(TextField espessuraLaje) {
		this.espessuraLaje = espessuraLaje;
	}

	public void setEspessuraParede(TextField espessuraParede) {
		this.espessuraParede = espessuraParede;
	}

	public void setAlturaParede(TextField alturaParede) {
		this.alturaParede = alturaParede;
	}

	public void setCargaAcidental(TextField cargaAcidental) {
		this.cargaAcidental = cargaAcidental;
	}

	public void setPsi0_3(RadioButton psi0_3) {
		this.psi0_3 = psi0_3;
	}

	public void setPsi0_4(RadioButton psi0_4) {
		this.psi0_4 = psi0_4;
	}

	public void setPsi0_5(RadioButton psi0_5) {
		this.psi0_5 = psi0_5;
	}

	public void setPsi0_6(RadioButton psi0_6) {
		this.psi0_6 = psi0_6;
	}

	public void setParedeSim(RadioButton paredeSim) {
		this.paredeSim = paredeSim;
	}

	public void setParedeNao(RadioButton paredeNao) {
		this.paredeNao = paredeNao;
	}

	public void setTijoloFuradoSim(RadioButton tijoloFuradoSim) {
		this.tijoloFuradoSim = tijoloFuradoSim;
	}

	public void setTijoloFuradoNao(RadioButton tijoloFuradoNao) {
		this.tijoloFuradoNao = tijoloFuradoNao;
	}

	public void setAgregadoBasaltoDiabasio(RadioButton agregadoBasaltoDiabasio) {
		this.agregadoBasaltoDiabasio = agregadoBasaltoDiabasio;
	}

	public void setAgregadoGranitoGnaisse(RadioButton agregadoGranitoGnaisse) {
		this.agregadoGranitoGnaisse = agregadoGranitoGnaisse;
	}

	public void setAgregadoCalcario(RadioButton agregadoCalcario) {
		this.agregadoCalcario = agregadoCalcario;
	}

	public void setAgregadoArenito(RadioButton agregadoArenito) {
		this.agregadoArenito = agregadoArenito;
	}

	public void setCheckYCima(CheckBox checkYCima) {
		this.checkYCima = checkYCima;
	}

	public void setCheckYBaixo(CheckBox checkYBaixo) {
		this.checkYBaixo = checkYBaixo;
	}

	public void setCheckXEsquerda(CheckBox checkXEsquerda) {
		this.checkXEsquerda = checkXEsquerda;
	}

	public void setCheckXDireita(CheckBox checkXDireita) {
		this.checkXDireita = checkXDireita;
	}

	public void setImprimeResultados(TextArea imprimeResultados) {
		this.imprimeResultados = imprimeResultados;
	}

	public LajeSemParede getLajeSemParede() {
		return lajeSemParede;
	}

	public Coeficientes getCoeficientesMxKx() {
		return coeficientesMxKx;
	}

	public List<Coeficientes> getCoeficientesMxKxList() {
		return coeficientesMxKxList;
	}

	public Label getTijoloFurado() {
		return tijoloFurado;
	}

	public Label getTijoloFuradoS() {
		return tijoloFuradoS;
	}

	public Label getTijoloFuradoN() {
		return tijoloFuradoN;
	}

	public Label getTextEspessuraDaParede() {
		return textEspessuraDaParede;
	}

	public Label getTextAlturaDaParede() {
		return textAlturaDaParede;
	}

	public BigDecimal getLajeDirecao() {
		return lajeDirecao;
	}

	public BigDecimal getCaso() {
		return caso;
	}

	public void setLajeSemParede(LajeSemParede lajeSemParede) {
		this.lajeSemParede = lajeSemParede;
	}

	public void setCoeficientesMxKx(Coeficientes coeficientesMxKx) {
		this.coeficientesMxKx = coeficientesMxKx;
	}

	public void setCoeficientesMxKxList(List<Coeficientes> coeficientesMxKxList) {
		this.coeficientesMxKxList = coeficientesMxKxList;
	}

	public void setTijoloFurado(Label tijoloFurado) {
		this.tijoloFurado = tijoloFurado;
	}

	public void setTijoloFuradoS(Label tijoloFuradoS) {
		this.tijoloFuradoS = tijoloFuradoS;
	}

	public void setTijoloFuradoN(Label tijoloFuradoN) {
		this.tijoloFuradoN = tijoloFuradoN;
	}

	public void setTextEspessuraDaParede(Label textEspessuraDaParede) {
		this.textEspessuraDaParede = textEspessuraDaParede;
	}

	public void setTextAlturaDaParede(Label textAlturaDaParede) {
		this.textAlturaDaParede = textAlturaDaParede;
	}

	public void setLajeDirecao(BigDecimal lajeDirecao) {
		this.lajeDirecao = lajeDirecao;
	}

	public void setCaso(BigDecimal caso) {
		this.caso = caso;
	}

	public Label getAcoPositivoEmXComParede() {
		return acoPositivoEmXComParede;
	}

	public Label getAcoNegativoEmXComParede() {
		return acoNegativoEmXComParede;
	}

	public Label getAcoPositivoEmYComParede() {
		return acoPositivoEmYComParede;
	}

	public Label getAcoNegativoEmYComParede() {
		return acoNegativoEmYComParede;
	}

	public ComboBox<EspacamentoAco> getAcoXPositivo() {
		return acoXPositivo;
	}

	public ComboBox<EspacamentoAco> getAcoXNegativo() {
		return acoXNegativo;
	}

	public ComboBox<EspacamentoAco> getAcoYPositivo() {
		return acoYPositivo;
	}

	public ComboBox<EspacamentoAco> getAcoYNegativo() {
		return acoYNegativo;
	}

	public ComboBox<EspacamentoAco> getAcoXPositivoComParede() {
		return acoXPositivoComParede;
	}

	public ComboBox<EspacamentoAco> getAcoXNegativoComParede() {
		return acoXNegativoComParede;
	}

	public ComboBox<EspacamentoAco> getAcoYPositivoComParede() {
		return acoYPositivoComParede;
	}

	public ComboBox<EspacamentoAco> getAcoYNegativoComParede() {
		return acoYNegativoComParede;
	}

	public Rectangle getRetanguloComParede() {
		return retanguloComParede;
	}

	public Label getLabelEscolhaBitolaEspacamento() {
		return labelEscolhaBitolaEspacamento;
	}

	public Label getLabelEscolhaBitolaEspacamentoComParede() {
		return labelEscolhaBitolaEspacamentoComParede;
	}

	public Label getArmadurasForaDaZonaDeInflucencia() {
		return armadurasForaDaZonaDeInflucencia;
	}

	public Label getArmadurasNaZonaDeInflucencia() {
		return armadurasNaZonaDeInflucencia;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setAcoPositivoEmXComParede(Label acoPositivoEmXComParede) {
		this.acoPositivoEmXComParede = acoPositivoEmXComParede;
	}

	public void setAcoNegativoEmXComParede(Label acoNegativoEmXComParede) {
		this.acoNegativoEmXComParede = acoNegativoEmXComParede;
	}

	public void setAcoPositivoEmYComParede(Label acoPositivoEmYComParede) {
		this.acoPositivoEmYComParede = acoPositivoEmYComParede;
	}

	public void setAcoNegativoEmYComParede(Label acoNegativoEmYComParede) {
		this.acoNegativoEmYComParede = acoNegativoEmYComParede;
	}

	public void setAcoXPositivo(ComboBox<EspacamentoAco> acoXPositivo) {
		this.acoXPositivo = acoXPositivo;
	}

	public void setAcoXNegativo(ComboBox<EspacamentoAco> acoXNegativo) {
		this.acoXNegativo = acoXNegativo;
	}

	public void setAcoYPositivo(ComboBox<EspacamentoAco> acoYPositivo) {
		this.acoYPositivo = acoYPositivo;
	}

	public void setAcoYNegativo(ComboBox<EspacamentoAco> acoYNegativo) {
		this.acoYNegativo = acoYNegativo;
	}

	public void setAcoXPositivoComParede(ComboBox<EspacamentoAco> acoXPositivoComParede) {
		this.acoXPositivoComParede = acoXPositivoComParede;
	}

	public void setAcoXNegativoComParede(ComboBox<EspacamentoAco> acoXNegativoComParede) {
		this.acoXNegativoComParede = acoXNegativoComParede;
	}

	public void setAcoYPositivoComParede(ComboBox<EspacamentoAco> acoYPositivoComParede) {
		this.acoYPositivoComParede = acoYPositivoComParede;
	}

	public void setAcoYNegativoComParede(ComboBox<EspacamentoAco> acoYNegativoComParede) {
		this.acoYNegativoComParede = acoYNegativoComParede;
	}

	public void setRetanguloComParede(Rectangle retanguloComParede) {
		this.retanguloComParede = retanguloComParede;
	}

	public void setLabelEscolhaBitolaEspacamento(Label labelEscolhaBitolaEspacamento) {
		this.labelEscolhaBitolaEspacamento = labelEscolhaBitolaEspacamento;
	}

	public void setLabelEscolhaBitolaEspacamentoComParede(Label labelEscolhaBitolaEspacamentoComParede) {
		this.labelEscolhaBitolaEspacamentoComParede = labelEscolhaBitolaEspacamentoComParede;
	}

	public void setArmadurasForaDaZonaDeInflucencia(Label armadurasForaDaZonaDeInflucencia) {
		this.armadurasForaDaZonaDeInflucencia = armadurasForaDaZonaDeInflucencia;
	}

	public void setArmadurasNaZonaDeInflucencia(Label armadurasNaZonaDeInflucencia) {
		this.armadurasNaZonaDeInflucencia = armadurasNaZonaDeInflucencia;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

}