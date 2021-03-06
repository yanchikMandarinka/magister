package com.magister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magister.db.domain.Network;
import com.magister.db.repository.MoteRepository;
import com.magister.db.repository.NetworkRepository;
import com.magister.db.repository.TopologyRepository;
import com.magister.manager.NetworkManager;

@Controller
@RequestMapping("/network")
public class NetworkController {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkManager networkManager;

    @Autowired
    private MoteRepository moteRepository;

    @Autowired
    private TopologyRepository topologyRepository;

    @RequestMapping(value = { "/list", "/" })
    public String networks(Model model) {
        Iterable<Network> networks = networkRepository.findAll();
        model.addAttribute("networks", networks);
        return "network/list";
    }

    @RequestMapping("/show")
    public ModelAndView show(long id) {
        Network network = networkRepository.findOne(id);

        ModelAndView modelAndView = new ModelAndView("network/show");
        modelAndView.addObject("network", network);

        return modelAndView;
    }

    @RequestMapping("/create")
    public String createNetwork(@ModelAttribute Network network) {
        return "network/create";
    }

    @RequestMapping("/edit")
    public ModelAndView editNetwork(long id) {
        Network network = networkRepository.findOne(id);
        return new ModelAndView("network/edit", "command", network);
    }

    @RequestMapping("/save")
    public String createNetwork(@ModelAttribute Network network, BindingResult bindingResult) {
        if (!StringUtils.hasText(network.getName())) {
            bindingResult.rejectValue("name", "error.network.name", "Network must has a name");
        }

        if (NetworkManager.findLiveGateway(network) == null) {
            bindingResult.rejectValue("motes", "error.motes.no.gateway", "Network must has at least ONE gateway");
        }

        if (bindingResult.hasErrors()) {
            return "network/create";
        }

        networkManager.saveOrUpdateNetwork(network);
        return "redirect:list";
    }

    @RequestMapping("/saveedit")
    @Transactional
    public String editNetwork(Network network) {
        networkManager.updateNetwork(network);
        return "redirect:list";
    }

    @RequestMapping("/remove")
    public String removeNetwork(long id) {
        networkManager.removeNetwork(id);
        return "redirect:list";
    }

    @RequestMapping("/chart")
    public ModelAndView chartNetwork(long id) {
        Network network = networkRepository.findOne(id);
        return new ModelAndView("network/chart", "network", network);
    }

    @RequestMapping("/topology")
    public ModelAndView topologyNetwork(long id) {
        Network network = networkRepository.findOne(id);
        return new ModelAndView("network/topology", "network", network);
    }

}
