package org.example.ptit_cntt2_it210_session07_ex4.controller;

import org.example.ptit_cntt2_it210_session07_ex4.model.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/ex4")
public class FoodController {
    private static List<Food> foodList = new ArrayList<>();

    @GetMapping("/add-food")
    public String foodForm(Model model){
        model.addAttribute("food", new Food());
        model.addAttribute("categories", Arrays.asList("Khai vị", "Món chính", "Đồ uống", "Tráng miệng"));
        return "food-form";
    }

    @GetMapping("/food-detail")
    public String showDetail(@RequestParam("id") int id, Model model) {
        if (id >= 0 && id < foodList.size()) {
            Food food = foodList.get(id);
            model.addAttribute("food", food);
            model.addAttribute("absolutePath", "C:/RikkeiFood_Temp/" + food.getImageUrl());
        }
        return "food-detail";
    }

    @PostMapping("/add-food")
    public String handleAddFood(
            @ModelAttribute("food") Food food,
            @RequestParam("image") MultipartFile file,
            RedirectAttributes redirectAttributes,
            Model model) {

        if(file.isEmpty()){
            model.addAttribute("error", "Vui lòng đính kèm ảnh!");
            return "food-form";
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.toLowerCase().matches(".*\\.(jpg|png|jpeg)$")) {
            model.addAttribute("error", "Chỉ chấp nhận file .jpg, .png, .jpeg!");
            return "food-form";
        }

        if (food.getPrice() < 0) {
            model.addAttribute("error", "Giá tiền phải >= 0!");
            return "food-form";
        }

        try {
            File uploadDir = new File("C:/RikkeiFood_Temp/");
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

            File destination = new File(uploadDir.getAbsolutePath() + File.separator + uniqueFileName);
            file.transferTo(destination);

            food.setImageUrl(uniqueFileName);
            foodList.add(food);

            redirectAttributes.addFlashAttribute("message", "Thêm món ăn thành công!");
            redirectAttributes.addAttribute("id", foodList.size() - 1);

            return "redirect:food-detail";

        } catch (IOException e) {
            return "food-form";
        }
    }


}
