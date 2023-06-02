package com.tsi.vehicle.constants;

public class ConstantsEnum {

    public  enum VehicleCondition {
        NEW,
        USED,
        CERTIFIED,
        PREOWNED;
    }

    public enum EngineType {
        GASOLINE,
        DIESEL,
        PETROL,
        HYBRID,
        ELECTRIC;
    }

    public enum Status {
        INSTOCK,
        INVOICED,
        RESERVED,
        DELIVERED;
    }

    public enum TransmissionType {
        MANUAL,
        AUTOMATIC,
        CVT;
    }

    public enum FeaturesAndOptionsEnum{
        CRUISE,
        SUNROOF;
    }
    public enum Colors {
        RED,
        GREEN,
        BLUE;
    }

    public enum EmissionClass {
        INDIA2000,
        BSII,
        BSIII,
        BSIV,
        BSVI;
    }

}
