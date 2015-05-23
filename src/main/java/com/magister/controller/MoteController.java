package com.magister.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.NetworkRepository;

@Controller
@RequestMapping("/mote")
public class MoteController {

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @RequestMapping(value = { "/list", "/" })
    public String networks(Model model) {
        Iterable<Network> networks = networkRepository.findAll();
        model.addAttribute("networks", networks);
        return "mote/list";
    }

    @RequestMapping("/show")
    public ModelAndView chart(long id) {
        ModelAndView modelAndView = new ModelAndView("mote/chart");
        Mote mote = moteRepository.findOne(id);
        modelAndView.addObject("mote", mote);
        return modelAndView;
    }

    @RequestMapping("/burn")
    public String burn(long id, HttpServletRequest request) {
        Mote mote = moteRepository.findOne(id);
        mote.setBroken(true);
        moteRepository.save(mote);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping("/repair")
    public String repair(long id, HttpServletRequest request) {
        Mote mote = moteRepository.findOne(id);
        mote.setBroken(false);
        moteRepository.save(mote);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping("/charge")
    public String charge(long id, HttpServletRequest request) {
        Mote mote = moteRepository.findOne(id);
        mote.setPower(mote.getPower() + 100);
        moteRepository.save(mote);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
