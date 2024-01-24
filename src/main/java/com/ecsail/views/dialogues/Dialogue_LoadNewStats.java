package com.ecsail.views.dialogues;

import com.ecsail.dto.StatsDTO;
import com.ecsail.repository.implementations.StatRepositoryImpl;
import com.ecsail.repository.interfaces.StatRepository;
import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Year;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Dialogue_LoadNewStats extends Stage {
	final ProgressBar pb;
	private int statId = 0;
	private int startYear;
	private final int stopYear;
	BooleanProperty dataBaseStatisticsRefreshed;
	StatRepository statRepository = new StatRepositoryImpl();

	public Dialogue_LoadNewStats(BooleanProperty dataBaseStatisticsRefreshed) {
		this.dataBaseStatisticsRefreshed = dataBaseStatisticsRefreshed;
		stopYear= Year.now().getValue();
		startYear=1970;
		
		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table

		Scene scene = new Scene(vboxBlue, 400, 100);

		HBox hboxYearChoose = new HBox();
		pb = new ProgressBar(0);

		/////////////////// ATTRIBUTES ///////////////////
		hboxYearChoose.setSpacing(10);
		vboxGrey.setSpacing(20);
		
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxBlue.setId("box-frame-dark");

		pb.setPrefSize(300, 30);
		vboxGrey.setPrefHeight(688);
		vboxGrey.setAlignment(Pos.CENTER);
		hboxYearChoose.setAlignment(Pos.CENTER);
		scene.getStylesheets().add("css/dark/custom_dialogue.css");

		setTitle("Updating Statistics");
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		this.getIcons().add(mainIcon);
		vboxGrey.getChildren().addAll(pb);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setScene(scene);
		show();
		updateStats();
	}

	public void updateStats() {
		statRepository.deleteAllStats();
		int numberOfYears = stopYear - startYear + 1;
		var task = new Task<String>(){
	        @Override
	        protected String call() {
	        for (int i = 0; i < numberOfYears; i++) {
				StatsDTO statsDTO = statRepository.createStatDTO(startYear);
				statRepository.insertStat(statsDTO);
			startYear++;
			statId++;
			pb.setProgress((double)statId/ numberOfYears);
		}
			return null;
	        }
	    };

	    task.setOnSucceeded(e -> {
			dataBaseStatisticsRefreshed.set(true);
			this.close();
		});

	    task.setOnFailed(e -> System.out.println("Was unable to compile stats"));
	    exec.execute(task);
	}
	
	private final Executor exec = Executors.newCachedThreadPool(runnable -> {
	    Thread t = new Thread(runnable);
	    t.setDaemon(true);
	    return t ;
	});
}
