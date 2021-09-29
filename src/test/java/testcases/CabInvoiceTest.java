package testcases;

import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.invoice.CabInvoiceGenerator;
import com.invoice.Invoice;
import com.invoice.Ride;
import com.invoice.RideRepository;

public class CabInvoiceTest {

	/**
	 * Test for normal ride fare
	 */
	@Test
	public void givenNormalJourney_withCorrectExpectedFare_returnsTrue() {
		double epsilon = 1e-15;
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		Assert.assertEquals(66.2, cabInvoiceGenerator.generateFare("normal", 5.3, 13.20), epsilon);
	}

	/**
	 * Test for normal ride fare
	 */
	@Test
	public void givenPremiumJourney_withCorrectExpectedFare_returnsTrue() {
		double epsilon = 1e-15;
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		Assert.assertEquals(105.9, cabInvoiceGenerator.generateFare("premium", 5.3, 13.20), epsilon);
	}

	/**
	 * Test for normal ride with less journey which returns minimum fare
	 */
	@Test
	public void givenJourney_withMiniMumFare_returnsTrue() {
		double epsilon = 1e-15;
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		Assert.assertEquals(5, cabInvoiceGenerator.generateFare("normal", 0.2, 2.5), epsilon);

	}

	/**
	 * Test for fare of customer1
	 */
	@Test
	public void givenMultipleRide_matchingWithExpectedFare_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();
		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(1, 10, 25, "normal"));

		double expectedTotalFare = 185;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFare, cabInvoiceGenerator.generateTotalFare(rideRepository.getRides()),
				epsilon);

	}

	/**
	 * Test for total number of ride of customer1
	 */
	@Test
	public void givenMultipleRide_matchingWithTotalNumberOfRide_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();
		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(1, 10, 25, "normal"));
		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(rideRepository.getRides());
		int expectedTotalRides = 2;
		Assert.assertEquals(expectedTotalRides, invoice.getTotalRides());

	}

	/**
	 * Test for total fare of customer1
	 */
	@Test
	public void givenMultipleRide_matchingWithTotalFare_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();
		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(1, 10, 25, "normal"));
		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(rideRepository.getRides());
		double expectedTotalFare = 185;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFare, invoice.getTotalFare(), epsilon);

	}

	/**
	 * Test for average fare of customer1
	 */
	@Test
	public void givenMultipleRide_matchingWithAverageFare_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();
		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(1, 10, 25, "normal"));
		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(rideRepository.getRides());
		double expectedTotalFare = 92.5;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFare, invoice.getAvgfare(), epsilon);

	}

	/**
	 * Test for the Total number of rides of customer1 with other customers in
	 * repository.
	 */
	@Test
	public void givenMultipleRideOfDifferentCustomer_matchingWithNUmberOfRidesOfCustomer1_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();

		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(2, 10, 25, "normal"));
		rideRepository.addRide(new Ride(1, 12, 30, "normal"));

		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(
				rideRepository.getRides().stream().filter(ride -> ride.getUserId() == 1).collect(Collectors.toList()));

		int expectedTotalRideofUser1 = 2;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalRideofUser1, invoice.getTotalRides(), epsilon);

	}

	/**
	 * Test for the Total fare of customer1 with other customers in repository.
	 */
	@Test
	public void givenMultipleRideOfDifferentCustomer_matchingWithTotalFareOfCustomer1_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();

		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(2, 10, 25, "normal"));
		rideRepository.addRide(new Ride(1, 12, 30, "normal"));

		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(
				rideRepository.getRides().stream().filter(ride -> ride.getUserId() == 1).collect(Collectors.toList()));

		double expectedTotalFareofUser1 = 210;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFareofUser1, invoice.getTotalFare(), epsilon);

	}

	/**
	 * Test for the average fare of customer1 with other customers in repository
	 */
	@Test
	public void givenMultipleRideOfDifferentCustomer_matchingWithAverageFareOfCustomer1_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();

		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(2, 10, 25, "normal"));
		rideRepository.addRide(new Ride(1, 12, 30, "normal"));

		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(
				rideRepository.getRides().stream().filter(ride -> ride.getUserId() == 1).collect(Collectors.toList()));

		double expectedTotalFareofUser1 = 105;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFareofUser1, invoice.getAvgfare(), epsilon);

	}

	/**
	 * Test for the total fare of customer over different ride type
	 */
	@Test
	public void givenMultipleRideOfSingleCustomerOfDiffereentType_matchingWithTotalFareOfCustomer_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();

		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(1, 10, 25, "premium"));
		rideRepository.addRide(new Ride(1, 12, 30, "normal"));
		rideRepository.addRide(new Ride(1, 3, 8, "premium"));

		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(
				rideRepository.getRides().stream().filter(ride -> ride.getUserId() == 1).collect(Collectors.toList()));

		double expectedTotalFareofUser1 = 471;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFareofUser1, invoice.getTotalFare(), epsilon);

	}

	/**
	 * Test for the average fare of customer over different ride type
	 */
	@Test
	public void givenMultipleRideOfSingleCustomerOfDiffereentType_matchingWithAverageFareOfCustomer_returnsTrue() {
		CabInvoiceGenerator cabInvoiceGenerator = new CabInvoiceGenerator();
		RideRepository rideRepository = new RideRepository();

		rideRepository.addRide(new Ride(1, 5, 10, "normal"));
		rideRepository.addRide(new Ride(1, 10, 25, "premium"));
		rideRepository.addRide(new Ride(1, 12, 30, "normal"));
		rideRepository.addRide(new Ride(1, 3, 8, "premium"));

		Invoice invoice = cabInvoiceGenerator.getEnhancedInvoice(
				rideRepository.getRides().stream().filter(ride -> ride.getUserId() == 1).collect(Collectors.toList()));

		double expectedTotalFareofUser1 = 117.75;
		double epsilon = 1e-15;
		Assert.assertEquals(expectedTotalFareofUser1, invoice.getAvgfare(), epsilon);

	}

}
