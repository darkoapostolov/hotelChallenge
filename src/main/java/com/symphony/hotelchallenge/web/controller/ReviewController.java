package com.symphony.hotelchallenge.web.controller;

import com.symphony.hotelchallenge.model.*;
import com.symphony.hotelchallenge.model.exceptions.*;
import com.symphony.hotelchallenge.service.HotelService;
import com.symphony.hotelchallenge.service.ReviewService;
import com.symphony.hotelchallenge.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    // dependency injection
    private final HotelService hotelService;
    private final UserService userService;
    private final ReviewService reviewService;

    public ReviewController(HotelService hotelService, UserService userService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    // displays form for adding reviews to hotel
    @GetMapping("/addReviewForm/{id}")
    public String addReview(@PathVariable Long id, Model model) {
        model.addAttribute("ratings", Rating.values());
        model.addAttribute("hotelId", id);
        model.addAttribute("bodyContent", "reviewForm");
        return "masterTemplate";
    }

    // depending on the existence of an id, either edits or creates review
    @PostMapping("/addEdit")
    public String ReviewAddEdit(@RequestParam(required = false) Long id, String user, String description, Long hotelId, Rating rating) throws InvalidHotelIdException, InvalidReviewIdException, InvalidAccountIdException {
        User userAccount = userService.findByUsername(user);
        if (id != null) {
            reviewService.edit(id, userAccount, rating, description);
        } else {
            Review review = reviewService.create(userAccount, rating, description);
            hotelService.addReview(hotelId, review);
            userService.addReview(review, userAccount.getId());
        }
        return "redirect:/" + hotelId + "/details/";
    }

    // calls previous form with autofill for easy editing
    @GetMapping("/edit/{id}/{hotelId}")
    public String editReview(Model model, @PathVariable Long id, @PathVariable Long hotelId) throws InvalidReviewIdException {
        model.addAttribute("review", reviewService.findById(id));
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("ratings", Rating.values());
        model.addAttribute("bodyContent", "reviewForm");
        return "masterTemplate";
    }

    // adds like to the review
    @PostMapping("/addLike/{id}/{hotelId}")
    public String like(@PathVariable Long id, @PathVariable Long hotelId, HttpServletRequest request) throws InvalidReviewIdException, InvalidAccountIdException {
        String user = request.getRemoteUser();
        User userAccount = userService.findByUsername(user);
        Review review = reviewService.findById(id);
        review.getUsersThatLiked().add(userAccount.getUsername());
        reviewService.like(id, userAccount.getId());
        return "redirect:/" + hotelId + "/details/";
    }

    // removes like from the review
    @PostMapping("/removeLike/{id}/{hotelId}")
    public String removeLike(@PathVariable Long id, @PathVariable Long hotelId, HttpServletRequest request) throws InvalidReviewIdException, InvalidAccountIdException, InvalidLikeId {
        String user = request.getRemoteUser();
        User userAccount = userService.findByUsername(user);
        Review review = reviewService.findById(id);
        review.getUsersThatLiked().remove(userAccount.getUsername());
        reviewService.unlike(id, userAccount.getId());
        return "redirect:/" + hotelId + "/details/";
    }

    // adds dislike to review
    @PostMapping("/addDislike/{id}/{hotelId}")
    public String dislike(@PathVariable Long id, @PathVariable Long hotelId, HttpServletRequest request) throws InvalidReviewIdException, InvalidAccountIdException {
        String user = request.getRemoteUser();
        User userAccount = userService.findByUsername(user);
        Review review = reviewService.findById(id);
        review.getUsersThatDisliked().add(userAccount.getUsername());
        reviewService.dislike(id, userAccount.getId());
        return "redirect:/" + hotelId + "/details/";
    }

    // removes dislike from review
    @PostMapping("/removeDislike/{id}/{hotelId}")
    public String removeDislike(@PathVariable Long id, @PathVariable Long hotelId, HttpServletRequest request) throws InvalidReviewIdException, InvalidAccountIdException, InvalidDislikeId {
        String user = request.getRemoteUser();
        User userAccount = userService.findByUsername(user);
        Review review = reviewService.findById(id);
        review.getUsersThatDisliked().remove(userAccount.getUsername());
        reviewService.unDislike(id, userAccount.getId());
        return "redirect:/" + hotelId + "/details/";
    }

    // displays list of user usernames and emails of users that liked the review
    @GetMapping("/likedList/{id}/")
    public String viewLikes(Model model, @PathVariable Long id) throws InvalidReviewIdException {
        Review review = reviewService.findById(id);
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < review.getLikes().size(); i++) {
            userList.add(review.getLikes().get(i).getUser());
        }
        model.addAttribute("users", userList);
        model.addAttribute("bodyContent", "viewLikes");
        return "masterTemplate";
    }

    // displays list of user usernames and emails of users that disliked the review
    @GetMapping("/dislikedList/{id}/")
    public String viewDislikes(Model model, @PathVariable Long id) throws InvalidReviewIdException {
        Review review = reviewService.findById(id);
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < review.getDislikes().size(); i++) {
            userList.add(review.getDislikes().get(i).getUser());
        }
        model.addAttribute("users", userList);
        model.addAttribute("bodyContent", "viewDislikes");
        return "masterTemplate";
    }
}
