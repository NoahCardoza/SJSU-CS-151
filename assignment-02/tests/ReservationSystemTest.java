import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationSystemTest {

    @Test
    void reserveWindowSeat() {
        ReservationSystem rs = new ReservationSystem();

        // test first class
        assertEquals(
                "1A",
                rs.reserveWindowSeat("1", true).getSeats().get(0).toString()
        );

        assertEquals(
                "1D",
                rs.reserveWindowSeat("2", true).getSeats().get(0).toString()
        );

        assertEquals(
                "2A",
                rs.reserveWindowSeat("3", true).getSeats().get(0).toString()
        );

        assertEquals(
                "2D",
                rs.reserveWindowSeat("4", true).getSeats().get(0).toString()
        );

        // make sure null is received when no more seats of said type
        assertNull(
                rs.reserveWindowSeat("5", true)
        );

        // test economy class
        assertEquals(
                "3A",
                rs.reserveWindowSeat("6", false).getSeats().get(0).toString()
        );

        assertEquals(
                "3F",
                rs.reserveWindowSeat("7", false).getSeats().get(0).toString()
        );

        assertEquals(
                "4A",
                rs.reserveWindowSeat("8", false).getSeats().get(0).toString()
        );

        assertEquals(
                "4F",
                rs.reserveWindowSeat("9", false).getSeats().get(0).toString()
        );
    }

    @Test
    void reserveAisleSeat() {
        ReservationSystem rs = new ReservationSystem();

        // test first class
        assertEquals(
                "1B",
                rs.reserveAisleSeat("1", true).getSeats().get(0).toString()
        );

        assertEquals(
                "1C",
                rs.reserveAisleSeat("2", true).getSeats().get(0).toString()
        );

        assertEquals(
                "2B",
                rs.reserveAisleSeat("3", true).getSeats().get(0).toString()
        );

        assertEquals(
                "2C",
                rs.reserveAisleSeat("4", true).getSeats().get(0).toString()
        );

        // make sure null is received when no more seats of said type
        assertNull(
                rs.reserveAisleSeat("5", true)
        );

        // test economy class
        assertEquals(
                "3C",
                rs.reserveAisleSeat("6", false).getSeats().get(0).toString()
        );

        assertEquals(
                "3D",
                rs.reserveAisleSeat("7", false).getSeats().get(0).toString()
        );

        assertEquals(
                "4C",
                rs.reserveAisleSeat("8", false).getSeats().get(0).toString()
        );

        assertEquals(
                "4D",
                rs.reserveAisleSeat("9", false).getSeats().get(0).toString()
        );
    }

    @Test
    void reserveEconomyCenterSeat() {
        ReservationSystem rs = new ReservationSystem();

        // test economy class
        assertEquals(
                "3B",
                rs.reserveEconomyCenterSeat("1").getSeats().get(0).toString()
        );

        assertEquals(
                "3E",
                rs.reserveEconomyCenterSeat("2").getSeats().get(0).toString()
        );

        assertEquals(
                "4B",
                rs.reserveEconomyCenterSeat("3").getSeats().get(0).toString()
        );

        assertEquals(
                "4E",
                rs.reserveEconomyCenterSeat("4").getSeats().get(0).toString()
        );
    }

    @Test
    void general() {
        ReservationSystem reservationSystem = new ReservationSystem();

        Reservation noahCardoza = reservationSystem.reserveWindowSeat("Noah Cardoza", true);
        reservationSystem.reserveAisleSeat("Izzy Revera", false).getSeats().get(0).toString();
        reservationSystem.reserveEconomyCenterSeat("Josh Rider").getSeats().get(0).toString();
        List<String> people = new ArrayList<>();
        people.add("Jason");
        people.add("Tammy");
        people.add("Zoe");
        people.add("Quinn");
        people.add("Tegan");
        reservationSystem.reserveGroup("Wicks Family", people, true);

        assertEquals("Manifest:\n" +
                "1A: Noah Cardoza\n" +
                "1B: Tegan\n" +
                "2A: Jason\n" +
                "2B: Tammy\n" +
                "2C: Zoe\n" +
                "2D: Quinn\n" +
                "3B: Josh Rider\n" +
                "3C: Izzy Revera", reservationSystem.getManifest());

        reservationSystem.cancelReservation(noahCardoza);
        reservationSystem.cancelReservation(reservationSystem.getReservationByName("Wicks Family"));

        assertEquals("Manifest:\n" +
                "3B: Josh Rider\n" +
                "3C: Izzy Revera", reservationSystem.getManifest());
    }

    /**
     * Verifies serialized data can be deserialized correctly
     */
    @Test
    void readAndWrite() {
//        ByteArrayOutputStream stdout = new ByteArrayOutputStream();

        ReservationSystem reservationSystem = new ReservationSystem();

        Reservation noahCardoza = reservationSystem.reserveWindowSeat("Noah Cardoza", true);
        reservationSystem.reserveAisleSeat("Izzy Revera", false).getSeats().get(0).toString();
        reservationSystem.reserveEconomyCenterSeat("Josh Rider").getSeats().get(0).toString();
        List<String> people = new ArrayList<>();
        people.add("Jason");
        people.add("Tammy");
        people.add("Zoe");
        people.add("Quinn");
        people.add("Tegan");
        reservationSystem.reserveGroup("Wicks Family", people, true);

        String serialization = reservationSystem.dumps();
        System.out.println(serialization);
        ReservationSystem reservationSystemFromFile = ReservationSystem.loads(serialization);
        assertEquals(reservationSystem.getManifest(), reservationSystemFromFile.getManifest());
        assertEquals(reservationSystem.getFirstClassAvailabilityList(), reservationSystemFromFile.getFirstClassAvailabilityList());
        assertEquals(reservationSystem.getEconomyClassAvailabilityList(), reservationSystemFromFile.getEconomyClassAvailabilityList());

//        reservationSystemFromFile.getReservationByName("Noah Cardoza", "Izzy Revera", "Josh Rider", "Wicks Family")
    }

    @Test
    void largeGroup() {
        ReservationSystem reservationSystem = new ReservationSystem();
        List<String> people = new ArrayList<>();
        people.add("Jason");
        people.add("Tammy");
        people.add("Zoe");
        people.add("Quinn");
        people.add("Tegan");
        people.add("Jason");
        people.add("Tammy");
        people.add("Zoe");
        people.add("Quinn");
        people.add("Tegan");
        people.add("Jason");
        people.add("Tammy");
        people.add("Zoe");
        people.add("Quinn");
        people.add("Tegan");
        reservationSystem.reserveGroup("Wicks Family", people, false);

        people = new ArrayList<>();
        people.add("Jason");
        people.add("Tammy");
        people.add("Zoe");
        people.add("Quinn");
        people.add("Quinn");
        people.add("Tegan");
        people.add("Quinn");
        people.add("Quinn");
        reservationSystem.reserveGroup("x2 Wicks Family", people, false);
        System.out.println(reservationSystem.getEconomyClassAvailabilityList());
    }

    @Test
    void noOverLap() {
        ReservationSystem reservationSystem = new ReservationSystem();
        Reservation reservation;
        ArrayList<String> people;

        reservation = reservationSystem.reserveAisleSeat("Noah", true);

        assertEquals("1B", reservation.getSeats().get(0).toString());

        people = new ArrayList<>();
        people.add("Rod");
        people.add("Michelle");
        people.add("Abram");

        reservation = reservationSystem.reserveGroup("Cardoza", people, true);

        assertEquals("2A", reservation.getSeats().get(0).toString());
        assertEquals("2B", reservation.getSeats().get(1).toString());
        assertEquals("2C", reservation.getSeats().get(2).toString());

        people = new ArrayList<>();
        people.add("Zoe");
        people.add("Quinn");
        people.add("Tegan");

        reservation = reservationSystem.reserveGroup("Wick", people, true);

        assertEquals("1C", reservation.getSeats().get(0).toString());
        assertEquals("1D", reservation.getSeats().get(1).toString());
        assertEquals("1A", reservation.getSeats().get(2).toString());

        String manifest = reservationSystem.getManifest();
        String firstClassAvailabilityList = reservationSystem.getFirstClassAvailabilityList();

        people = new ArrayList<>();
        people.add("James");
        people.add("John");
        reservation = reservationSystem.reserveGroup("MLH", people, true);

        assertNull(reservation);

        assertEquals(manifest, reservationSystem.getManifest());
        assertEquals(firstClassAvailabilityList, reservationSystem.getFirstClassAvailabilityList());
    }

    @Test
    void lastWindowSeatFirstClass() throws FileNotFoundException {
        File file = new File("./tests/data/lastWindowSeatFirstClass.dat");
        ReservationSystem reservationSystem = ReservationSystem.load(file);

        Reservation reservation = reservationSystem.reserveWindowSeat("James", true);

        assertNotNull(reservation);

        assertEquals("2D", reservation.getSeats().get(0).toString());

        System.out.println(reservationSystem.getFirstClassAvailabilityList());
    }

}