package com.lehrerkalender.controller;

import com.lehrerkalender.extAPI.CatPhotoAPIService;
import com.lehrerkalender.extAPI.JokesAPIService;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
@Controller
@RequestMapping("/motivation")
public class MotivationController {

    private CatPhotoAPIService catService;

    private JokesAPIService jokeService;

    @Autowired
    public MotivationController(CatPhotoAPIService catService, JokesAPIService jokeService) {
        this.catService = catService;
        this.jokeService = jokeService;
    }

    @GetMapping("/categories")
    public String showMotivationPage() {
        return "motivation-module";
    }

    @GetMapping("/cat")
    public String getCatPhoto(Model model) {
        //erhält ein zufälliges Katzenfoto von einer externen API
        JSONObject catPhoto = catService.getRandomCatPhoto();
        if(catPhoto != null) {
            model.addAttribute("catPhoto", catPhoto.get("webpurl"));
        }
        return "motivation-module";
    }

    @GetMapping("/jokes")
    public String getJokes(Model model) {
        //erhält einen zufälligen Witz von einer externen API
        JSONObject joke = jokeService.getJoke();

        if(joke != null) {
            model.addAttribute("joke", joke.get("joke"));
        }

        return "motivation-module";
    }
}
