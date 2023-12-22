package com.ecsail.repository.interfaces;

import com.ecsail.dto.StatsDTO;

import java.util.List;

public interface StatRepository {
    List<StatsDTO> getStatistics(int startYear, int stopYear);

    List<StatsDTO> createStatDTO(int year, int statID);

    StatsDTO createStatDTO(int year);

    int getNumberOfStatYears();

    int deleteAllStats();

    int insertStat(StatsDTO s);
}
