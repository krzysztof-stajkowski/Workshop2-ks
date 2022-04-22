package pl.coderslab.UserDao;

public class UserDao {

    //--------------------------------
    //Stałe z zapytaniami -> nie mogą być private bo nie będą widoczne w klasie Main
    protected static final String CREATE_USER_QUERY =             //widoczne tylko w dziedziczonych klasach Protected
            "INSERT INTO Users(username, email, password) VALUES (?, ?, ?)";

    protected static final String READ_USER_QUERY =
            "SELECT id, email, username FROM Users WHERE id = ?";

    protected static final String UPDATE_USER_QUERY =
            "UPDATE Users SET username = ?, email = ?, password = ? where id = ?";

    protected static final String DELETE_USER_QUERY =
            "DELETE FROM Users WHERE id = ?";

    protected static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM Users";


}
