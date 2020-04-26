package run.bottle.app.service.impl;

import org.springframework.stereotype.Service;
import run.bottle.app.model.entity.FileInfo;
import run.bottle.app.repository.FileInfoRepository;
import run.bottle.app.service.FileInfoService;
import run.bottle.app.service.base.AbstractCrudService;

@Service
public class FileInfoServiceImpl extends AbstractCrudService<FileInfo, Integer> implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;

    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository) {
        super(fileInfoRepository);
        this.fileInfoRepository = fileInfoRepository;
    }

    @Override
    public FileInfo addFileInfo(FileInfo fileInfo) {
        return fileInfoRepository.save(fileInfo);
    }
}
