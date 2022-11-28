package com.symphony.hotelchallenge.web.controller;

import com.symphony.hotelchallenge.model.Hotel;
import com.symphony.hotelchallenge.model.User;
import com.symphony.hotelchallenge.model.exceptions.InvalidAccountIdException;
import com.symphony.hotelchallenge.model.exceptions.InvalidHotelIdException;
import com.symphony.hotelchallenge.service.HotelService;
import com.symphony.hotelchallenge.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HotelController {

    // dependency injection
    private final HotelService hotelService;
    private final UserService userService;

    public HotelController(HotelService hotelService, UserService userService) {
        this.hotelService = hotelService;
        this.userService = userService;
    }

    // default landing page after login, displays list of hotels
    @GetMapping
    public String listHotels(Model model, @RequestParam(required = false) String hotelName, String address) {
        model.addAttribute("bodyContent", "listHotels");
        model.addAttribute("hotels", hotelService.filterHotels(hotelName, address));
        return "masterTemplate";
    }

    // can be used only by admin, takes admin to form for adding hotels
    @GetMapping("/addForm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addForm(Model model) {
        model.addAttribute("bodyContent", "addForm");
        return "masterTemplate";
    }

    // adds or edits hotel after clicking submit in the previous form
    @PostMapping("/addHotel")
    public String addHotel(@RequestParam(required = false) Long id, String hotelName, String address, String image, String description, String geolocation) throws InvalidHotelIdException {
        if (id != null) {
            hotelService.edit(id, hotelName, address, image, description, geolocation);
        } else {
            hotelService.create(hotelName, address, image, description, geolocation);
        }
        return "redirect:/";
    }

    // lists hotel details and reviews
    @GetMapping("/{id}/details/")
    public String details(@PathVariable Long id, Model model, HttpServletRequest request) throws InvalidHotelIdException {
        Hotel hotel = hotelService.findById(id);
        User user = userService.findByUsername(request.getRemoteUser());
        model.addAttribute("user", user);
        model.addAttribute("reviews", hotel.getReviews());
        model.addAttribute("rating", String.format("%.2f", hotelService.calculateHotelRating(hotel)));
        model.addAttribute("hotel", hotel);
        model.addAttribute("bodyContent", "details");
        return "masterTemplate";
    }

    // only admin can use this function, deletes hotel
    @DeleteMapping("/{id}/delete/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable Long id) throws InvalidHotelIdException {
        this.hotelService.delete(id);
        return "redirect:/";
    }

    // only admin can use this, opens the previous form
    // just filled out with the information from the selected hotel so the admin can edit it easier
    @GetMapping("/{id}/edit/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editForm(@PathVariable Long id, Model model) throws InvalidHotelIdException {
        Hotel hotel = this.hotelService.findById(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("bodyContent", "addForm");
        return "masterTemplate";
    }

    // adds hotel to user favorites
    @PostMapping("/addHotelToFavorites/{id}/")
    public String addHotelToFavorites(@PathVariable Long id, HttpServletRequest request) throws InvalidHotelIdException, InvalidAccountIdException {
        String user = request.getRemoteUser();
        Hotel hotel = hotelService.findById(id);
        User userAccount = userService.findByUsername(user);
        hotel.getUsersThatHaveHotelInFavorites().add(userAccount.getUsername());
        userService.addToFavorites(hotel, userAccount.getId());
        return "redirect:/";
    }

    // displays user favorites
    @GetMapping("/favorites")
    public String favoriteHotels(Model model, HttpServletRequest request) {
        String user = request.getRemoteUser();
        model.addAttribute("favorites", userService.findByUsername(user).getFavorites());
        model.addAttribute("bodyContent", "myFavorites");
        return "masterTemplate";
    }

    // removes hotel from user favorites
    @PostMapping("/removeHotelFromFavorites/{id}/")
    public String removeFromFavorites(@PathVariable Long id, HttpServletRequest request) throws InvalidHotelIdException, InvalidAccountIdException {
        String user = request.getRemoteUser();
        Hotel hotel = hotelService.findById(id);
        User userAccount = userService.findByUsername(user);
        hotel.getUsersThatHaveHotelInFavorites().remove(userAccount.getUsername());
        userService.removeFromFavorites(hotel, userAccount.getId());
        return "redirect:/";
    }
}
