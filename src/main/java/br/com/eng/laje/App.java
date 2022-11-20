package br.com.eng.laje;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage stage;
	
	private static Scene primaryScene;
		
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		stage = primaryStage;
		
		Parent fxmlPrimary = FXMLLoader.load(getClass().getResource("primary.fxml"));
		primaryScene = new Scene(fxmlPrimary);
		
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		
	}
	
	public static void escolheTela(String tela, Object userData) {
		
		switch (tela) {
			case "primary":
				stage.setScene(primaryScene);
				notifyAllListeners("primary", userData);
			break;
		}
		
	}
	
	public static void escolheTela(String tela) {
		escolheTela(tela, null);
	}
	
	private static ArrayList<OnChangeScreen> listeners = new ArrayList<>();
	
	public static interface OnChangeScreen{
		void onScreenChanged(String novaTela, Object userData);
	}
	
	public static void addOnChangeScreenListener(OnChangeScreen newListener) {
		listeners.add(newListener);
	}
	
	private static void notifyAllListeners(String novaTela, Object userData) {
		
		for(OnChangeScreen l : listeners) {
			l.onScreenChanged(novaTela, userData);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}