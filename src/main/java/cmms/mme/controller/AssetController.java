package cmms.mme.controller;


import cmms.mme.dto.APIResponse;
import cmms.mme.dto.AssetDto;
import cmms.mme.dto.PageDto;
import cmms.mme.service.asset.AssetService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("v1/asset") @RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    @GetMapping
    public APIResponse<PageDto<AssetDto>> getAsset(@RequestParam(name ="start", required = false) Integer start, @RequestParam(name="size", required = false) Integer size) {
        int st = (start != null) ? start : 0;
        int sz = (size != null) ? size : 10;
        PageDto<AssetDto> asset = assetService.getAsset(st, sz);
        return new APIResponse<>(asset.getSize(), asset);
    }

    @GetMapping(path = "/search")
    public List<AssetDto> getAssetByKeyword(@RequestParam(name="keyword", required = false) String keyword) {
        return assetService.getAssetByKeyword(keyword);
    }

    @GetMapping(path = "/{id}")
    public AssetDto getAssetById(@PathVariable("id") long id) throws NotFoundException {
        return assetService.getAssetById(id);
    }

    @PostMapping
    public AssetDto postAsset(@Valid @RequestBody AssetDto asset) {

        return assetService.postAsset(asset);
    }

    @PutMapping(path = "/{id}")
    public AssetDto updateAsset(@Valid @RequestBody AssetDto asset, @PathVariable("id") long id) throws NotFoundException {
        return assetService.updateAsset(asset, id);
    }

    @DeleteMapping
    public void deleteAsset(@RequestParam(name = "id") List<Long> listIDs) throws NotFoundException {
        assetService.deleteAsset(listIDs);
    }
}
