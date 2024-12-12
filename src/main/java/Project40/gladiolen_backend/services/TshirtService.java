package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.models.Tshirt;
import Project40.gladiolen_backend.repositories.TshirtRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TshirtService {

    private final TshirtRepository tshirtRepository;

    @Transactional
    public Tshirt createTshirt(Tshirt tshirt) {
        Tshirt newTshirt = Tshirt.builder()
                .size(tshirt.getSize())
                .sex(tshirt.getSex())
                .job(tshirt.getJob())
                .quantity(tshirt.getQuantity())
                .build();
        return tshirtRepository.save(newTshirt);
    }

    public Tshirt getTshirtById(Long id) {
        return tshirtRepository.findById(id).orElseThrow(() -> new RuntimeException("Tshirt not found"));
    }

    @Transactional
    public void updateTshirt(Long id, Tshirt tshirt) {
        Tshirt existingTshirt = tshirtRepository.findById(id).orElseThrow(() -> new RuntimeException("Tshirt not found"));
        existingTshirt.setSize(tshirt.getSize());
        existingTshirt.setSex(tshirt.getSex());
        existingTshirt.setJob(tshirt.getJob());
        existingTshirt.setQuantity(tshirt.getQuantity());
        tshirtRepository.save(existingTshirt);
    }

    public void deleteTshirt(Long id) {
        tshirtRepository.deleteById(id);
    }
}
