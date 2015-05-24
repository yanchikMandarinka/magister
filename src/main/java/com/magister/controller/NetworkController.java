package com.magister.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.Topology;
import com.magister.db.repository.NetworkRepository;
import com.magister.network.service.NetworkEmulationService;

@Controller
@RequestMapping("/network")
public class NetworkController {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkEmulationService sensorNetworkService;

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
    public ModelAndView createNetwork() {
        return new ModelAndView("network/create", "command", new Network());
    }

    @RequestMapping("/edit")
    public ModelAndView editNetwork(long id) {
        Network network = networkRepository.findOne(id);
        return new ModelAndView("network/edit", "command", network);
    }

    @RequestMapping("/save")
    @Transactional
    public String createNetwork(Network network) {
        Assert.hasText(network.getName(), "Network must has a name");

        // check that at least one gateway is present
        Mote gateway = null;
        List<Mote> motes = network.getMotes();
        for (Mote mote : motes) {
            if (mote.isGateway()) {
                gateway = mote;
                break;
            }
        }
        Assert.notNull(gateway, "Network must has at least ONE gateway");

        // if network is automode let's pick any gateway and create links between all nodes to gateway
        Topology topology = new Topology();
        for (Mote mote : motes) {
            if (!mote.isGateway()) {
                MoteLink link = new MoteLink();
                link.setSource(mote);
                link.setTarget(gateway);
                topology.getLinks().add(link);
            }
        }

        // when we save new network we need to start/restart it's emulation
        networkRepository.save(network);
        sensorNetworkService.emulateNetwork(network);
        return "redirect:list";
    }

    @RequestMapping("/remove")
    public String removeNetwork(long id) {
        Network network = networkRepository.findOne(id);

        sensorNetworkService.cancelEmulation(network);
        networkRepository.delete(network);
        return "redirect:list";
    }

    @RequestMapping("/chart")
    public ModelAndView chartNetwork(long id) {
        Network network = networkRepository.findOne(id);
        return new ModelAndView("network/chart", "network", network);
    }

}
