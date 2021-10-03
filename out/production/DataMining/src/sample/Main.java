package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {
    public static Stage stage;



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(this.getClass().getResource("sample.fxml"));
        Scene scene=new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        Main.stage = primaryStage;
        stage.setTitle("Accueil");

    }


    public static void main(String[] args) throws ClassNotFoundException {

/*        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Boxplot demo = new Boxplot();
                frame.add(demo.BoxAndWhiskerDemo(), BorderLayout.CENTER);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });*/
        launch(args);
    }


}
