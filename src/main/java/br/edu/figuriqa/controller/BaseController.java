package br.edu.figuriqa.controller;

import br.edu.figuriqa.model.User;
import jakarta.servlet.http.HttpSession;

public abstract class BaseController {
    protected User currentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }
}
