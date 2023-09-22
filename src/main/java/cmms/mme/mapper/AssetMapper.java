package cmms.mme.mapper;

import cmms.mme.dto.AssetDto;
import cmms.mme.dto.PageDto;
import cmms.mme.entity.Asset;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AssetMapper {

    List<AssetDto> assetEntityToDtoList(List<Asset> assets);
    List<Asset> assetDtoToEntityList(List<AssetDto> assetDtos);
    PageDto<AssetDto> assetEntityToDtoPage(Page<Asset> assets);
    AssetDto assetEntityToDto(Asset asset);
    Asset assetDtoToEntity(AssetDto assetDto);
}
