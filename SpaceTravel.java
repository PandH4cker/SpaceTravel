import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class SpaceTravel {
    public static final double ROCKET_SPEED_IN_KM_PER_S = 4.0;
    public static final double S_TO_MONTH = 2592000.0;

    private static Planet choosePlanet(Scanner scan) {
        for(Planet p : Planet.values()) {
            System.out.print(p.getCommonName());
            if(!p.getCommonName().equals("Neptune")) System.out.print(", ");
        }
        System.out.println();
        final String planet = scan.next();
        for(Planet p : Planet.values()) {
            if(p.getCommonName().startsWith(planet))
                return p;
        }
        return null;
    }

    private static Date chooseDate(Scanner scan, Calendar calendar) {
        System.out.println("When do you want to leave?");

        System.out.print("Year: ");
        calendar.set(Calendar.YEAR, scan.nextInt());

        System.out.print("Month: ");
        calendar.set(Calendar.MONTH, scan.nextInt() - 1);

        System.out.print("Day: ");
        calendar.set(Calendar.DAY_OF_MONTH, scan.nextInt());

        System.out.print("Hour: ");
        calendar.set(Calendar.HOUR, scan.nextInt());

        System.out.print("Minute: ");
        calendar.set(Calendar.MINUTE, scan.nextInt());

        System.out.print("Second: ");
        calendar.set(Calendar.SECOND, scan.nextInt());

        return calendar.getTime();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMM y H:mm:s");
        Planet departure = null, arrival = null;

        System.out.println("Welcome to the SpaceTravel agency");
        while(true) {
            System.out.println("What do you want do? [h for help]");
            switch(scan.next().charAt(0)) {
                case 't':
                case 'T':
                    if(departure == null)
                        System.out.println("Please choose first your planet with key 'd'");

                    else {
                       System.out.println("What do you want to know?");
                       System.out.println("1. Arrival date");
                       System.out.println("2. Departure date");

                       if(scan.next().charAt(0) == '1') {
                           Date departureDate = chooseDate(scan, calendar);

                           System.out.println("Rocket departure time: " + dateFormatter.format(departureDate));

                           calendar.add(Calendar.SECOND, (int) departure.travelTimeInSTo(arrival, ROCKET_SPEED_IN_KM_PER_S));
                           Date arrivalDate = calendar.getTime();

                           System.out.println("Rocket arrival time: " + dateFormatter.format(arrivalDate));
                       }

                       else if(scan.next().charAt(0) == '2') {
                           Date arrivalDate = chooseDate(scan, calendar);

                           System.out.println("Rocket arrival time: " + dateFormatter.format(arrivalDate));

                           calendar.add(Calendar.SECOND, - (int) departure.travelTimeInSTo(arrival, ROCKET_SPEED_IN_KM_PER_S));
                           Date maxDepartureDate = calendar.getTime();

                           System.out.println("Rocket maximum departure time: " + dateFormatter.format(maxDepartureDate));
                       }

                       else System.out.println("You need to select 1 or 2");

                    }

                    break;

                case 'd':
                case 'D':
                    System.out.println("What is your departure planet?");
                    departure = choosePlanet(scan);
                    assert departure != null;
                    System.out.println("Departure planet set to: " + departure.getCommonName());

                    System.out.println("What is your arrival planet?");
                    arrival = choosePlanet(scan);
                    assert arrival != null;
                    System.out.println("Arrival planet set to: " + arrival.getCommonName());

                    System.out.printf("The distance between %s and %s is %.4f UA\n",
                                      departure.getCommonName(), arrival.getCommonName(), departure.distanceInUATo(arrival));

                    double distanceInKMMillTo = Math.round(departure.distanceInKMTo(arrival) / Math.pow(10, 5)) / 10.0;
                    System.out.printf("It is equivalent to %.1f million of Km!\n", distanceInKMMillTo);

                    double travelTimeInMinTo = Math.round(departure.travelTimeInSTo(arrival) / 60 * 10) / 10.0;
                    System.out.printf("At the speed of the light, you will need %.1f minutes\n", travelTimeInMinTo);

                    double travelTimeInMonthTo = departure.travelTimeInSTo(arrival, ROCKET_SPEED_IN_KM_PER_S) / S_TO_MONTH;
                    System.out.printf("But with our current technology it's more %.1f month!\n\n", travelTimeInMonthTo);
                    break;

                case 'l':
                case 'L':
                    for(Planet p : Planet.values()) {
                        System.out.print(p);
                        if(!p.getCommonName().equals("Neptune")) System.out.print(", ");
                    }
                    System.out.println();
                    break;

                case 'q':
                case 'Q':
                    System.out.println("Bye bye!");
                    System.exit(0);
                    break;

                case 'h':
                case 'H':
                    System.out.println("t: Plan your date departure");
                    System.out.println("d: Set your departure/arrival planet");
                    System.out.println("l: list the planets");
                    System.out.println("h: print this help screen");
                    System.out.println("q: quit the program");
                    break;
            }
        }
    }
}
