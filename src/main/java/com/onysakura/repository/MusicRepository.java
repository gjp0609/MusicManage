package com.onysakura.repository;

import com.onysakura.model.MusicLocal;
import com.onysakura.utils.CustomLogger;

import java.util.logging.Logger;

public class MusicRepository extends BaseRepository<MusicLocal> {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicRepository.class);
    private static final MusicRepository musicRepository = new MusicRepository();

    private MusicRepository() {
        super();
    }

    public static MusicRepository getInstance() {
        return musicRepository;
    }
}
