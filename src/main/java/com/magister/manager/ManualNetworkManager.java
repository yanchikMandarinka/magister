package com.magister.manager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Network;

@Service
public class ManualNetworkManager {

//    private static final Logger LOG = LoggerFactory.getLogger(ManualNetworkManager.class);

    @Transactional
    public void reorganizeTopology(Network network) {
    }

}
