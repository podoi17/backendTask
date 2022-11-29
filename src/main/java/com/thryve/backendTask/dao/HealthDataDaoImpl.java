package com.thryve.backendTask.dao;

import com.google.common.collect.Lists;
import com.thryve.backendTask.model.EventDto;
import com.thryve.backendTask.model.HeartRateDto;
import com.thryve.backendTask.model.HeartRateEntity;
import com.thryve.backendTask.repo.DataRepository;
import com.thryve.backendTask.service.eventstore.EventPublisher;
import com.thryve.backendTask.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static com.thryve.backendTask.util.Mapper.*;

public class HealthDataDaoImpl implements HealthDataDao {


    @Autowired
    private final DataRepository dataRepository;
    @Autowired
    private final EventPublisher eventPublisher;



    public HealthDataDaoImpl(DataRepository dataRepository, EventPublisher eventPublisher) {
        this.dataRepository = dataRepository;
        this.eventPublisher = eventPublisher;
    }


    @Override
    public void save(final HeartRateDto heartRate) {
        HeartRateEntity heartRateEntity = mapToEntity(heartRate);
        dataRepository.save(heartRateEntity);
    }

    @Override
    public void saveAll(final Collection<HeartRateDto> heartRates) {
        try {
            List<HeartRateEntity> heartRateEntities = mapDtosToEntities(heartRates);
                dataRepository.saveAll(heartRateEntities);
                dataRepository.saveAll(heartRateEntities);
            eventPublisher.publishHeartRateEvent(heartRates, EventDto.Action.INSERT, EventDto.Type.HEART_RATE);
        } catch (Exception e) {
            throw new CustomException("Error while persisting HeartRate-Data");
        }

    }



    @Override
    public List<HeartRateDto> getAll() {
    }

    @Override
    public List<HeartRateDto> getHeartRatesForUser(final int userId) {
    }

    @Override
    public List<HeartRateDto> getHeartRatesAboveThreshold(final Long threshold) {
    }

    @Override
    public List<HeartRateDto> getHeartRatesBelowThreshold(final Long threshold) {
    }







}
