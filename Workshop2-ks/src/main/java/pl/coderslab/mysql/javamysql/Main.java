package pl.coderslab.mysql.javamysql;

import pl.coderslab.ConsoleColors;
import pl.coderslab.UserDao.UserDao;
import pl.coderslab.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static pl.coderslab.mysql.javamysql.DbUtil.*;

public class Main extends UserDao { // zamiast extends mogę użyć obiekt klasy Userdao.FIND_ALL... przy wywoływaniu gdy nie jest statyczna
    //bez extends musze zaimportować statyczną metodę FIND_ALL z UserDao ale musi być public
    //jak jest protected to musi być Extends oraz import klasy UserDao

    public static void main(String[] args) {

        User user = new User(); // dzięki temu obiektowi mogę go w tej klasie (obiekt klasy)
        Scanner scan = new Scanner(System.in); //tworzę obiekt skanera scan

        try (Connection connection = connect()) { // cały kod dałem w Try aby go nie powtarzać w każdym casie

            System.out.println(ConsoleColors.BLUE + "Wybierz czynność:\n" + ConsoleColors.GREEN +
                    "1 - wyświetl wszystkie rekordy\n" +
                    "2 - wprowadź rekord \n" +
                    "3 - zmień dane w rekordzie \n" +
                    "4 - usuń rekord \n" +
                    "5 - pokaż 1 rekord \n");

            int selection = scan.nextInt(); //wybór CRUD co robimy

            switch (selection) {
                case 1:
                    System.out.println(ConsoleColors.BLUE +"Wybrałeś 1");

                    System.out.println(ConsoleColors.BLUE_BOLD +"Tabela: id - email - password" + ConsoleColors.RESET);
                    printAllData(connection, FIND_ALL_USERS_QUERY);

                    break;
                case 2:
                    System.out.println(ConsoleColors.BLUE +"Wybrałeś 2");
                    System.out.println(ConsoleColors.GREEN+"Wpisz nowy username:");

                    scan.nextLine();
                    user.setUserName(scan.nextLine());

                    System.out.println(ConsoleColors.GREEN+"Wpisz nowy Email:");

                    user.setEmail(scan.nextLine());

                    System.out.println(ConsoleColors.GREEN+"Wpisz nowe hasło:");

                    user.setPassword(scan.nextLine()); //musiałem dopisac w User class String password w setterze (InteliJ podpowiedział)

                    insert(connection, CREATE_USER_QUERY, user.getUserName(), user.getEmail(), user.getPassword());
                    System.out.println("Dodano " + user.getUserName() + ConsoleColors.RESET);
                    printAllData(connection, FIND_ALL_USERS_QUERY);
                    break;
                case 3:
                    System.out.println(ConsoleColors.BLUE +"Wybrałeś 3");
                    System.out.println(ConsoleColors.GREEN+"Podaj ID do zmiany:");
                    int id_update = scan.nextInt();
                    System.out.println(ConsoleColors.GREEN+"Podaj nowy email do zmiany:");
                    user.setEmail(scan.next());
                    System.out.println(ConsoleColors.GREEN+"Podaj nowy username:");

                    scan.nextLine(); //aby poprawnie wyświetliło pytanie i pobrało dwa wyrazy trzeba ten Next dać 2x
                    user.setUserName(scan.nextLine());

                    System.out.println(ConsoleColors.GREEN+"Podaj nowy password:");
                    user.setPassword(scan.next());

                    System.out.println(ConsoleColors.BLUE +"Zmieniono pozycję "  + id_update + ConsoleColors.RESET);
                    updateData(connection, UPDATE_USER_QUERY,id_update,user.getEmail(), user.getUserName(), user.getPassword());

                    //po update pokazuje zmianę
                    printData(connection, READ_USER_QUERY,id_update); //metoda nie pokazuje kolumny password specjalnie
                    break;
                case 4:
                    System.out.println(ConsoleColors.BLUE +"Wybrałeś 4 - kasowanie");

                    System.out.println(ConsoleColors.BLUE_UNDERLINED+"Podaj ID do usunięcia:" + ConsoleColors.GREEN);
                    printAllData(connection, FIND_ALL_USERS_QUERY);//wyświetla listę do wyboru
                    int id_remove = scan.nextInt();

                    remove(connection, DELETE_USER_QUERY, id_remove);
                    System.out.println(ConsoleColors.BLUE_BOLD +"Usunięto linię z id " + id_remove);

                    //Pokazuje co usunięto
                    printData(connection, READ_USER_QUERY, id_remove);

                    System.out.println(ConsoleColors.BLUE_BOLD +"Pozostało w bazie:" + ConsoleColors.RESET);
                    printAllData(connection, FIND_ALL_USERS_QUERY);//wyświetla listę pozostałych

                    break;

                case 5:
                    System.out.println(ConsoleColors.BLUE +"Wybrałeś 5 - wyświetlanie rekordu");

                    System.out.println(ConsoleColors.GREEN+"Podaj ID do wyświetlenia:");
                    printAllData(connection, FIND_ALL_USERS_QUERY);//wyświetla listę pozostałych
                    scan.nextLine();
                    int id_show = scan.nextInt();

                    System.out.println(ConsoleColors.BLUE_BOLD +"Wyświelono linię z id " + id_show + ConsoleColors.RESET);

                    printData(connection, READ_USER_QUERY,id_show);//metoda nie pokazuje kolumny password specjalnie
                    System.out.println("");
                    System.out.println("---------------------------"); //separator
                    break;

                default:
                    System.out.println(ConsoleColors.RED +"Nie dokonałeś poprawnego wyboru");
                    break;
            }
        }catch ( SQLException e){
            e.printStackTrace();
        }
    }
}


