package com.onysakura.repository;

import com.onysakura.model.MusicLocal;
import com.onysakura.utils.CustomLogger;

public class MusicRepository extends BaseRepository<MusicLocal> {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicRepository.class);
    private static final MusicRepository REPOSITORY = new MusicRepository();

    private MusicRepository() {
        super(null);
    }

    public static MusicRepository getInstance() {
        return REPOSITORY;
    }
}
