import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    String Input = "";

    @FXML
    AnchorPane DynamicPane;

    @FXML
    private TextField InputAgeField;

    @FXML
    private TextField InputCityField;

    @FXML
    private TextField InputNOField;

    @FXML
    private TextField InputNameField;

    @FXML
    void btnResetClicked(ActionEvent event) {
        reset();
    }

    @FXML
    void btnSubmitClicked(ActionEvent event) {
        submit();
    }

    @FXML
    void btnViewClicked(ActionEvent event) throws IOException {
        setDynamicPane(FXMLLoader.load(getClass().getResource("Table.fxml")));
    }

    public void reset(){
        InputCityField.setText("");
        InputAgeField.setText("");
        InputNOField.setText("");
        InputNameField.setText("");
    }

    private void setDynamicPane(AnchorPane DynamicPane){
        this.DynamicPane.getChildren().clear();
        this.DynamicPane.getChildren().add(DynamicPane);
    }

    public void data(){
        String Name = InputNameField.getText();
        String Age = InputAgeField.getText();
        String Number = InputNOField.getText();
        String City = InputCityField.getText();
        InputCityField.setText("");
        InputAgeField.setText("");
        InputNOField.setText("");
        InputNameField.setText("");

        Input = (Name + "," + Age + "," + Number + "," + City + System.lineSeparator());

    }

    public void submit(){
        data();
        try{
            File file = new File("Details.txt");
                if(!file.exists()){
                    file.createNewFile();
                }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Input);
            bw.close();
        }
        catch(IOException el){
            el.printStackTrace();
        }
    }
}
