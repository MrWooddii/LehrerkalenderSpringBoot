package com.lehrerkalender.controller;

import com.lehrerkalender.extAPI.CatPhotoAPIService;
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

    @Autowired
    private CatPhotoAPIService catService;

    @GetMapping("/categories")
    public String showMotivationPage() {
        return "motivation";
    }

    @GetMapping("/cat")
    public String getCatPhoto(Model model) {
        //erhält ein zufälliges Katzenfoto von einer externen API
        JSONObject catPhoto = catService.getRandomCatPhoto();
        if(catPhoto != null) {
            model.addAttribute("catPhoto", catPhoto.get("webpurl"));
        }

        return "motivation";
    }
}
