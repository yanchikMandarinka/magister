package com.magister.network.service.auto;

import com.magister.db.domain.Mote;

public interface NodeRunnableFactory {
    Runnable createGatewayRunnable(Mote mote);
}
