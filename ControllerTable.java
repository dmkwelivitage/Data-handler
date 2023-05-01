import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ControllerTable{

    @FXML
    AnchorPane DynamicPane;

    @FXML
    private TableColumn<InputData,String> ColAge;

    @FXML
    private TableColumn<InputData,String> ColCity;

    @FXML
    private TableColumn<InputData,String> ColName;

    @FXML
    private TableColumn<InputData,String> ColPhoneNumber;

    @FXML
    private TableView<InputData> Table;

    @FXML
    void btnReturnClicked(ActionEvent event) throws IOException{
        setDynamicPane(FXMLLoader.load(getClass().getResource("Main.fxml")));
    }

    private void setDynamicPane(AnchorPane DynamicPane){
        this.DynamicPane.getChildren().clear();
        this.DynamicPane.getChildren().add(DynamicPane);
    }

    public void initialize() throws IOException{
        Collection<InputData> list = Files.readAllLines(new File("details.txt").toPath())
                .stream()
                .map(line -> {
                    String[] details = line.split(",");
                    InputData cd = new InputData();
                    cd.setDataName(details[0]);
                    cd.setDataAge(details[1]);
                    cd.setDataPhoneNumber(details[2]);
                    cd.setDataCity(details[3]);
                    return cd;
                })
                .collect(Collectors.toList());

        ObservableList<InputData> details = FXCollections.observableArrayList(list);

        ColName.setCellValueFactory(data -> data.getValue().DataNameProperty());
        ColAge.setCellValueFactory(data -> data.getValue().DataAgeProperty());
        ColPhoneNumber.setCellValueFactory(data -> data.getValue().DataPhoneNumberProperty());
        ColCity.setCellValueFactory(data -> data.getValue().DataCityProperty());
        Table.setItems(details);
        addButtonToTable();
        }

        public void deletedata(String Value) throws Exception{
            File inputFile = new File("Details.txt");
                                File tempFile = new File("myTempFile.txt");

                                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                                String lineToRemove = Value;
                                String currentLine;

                                while((currentLine = reader.readLine()) != null) {
                                // trim newline when comparing with lineToRemove
                                String trimmedLine = currentLine.trim();
                                if(trimmedLine.equals(lineToRemove)) continue;
                                writer.write(currentLine + System.getProperty("line.separator"));
                            }
                            writer.close(); 
                            reader.close(); 
                            inputFile.delete();
                            tempFile.renameTo(inputFile);
        }

        private void addButtonToTable(){
            TableColumn<InputData, Void> ColHandler = new TableColumn<InputData, Void>("Handler");
            Callback<TableColumn<InputData, Void>, TableCell<InputData, Void>> cellFactory = new Callback<TableColumn<InputData, Void>, TableCell<InputData, Void>>() {
                @Override
                public TableCell<InputData, Void> call(final TableColumn<InputData, Void> param) {
                    final TableCell<InputData, Void> cell = new TableCell<InputData, Void>() {
    
                        private final Button btn = new Button("Delete");
    
                        {
                            btn.setOnAction((ActionEvent event) -> {
                                InputData index = Table.getItems().get(getIndex());
                                String DelName = index.getDataName();
                                String DelAge = index.getDataAge();
                                String DelNumber = index.getDataPhoneNumber();
                                String DelCity = index.getDataCity();
                                Table.getItems().remove(getIndex());
                                String Value = (DelName + "," + DelAge + "," + DelNumber + "," + DelCity);
                                try {
                                    deletedata(Value);
                                } catch (Exception e) {
                                    System.out.println("error");
                                }
                                                });
                        }
    
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);
                            }
                        }
                    };
                    return cell;
                }
            };
    
            ColHandler.setCellFactory(cellFactory);
    
            Table.getColumns().add(ColHandler);
    
        }

}
