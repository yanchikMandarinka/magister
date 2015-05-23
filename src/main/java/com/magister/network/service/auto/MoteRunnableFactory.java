package com.magister.network.service.auto;

import com.magister.db.domain.Mote;

public interface MoteRunnableFactory {
    Runnable createMoteRunnable(Mote mote);
}
