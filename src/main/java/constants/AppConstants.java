package constants;

public class AppConstants {

	//Endpoints uri
    public static final String LOGIN = "api/user/login";
    public static final String REGISTER = "api/user/register";
    public static final String FORGET_PASSWORD = "api/user/forgetpassword";
    public static final String RESET_PASSWORD = "api/user/resetpassword";
    public static final String GET_CURRENTUSER = "api/user/get-currentUser";
   
    public static final String ADD_THEATRE = "api/theatre/add-theatre";
    public static final String GET_ALL_THEATRE = "api/theatre/get-all-theatre";
    public static final String GET_THEATRE_BY_OWNER = "api/theatre/get-theatres-ByOwner/";
    public static final String UPDATE_THEATRE = "api/theatre/update-theatre";
    public static final String DELETE_THEATRE = "api/theatre/delete-theatre";

    public static final String ADD_MOVIE = "api/movie/add-movie";
    public static final String UPDATE_MOVIE = "api/movie/update-movie";
    public static final String DELETE_MOVIE = "api/movie/delete-movie";
    public static final String GET_ALL_MOVIES = "api/movie/get-all-movies";
    public static final String GET_MOVIE_BY_ID = "api/movie/";

    public static final String ADD_SHOW = "api/show/add-show";
    public static final String UPDATE_SHOW = "api/show/update-show";
    public static final String DELETE_SHOW = "api/show/delete-show";
    public static final String GET_SHOW_BY_ID = "api/show/get-show-by-id";
    public static final String GET_SHOWS_BY_THEATRE = "api/show/get-all-shows-by-theatre";
    public static final String GET_THEATRES_BY_MOVIE = "api/show/get-all-theatres-by-movie";

    public static final String BOOK_SHOW = "api/book/book-show";
    public static final String MAKE_PAYMENT = "api/book/make-payment";
    public static final String GET_ALL_BOOKINGS = "api/book/get-all-bookings";

    // Existing constants
    public static final int EXPLICIT_WAIT = 10;
    public static final int PAGE_LOAD_WAIT = 30;
    public static final int MAX_RETRY = 2;
    public static final String REPORTS_PATH = "./reports/";
    public static final String SCREENSHOTS_PATH = "./screenshots/";

    // ─── Auth API Messages ───────────────────────────────────────
    
    // Login
    public static final String LOGIN_SUCCESS_MESSAGE = "Successfully logged in!";
    public static final String LOGIN_INVALID_CREDENTIALS_MESSAGE = "Invalid email or password";
    
    // Register
    public static final String REGISTER_SUCCESS_MESSAGE = "Registration is successful";
    public static final String REGISTER_EXISTS_MESSAGE = "User already exists";

    // Forget Password
    public static final String FORGET_PASSWORD_SUCCESS_MESSAGE = "If the email exists, an OTP has been sent";
    public static final String FORGET_PASSWORD_EMAIL_REQUIRED_MESSAGE = "Email is required";
    
    // Reset Password
    public static final String RESET_PASSWORD_SUCCESS_STATUS = "success";
    public static final String RESET_PASSWORD_SUCCESS_MESSAGE = "password reset successfully";
    public static final String RESET_PASSWORD_FAILURE_STATUS = "failure";
    public static final String RESET_PASSWORD_INVALID_REQUEST_MESSAGE = "invalid request";
    public static final String RESET_PASSWORD_WRONG_OTP_MESSAGE = "OTP is wrong";
    
    //Theatre
    public static final String THEATRE_SUCCESS_MESSAGE = "Theatre Added!";
    public static final String GET_ALL_THEATRES_BY_OWNER_SUCCESS_MESSAGE = "Theatre by owner fetched!";
    public static final String GET_ALL_THEATRES_SUCCESS_MESSAGE = "Theatre fetched!";

    //Movie
    public static final String MOVIE_SUCCESS_MESSAGE="Movie is added";
    public static final String GET_MOVIE_BY_ID_SUCCESS_MESSAGE="Movies fetched!";

    //Show
    public static final String SHOW_SUCCESS_MESSAGE = "show Added!";
    public static final String SHOW_UPDATE_MESSAGE = "The show has been updated!";
    public static final String GET_ALL_SHOWS_MESSAGE = "All shows fetched (past shows updated to today IST)";

    //Book
    public static final String PAYMENT_SUCCESS_MESSAGE = "Payment & booking successful!";
    public static final String BOOKING_SUCCESS_MESSAGE="Booking successful!";
    public static final String GET_ALL_BOOKINGS_MESSAGE ="Bookings fetched!";
    // ─── Roles ───────────────────────────────────────────────────
    public static final String ROLE_USER = "User";
    public static final String ROLE_PARTNER = "Partner";
    public static final String ROLE_ADMIN = "Admin";

    
    //Mongo
    public static final String MONGO_DB_NAME ="test";
    public static final String MONGO_USERS_COLLECTION = "users";
    public static final String MONGO_THEATRES_COLLECTION = "theatres";
    public static final String MONGO_MOVIES_COLLECTION = "movies";
    public static final String MONGO_SHOWS_COLLECTION = "shows";
    public static final String MONGO_BOOKINGS_COLLECTION = "bookings";

    //Dummy Data
    public static final String INVALID_TEST_EMAIL = "invalid_test@gmail.com";
    public static final String INVALID_OTP = "123456";    
    public static final String TEST_USER_PASSWORD = "Pass@123";    
    public static final String TEST_USER_NAME = "UserPramod";    
    public static final String SEED_MOVIE_NAME = "Fight Club";
    public static final String SEED_THEATRE_NAME = "PVR";

}