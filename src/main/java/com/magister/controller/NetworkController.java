package com.magister.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.Topology;
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
    public ModelAndView createNetwork() {
        return new ModelAndView("network/create", "command", new Network());
    }

    @RequestMapping("/edit")
    public ModelAndView editNetwork(long id) {
        Network network = networkRepository.findOne(id);
        return new ModelAndView("network/edit", "command", network);
    }

    @RequestMapping("/save")
    public String createNetwork(Network network) {
        networkManager.saveOrUpdateNetwork(network);
        return "redirect:list";
    }

    @RequestMapping("/saveedit")
    @Transactional
    public String editNetwork(Network network) {
        Network network2 = networkRepository.findOne(network.getId());
        network2.setEnabled(network.isEnabled());
        network2.setName(network.getName());
        network2.setStatus(network.getStatus());
        network2.setMode(network.getMode());

        List<Mote> motes = network.getMotes();
        Map<Long, Mote> motesMap = new HashMap<>();
        for (Mote mote : motes) {
            motesMap.put(mote.getId(), mote);
        }
        network2.setMotes(motes);

        Topology topology = network.getTopology();
        List<MoteLink> links = topology.getLinks();
        for (MoteLink moteLink : links) {
            moteLink.setSource(motesMap.get(moteLink.getSource().getId()));
            moteLink.setTarget(motesMap.get(moteLink.getTarget().getId()));
        }
        topologyRepository.delete(network2.getTopology());

        network2.setTopology(topology);

        networkManager.saveOrUpdateNetwork(network2);
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
