package com.example.aplicacioncitas.controller;

import com.example.aplicacioncitas.dao.CitaDAO;
import com.example.aplicacioncitas.model.Cita;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EditarCitaController {

    @FXML
    private DatePicker dateFechaCita;
    @FXML
    private TextField txtHora;   // mejor si lo validamos (ej: 09:30)
    @FXML
    private TextField txtMonto;
    @FXML
    private TextField txtMotivo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private Cita cita;

    //Se llama desde PantallaPrincipalController al abrir ventana de edición
    public void setCita(Cita cita)
    {
        this.cita = cita;

        dateFechaCita.setValue(cita.getFechaCita().toLocalDate());
        txtHora.setText(String.valueOf(cita.getFechaCita().getHour()));
        txtMonto.setText(String.valueOf(cita.getMonto()));
        txtMotivo.setText(cita.getMotivo());

        //mantener los IDs originales
        this.cita.setIdConsultorio(cita.getIdConsultorio());
        this.cita.setIdDoctor(cita.getIdDoctor());
        this.cita.setIdPaciente(cita.getIdPaciente());
    }

    @FXML
    private void guardarCambios()
    {
        try
        {
            LocalTime hora = LocalTime.parse(txtHora.getText()); //Permite HH:mm
            cita.setFechaCita(LocalDateTime.of(dateFechaCita.getValue(), hora));
            cita.setMonto(Float.parseFloat(txtMonto.getText()));
            cita.setMotivo(txtMotivo.getText());

            if (!CitaDAO.doctorDisponible(cita.getIdDoctor(), cita.getFechaCita(), cita.getIdCita()))
            {
                mostrarAlerta("Error", "El doctor ya está ocupado en esa fecha y hora.");
                return;
            }

            CitaDAO.actualizarCita(cita);

            Stage stage = (Stage) btnGuardar.getScene().getWindow();
            stage.close();

        } catch (Exception e)
        {
            e.printStackTrace();
            mostrarAlerta("Error", "Datos inválidos. Revisa la hora (HH:mm) y monto.");
        }
    }

    @FXML
    private void cancelar()
    {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje)
    {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
