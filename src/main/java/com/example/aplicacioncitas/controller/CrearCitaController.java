package com.example.aplicacioncitas.controller;

import com.example.aplicacioncitas.dao.CitaDAO;
import com.example.aplicacioncitas.dao.DoctorDAO;
import com.example.aplicacioncitas.dao.ConsultorioDAO;
import com.example.aplicacioncitas.model.Cita;
import com.example.aplicacioncitas.util.Sesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CrearCitaController {

    @FXML
    private ComboBox<String> comboDoctor;
    @FXML
    private ComboBox<String> comboConsultorio;
    @FXML
    private DatePicker dateFechaCita;
    @FXML
    private TextField txtHora;
    @FXML
    private TextField txtMonto;
    @FXML
    private TextField txtMotivo;
    @FXML
    private Button btnAgendar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnRegresar;

    private int idDoctor;
    private Cita citaEditando = null; //si esto es null, significa que es nueva cita

    @FXML
    public void initialize()
    {
        comboDoctor.setItems(FXCollections.observableArrayList(DoctorDAO.obtenerNombresDoctores()));
        comboConsultorio.setItems(FXCollections.observableArrayList(ConsultorioDAO.obtenerNombresConsultorios()));

        //Cuando seleccionan un doctor, guardamos su ID
        comboDoctor.setOnAction(e ->
        {
            if (comboDoctor.getValue() != null)
            {
                idDoctor = DoctorDAO.obtenerIdPorNombre(comboDoctor.getValue());
            }
        });
    }

    public void cargarCita(Cita cita)
    {
        this.citaEditando = cita; //Decimos que vamos a editar la entidad cita

        //Con sus atributos
        dateFechaCita.setValue(cita.getFechaCita().toLocalDate());
        txtHora.setText(cita.getFechaCita().toLocalTime().toString());
        txtMonto.setText(String.valueOf(cita.getMonto()));
        txtMotivo.setText(cita.getMotivo());
        comboDoctor.setValue(cita.getNombreDoctor());
        comboConsultorio.setValue(cita.getNombreConsultorio());

        //Guardamos también el ID real del doctor para validar
        idDoctor = cita.getIdDoctor();
    }

    @FXML
    private void agendarCita()
    {
        try
        {
            LocalDate fecha = dateFechaCita.getValue();
            LocalTime hora = LocalTime.parse(txtHora.getText());

            if (fecha == null || hora == null || comboDoctor.getValue() == null || comboConsultorio.getValue() == null)
            {
                mostrarAlerta("Error", "Debes llenar todos los campos.");
                return;
            }

            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

            if (citaEditando == null)
            {


                Cita cita = new Cita();
                cita.setFechaCita(fechaHora);
                cita.setMonto(Float.parseFloat(txtMonto.getText()));
                cita.setMotivo(txtMotivo.getText());
                cita.setIdPaciente(Sesion.getIdPacienteLoggeado());
                cita.setIdDoctor(idDoctor);
                cita.setIdConsultorio(ConsultorioDAO.obtenerIdPorNombre(comboConsultorio.getValue()));

                //Validar disponibilidad del doctor
                if (!CitaDAO.doctorDisponible(idDoctor, fechaHora, null))
                {
                    mostrarAlerta("Error", "El doctor ya tiene una cita en esa fecha y hora.");
                    return;
                }

                CitaDAO.insertarCita(cita);

            } else
            {
                citaEditando.setFechaCita(fechaHora);
                citaEditando.setMonto(Float.parseFloat(txtMonto.getText()));
                citaEditando.setMotivo(txtMotivo.getText());
                citaEditando.setIdDoctor(idDoctor);
                citaEditando.setIdConsultorio(ConsultorioDAO.obtenerIdPorNombre(comboConsultorio.getValue()));

                //Validar disponibilidad (excluyendo la misma cita que editamos)
                if (!CitaDAO.doctorDisponible(citaEditando.getIdDoctor(), fechaHora, citaEditando.getIdCita())) {
                    mostrarAlerta("Error", "El doctor ya tiene una cita en esa fecha y hora.");
                    return;
                }

                CitaDAO.actualizarCita(citaEditando);
            }

            mostrarAlerta("Éxito", "La cita fue guardada correctamente.");
            cerrarVentana();

        } catch (Exception e)
        {
            e.printStackTrace();
            mostrarAlerta("Error", "Hubo un problema al guardar la cita, verifica los datos.");
        }
    }

    @FXML
    private void limpiarFormulario()
    {
        comboDoctor.getSelectionModel().clearSelection();
        comboConsultorio.getSelectionModel().clearSelection();
        dateFechaCita.setValue(null);
        txtHora.clear();
        txtMonto.clear();
        txtMotivo.clear();
    }

    @FXML
    private void cerrarVentana()
    {
        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje)
    {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
