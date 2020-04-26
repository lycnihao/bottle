package run.bottle.app.service;


import run.bottle.app.model.entity.FileInfo;
import run.bottle.app.service.base.CrudService;

public interface FileInfoService extends CrudService<FileInfo, Integer> {
    FileInfo addFileInfo(FileInfo fileInfo);
}
