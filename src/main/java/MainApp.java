package com.example.aplicacioncitas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Verificar que el archivo login.fxml existe
        URL fxmlLocation = getClass().getResource("/fxml/login.fxml");

        System.out.println("Cargando FXML desde: " + fxmlLocation);

        if(fxmlLocation == null)
        {
            throw new RuntimeException("No se encontro el login.fxml en /resources/fxml");
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root);

        System.out.println("CSS: " + getClass().getResource("/css/style.css"));

        //Cargar CSS global
        URL cssLocation = getClass().getResource("/css/style.css");
        if (cssLocation != null) {
            System.out.println("CSS: " + cssLocation);
            scene.getStylesheets().add(cssLocation.toExternalForm());
        }

        primaryStage.setTitle("Sistema Citas Medicas");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
