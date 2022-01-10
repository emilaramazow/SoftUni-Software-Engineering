package L05Polymorphism.Exercise.P02VehiclesExtension;

public class Truck extends VehicleImpl {

    private static final double AC_ADDITIONAL_CONSUMPTION = 1.6;
    private static final double REFUEL_PERCENTAGE = 0.95;


    public Truck(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption, tankCapacity);
    }

    @Override
    public void setFuelConsumption(double fuelConsumption) {
        super.setFuelConsumption(fuelConsumption + AC_ADDITIONAL_CONSUMPTION);
    }

    @Override
    public void refueling(double liters) {
        super.refueling(liters * REFUEL_PERCENTAGE);
    }


}
