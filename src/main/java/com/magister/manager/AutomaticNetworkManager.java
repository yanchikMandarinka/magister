package com.magister.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.magister.db.domain.Mote;
import com.magister.db.domain.MoteLink;
import com.magister.db.domain.Network;
import com.magister.db.domain.Topology;
import com.magister.db.repository.NetworkRepository;

@Service
public class AutomaticNetworkManager {

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticNetworkManager.class);

    @Autowired
    private NetworkRepository networkRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean prepareNetwork(Network network) {
        network = entityManager.find(Network.class, network.getId());
        final List<Mote> motes = network.getMotes();

        if (motes.size() == 0) {
            LOG.warn("Network {} has no motes and will not be emulated", network);
            return true;
        }

        // try to find any gateway node
        Mote gateway = null;
        for (Mote mote : motes) {
            if (mote.isGateway()) {
                gateway = mote;
                break;
            }
        }

        // if we didn't find one, pick random
        // TODO: this should be removed
        if (gateway == null) {
            LOG.warn("Network {} has no gateway, marking random node as gateway", network);
            Random random = new Random();
            gateway = motes.get(random.nextInt(motes.size()));
            gateway.setGateway(true);
        }

        // network is automode let's pick any gateway and create links between all nodes to gateway
        Topology topology = new Topology();
        List<MoteLink> links = new ArrayList<>();
        for (Mote mote : motes) {
            if (!mote.isGateway()) {
                MoteLink link = new MoteLink();
                link.setSource(mote);
                link.setTarget(gateway);
                links.add(link);
                LOG.info(link.toString());
            }
        }
        topology.setLinks(links);
        network.setTopology(topology);

        networkRepository.save(network);
        return false;
    }

}
