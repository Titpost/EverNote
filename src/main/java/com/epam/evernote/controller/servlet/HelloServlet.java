package com.epam.evernote.controller.servlet;

/**
 * Created by anon on 1/10/2017.
 */

import com.epam.evernote.service.interfaces.NoteService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {


    @Autowired
    private NoteService noteService;

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.getWriter().print("Hello from servlet");
    }
}