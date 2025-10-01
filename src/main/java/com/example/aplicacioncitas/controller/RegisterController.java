package com.example.aplicacioncitas.controller;

import com.example.aplicacioncitas.dao.PacienteDAO;
import com.example.aplicacioncitas.model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class RegisterController
{
    @FXML
    private ToggleGroup sexoGroup;

    @FXML
    private RadioButton femenino;

    @FXML
    private RadioButton masculino;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidoPat;

    @FXML
    private TextField txtApellidoMat;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtAltura;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnBuscarExpediente;

    @FXML
    private Button btnCerrar;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnInicioSesion;

    //Guardamos el nombre del archivo del expediente
    private String antecedentes;

    //Paciente que se reutiliza en toda la clase
    private Paciente paciente;

    @FXML
    public void initialize()
    {
        //Configuramos el botón de expediente al iniciar
        btnBuscarExpediente.setOnAction((event) -> {seleccionarExpediente();});
    }

    @FXML
    private void showAlert(String titulo, String mensaje, Alert.AlertType tipo)
    {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void onRegister(ActionEvent actionEvent)
    {
        //Validación de campos obligatorios
        if (txtNombre.getText().isEmpty() ||
                txtApellidoPat.getText().isEmpty() ||
                txtApellidoMat.getText().isEmpty() ||
                txtEdad.getText().isEmpty() ||
                txtPeso.getText().isEmpty() ||
                txtAltura.getText().isEmpty() ||
                txtEmail.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() ||
                txtContrasena.getText().isEmpty() ||
                sexoGroup.getSelectedToggle() == null)
        {
            showAlert("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        //Creamos o reutilizamos el modelo de Paciente
        if (paciente == null)
        {
            paciente = new Paciente();
        }

        paciente.setNombre(txtNombre.getText());
        paciente.setApellidoPat(txtApellidoPat.getText());
        paciente.setApellidoMat(txtApellidoMat.getText());
        paciente.setEdad(Integer.parseInt(txtEdad.getText()));
        paciente.setPeso(Float.parseFloat(txtPeso.getText()));
        paciente.setAltura(Float.parseFloat(txtAltura.getText()));
        paciente.setEmail(txtEmail.getText());
        paciente.setContrasena(txtContrasena.getText());
        paciente.setTelefono(txtTelefono.getText());

        //Sexo seleccionado
        String sexo = ((RadioButton) sexoGroup.getSelectedToggle()).getText();
        paciente.setSexo(sexo);

        //Si se seleccionó un expediente antes, lo asignamos
        if (this.antecedentes != null)
        {
            paciente.setAntecedentes(this.antecedentes);
        }

        //Guardamos en la BD
        PacienteDAO dao = new PacienteDAO();
        boolean registrado = dao.registrarPaciente(paciente);

        if (registrado)
        {
            showAlert("Éxito", "Paciente registrado correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
            paciente = null; // Reiniciamos para evitar usar el mismo objeto en registros nuevos
        } else
        {
            showAlert("Error", "Ya existe un paciente con este correo", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limpiarCampos()
    {
        txtNombre.clear();
        txtApellidoPat.clear();
        txtApellidoMat.clear();
        txtEdad.clear();
        txtPeso.clear();
        txtAltura.clear();
        txtEmail.clear();
        txtContrasena.clear();
        sexoGroup.selectToggle(null);
        txtTelefono.clear();
        antecedentes = null; //Reiniciamos el expediente
    }

    @FXML
    private void cerrarVentana()
    {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void abrirLogin()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            //Obtener ventana actual
            Stage stage = (Stage) btnInicioSesion.getScene().getWindow();

            //Definir tamaño
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            showAlert("Error", "No se puede abrir la ventana Login", Alert.AlertType.ERROR);
        }
    }

    private void mostrarError(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }

    @FXML
    private void seleccionarExpediente()
    {
        //Creamos una nuevo objeto de FileChooser para seleccionar el expediente
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione el expediente");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos soportados", "*.txt", "*.pdf", "*.docx", "*.word")
        );
        //Abrimos la ventana para elegir el expediente
        File file = fileChooser.showOpenDialog(btnBuscarExpediente.getScene().getWindow());
        //Si no hay un archivo
        if(file != null)
            try
            {
                //Guardamoos el nombre del archivo en una variable llamada antecedentesFile
                String antecedentesFile = file.getName();
                String projectDir = System.getProperty("user.dir");
                File destino = new File(projectDir + "/Expedientes/" + antecedentesFile);
                destino.getParentFile().mkdir();
                Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                //Guardar solo el nombre del archivo en el atributo antecedentes
                this.antecedentes = antecedentesFile;

                //Si el paciente aún no está creado, lo inicializamos
                if (paciente == null)
                {
                    paciente = new Paciente();
                }
                paciente.setAntecedentes(this.antecedentes);
            } catch (Exception e)
            {
                mostrarError("Error al copiar el antecedente " + e.getMessage());
            }

    }
}
