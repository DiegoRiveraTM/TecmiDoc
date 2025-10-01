package com.example.aplicacioncitas.controller;

import com.example.aplicacioncitas.dao.CitaDAO;
import com.example.aplicacioncitas.model.Cita;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PantallaPrincipalController {

    @FXML
    private VBox contenidoCitas;

    @FXML
    private Button btnCerrar;

    @FXML
    private void initialize()
    {
        cargarCitas();
    }

    @FXML
    private void cargarCitas()
    {
        contenidoCitas.getChildren().clear();

        for (Cita cita : CitaDAO.obtenerCitas())
        {
            VBox tarjeta = new VBox(5);
            tarjeta.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-background-color: white; -fx-background-radius: 5;");

            Label lblDoctor = new Label("Doctor: " + cita.getNombreDoctor());
            Label lblPaciente = new Label("Paciente: " + cita.getNombrePaciente());
            Label lblConsultorio = new Label("Consultorio: " + cita.getNombreConsultorio());
            Label lblFecha = new Label("Fecha: " + cita.getFechaCita().toString());
            Label lblMotivo = new Label("Motivo: " + cita.getMotivo());
            Label lblMonto = new Label("Monto: " + cita.getMonto());

            Button btnEliminar = new Button("X");
            btnEliminar.setStyle("-fx-background-color: #e57373; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px; -fx-min-width: 30; -fx-min-height: 20;");

            btnEliminar.setOnAction(e -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Eliminar Cita");
                alerta.setHeaderText(null);
                alerta.setContentText("¿Seguro que deseas eliminar la cita #" + cita.getIdCita() + "?");

                if (alerta.showAndWait().get() == ButtonType.OK)
                {
                    CitaDAO.eliminarCita(cita.getIdCita());
                    cargarCitas();
                }
            });

            HBox filaBoton = new HBox();
            filaBoton.setStyle("-fx-alignment: center-right;");
            filaBoton.getChildren().add(btnEliminar);

            tarjeta.getChildren().addAll(lblDoctor, lblPaciente, lblConsultorio, lblFecha, lblMotivo, lblMonto, filaBoton);

            //Evento para editar con triple click
            tarjeta.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 3) {
                    abrirEditarCita(cita);
                }
            });

            //Esto agrega las tarjeta al VBox
            contenidoCitas.getChildren().add(tarjeta);
        }
    }

    private void abrirEditarCita(Cita cita)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editarCita.fxml"));
            Parent root = loader.load();

            EditarCitaController controller = loader.getController();
            controller.setCita(cita);

            Stage stage = new Stage();
            stage.setTitle("Editar Cita");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            cargarCitas();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCrearCita()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nuevaCita.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Cita");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            cargarCitas();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarVentana()
    {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
