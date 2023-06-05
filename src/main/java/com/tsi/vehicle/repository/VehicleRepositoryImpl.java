package com.tsi.vehicle.repository;

import com.tsi.vehicle.dto.SearchByAttributesRequestDTO;
import com.tsi.vehicle.model.Vehicle;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class VehicleRepositoryImpl implements VehicleRepoCustom{

    private final EntityManager entityManager;

    public VehicleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Vehicle> searchAllByAttributes(boolean vehicleTypeExist, boolean engineTypeExist, boolean vehicleCondExist,
                                               boolean mfgDateExist, SearchByAttributesRequestDTO attrRequestDTO) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Vehicle.class);

        Root<Vehicle>  vehicleRoot = query.from(Vehicle.class);

        List<Predicate> predicateList = new ArrayList<>();

        if(vehicleTypeExist){
            predicateList.add(criteriaBuilder.equal(vehicleRoot.get("vehicleType"), attrRequestDTO.getVehicleType()));
        }
        if(engineTypeExist){
            predicateList.add(criteriaBuilder.equal(vehicleRoot.get("engineType"), attrRequestDTO.getEngineType()));
        }
        if(vehicleCondExist){
            predicateList.add(criteriaBuilder.equal(vehicleRoot.get("vehicleCondition"), attrRequestDTO.getVehicleCondition()));
        }
        if(mfgDateExist){
            predicateList.add(criteriaBuilder.equal(vehicleRoot.get("manufacturingDate"), attrRequestDTO.getManufacturingDate()));
        }
        query.where(predicateList.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
