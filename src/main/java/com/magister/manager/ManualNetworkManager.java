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

        // build the graph and check it's connectivity, i.e.
        // all vertexes can be reached starting from gateway node

        network.setStatus(Status.WORKING);
        networkRepository.save(network);
    }

}
