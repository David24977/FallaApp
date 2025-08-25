package es.david.fallaapp.controller;

import es.david.fallaapp.dao.FalleroDao;
import es.david.fallaapp.model.Fallero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MainController {

    @FXML private TableView<Fallero> table;
    @FXML private TableColumn<Fallero, Number> colId;
    @FXML private TableColumn<Fallero, String> colNombre;
    @FXML private TableColumn<Fallero, String> colDni;
    @FXML private TableColumn<Fallero, String> colDireccion;
    @FXML private TableColumn<Fallero, String> colEmail;
    @FXML private TableColumn<Fallero, String> colTelefono;
    @FXML private TableColumn<Fallero, String> colCategoria;
    @FXML private Label statusLabel;

    private final FalleroDao dao = new FalleroDao();
    private final ObservableList<Fallero> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colDni.setCellValueFactory(cell -> cell.getValue().dniProperty());
        colDireccion.setCellValueFactory(cell -> cell.getValue().direccionProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
        colTelefono.setCellValueFactory(cell -> cell.getValue().telefonoProperty());
        colCategoria.setCellValueFactory(cell -> cell.getValue().categoriaProperty());

        table.setItems(data);
        reload();
    }

    @FXML
    private void onAddDemo() {
        Fallero f = new Fallero(null,
                "Demo " + (int)(Math.random()*1000),
                "00000000X",
                "C/ Principal 1",
                "demo@falla.es",
                "600000000",
                "MAYOR"
        );
        dao.insert(f);
        data.add(f);
        setStatus("Añadido: " + f.getNombre());
    }

    @FXML
    private void onDeleteSelected() {
        Fallero sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) {
            setStatus("Selecciona un fallero.");
            return;
        }
        dao.deleteById(sel.getId());
        data.remove(sel);
        setStatus("Eliminado ID " + sel.getId());
    }

    @FXML
    private void onReload() {
        reload();
    }

    private void reload() {
        List<Fallero> list = dao.findAll();
        data.setAll(list);
        setStatus("Cargados " + list.size() + " falleros.");
    }

    private void setStatus(String msg) {
        if (statusLabel != null) statusLabel.setText(msg);
    }
}

