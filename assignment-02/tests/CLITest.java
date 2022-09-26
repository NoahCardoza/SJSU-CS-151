import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {
    ReservationSystem reservationSystem;
    CLI cli;

    PipedInputStream pipeIn;
    PipedOutputStream pipeOut;
    Writer stdin;
    ByteArrayOutputStream stdout;


    @BeforeEach
    void setUp() throws IOException {
        pipeIn = new PipedInputStream(1024);
        pipeOut = new PipedOutputStream(pipeIn);

        stdout = new ByteArrayOutputStream();
        stdin = new OutputStreamWriter(pipeOut);

        reservationSystem = new ReservationSystem();
        cli = new CLI(reservationSystem, new PrintStream(stdout), new Scanner(pipeIn));

        //        System.out.println(stdout.toString());
//        stdout.reset();
    }

    @Test
    void fittingAGroupInBetweenWindowSeatsFirstClass() throws IOException {
        Reservation reservation;

        stdin.write("P\n" +
                "Noah Cardoza\n" +
                "F\n" +
                "W\n" +
                "\n");
        stdin.flush();
        cli.screenMainMenu();

        reservation = reservationSystem.getReservationByName("Noah Cardoza");

        assertEquals("1A", reservation.getSeats().get(0).toString());

        stdin.write("P\n" +
                "Izzy Revera\n" +
                "F\n" +
                "W\n" +
                "\n");
        stdin.flush();
        cli.screenMainMenu();

        reservation = reservationSystem.getReservationByName("Izzy Revera");

        assertEquals("1D", reservation.getSeats().get(0).toString());

        stdin.write("G\n" +
                "MLH Party\n" +
                "F\n" +
                "Swift\n" +
                "Amanda\n" +
                "\n");
        stdin.flush();
        cli.screenMainMenu();

        reservation = reservationSystem.getReservationByName("MLH Party");


        assertEquals("1B", reservation.getSeats().get(0).toString());
        assertEquals("1C", reservation.getSeats().get(1).toString());

        stdout.reset();
        stdin.write("M\n");
        stdin.flush();
        cli.screenMainMenu();
        assertEquals("Select one of the following main menu options:\n" +
                "Add [P]assenger, Add [G]roup, [C]ancel Reservations, Print Seating [A]vailability Chart, Print [M]anifest, [Q]uit\n" +
                "Manifest:\n" +
                "1A: Noah Cardoza\n" +
                "1B: Swift\n" +
                "1C: Amanda\n" +
                "1D: Izzy Revera\n", stdout.toString());
    }

//    @Test
//    void saveingAndLoading() throws IOException {
//
//    }
//        Reservation reservation;

//
//        stdin.write("P\n" +
//                "Noah Cardoza\n" +
//                "F\n" +
//                "W\n" +
//                "\n");
//        stdin.flush();
//        cli.screenMainMenu();
//
//        reservation = reservationSystem.getReservationByName("Noah Cardoza");
//
//        assertEquals("1A", reservation.getSeats().get(0).toString());
//
//        stdin.write("P\n" +
//                "Izzy Revera\n" +
//                "F\n" +
//                "W\n" +
//                "\n");
//        stdin.flush();
//        cli.screenMainMenu();
//
//        reservation = reservationSystem.getReservationByName("Izzy Revera");
//
//        assertEquals("1D", reservation.getSeats().get(0).toString());
//
//        stdin.write("G\n" +
//                "MLH Party\n" +
//                "F\n" +
//                "Swift\n" +
//                "Amanda\n" +
//                "\n");
//        stdin.flush();
//        cli.screenMainMenu();
//
//        reservation = reservationSystem.getReservationByName("MLH Party");
//
//
//        assertEquals("1B", reservation.getSeats().get(0).toString());
//        assertEquals("1C", reservation.getSeats().get(1).toString());
//    }
}