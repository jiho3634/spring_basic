package com.beyond.board.author.controller;

import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateReqDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author/create")
    public String authorCreateScreen() {
        return "/author/author_register";
    }

    @PostMapping("/author/create")
    public String authorCreate(@ModelAttribute AuthorSaveReqDto authorSaveReqDto) {
        authorService.authorCreate(authorSaveReqDto);
        return "redirect:/author/list";
    }

    @GetMapping("/author/list")
    public String authorList(Model model) {
        model.addAttribute("authorList", authorService.authorList());
        return "/author/author_list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.authorDetail(id));
        return "/author/author_detail";
    }

    @GetMapping("/author/delete/{id}")
    public String authorDelete(@PathVariable Long id) {
        authorService.authorDelete(id);
        return "redirect:/author/list";
    }

    @PostMapping("/author/update/{id}")
    public String authorUpdate(@PathVariable Long id, @ModelAttribute AuthorUpdateReqDto authorUpdateReqDto) {
        authorService.authorUpdate(id, authorUpdateReqDto);
        return "redirect:/author/detail/" + id;
    }
}
