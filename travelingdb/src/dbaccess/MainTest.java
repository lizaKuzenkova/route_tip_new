package dbaccess;

import dbmodel.Users;

public class MainTest {

	public static void main(String[] args) {

//		Attraction attraction = new Attraction();
//		List<Attractions> AttractionData = attraction.getAttractionsByCityName("Париж");
//
//		String attraction_html = null;
//
//		for (Attractions attractionRow : AttractionData) {
//			attraction_html = "<tr><td>" + attractionRow.getAttractionName() + "</td><td> <b>Описание:</b> "
//					+ attractionRow.getAttDescription() + "</td></tr>";
////			response.getWriter().append(attraction);
//			System.out.println(attraction_html);
//		}


//		Cuisine cus = new Cuisine();

//		Restaurant restaurant = new Restaurant();
//		List<Restaurants> RestaurantData = restaurant.getRestaurantsByCityName("Минск");
//		for (Restaurants restaurantRow : RestaurantData) {
//			System.out.println("rest - " + restaurantRow.getIdRestaurant());
//			for (RestCuis cus : restaurantRow.getRestCuises()) {
//				if (restaurantRow.getIdRestaurant() == cus.getRestaurants().getIdRestaurant()) {
//					System.out.println(cus.getCuisine().getCuisineType());
//				}
//			}
//		}

		/*
		 * GeoApiContext context = new GeoApiContext().setApiKey("AIza...");
		 * GeocodingResult[] results = GeocodingApi.geocode(context,
		 * "1600 Amphitheatre Parkway Mountain View, CA 94043") .await();
		 * System.out.println(results[0].formattedAddress);
		 */

		Users user = new Users();
		User u = new User();
		
		int user_id = u.getUserIdByLoginPassword("admin", "admin");
		
//		u.getUserIdByLoginPassword(login, password);
		
		if (user_id > 0) {
			user = u.getUserByUserId(user_id);
			String role_name = user.getRoles().getRoleName();
			System.out.println(role_name);
		}
		
		System.out.println("Work done!!!");

	}

}
