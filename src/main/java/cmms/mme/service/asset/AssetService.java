package cmms.mme.service.asset;

import cmms.mme.dto.AssetDto;
import cmms.mme.dto.PageDto;
import javassist.NotFoundException;

import java.util.List;

public interface AssetService {
    PageDto<AssetDto> getAsset(int st, int sz);

    List<AssetDto> getAssetByKeyword(String keyword);

    AssetDto getAssetById(long id) throws NotFoundException;

    AssetDto postAsset(AssetDto asset);

    AssetDto updateAsset(AssetDto asset, long id) throws NotFoundException;

    void deleteAsset(List<Long> listIDs);
}
