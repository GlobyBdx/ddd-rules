package fr.ubordeaux.ddd.cargoshippingsystem.application;

import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Invoice;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.State;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.Customer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerBook;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Location;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.Cargo;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoCustomer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoFactory;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.DeliverySpecification;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.EventType;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEvent;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEventFactory;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEventRepository;
import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
public class BookingImplementation implements Booking {
	private final InvoiceRepository invoiceRepository;
	private final CustomerRepository customerRepository;
	private final CargoRepository cargoRepository;
	private final LocationRepository locationRepository;

	private final CargoFactory cargoFactory;
	private final HandlingEventFactory handlingEventFactory;

	public BookingImplementation(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, CargoRepository cargoRepository, LocationRepository locationRepository, HandlingEventRepository handlingEventRepository) {
		this.invoiceRepository = invoiceRepository;
		this.customerRepository = customerRepository;
		this.cargoRepository = cargoRepository;
		this.locationRepository = locationRepository;
		this.cargoFactory = new CargoFactory(cargoRepository);
		this.handlingEventFactory = new HandlingEventFactory(handlingEventRepository);
	}

	private Invoice checkInvoice(InvoiceID invoiceID) {
		Invoice invoice = this.invoiceRepository.findByInvoiceID(invoiceID);
		if (invoice == null) {
			throw new InvoiceException("Invoice with given ID not found.");
		}
		return invoice;
	}

	private Customer checkCustomer(CustomerID customerID) {
		Customer customer = this.customerRepository.findByCustomerID(customerID);
		if (customer == null) {
			throw new CustomerException("Customer with given ID not found.");
		}
		return customer;
	}

	private Cargo checkCargo(CargoTrackingID cargoTrackingID) {
		Cargo cargo = this.cargoRepository.findByCargoTrackingID(cargoTrackingID);
		if (cargo == null) {
			throw new CargoException("Cargo with given ID not found.");
		}
		return cargo;
	}

	private Location checkLocation(LocationID locationID) {
		Location location = this.locationRepository.findByLocationID(locationID);
		if (location == null) {
			throw new LocationException("Location with given ID not found.");
		}
		return location;
	}

	@Override
	public CargoTrackingID bookCargo(InvoiceID invoiceID, Set<CargoCustomer> cargoCustomers, LocationID locationID) {
		Invoice invoice = this.checkInvoice(invoiceID);
		this.checkLocation(locationID);
		Cargo cargo = this.cargoFactory.newCargo(cargoCustomers);
		DeliverySpecification deliverySpecification = new DeliverySpecification(locationID, EventType.RECEIVED);
		cargo.setDeliverySpecification(deliverySpecification);
		HandlingEvent handlingEvent = this.handlingEventFactory.newBookingHandlingEvent(cargo.getCargoTrackingID(), null);
		cargo.getDeliveryHistory().addHandlingEvent(handlingEvent);
		for (CargoCustomer cargoCustomer : cargoCustomers) {
			Customer customer = checkCustomer(cargoCustomer.getCustomerID());
			customer.addCustomerBook(new CustomerBook(cargo.getCargoTrackingID(), invoiceID));
		}
		invoice.setState(State.PAID);
		return cargo.getCargoTrackingID();
	}

	@Override
	public CargoTrackingID bookAnotherCargo(InvoiceID invoiceID, CargoTrackingID cargoTrackingID, LocationID locationID) {
		Invoice invoice = this.checkInvoice(invoiceID);
		this.checkLocation(locationID);
		Cargo cargo = checkCargo(cargoTrackingID);
		Cargo anotherCargo = this.cargoFactory.newCargo(cargo);
		DeliverySpecification deliverySpecification = new DeliverySpecification(locationID, EventType.RECEIVED);
		anotherCargo.setDeliverySpecification(deliverySpecification);
		HandlingEvent handlingEvent = this.handlingEventFactory.newBookingHandlingEvent(cargo.getCargoTrackingID(), null);
		anotherCargo.getDeliveryHistory().addHandlingEvent(handlingEvent);
		for (CargoCustomer cargoCustomer : anotherCargo.getCargoCustomers()) {
			Customer customer = checkCustomer(cargoCustomer.getCustomerID());
			customer.addCustomerBook(new CustomerBook(anotherCargo.getCargoTrackingID(), invoiceID));
		}
		invoice.setState(State.PAID);
		return anotherCargo.getCargoTrackingID();
	}

	@Override
	public void cancelBookedCargo(InvoiceID invoiceID, CargoTrackingID cargoTrackingID) {
		Invoice invoice = this.checkInvoice(invoiceID);
		Cargo cargo = checkCargo(cargoTrackingID);
		if (cargo.getDeliverySpecification() == null) {
			throw new BookingException("Cargo with given ID not booked.");
		}
		HandlingEvent handlingEvent = this.handlingEventFactory.newCancelingHandlingEvent(cargo.getCargoTrackingID(), null);
		cargo.getDeliveryHistory().addHandlingEvent(handlingEvent);
		invoice.setState(State.REFUNDED);
	}

	@Override
	public void changeBookedCargoDestination(CargoTrackingID cargoTrackingID, LocationID locationID) {
		this.checkLocation(locationID);
		Cargo cargo = checkCargo(cargoTrackingID);
		if (cargo.getDeliverySpecification() == null) {
			throw new BookingException("Cargo with given ID not booked.");
		}
		DeliverySpecification deliverySpecification = new DeliverySpecification(locationID, cargo.getDeliverySpecification().getDeliveryStatus());
		cargo.setDeliverySpecification(deliverySpecification);
	}
}