package api.builder;

import java.util.List;

import api.model.AddMovieRequest;
import api.model.AddShowRequest;
import api.model.AddTheatreRequest;
import api.model.BookShowRequest;
import api.model.Card;
import api.model.ForgetPasswordRequest;
import api.model.LoginRequest;
import api.model.PaymentRequest;
import api.model.RegisterRequest;
import api.model.ResetPasswordRequest;
import api.model.Token;

public class RequestBuilder {

    // -------- AUTH --------

    public static LoginRequest buildLoginRequest(String email, String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

    public static RegisterRequest buildRegisterRequest(String name, String email, String password) {
        RegisterRequest request = new RegisterRequest();
        request.setname(name);
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

    public static ForgetPasswordRequest buildForgetPasswordRequest(String email) {
        ForgetPasswordRequest request = new ForgetPasswordRequest();
        request.setEmail(email);
        return request;
    }

    public static ResetPasswordRequest buildResetPasswordRequest(String password, String otp) {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setpassword(password);
        request.setOtp(otp);
        return request;
    }

    // -------- THEATRE --------

    public static AddTheatreRequest buildAddTheatreRequest(
            String name,
            String address,
            String email,
            String phone,
            String owner
    ) {
        AddTheatreRequest request = new AddTheatreRequest();
        request.setName(name);
        request.setAddress(address);
        request.setEmail(email);
        request.setPhone(phone);
        request.setOwner(owner);
        return request;
    }

    public static AddTheatreRequest buildUpdateTheatreRequest(
            String id,
            String name,
            String address,
            String email,
            String phone,
            String owner
    ) {
        AddTheatreRequest request = new AddTheatreRequest();
        request.setId(id); // make sure your POJO has this
        request.setName(name);
        request.setAddress(address);
        request.setEmail(email);
        request.setPhone(phone);
        request.setOwner(owner);
        return request;
    }
    
    public static AddMovieRequest buildAddMovieRequest(
            String name,
            String description,
            int duration,
            String language,
            String releaseDate,
            String genre,
            String poster
    ) {
        AddMovieRequest req = new AddMovieRequest();
        req.setMovieName(name);
        req.setDescription(description);
        req.setDuration(duration);
        req.setLanguage(language);
        req.setReleaseDate(releaseDate);
        req.setGenre(genre);
        req.setPoster(poster);
        return req;
    }
    
    public static AddMovieRequest buildUpdateMovieRequest(
    		String id,
            String name,
            String description,
            int duration,
            String language,
            String releaseDate,
            String genre,
            String poster
    ) {
        AddMovieRequest req = new AddMovieRequest();
        req.setMovieId(id);
        req.setMovieName(name);
        req.setDescription(description);
        req.setDuration(duration);
        req.setLanguage(language);
        req.setReleaseDate(releaseDate);
        req.setGenre(genre);
        req.setPoster(poster);
        return req;
    }
    
    public static AddShowRequest buildAddShowRequest(
            String name,
            String date,
            String time,
            String movie,
            int ticketPrice,
            int totalSeats,
            String theatre
    ) {
        AddShowRequest req = new AddShowRequest();
        req.setName(name);
        req.setDate(date);
        req.setTime(time);
        req.setMovie(movie);
        req.setTicketPrice(ticketPrice);
        req.setTotalSeats(totalSeats);
        req.setTheatre(theatre);
        return req;
    }
    
    public static AddShowRequest buildUpdateShowRequest(
    		String id,
            String name,
            String date,
            String time,
            String movie,
            int ticketPrice,
            int totalSeats,
            String theatre
    ) {
        AddShowRequest req = new AddShowRequest();
        req.setShowId(id);
        req.setName(name);
        req.setDate(date);
        req.setTime(time);
        req.setMovie(movie);
        req.setTicketPrice(ticketPrice);
        req.setTotalSeats(totalSeats);
        req.setTheatre(theatre);
        return req;
    }

	public static PaymentRequest buildPaymentRequest() {
	
	    // Card
	    Card card = new Card();
	    card.setId("card_1TMJWx4FA6mIWZeox5UtQV2B");
	    card.setBrand("Visa");
	    card.setLast4("5556");
	    card.setExp_month(2);
	    card.setExp_year(2028);
	
	    // Token
	    Token token = new Token();
	    token.setId("tok_1TMJWx4FA6mIWZeoR9IZrcqA");
	    token.setObject("token");
	    token.setEmail("pramodkumaras143@gmail.com");
	    token.setCard(card);
		
	    // Request
	    PaymentRequest request = new PaymentRequest();
	    request.setToken(token);
	    request.setAmount(1500);
	
	    return request;
	}
	
	public static BookShowRequest buildBookShowRequest(
	        List<Integer> seats,
	        String showId,
	        String transactionId,
	        String userId
	) {
	    BookShowRequest request = new BookShowRequest();
	    request.setSeats(seats);
	    request.setShow(showId);
	    request.setTransactionId(transactionId);
	    request.setUser(userId);
	    return request;
	}
}