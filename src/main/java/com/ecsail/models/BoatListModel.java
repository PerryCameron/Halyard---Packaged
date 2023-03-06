package com.ecsail.models;

import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.views.tabs.boatlist.BoatListRadioDTO;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.SettingsRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.SettingsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class BoatListModel {

    protected SettingsRepository settingsRepository = new SettingsRepositoryImpl();
    protected BoatRepository boatRepository = new BoatRepositoryImpl();
    protected ObservableList<BoatListDTO> boats = FXCollections.observableArrayList();
    protected ObservableList<BoatListDTO> searchedBoats = FXCollections.observableArrayList();
    protected ArrayList<BoatListRadioDTO> boatListRadioDTOs;
    protected ArrayList<DbBoatSettingsDTO> boatSettings;

    public BoatListModel() {
        this.boatListRadioDTOs = (ArrayList<BoatListRadioDTO>) settingsRepository.getBoatRadioChoices();
        this.boatSettings = (ArrayList<DbBoatSettingsDTO>) settingsRepository.getBoatSettings();
    }

    public SettingsRepository getSettingsRepository() {
        return settingsRepository;
    }

    public void setSettingsRepository(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public BoatRepository getBoatRepository() {
        return boatRepository;
    }

    public void setBoatRepository(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public ObservableList<BoatListDTO> getBoats() {
        return boats;
    }

    public void setBoats(ObservableList<BoatListDTO> boats) {
        this.boats = boats;
    }

    public ObservableList<BoatListDTO> getSearchedBoats() {
        return searchedBoats;
    }

    public void setSearchedBoats(ObservableList<BoatListDTO> searchedBoats) {
        this.searchedBoats = searchedBoats;
    }

    public ArrayList<BoatListRadioDTO> getBoatListRadioDTOs() {
        return boatListRadioDTOs;
    }

    public void setBoatListRadioDTOs(ArrayList<BoatListRadioDTO> boatListRadioDTOs) {
        this.boatListRadioDTOs = boatListRadioDTOs;
    }

    public ArrayList<DbBoatSettingsDTO> getBoatSettings() {
        return boatSettings;
    }

    public void setBoatSettings(ArrayList<DbBoatSettingsDTO> boatSettings) {
        this.boatSettings = boatSettings;
    }
}
