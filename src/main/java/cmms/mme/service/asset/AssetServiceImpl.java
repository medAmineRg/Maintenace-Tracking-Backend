package cmms.mme.service.asset;

import cmms.mme.dto.AssetDto;
import cmms.mme.dto.PageDto;
import cmms.mme.dto.UserDto;
import cmms.mme.entity.AppUser;
import cmms.mme.entity.Asset;
import cmms.mme.mapper.AssetMapper;
import cmms.mme.mapper.UserMapper;
import cmms.mme.repository.AssetRepository;
import cmms.mme.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import javassist.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AccountService accountService;
    private final static AssetMapper assetMapper = Mappers.getMapper(AssetMapper.class);
    private final static UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Override
    public PageDto<AssetDto> getAsset(int st, int sz) {
        return assetMapper.assetEntityToDtoPage(assetRepository.findAll(PageRequest.of( st,  sz)));
    }

    @Override
    public List<AssetDto> getAssetByKeyword(String keyword) {
        return assetMapper.assetEntityToDtoList(assetRepository.search(keyword));
    }

    @Override
    public AssetDto getAssetById(long id) throws NotFoundException{
        return assetMapper
                .assetEntityToDto(assetRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Asset not found")));
    }

    @Override
    public AssetDto postAsset(AssetDto asset) {

        Asset savedAsset = assetMapper.assetDtoToEntity(asset);
        return assetMapper.assetEntityToDto(assetRepository.save(savedAsset));
    }

    @Override @Transactional
    public AssetDto updateAsset(AssetDto asset, long id) throws NotFoundException {

        Asset updatedAsset = assetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asset not found"));

        String name = asset.getName();
        String desc = asset.getDescription();
        String model = asset.getModel();
        LocalDate purchaseAt = asset.getPurchaseDate();
        UserDto creator = asset.getCreator();

        if(name != null && !Objects.equals(name, updatedAsset.getName())) {
            updatedAsset.setName(name);
        }

        if(desc != null && !Objects.equals(desc, updatedAsset.getDescription())) {
            updatedAsset.setDescription(desc);
        }

        if(model != null && !Objects.equals(model, updatedAsset.getModel())) {
            updatedAsset.setModel(model);
        }

        if(purchaseAt != null && !Objects.equals(purchaseAt, updatedAsset.getPurchaseDate())) {
            updatedAsset.setPurchaseDate(purchaseAt);
        }

        if(creator != null) {
            AppUser appUser = userMapper.userDtoToEntity(accountService.getUserById(creator.getId()));
            updatedAsset.setCreator(appUser);
        }

        return assetMapper.assetEntityToDto(updatedAsset);
    }

    @Override
    public void deleteAsset(List<Long> listIDs) {

        assetRepository.deleteAllById(listIDs);
    }
}
