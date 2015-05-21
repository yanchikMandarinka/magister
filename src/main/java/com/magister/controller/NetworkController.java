package com.magister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magister.db.domain.Network;
import com.magister.db.repository.NetworkRepository;

@Controller
@RequestMapping("/network")
public class NetworkController {

    @Autowired
    private NetworkRepository networkRepository;

    @RequestMapping(value = { "/list", "/" })
    public String networks(Model model) {
        Iterable<Network> networks = networkRepository.findAll();
        model.addAttribute("networks", networks);
        return "network/network";
    }

    @RequestMapping("/create")
    public ModelAndView createNetwork() {
        return new ModelAndView("network/create", "command", new Network());
    }

    @RequestMapping("/save")
    public String createNetwork(Network network) {
        Assert.hasText(network.getName());

        networkRepository.save(network);
        return "redirect:list";
    }

}
