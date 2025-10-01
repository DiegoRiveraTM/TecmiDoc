package com.example.aplicacioncitas.controller;

import com.example.aplicacioncitas.dao.PacienteDAO;
import com.example.aplicacioncitas.model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.aplicacioncitas.util.Sesion;


public class LoginController
{
    //Hay que declarar todos los botones de la forma

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnEntrar;

    @FXML
    private Hyperlink linkRegistro;

    @FXML
    private Button btnCerrar;

    @FXML
    //Funcion para iniciar sesion de manera correcta sin que falte ningun campo
    public void onLogin(ActionEvent actionEvent)
    {
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();

        if (txtCorreo.getText().isEmpty() || txtPassword.getText().isEmpty())
        {
            showAlert("Error", "Los 2 campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        PacienteDAO pacienteDAO = new PacienteDAO();
        Paciente paciente = pacienteDAO.login(correo, password);

        if (paciente != null)
        {
            //Guardar el id del paciente en la sesión
            Sesion.setIdPacienteLoggeado(paciente.getIdPaciente());
            System.out.println("DEBUG -> Paciente loggeado con id: " + paciente.getIdPaciente());

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pantallaPrincipal.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 700, 500);
                Stage stage = new Stage();
                stage.setTitle("Aplicacion Citas");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

                Stage currentStage = (Stage) txtCorreo.getScene().getWindow();
                currentStage.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
                showAlert("Error", "No se pudo abrir la pantalla principal", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Correo o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    //Funcion para mostrar alerta (reutilizable)
    private void showAlert(String titulo, String mensaje, Alert.AlertType tipo)
    {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Funcion para el hyperlink para abrir el registro
    @FXML
    private void abrirRegistro()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();

            //Obtener ventana actual
            Stage stage = (Stage) linkRegistro.getScene().getWindow();

            //Definir tamaño
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            showAlert("Error", "No se puede abrir la ventana Registro", Alert.AlertType.ERROR);
        }
    }

    //Funcion para cerrar la ventana
    @FXML
    private void cerrarVentana()
    {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }


}

