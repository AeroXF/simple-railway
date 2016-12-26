package com.fengyunjie.railway.service;

import java.util.List;

import com.fengyunjie.railway.model.Train;

public interface TrainService {
	public List<List<String>> getTrainInfo(int length, String trainNo);

	public void addTicketByTrainTag(String trainTag, String trainNo);

	public void addTrain(Train train);

	public List<Train> getAllTrain();

	public void modifyTrain(Train train);

	public void delTrain(String trainNo);

	public void saveTrainList(List<Train> list);
}
