package com.magister.network.service;

import com.magister.db.domain.Mote;
import com.magister.db.domain.Network;

public interface MoteRunnableFactory {
    Runnable createMoteRunnable(Mote mote, Network network);
}
