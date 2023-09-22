package cmms.mme.repository;

import cmms.mme.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT p FROM Asset p WHERE CONCAT(p.name, p.model, p.description, p.purchaseDate, p.creator.lastName, p.creator.firstName) LIKE %?1%")
    List<Asset> search(String keyword);
}
