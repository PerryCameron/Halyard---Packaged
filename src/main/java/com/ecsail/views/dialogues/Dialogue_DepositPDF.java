package com.ecsail.views.dialogues;

import com.ecsail.BaseApplication;
import com.ecsail.repository.implementations.DepositRepositoryImpl;
import com.ecsail.repository.interfaces.DepositRepository;
import com.ecsail.views.tabs.deposits.TabDeposits;
import com.ecsail.pdf.PDF_DepositReport;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.DepositPDFDTO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Dialogue_DepositPDF extends Stage {
    private DepositDTO depositDTO;
//    private final DefinedFeeDTO currentDefinedFee;
    private final DepositPDFDTO pdfOptions;
    String selectedYear;

    private DepositRepository depositRepository = new DepositRepositoryImpl();
//    DepositDTO cd, DefinedFeeDTO cdf, String y ( probably get rid of these items)
    public Dialogue_DepositPDF(TabDeposits td, boolean isSinglePDF) {
        double centerXPosition = BaseApplication.stage.getX() + BaseApplication.stage.getWidth() / 2d;
        double centerYPosition = BaseApplication.stage.getY() + BaseApplication.stage.getHeight() / 2d;
        setOnShown(windowEvent -> {
            setX(centerXPosition - getWidth() / 2d);
            setY(centerYPosition - getHeight() / 2d);
        });
        this.pdfOptions = new DepositPDFDTO();
        this.depositDTO = td.getDepositDTO();

        Button createPDFbutton = new Button("Create PDF");
        ToggleGroup tg1 = new ToggleGroup();
        RadioButton r1 = new RadioButton("Print All Deposits");
        RadioButton r2 = new RadioButton("Print Only Deposit Number");
        CheckBox c1 = new CheckBox("Detailed Report");
        CheckBox c2 = new CheckBox("Summary");
        HBox hboxGrey = new HBox(); // this is the vbox for organizing all the widgets
        VBox vboxBlue = new VBox();
        VBox vboxPink = new VBox(); // this creates a pink border around the table
        VBox vboxColumn1 = new VBox();
        VBox vboxColumn2 = new VBox();

        Scene scene = new Scene(vboxBlue, 600, 300);
        final Spinner<Integer> batchSpinner = new Spinner<>();
        Image pdf = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pdf.png")));
        ImageView pdfImage = new ImageView(pdf);

        SpinnerValueFactory<Integer> batchSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        batchSpinner.setValueFactory(batchSlipValueFactory);
        batchSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                batchSpinner.increment(0); // won't change value, but will commit editor
                int fieldValue = Integer.parseInt(batchSpinner.getEditor().getText());
                if (depositRepository.depositRecordExists(depositDTO.getFiscalYear(), fieldValue))  // deposit exists
                    depositDTO.setBatch(fieldValue);
                else
                    depositDTO.setBatch(1);
            }
        });

        /////////////////// ATTRIBUTES ///////////////////
        r1.setToggleGroup(tg1);
        r2.setToggleGroup(tg1);
        r2.setSelected(true);
        c1.setSelected(true);
        c2.setSelected(true);
        //batchSpinner.setPadding(new Insets(0,0,0,10));
        hboxGrey.setPadding(new Insets(5, 0, 0, 5));
        batchSpinner.setPrefWidth(60);
        vboxColumn1.setSpacing(5);
        vboxColumn2.setSpacing(15);
        vboxBlue.setId("box-blue");
        vboxBlue.setPadding(new Insets(10, 10, 10, 10));
        vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
        vboxPink.setId("box-pink");
        // vboxGrey.setId("slip-box");
        hboxGrey.setPrefHeight(688);
        scene.getStylesheets().add("css/dark/custom_dialogue.css");
        setTitle("Print to PDF");
        ////////////  Check to see if batch exists first////////////

        if (depositDTO == null) { // deposit does not exist
            depositDTO = depositRepository.getDeposit(Integer.parseInt(selectedYear), 1);
            batchSpinner.getValueFactory().setValue(1);
        } else {
            batchSpinner.getValueFactory().setValue(depositDTO.getBatch());
        }

        /////////////// LISTENERS ///////////////////////


        createPDFbutton.setOnAction(e -> {
			if (c1.isSelected()) pdfOptions.setIncludesDetailedReport(true);
			if (!c1.isSelected()) pdfOptions.setIncludesDetailedReport(false);
			if (c2.isSelected()) pdfOptions.setIncludesSummaryReport(true);
			if (!c2.isSelected()) pdfOptions.setIncludesSummaryReport(false);
			if (r2.isSelected()) pdfOptions.setSingleDeposit(true);
			if (!r2.isSelected()) pdfOptions.setSingleDeposit(false);
			pdfOptions.setDepositNumber(depositDTO.getBatch());
			new PDF_DepositReport(td, pdfOptions);  // makes the PDF
		});

        //////////////// ADD CONTENT ///////////////////
        vboxColumn1.getChildren().addAll(r1, r2, batchSpinner, c1, c2);
        vboxColumn2.getChildren().addAll(pdfImage, createPDFbutton);
        hboxGrey.getChildren().addAll(vboxColumn1, vboxColumn2);
        vboxBlue.getChildren().add(vboxPink);
        vboxPink.getChildren().add(hboxGrey);
        Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
        this.getIcons().add(mainIcon);
        setScene(scene);
        show();
    }
}
