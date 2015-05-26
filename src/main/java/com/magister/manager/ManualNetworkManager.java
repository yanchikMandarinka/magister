package com.magister.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;
import com.magister.db.domain.Network.Status;
import com.magister.db.repository.NetworkRepository;

@Service
public class ManualNetworkManager {

//    private static final Logger LOG = LoggerFactory.getLogger(ManualNetworkManager.class);

    @Autowired
    private NetworkRepository networkRepository;

    @Transactional
    public void reorganizeTopology(Network network) {
        //TODO: in case of broken topology have to fix it by
        // choosing closest node (don't really work because of cycles)
        // G <-> D <-> A <-> B <-> C
        // when D dies
        // G || A <-> C <-> B <-> A
        // need to find some path towards Gateway
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
//        Topology topology = network.getTopology();
//        List<MoteLink> links = topology.getLinks();
//        links.clear();
//        for (Mote mote : motes) {
//            if (!mote.isGateway()) {
//                MoteLink link = new MoteLink();
//                link.setSource(mote);
//                link.setTarget(gateway);
//                links.add(link);
//            }
//        }

        network.setStatus(Status.WORKING);
        networkRepository.save(network);
    }

}
