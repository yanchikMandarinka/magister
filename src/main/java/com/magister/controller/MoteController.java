package com.magister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magister.db.domain.Mote;
import com.magister.db.repository.MoteRepository;

@Controller
@RequestMapping("/mote")
public class MoteController {

    @Autowired
    private MoteRepository moteRepository;

    @RequestMapping("/show")
    public ModelAndView chart(long id) {
        ModelAndView modelAndView = new ModelAndView("mote/chart");
        Mote mote = moteRepository.findOne(id);
        modelAndView.addObject("mote", mote);
        return modelAndView;
    }
}
