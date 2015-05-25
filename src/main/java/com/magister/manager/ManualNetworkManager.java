package com.magister.manager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Network;

@Service
public class ManualNetworkManager {

//    private static final Logger LOG = LoggerFactory.getLogger(ManualNetworkManager.class);

    @Transactional
    public void reorganizeTopology(Network network) {
        //TODO: in case of broken topology have to fix it by
        // choosing closest node (don't really work because of cycles)
        // G <-> D <-> A <-> B <-> C
        // when D dies
        // G || A <-> C <-> B <-> A
        // need to find some path towards Gateway
    }

}
