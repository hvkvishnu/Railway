package com.vishnu.railway.service;

import com.vishnu.railway.Station;
import com.vishnu.railway.TimeTable;
import com.vishnu.railway.Train;
import com.vishnu.railway.dao.DaoFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StationService {

    public Map<String, Station> getToStations(String fromStationCode) throws IOException, NoSuchFromStationException {
        TimeTable timeTable = DaoFactory.getTimeTableDao().getTimeTable();
        return getToStations(fromStationCode, timeTable);
    }
    Map<String, Station> getToStations(String fromStationCode, TimeTable timeTable) throws NoSuchFromStationException {
          /* Algorithm:
        Step1:Declare a TimeTable object as timeTable and a map as destinationStations.
        Step2:Get the fromStation object from the stations map in the timeTable using the fromStationCode as key.
        Step3:for each trainStop in the fromStation object do
            i)Get the train from the trainStop
            ii)For each trainStop in the train do
            a)Get the Station and put it into the destinationStations

        return null;*/
        System.out.println("Getting to stations for fromStationdCode:" + fromStationCode);
        Map<String, Station> destinationStations = new HashMap();
        Map<String, Station> stations = timeTable.getStations();
        Station fromStation = stations.get(fromStationCode);
        if (fromStation == null) {
            throw new NoSuchFromStationException("");
        }
       /* for (TrainStop trainStop : fromStation.getTrainStops()) {
            int sequence = trainStop.getSequence();
            Train stoppingTrain = trainStop.getTrain();
            ArrayList<Station> stopingStations = stoppingTrain.getStoppingStations(sequence);
            for (Station stoppingStation : stopingStations) {
                destinationStations.put(stoppingStation.getCode(), stoppingStation);
            }
        }
        return destinationStations;*/
        fromStation.getTrainStops().stream()
                .forEach(trainStop ->destinationStations.putAll(getDestinationStation(trainStop.getTrain(),trainStop.getSequence())));
        return destinationStations;
    }

    private Map<String, Station> getDestinationStation(Train stoppingTrain, int sequence){
        ArrayList<Station> stopingStations = stoppingTrain.getStoppingStations(sequence);
        return stopingStations.stream()
                .collect(Collectors.toMap(Station::getCode, Function.identity()));

    }


    public Map<String, Station> getFromStations() throws IOException {
        return DaoFactory.getTimeTableDao().getTimeTable().getStations();
    }
}
