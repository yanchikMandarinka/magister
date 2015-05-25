package com.magister.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.Topology;
import com.magister.db.domain.Network.Status;
import com.magister.db.repository.NetworkRepository;

@Service
public class AutomaticNetworkManager {

//    private static final Logger LOG = LoggerFactory.getLogger(AutomaticNetworkManager.class);

    @Autowired
    private NetworkRepository networkRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void reorganizeTopology(Network network) {
        List<Mote> motes = network.getMotes();

        if (motes.size() == 0) {
            network.setStatus(Status.NO_LIVE_MOTES);
            networkRepository.save(network);
            return;
        }

        Mote gateway = NetworkManager.findLiveGateway(network);
        if (gateway == null) {
            network.setStatus(Status.NO_LIVE_GATEWAYS);
            networkRepository.save(network);
            return;
        }

        // pick any gateway and create links between all nodes to gateway
        Topology topology = new Topology();
        List<MoteLink> links = new ArrayList<>();
        for (Mote mote : motes) {
            if (!mote.isGateway()) {
                MoteLink link = new MoteLink();
                link.setSource(mote);
                link.setTarget(gateway);
                links.add(link);
            }
        }
        topology.setLinks(links);
        network.setTopology(topology);

        network.setStatus(Status.WORKING);
        networkRepository.save(network);
    }

}
